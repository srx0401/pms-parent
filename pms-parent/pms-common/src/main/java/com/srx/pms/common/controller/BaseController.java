package com.srx.pms.common.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.user.model.entity.User;
import com.srx.pms.util.date.DateUtil;
import com.srx.pms.util.session.SessionUtil;
//@Controller
public class BaseController<T extends BaseEntity, Q extends BaseQueryBean<T>> {
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	@ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.req = request;  
        this.resp = response;  
        this.session = request.getSession();  
    }
	protected HttpServletRequest getRequest() {
		return this.req;
	}
	protected HttpServletResponse getResponse() {
		return this.resp;
	}
	protected HttpSession getSession() {
		return this.session;
	}
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtil.DATE_TIME_FORMATER, true));
	}
	protected User getSessionUser() throws PMSException{
		Object _u = SessionUtil.getSessionAttribute(Contant.Session.KEY_SESSION_USER);
		if (_u == null) {
			throw new PMSException(Contant.Message.UNAUTHENTICATED);
		}
		return (User) _u;
	}
}
