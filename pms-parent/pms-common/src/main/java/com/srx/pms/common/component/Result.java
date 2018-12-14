package com.srx.pms.common.component;

import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.contant.Contant.Message;

public class Result {
	private boolean success = false;
	private int code;
    private String msg;
    private String info;
    private Object data = "";
    private String key;
	public boolean getSuccess() {
		return success;
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public String getInfo() {
		return info;
	}
	public Object getData() {
		return data;
	}
	
	public String getKey() {
		return key;
	}
	public Result(boolean success,Message message ,String info, Object data) {
		super();
		this.success = success;
		this.code = message.getCode();
		this.msg = message.getMsg();
		this.info = info;
		this.data = data;
		this.key = message.name();
	}
	
	public Result(Message message,String info, Object data) {
		this(true,message,info,data);
	}
	public Result(Message message,String info) {
		this(true,message,info,null);
	}
	public Result(Message message,Object data) {
		this(true,message,"",data);
	}
	public Result(Message message) {
		this(true,message,"","");
	}
	public static Result successed(){
		return new Result(Contant.Message.SUCCESS);
	}
	public static Result successed(Object data){
		return new Result(Contant.Message.SUCCESS,data);
	}
	public static Result failed(){
		return new Result(false,Contant.Message.FAILE,null,null);
	}
	public static Result failed(String info){
		return new Result(false,Contant.Message.FAILE,info,null);
	}
	public static Result failed(Contant.Message msg){
		return new Result(false,msg,msg.getMsg(),null);
	}
	public static Result failed(Contant.Message msg,String info){
		return new Result(false,msg,info,null);
	}
	public static Result error(){
		return new Result(false,Contant.Message.ERROR,null,null);
	}
	public static Result error(String info){
		return new Result(false,Contant.Message.ERROR,info,null);
	}
	public static Result error(Contant.Message msg){
		return new Result(false,msg,msg.getMsg(),null);
	}
	public static Result error(Contant.Message msg,String info){
		return new Result(false,msg,info,null);
	}
}
