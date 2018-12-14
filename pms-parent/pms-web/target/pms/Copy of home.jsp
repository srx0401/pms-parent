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
  
        <link rel="stylesheet" type="text/css" href="plugins/extjs/4.1/resources/css/ext-all.css" />  
        <link rel="stylesheet" type="text/css" href="plugins/extjs/4.1/resources/css/desktop.css" />  
        <link rel="stylesheet" type="text/css" href="app/resources/css/modules.css" />  
  
        <script type="text/javascript" src="plugins/extjs/4.1/ext-all.js"></script>  
        <script type="text/javascript" src="plugins/extjs/4.1/locale/ext-lang-zh_CN.js"></script>
        <script type="text/javascript" src="app/common/constant/Store.js"></script>
<!--         <script type="text/javascript" src="app/ux/ComboboxTree.js"></script>   -->
  	<style type="text/css">
  		
  	</style>
        <script type="text/javascript">
        debugger;
        	var __ctxPath = "<%=request.getContextPath()%>";
        	debugger;
        	var __menus = "";
        	
            Ext.Loader.setConfig({enabled:true});  
            Ext.Loader.setPath({
                'Ext.ux.desktop': 'app/basic',
                'App.extend': 'app/extend-component',
                'App.modules': 'app/modules', 
                'MyDesktop': 'app'
            });  
            Ext.require('MyDesktop.App');  
            Ext.require('Ext.util.Cookies');  
            Ext.onReady(function () {
            	debugger;
            	if("${loginStatus}"){
            		toHome();
            	}else{
            		toLogin();
            	}
            });
            function toHome(){
            	Ext.Ajax.request({
                    url: 'loadMenus.do',
                    method: 'POST',
                    success: function (response, options) {
                        var res = Ext.decode(response.responseText);
                        __menus = Ext.decode(res.data);
                		if(__menus){
                			Ext.create('MyDesktop.App', {menus:__menus});
                		}else{
                			Ext.Msg.alert('提示',"请联系管理员");
                		}
                    },
                    failure: function (response, options) {
                        Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
                    }
                });
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
	              		loginForm.getForm().submit({  
	                        url: "login.do",  
	                        submitEmptyText: false,  
	                        waitTitle:'请等待',  
	                        waitMsg: '正在登陆...',  
	                        success:function(form,action){
	                        	console.log(action);
	                        	debugger;
	                        	var res = action.result;
	                        	loginWin.close();
	                    		__menus = res.data;
	                    		if(__menus && __menus.length > 0){
	                    			Ext.create('MyDesktop.App', {menus:__menus});
	                    		}else{
	                    			Ext.Msg.alert('提示',"请联系管理员");
	                    		}
	                        },  
	                        failure:function(form,action){
	                            Ext.Msg.alert('温馨提示',action.result.info);
	                        }  
	                    }); 
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
    <body style="background-image: url(app/resources/wallpaper/kung_fu_panda_2-008.jpg);background-size:cover;">
    </body>
</html>  