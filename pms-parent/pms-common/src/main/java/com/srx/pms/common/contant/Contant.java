package com.srx.pms.common.contant;

public class Contant {
	public interface Session{
		String KEY_SESSION_USER = "com.srx.pms.session.user";
		String KEY_SESSION_LOGIN_STATUS = "loginStatus";
		String KEY_SESSION_MENU = "menus";
		String KEY_SESSION_WALLPAPERS = "wallpapers";
		String KEY_SESSION_DEFAULT_WALLPAPER = "defaultWallpaper";
		String KEY_SESSION_DEFAULT_START_ICON = "defaultDesktopStartIcon";
		String KEY_SESSION_USER_SALT = "sessionUserSalt";
	}
	public enum Message{
		SUCCESS(10000, "操作成功"),
		ERROR(20000, "系统错误"),
	    BUSINESS_ERROR(21000, "系统业务处理错误"),
	    UNKNOWN_ACCOUNT(21001, "用户名不存在"),
	    FORBIDDEN_ACCOUNT(21002, "账户已禁用"),
	    
	    PASSWORD_INCORRECT(21003, "密码错误"),
	    VERIFY_CAPTCHA_ERROR(21004, "验证码错误,请重新刷新并滑动验证码!"),
	    UNAUTHORIZED(21005, "无操作权限"),
	    CAN_NOT_EDIT(21006, "该条记录无法编辑"),
	    UNAUTHENTICATED(21007, "未登录"),
	    FORBIDDEN_IP(21008, "非法请求"),
	    NOT_FOUND_URL(21009, "url不存在"),
	    PARAM_FORMAT_ERROR(21010, "参数格式错误"),
	    PARAM_ERROR(21011, "参数错误"),
	    MISSING_PARAMETER(21012, "缺少参数"),
	    NAME_ALREADY_EXIST(21013, "该名称已存在"),
	    DATA_NOT_EXIST(21014, "该记录不存在"),
	    LOGIN_NAME_ALREADY_EXIST(21015, "该登录名已存在"),
	    CODE_ALREADY_EXIST(21016, "该编码已存在"),
	    FULLNAME_ALREADY_EXIST(21017, "该全称已存在"),
	    LOGINNAME_OR_PASSWORD_INCORRECT(21018, "用户名或密码错误"),
	    LOGIN_ACCOUNT_EXCEPTION(21019, "账户异常,请联系管理员"),
		DATA_ACCESS_ERROR(22000, "系统数据处理错误"),
		FAILE(30000, "操作失败"),
		EXCEL_PARSE_ERROR(40000, "EXCEL解析错误"),
		EXCEL_WRITE_ERROR(40001, "EXCEL生成错误");
	    private int code;
	    private String msg;

	    Message(int code, String msg) {
	        this.code = code;
	        this.msg = msg;
	    }

	    @Override
	    public String toString() {
	        return "Response{" + "code=" + code + ", msg='" + msg + '\'' + '}';
	    }

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        this.code = code;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	    }
	}
}
