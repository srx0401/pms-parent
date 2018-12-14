package com.srx.pms.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.srx.pms.common.annotation.ExcelAnnotationFactory;
import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.mapper.BaseMapper;
import com.srx.pms.common.model.dataobject.Page;
import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.common.service.BaseService;
import com.srx.pms.user.mapper.UserMapper;
import com.srx.pms.user.model.entity.User;
import com.srx.pms.user.model.querybean.UserQueryBean;
import com.srx.pms.util.BooleanUtil;
import com.srx.pms.util.file.ExcelSheet;
import com.srx.pms.util.file.ExcelTitleCell;
import com.srx.pms.util.file.POIUtil;
import com.srx.pms.util.security.MD5Util;
import com.srx.pms.util.session.SessionUtil;

@Transactional
public class BaseServiceImpl<T extends BaseEntity, Q extends BaseQueryBean<T>> implements BaseService<T, Q> {
	private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);
	private BaseMapper<T, Q> baseMapper;
	@Autowired
	private UserMapper userMapper;
	public void setBaseMapper(BaseMapper<T, Q> baseMapper) {
		this.baseMapper = baseMapper;
	}
	@Override
	public boolean save(T t,User user) throws PMSException {
 		return saveData(t, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	@Override
	public boolean remove(String id,User user) throws PMSException {
		return removeData(id, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	@Override
	public boolean remove(Q q, User user)throws PMSException {
		return removeData(q, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	@Override
	public List<T> query(Q q,User user) throws PMSException {
		return queryData(q, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	@Override
	public T load(String id,User user) throws PMSException {
		return loadData(id, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	@Override
	public Page<T> list(Q q,User user) throws PMSException {
		return listData(q, validateUserWithUserNameAndPwd(user.getLoginName(), user.getPassword()));
	}
	protected void validateUserWithIdNotNull(User sessionUser) {
		if (sessionUser == null || StringUtils.isEmpty(sessionUser.getId())) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[user]不能为空!");
		}
	}
	protected User validateUserWithUserNameAndPwd(String userName,String password) {
		List<User> users = null;
		try {
			users = userMapper.select(new UserQueryBean(userName,MD5Util.MD5(userName +"_"+ password)));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
		if (users == null || users.size() < 1) {
			throw new PMSException(Contant.Message.LOGINNAME_OR_PASSWORD_INCORRECT);
		}
		return users.get(0);
	}
	protected boolean saveData(T t,User user) throws PMSException {
 		if (t == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[t]不能为空!");
		}
 		validateUserWithIdNotNull(user);
		try {
			String userId = StringUtils.isEmpty(t.getCreateUserId()) ? user.getId() : t.getCreateUserId();
			if (StringUtils.isEmpty(t.getId())) {
				t.setCreateTime(new Date());
				t.setCreateUserId(userId);
				t.setValid(1);
				return baseMapper.insert(t) > 0;
			}
			t.setUpdateTime(new Date());
			t.setUpdateUserId(userId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(e.getMessage());
		}
		try {
			return baseMapper.update(t) > 0;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	protected List<T> queryData(Q q,User user) throws PMSException {
		if (q == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[q]不能为空!");
		}
		validateUserWithIdNotNull(user);
		if (!BooleanUtil.parseBoolean(user.getIsRoot())) {
			q.setCreateUserId(user.getId());
		}
		try {
			return baseMapper.select(q);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	protected T loadData(String id,User user) throws PMSException {
		if (StringUtils.isEmpty(id)) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[id]不能为空!");
		}
		validateUserWithIdNotNull(user);
		T res = null;
		try {
			res = baseMapper.load(id);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
		if (BooleanUtil.parseBoolean(user.getIsRoot()) || res.getCreateUserId().equals(user.getId())) {
			return res;
		}
		throw new PMSException(Contant.Message.UNAUTHORIZED.name());
	}
	protected Page<T> listData(Q q,User user) {
		if (q == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[q]不能为空!");
		}
		validateUserWithIdNotNull(user);
		if (!BooleanUtil.parseBoolean(user.getIsRoot())) {
			q.setCreateUserId(user.getId());
		}
		Page<T> res = new Page<T>(q.getPage(),q.getStart(),q.getLimit());
//		PageHelper.startPage(p.getPage(), p.getSize(), q.getOrder());
		try {
			Long count = baseMapper.count(q);
			if (count != null && count.intValue() > 0) {
				List<T> list = baseMapper.select(q);
				res.setList(list);
				res.setTotal(count);
			}
			return res;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	protected boolean removeData(String id,User user) throws PMSException {
		if (StringUtils.isEmpty(id)) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[id]不能为空!");
		}
		validateUserWithIdNotNull(user);
		try {
			T res = baseMapper.load(id);
			if (res.getCreateUserId().equals(user.getId())) {
				return baseMapper.delete(id) > 0;
			}
			throw new PMSException(Contant.Message.UNAUTHORIZED);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	
	protected boolean removeData(Q q, User user)
			throws PMSException {
		if (q == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[q]不能为空!");
		}
		List<String> ids = q.getIds();
		if (ids == null || ids.size() < 1) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[q.ids]不能为空!");
		}
		validateUserWithIdNotNull(user);
		try {
			q.setLimit(Long.MAX_VALUE);
			q.setStart(0l);
			List<T> res = baseMapper.select(q);
			if (res != null && res.size() > 0) {
				for (T t : res) {
					if (!t.getCreateUserId().equals(user.getId())) {
						throw new PMSException(Contant.Message.UNAUTHORIZED);
					}
				}
			}
			return baseMapper.batchDelete(ids) > 0;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	@Override
	public boolean save(T t) throws PMSException {
		return saveData(t, getSessionUser());
	}
	
	@Override
	public List<String> importExcel(MultipartFile file,Class<T> t)
			throws PMSException {
		if (file == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[file]不能为空!");
		}
		if (t == null) {
			throw new PMSException(Contant.Message.MISSING_PARAMETER.name() + ",入参[t]不能为空!");
		}
		List<String> res = new ArrayList<>();
		try {
			List<Map<String,String>> data = POIUtil.readExcel(file);
			int errorCount = 0;
			int faileCount = 0;
			T obj = null;
			for (int i = 0; i < data.size(); i++) {
				try {
					obj = ExcelAnnotationFactory.parse(data.get(i), t);
				} catch (Exception e) {
					LOG.error(e.getMessage());
					res.add("第" + (i + 1) + "行解析错误:" + e.getMessage());
					errorCount ++;
//					throw new PMSException(Contant.Message.EXCEL_PARSE_ERROR);
				}
				if (obj != null) {
					if (!save(obj)) {
						faileCount ++;
						res.add("第" + (i + 1) + "行保存失败");
					}
				}
			}
			res.add(0, "本次导入共计["+data.size()+"],成功["+(data.size() - faileCount - errorCount)+"],失败["+(errorCount + faileCount)+"];");
			return res;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.EXCEL_PARSE_ERROR);
		}
	}
	@Override
	public boolean remove(String id) throws PMSException {
		return removeData(id, getSessionUser());
	}
	@Override
	public boolean remove(Q q) throws PMSException {
		return removeData(q, getSessionUser());
	}
	@Override
	public List<T> query(Q q) throws PMSException {
		return queryData(q,getSessionUser());
	}
	@Override
	public T load(String id) throws PMSException {
		return loadData(id,getSessionUser());
	}
	@Override
	public Page<T> list(Q q) throws PMSException {
		return listData(q,getSessionUser());
	}
	/**
	 * 	从SESSION中取User
	 * @return
	 */
	protected User getSessionUser() {
		Object _u = SessionUtil.getSessionAttribute(Contant.Session.KEY_SESSION_USER);
		if (_u == null) {
			throw new PMSException(Contant.Message.UNAUTHENTICATED);
		}
		return (User) _u;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void downloadTemplate(Class clazz, HttpServletRequest req,
			HttpServletResponse resp) throws PMSException {
		ExcelSheet [] sheets = {getExcelSheet(clazz)};
		try {
			if (sheets != null && sheets.length > 0) {
				String fileName = sheets[0].getName();
				POIUtil.writeExcel(fileName, sheets, req, resp);
			}
		} catch (Exception e) {
			throw new PMSException(Contant.Message.EXCEL_WRITE_ERROR);
		}
	}
	@SuppressWarnings("rawtypes")
	protected ExcelSheet getExcelSheet(Class clazz) {
		String fileName = null;
		try {
			fileName = ExcelAnnotationFactory.getExcelFileName(clazz);
			List<com.srx.pms.common.component.file.excel.ExcelTitleCell> titleCells = ExcelAnnotationFactory.getObjectTitleCellArray(clazz, null);
			List<ExcelTitleCell> titleCellList = new ArrayList<ExcelTitleCell>();
			for (com.srx.pms.common.component.file.excel.ExcelTitleCell cell : titleCells) {
				titleCellList.add(new ExcelTitleCell(cell.getCode(), cell.getName(), cell.getComment(), cell.getSort(), cell.getExampleValue(), cell.getType()));
			}
			ExcelTitleCell[]titleCellArray =new ExcelTitleCell[titleCellList.size()];
			titleCellList.toArray(titleCellArray);
			return new ExcelSheet(fileName,titleCellArray);
		} catch (Exception e) {
			throw new PMSException(Contant.Message.EXCEL_PARSE_ERROR);
		}
	}
}