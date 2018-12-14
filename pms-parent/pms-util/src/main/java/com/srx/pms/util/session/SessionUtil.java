package com.srx.pms.util.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {
//	private static final Logger LOG = LoggerFactory.getLogger(SessionUtil.class);
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes == null ? null : requestAttributes.getRequest();
	}

	public static HttpSession getSession() {
		return getRequest().getSession(false);
	}

	public static String getRealRootPath() {
		return getRequest().getServletContext().getRealPath("/");
	}

	public static String getIp() {
		return getRequest().getRemoteAddr();
	}

	public static Object getRequestAttribute(String name) {
		return getRequest().getAttribute(name);
	}

	public static void setRequestAttribute(String name, Object value) {
		getRequest().setAttribute(name, value);
	}

	public static String getContextPath() {
		return getRequest().getContextPath();
	}

	public static void removeSessionAttribute(String name) {
		getRequest().getSession().removeAttribute(name);
	}

	public static Object getSessionAttribute(String name) {
		return getRequest().getSession().getAttribute(name);
	}

	public static void setSessionAttribute(String name, Object value) {
		getRequest().getSession().setAttribute(name, value);
	}
}
