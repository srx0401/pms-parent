package com.srx.pms.common.component;

import com.srx.pms.common.contant.Contant.Message;


public class PMSException extends RuntimeException {
	private Message msg;
	private String info;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7199091443703897292L;

	public PMSException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PMSException(String message, Throwable cause) {
		super(message, cause);
	}

	public PMSException(String message) {
		super(message);
	}

	public PMSException(Throwable cause) {
		super(cause);
	}

	public PMSException(Message msg) {
		super(msg.getMsg());
		this.msg = msg;
	}

	public PMSException(Message msg, String info) {
		super(msg.getMsg()  + ":" + info);
		this.msg = msg;
		this.info = info;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
