/*! 
 * Ext JS Library 4.0 
 * Copyright(c) 2006-2011 Sencha Inc. 
 * licensing@sencha.com 
 * http://www.sencha.com/license 
 */  
  
Ext.define('App.modules.User', {  
    extend: 'Ext.ux.desktop.Module',  
  
    requires: [  
    'Ext.data.JsonStore',  
    'Ext.util.Format',  
    'Ext.grid.Panel',  
    'Ext.grid.RowNumberer'  
    ],  
  
    id:'user',  
  
    init : function(){
        this.launcher = {  
            text: '用户管理',
            iconCls:'user'
        };  
    },  
      
  
    createWindow : function(){  
        var desktop = this.app.getDesktop();  
        var win = desktop.getWindow('users-grid');  
        var required = '<span style="color:red;font-weight:bold" data-qtip="Required">* </span>';  
          
        Ext.define('Srx.model.User', {  
            extend: 'Ext.data.Model',  
            fields: [{  
                name: 'id',  
                type:'int'  
            },{  
                name: 'userName',  
                type:'string'  
            },{  
                name: 'userPasswd',  
                type:'password'  
            },{  
                name: 'userPermiss'  
            },{  
                name: 'description',  
                type:'string'  
            },{  
                name: 'logintimes',  
                type:'int'  
            }]  
        });  
          
        var userStore = Ext.create('Ext.data.Store', {  
            autoLoad: true,  
            autoDestroy: true,  
            storeId: 'userStore',  
            model:'Srx.model.User',  
            proxy: {
                actionMethods:{
                    create: "POST",
                    read: "POST",
                    update: "POST",
                    destroy: "POST"
                },
                type: 'ajax',
                api: {
                    create  : __ctxPath + 'user/add.do',
                    read    : __ctxPath + 'user/list.do',
                    update  : __ctxPath + 'user/update.do', 
                    destroy : __ctxPath + 'user/remove.do'
                },
                reader: {
                    type: 'json',
                    root: 'data.list',
                    idProperty: 'id'
                }
            }
        });
        //下拉框数据。   
        var cbstore = Ext.create('Ext.data.ArrayStore', {  
            autoDestroy : true,  
            fields: ['id', 'name'],  
            data : [[0,"管理员"],[1,"操作员"],[2,"浏览员"]]  
        });  
          
        Ext.apply(Ext.form.VTypes, {  
            confirmPwd : function(val, field) {  
                if (field.confirmPwd) {  
                    var firstPwdId = field.confirmPwd.first;  
                    var secondPwdId = field.confirmPwd.second;  
                    this.firstField = Ext.getCmp(firstPwdId);  
                    this.secondField = Ext.getCmp(secondPwdId);  
                    var firstPwd = this.firstField.getValue();  
                    var secondPwd = this.secondField.getValue();  
                    if (firstPwd == secondPwd) {  
                        return true;  
                    } else {  
                        return false;  
                    }  
                }  
            },  
            confirmPwdText : '两次输入的密码不一致!'  
        });  
          
        if(!win){  
            var userid;  
            var usergrid = Ext.create('Ext.grid.Panel',{  
                flex:2,  
                frame: true,  
                title:'用户列表',  
                store: userStore,  
                layout: 'column',  
                columns: [  
                new Ext.grid.RowNumberer(),  
                {  
                    text: "ID",  
                    flex: 1,  
                    dataIndex: 'id'  
                },{  
                    text: "用户名",  
                    flex: 2,  
                    dataIndex: 'userName'  
                },{  
                    text: "权限",  
                    flex: 1,  
                    dataIndex: 'userPermiss',  
                    renderer:function(v){  
                        if (v == 0) return "管理员";  
                        if (v == 1) return "操作员";  
                        if (v == 2) return "浏览员";  
                    }  
                },{  
                    text: "描述",  
                    flex: 3,  
                    dataIndex: 'description'  
                },{  
                    text: "登陆次数",  
                    flex: 1,  
                    dataIndex: 'logintimes'  
                }],  
                listeners: {  
                    selectionchange: function(model, records) {  
                        if (records[0]) {  
                            userid = records[0].data.id;  
                            Ext.getCmp('userForm').loadRecord(records[0]);  
                            Ext.getCmp('user_save').setDisabled(false);  
                            Ext.getCmp('userForm').remove('reUserPasswd');  
                            Ext.getCmp('user_update').setDisabled(false);  
                            Ext.getCmp('user_delete').setDisabled(false);  
                            Ext.getCmp('user_save').setText('增加');  
                        }  
                    }  
                }  
            });  
              
            var userform =  Ext.widget({  
                flex:1,  
                title:'用户编辑',  
                xtype: 'form',  
                frame: true,  
                id: 'userForm',  
                bodyPadding: 10,  
                layout:'anchor',  
                fieldDefaults: {  
                    labelStyle:'font-weight: bold;text-align:right',  
                    labelWidth: 70  
                },  
                defaultType: 'textfield',  
                items: [{  
                    id:'user-name',  
                    fieldLabel: '用户名',  
                    beforeLabelTextTpl: required,  
                    name: 'userName',  
                    allowBlank:false,  
                    enableKeyEvents: true,                  
                    listeners:{  
                        //事件监听，当用户离开输入框——失去焦点时执行  
                        'blur':function(){  
                            Ext.Ajax.request({  
                                url:'checkname.shtml?loginName='+Ext.getCmp('user-name').getValue(),  
                                method:'post',  
                                success: function(response,opts){  
                                    var respText=Ext.decode(response.responseText);  
                                    if(respText.success == true){  
                                        //根据ajax请求返回的数据信息手动的进行设置该字段无效  
                                        Ext.getCmp('user-name').markInvalid('该用户名已被使用');  
                                    }  
                                }  
                            });    
                        }                 
                    }  
                },{  
                    fieldLabel: '类型',  
                    beforeLabelTextTpl: required,  
                    xtype: 'combobox',  
                    store: cbstore,  
                    displayField: 'name',  
                    valueField: 'id',  
                    name: 'userPermiss',  
                    forceSelection:true,  
                    allowBlank:false  
                },{  
                    fieldLabel: '描述',  
                    name: 'description',  
                    xtype:'textarea'  
                },{  
                    id:'password',  
                    fieldLabel: '密码',  
                    beforeLabelTextTpl: required,  
                    name: 'userPasswd',  
                    blankText : '密码不能为空',  
                    regex : /^[\s\S]{6,32}$/,  
                    regexText : '密码长度必须大于6小于32',  
                    inputType : 'password',  
                    allowBlank:false  
                }],  
  
                buttons: [{  
                    id:'user_save',  
                    text: '增加',  
                    maxWidth:55,  
                    handler:function(){  
                        var user_form = this.up('form');  
                        if(this.getText() == '增加'){  
                            if(!Ext.get('reUserPasswd')){  
                                user_form.add({  
                                    id:'reUserPasswd',  
                                    name:'reUserPasswd',  
                                    fieldLabel: '确认密码',  
                                    beforeLabelTextTpl: required,  
                                    confirmPwd : {  
                                        first : 'password',  
                                        second : 'reUserPasswd'  
                                    },  
                                    inputType : 'password',  
                                    vtype : 'confirmPwd',  
                                    regex : /^[\s\S]{6,32}$/,  
                                    regexText : '密码长度必须大于6小于32',  
                                    allowBlank:false  
                                })  
                            }  
                            user_form.getForm().reset();  
                            Ext.getCmp('user_update').setDisabled(true);  
                            Ext.getCmp('user_delete').setDisabled(true);  
                            this.setText('保存');  
                        }else{  
                            if(user_form.getForm().isValid()){  
                                user_form.getForm().submit({  
                                    url: 'user/adduser.shtml',  
                                    submitEmptyText: false,  
                                    waitTitle:'请等待',  
                                    waitMsg: '正在添加用户...',  
                                    success:function(form,action){  
                                        var response = Ext.decode(action.response.responseText);  
                                        Ext.Msg.alert('提示', response.msg);  
                                        userStore.load();  
                                    },  
                                    failure:function(form,action){  
                                        Ext.Msg.alert('提示', '添加用户失败！');  
                                    }  
                                });  
                            }else{  
                                Ext.Msg.alert('提示', '数据验证失败！');  
                            }  
                            this.setText('增加');  
                            Ext.getCmp('userForm').remove('reUserPasswd');  
                            Ext.getCmp('user_update').setDisabled(false);  
                            Ext.getCmp('user_delete').setDisabled(false);  
                        }  
                    }  
                },{  
                    id:'user_update',  
                    text: '编辑',  
                    maxWidth:55,  
                    handler:function(){  
                        var user_form = this.up('form');  
                        if(user_form.getForm().isValid()){  
                            user_form.getForm().submit({  
                                url: 'user/updateuser.shtml',  
                                submitEmptyText: false,  
                                waitTitle:'请等待',  
                                waitMsg: '正在编辑用户...',  
                                params : {  
                                    id : userid  
                                },  
                                success:function(form,action){  
                                    var response = Ext.decode(action.response.responseText);  
                                    Ext.Msg.alert('提示', response.msg);  
                                    userStore.load();  
                                },  
                                failure:function(form,action){  
                                    Ext.Msg.alert('提示', '编辑用户失败！');  
                                }  
                            });  
                        }else{  
                            Ext.Msg.alert('提示', '数据验证失败！');  
                        }  
                    }  
                },{  
                    id:'user_delete',  
                    text: '删除',  
                    maxWidth:55,  
                    handler: function() {  
                        Ext.Ajax.request({  
                            url:'user/delete.shtml?ids='+userid,  
                            method:'post',  
                            waitTitle:'请等待',  
                            waitMsg: '正在删除用户...',  
                            params : {  
                                id : userid  
                            },  
                            success:function(response,opts){  
                                var respText=Ext.decode(response.responseText);  
                                if(respText.success == true){  
                                    Ext.Msg.alert('提示', respText.msg);  
                                    userStore.load();  
                                }  
                            },  
                            failure:function(form,action){  
                                Ext.Msg.alert('提示', '删除用户失败！');  
                            }  
                        });   
                    }  
                },{  
                    text: '取消',  
                    maxWidth:55,  
                    handler: function() {  
                        this.up('form').getForm().reset();  
                    }  
                }]  
            });  
  
            win = desktop.createWindow({  
                id: 'users-grid',  
                title:'用户管理',  
                width:800,  
                height:500,  
                iconCls: 'icon-grid',  
                layout: {  
                    type: 'hbox',
                    align: 'stretch',  
                    defaultMargins:{  
                        top: 1,  
                        right: 1,  
                        bottom: 1,  
                        left: 1  
                    },  
                    padding:0  
                },  
                items: [  
                usergrid,  
                userform  
                ]  
            });  
        }  
        return win;  
    }  
});  