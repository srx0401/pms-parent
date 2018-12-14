package com.srx.pms.system.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.component.Result;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.controller.BaseController;
import com.srx.pms.module.user.service.UserService;
import com.srx.pms.system.model.entity.DataDictionary;
import com.srx.pms.system.model.entity.Resource;
import com.srx.pms.system.model.querybean.DataDictionaryQueryBean;
import com.srx.pms.system.service.DataDictionaryService;
import com.srx.pms.system.service.ResourceService;
import com.srx.pms.user.model.entity.User;


@SuppressWarnings("rawtypes")
@Controller
public class SystemController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(SystemController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private ResourceService resourceService;
	@ResponseBody
    @RequestMapping(value = "/loadDict.do")
    public Result loadDict(DataDictionaryQueryBean q) throws Exception {
		try {
//	        return Result.successed(dataDictionaryService.loadDictByDataTypeCode(dataTypeCode));
			List<DataDictionary> res = dataDictionaryService.query(q);
			return Result.successed(res);
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "login")
    public Result login(User u) throws Exception {
		try {
			LOG.info("login:" + u.getLoginName());
			User sessionUser = userService.login(u.getLoginName(), u.getPassword());
			getSession().setAttribute(Contant.Session.KEY_SESSION_USER, sessionUser);
			getSession().setAttribute(Contant.Session.KEY_SESSION_LOGIN_STATUS, UUID.randomUUID());
			
			List<Resource> menus = resourceService.loadMenus();
			getSession().setAttribute(Contant.Session.KEY_SESSION_MENU, JSON.toJSONString(menus));
	        return Result.successed(menus);
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "loadMenus")
    public Result loadMenus() throws Exception {
		try {
	        return Result.successed(resourceService.loadMenus());
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "loadWallpapers")
    public Result loadWallpapers() throws Exception {
		try {
			Object wallpapers = getSession().getAttribute(Contant.Session.KEY_SESSION_WALLPAPERS);
			getRequest().setAttribute(Contant.Session.KEY_SESSION_WALLPAPERS, JSON.toJSONString(wallpapers));
			if (wallpapers == null) {
				wallpapers = dataDictionaryService.loadWallpapers();
			}
	        return Result.successed(wallpapers);
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
    @RequestMapping(value = "home")
    public String home() throws Exception {
		try {
			getRequest().setAttribute(Contant.Session.KEY_SESSION_USER_SALT, getSessionUser().getSalt());
			DataDictionary defaultWallpaper = dataDictionaryService.loadDefaultWallpaper();
			if (defaultWallpaper != null && StringUtils.hasLength(defaultWallpaper.getCode())) {
				String dwc = defaultWallpaper.getCode();
				getRequest().setAttribute(Contant.Session.KEY_SESSION_DEFAULT_WALLPAPER, dwc.substring(dwc.indexOf(".") + 1));
			}
			DataDictionary defaultStartIcon = dataDictionaryService.loadDefaultDesktopStartBtnIcon();
			if (defaultStartIcon != null && StringUtils.hasLength(defaultStartIcon.getCode())) {
				String dwc = defaultStartIcon.getCode();
				getRequest().setAttribute(Contant.Session.KEY_SESSION_DEFAULT_START_ICON, dwc.substring(dwc.indexOf(".") + 1));
			}
		} catch (PMSException e) {
			if (e.getMsg() != null && !e.getMsg().equals(Contant.Message.UNAUTHENTICATED)) {
				LOG.error(e.getMessage(),e);
			}
		}
		return "home";
    }
    /**
     * 閫�鍑�
     *
     * @return
     */
    @RequestMapping(value = "logout")
    public String logout() {
    	getSession().removeAttribute(Contant.Session.KEY_SESSION_USER);
    	getSession().removeAttribute(Contant.Session.KEY_SESSION_LOGIN_STATUS);
    	getSession().removeAttribute(Contant.Session.KEY_SESSION_MENU);
    	return "redirect:/home.do";
    }
}