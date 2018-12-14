<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/*
	if(request.getAttribute("menus") == null){
		response.sendRedirect(request.getContextPath());
	}
*/
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SRX</title>  
  	<script src="util/security/AES.js"></script>
	<script src="util/security/sha256.js"></script>
	<script src="util/security/Base64.js"></script>
	<script src="util/security/MD5.js"></script>
	<script src="util/security/pad-zeropadding-min.js"></script>
<!--         <link rel="stylesheet" type="text/css" href="plugins/extjs/4.1/resources/css/ext-all.css" />   -->
<!--         <link rel="stylesheet" type="text/css" href="plugins/extjs/4.1/resources/css/desktop.css" />   -->
<!--         <script type="text/javascript" src="plugins/extjs/4.1/ext-all.js"></script>   -->
<!--         <script type="text/javascript" src="plugins/extjs/4.1/locale/ext-lang-zh_CN.js"></script> -->

        <link rel="stylesheet" type="text/css" href="plugins/extjs/ext-4.2.1.883/resources/css/ext-all.css" />  
        <link rel="stylesheet" type="text/css" href="plugins/extjs/ext-4.2.1.883/resources/css/desktop.css" />  
        <script type="text/javascript" src="plugins/extjs/ext-4.2.1.883/ext-all.js"></script>  
        <script type="text/javascript" src="plugins/extjs/ext-4.2.1.883/locale/ext-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="app/resources/css/modules.css" />  
        <script type="text/javascript" src="app/common/constant/Store.js"></script>
        
        <script type="text/javascript" src="app/interceptor/AbstractInterceptor.js"></script>
        <script type="text/javascript" src="app/interceptor/ExceptionInterceptor.js"></script>
        <script type="text/javascript" src="app/ExtAjax.js"></script>
<!--         <script type="text/javascript" src="app/ux/ComboboxTree.js"></script>   -->
  	<style type="text/css">
  		
  	</style>
        <script type="text/javascript">
        Ext.Ajax.addInterceptor('App.web.ExceptionInterceptor');
        debugger;
        	var __ctxPath = "<%=request.getContextPath()%>";
        	var __TIP_TITLE = "温馨提示";
        	var __TIP_FAILURE_CONTENT = "系统请求故障,请联系管理员.";
        	var __SESSION_USER_SALT = "${sessionUserSalt}";
        	debugger;
        	var __menus = "";
        	Ext.QuickTips.init();
            Ext.Loader.setConfig({enabled:true});  
            Ext.Loader.setPath({
                'Ext.ux.desktop': 'app/basic',
                'App.extend': 'app/extend-component',
                'App.modules': 'app/modules', 
                'App.util': 'app/util',
                'MyDesktop': 'app'
            });  
            Ext.require('MyDesktop.App');  
            
            Ext.require('Ext.util.Cookies');  
            Ext.require('App.util.Security');  
            Ext.onReady(function () {
            	debugger;
            	if("${loginStatus}"){
            		toHome();
            	}else{
            		toLogin();
            	}
            });
            function EXT_AJAX_CALLBACK_FAILURE(resp,opt){
            	FN_error(__TIP_FAILURE_CONTENT);
            }
            function EXT_ALERT(cfg){
            	debugger;
            	var config = Ext.apply({title:__TIP_TITLE,buttons:Ext.MessageBox.OK,msg:"程序错误,请联系管理员.",icon:Ext.MessageBox.INFO,fn:Ext.emptyFn},cfg);
            	Ext.MessageBox.show(config);
            }
            function FN_alert(msg){
            	EXT_ALERT({icon:Ext.MessageBox.INFO,buttons:Ext.MessageBox.OK,msg:msg});
            }
            function FN_warning(msg){
            	EXT_ALERT({icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK,msg:msg});
            }
            function FN_error(msg){
            	EXT_ALERT({icon:Ext.MessageBox.ERROR,buttons:Ext.MessageBox.OK,msg:msg});
            }
            function EXT_AJAX_ERROR(json,callback){
            	var cfg = {
            			title:__TIP_TITLE,
            			msg : "请求错误,请稍候再试.",
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
            	}
            	if(json){
	        		if(json.code == "20000"){
	        			Ext.MessageBox.confirm(__TIP_TITLE,"登录超时,立即登录?",function(btn){
			        		if(btn == 'yes'){
			        			location.href = __ctxPath;
			        		}  
			        	});
	        			return;
	        		}
            	}
            	Ext.Msg.show(cfg);
            	if(callback){
            		callback();
            	}
            }
            function toHome(){
            	var menus = loadMenus();
            	var wallpapers = loadWallpapers();
            	var defaultWallpaper = "${defaultWallpaper}";
        		if(menus){
        			Ext.create('MyDesktop.App', {_menus:menus,wallpapers:wallpapers,defaultWallpaper:defaultWallpaper,defaultDesktopStartIcon:"${defaultDesktopStartIcon}"});
        		}
            }
            function loadMenus(){
            	debugger;
            	var res;
            	Ext.Ajax.request({
            		async: false,
					url : __ctxPath+"/loadMenus.do",
					method : "POST",
					success : function(o, c) {
						res = Ext.decode(o.responseText).data;
					}
				});
            	return res;
            }
            function loadWallpapers(){
            	debugger;
            	var res;
            	Ext.Ajax.request({
            		async: false,
					url : __ctxPath+"/loadWallpapers.do",
					method : "POST",
					//timeout : 30000,// 30秒超时,
					//waitTitle : "请稍候",
					//waitMsg : "正在提交请求，请稍候......",
					success : function(o, c) {
						res = Ext.decode(o.responseText).data;
					}
				});
            	return res;
            }
            function toLogin(){
            	var loginForm =  new Ext.form.FormPanel({
            		id:"loginForm",
	    			anchor:"100% 100%",
	                flex:1,
	                xtype: 'form',
	                frame: true,listeners : {  
	                	afterRender: function(thisForm, options){  
	                        this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {  
	                            enter: function(){  
	                            	login();
	                            },  
	                            scope: this  
	                        }); 
	                	}
		            },
	                items: [new Ext.form.FieldSet({
	            		collapsible : false,
	            		border:false,
	            		items : [ {
	            			layout : 'column',
	            			border : false,
	            			defaults:{
	            				columnWidth:1.0,layout:'form',border:false
	            			},
	            			items:[
	        			       {items:[{html:"<img src='app/resources/icon/login-logo.gif'>",height:160}]},
	        			       {items:[{name:'loginName',xtype:'textfield',allowBlank:false,emptyText: '请输入账号'}]},
	        			       {items:[{name:'password',xtype:'textfield',inputType:'password',allowBlank:false,emptyText: '请输入密码'}]}
	        			    ]
	            		}]
	            	})],buttons:[{text:"登 &nbsp;&nbsp;&nbsp;陆",pressed:true,handler:login}],buttonAlign:'center'
	            });
              	function login(){
              		if(loginForm.getForm().isValid()){
              			debugger;
              			var data = loginForm.getForm().getValues();
              			var md5pwd = App.util.Security.encryptByMD5(data["password"]);
              			debugger;
              			data["password"] = md5pwd;
              			Ext.Ajax.request({
            				url : "login.do",
            				method : "POST",
            				params:data,
            				timeout : 30000,// 30秒超时,
            				waitTitle : __TIP_TITLE,
            				waitMsg : "正在登陆...",
            				success : function(o, c) {
            					var res = Ext.decode(o.responseText);
            					if(res && res.success){
            						location.href=__ctxPath+"/home.do";
            					}
            				},  
                            failure:function(response, opts){
                            	console.log(response,opts);
                                Ext.Msg.alert(__TIP_TITLE,"error");
                            } 
            			});
              			/*
	              		loginForm.getForm().submit({  
	                        url: "login.do",  
	                        submitEmptyText: false,  
	                        waitTitle:'请等待',  
	                        waitMsg: '正在登陆...',  
	                        success:function(form,action){
	                    		location.href=__ctxPath+"/home.do";
	                        },  
	                        failure:function(form,action){
	                            Ext.Msg.alert(__TIP_TITLE,action.result.info);
	                        }  
	                    }); 
              			*/
              		}
              	}
                var loginWin = new Ext.Window({  
                    id:"loginWin",
                    width: 390,  
                    height: 280,  
                    layout: 'absolute',  
                    closable: false,  
                    draggable: false,  
                    resizable: false,  
                    shadow: false,  
                    border: false,  
                    items: [loginForm]
                });  
              
                loginWin.show();  
              
                //  添加浏览器缩放自动居中效果  
                Ext.EventManager.onWindowResize(function () {  
                	loginWin.center();  
                });
            }
            
        </script>  
    </head>  
    <body style="background-image: url(app/resources/wallpaper/${defaultWallpaper});background-size:cover;">
    </body>
</html>  