Ext.define('App.extend.ModuleWindow', {  
    extend: 'Ext.ux.desktop.Module',
        requires: [  
    	    'Ext.data.JsonStore',
    	    'Ext.util.Format',
    	    'Ext.grid.Panel',
    	    'Ext.grid.RowNumberer'
        ],  
        init : function(){
        	debugger;
            this.launcher = {  
                text:this.name,
                iconCls:this.icon48,
                iconSmall:this.icon16
            };  
        },
        fn:{
        	loadDataToModuleForm:function(me,cfg){
        		cfg = cfg || {formTitle:"修改",readOnly:false};
        		debugger;
        		var rows = me.getModuleResultGrid().getSelectionModel().getSelection();
        		if(rows == null || rows.length < 1){
        			FN_warning("请选择一条记录.");
        			return;
        		}
        		if(rows.length > 1){
        			FN_warning("请选择一条记录(暂不支持多条).");
        			return;
        		}
        		me.getModuleStore().load({
        			params:{id:rows[0].data.id},
        			callback: function(records, operation, success) {
        		        debugger;
        		        if(success){
        		        	var flag = true;
        		        	if(me.extendFnBeforeShowModuleEditWindow){
        		        		flag = me.extendFnBeforeShowModuleEditWindow();
        		        	}
    		        		if(flag){
    		        			try{
    			        			var form = me.getModuleForm();
    	                			var rec = records[0];
    	                			var encryptFieldCheckboxes = [];
    	                			if(rec && rec.data){
    	                				var data = rec.data;
    	                				var arr = data.encryptFields && Ext.util.Format.uppercase(data.encryptFields) != "NULL" ? data.encryptFields.split(",") : [];
    	                				
    	                				Ext.each(form.getForm().getFields().items,function(item){
    	                					if(item.xtype == "checkboxfield"){
    	                						debugger;
    	                						if(Ext.isEmpty(data[item.inputValue])){
    	                    						item.disable();
    	                    					}else{
    	                    						item.enable();
    	                    						if(Ext.Array.contains(arr,item.inputValue)){
    	                    							try{
    		                    							data[item.inputValue] = App.util.Security.decryptByAES(data[item.inputValue],__SESSION_USER_SALT);
    		                    							
    		                    							//data[item.inputValue + "_hide"] = data[item.inputValue];
    		                    							/*item.setValue(true);
    		                    							var fieldIdPrefix = me.getModuleFormFieldIdPrefix() + item.inputValue;
    		                    							Ext.getCmp(fieldIdPrefix+".hide").setValue(data[item.inputValue]);
    		                    							Ext.getCmp(fieldIdPrefix+".hide").show();
    		                    							Ext.getCmp(fieldIdPrefix+"").hide();
    		                    							*/
    		                    							//item.fireEvent('change',this);
    		                    							//me.toEncryptOrDecrypt(item, true, false, null);
    		                    							encryptFieldCheckboxes.push(item);
    		                    						}catch(e){
    		                    							console.log(e);
    		                    							throw "解密异常.";
    		                    						}
    		                    					}
    	                    					}
    	                					}
    	                				});
    	                				rec.data = data;
    	                			}
    	                			me.fn.setFormReadOnly(me,cfg.readOnly);
    	                			form.loadRecord(rec);
    	                			console.log(encryptFieldCheckboxes);
    	                			if(encryptFieldCheckboxes && encryptFieldCheckboxes.length > 0){
    	                				Ext.each(encryptFieldCheckboxes,function(item){
    	                					if(item.getValue()){
    	                						me.toEncryptOrDecrypt(item, true, false, null);
    	                					}else{
    	                						item.setValue(true);
    	                					}
    	                				});
    	                			}
    	                			me.getModuleWindow(cfg.formTitle,!cfg.readOnly).show();
    		        			}catch(e){
    		        				alert(e);
    		        			}
    		        		}
        		        }else{
        		        	EXT_AJAX_CALLBACK_FAILURE();
        		        }
        		    }
        		});
        	},
        	setFormReadOnly:function(me,readOnly){
        		var form = me.getModuleForm();
        		function initEmptyText(field){
        			var text = "请输入";
        			if(Ext.Array.contains(["textfield","textarea"],field.xtype)){
        				text = "请输入";
        			}else if(Ext.Array.contains(["combo","radiogroup","datefield"],field.xtype)){
        				text = "请选择";
        			}
        			return text;
        		}
        		Ext.each(form.getForm().getFields().items,
        				function(item,key){
    	    				if(!(item.xtype == "checkboxfield" && item.inputValue)){
    	    					debugger;
    	    					item.setReadOnly(readOnly);
    	    					if(readOnly){
    	    						item.emptyText = " ";
    	    					}else{
    	    						item.emptyText = initEmptyText(item);
    	    					}
    	    					item.reset();
    	    				}
        				});
            		if(readOnly){
            			Ext.getCmp(me.getModuleFormFieldIdPrefix()+"createTime").show();
            			Ext.getCmp(me.getModuleFormFieldIdPrefix()+"updateTime").show();
            		}else{
            			Ext.getCmp(me.getModuleFormFieldIdPrefix()+"createTime").hide();
            			Ext.getCmp(me.getModuleFormFieldIdPrefix()+"updateTime").hide();
            		}
        	},
        	detail:function(me){
        		me.fn.loadDataToModuleForm(me,{formTitle:"详情",readOnly:true});
        	},
        	add:function(me){
        		debugger;
        		var form = me.getModuleForm().getForm();
        		if(form){
        			me.fn.setFormReadOnly(me,false);
        			form.reset();
            		if(me.extendFnBeforeShowModuleAddWindow){
                		flag = me.extendFnBeforeShowModuleAddWindow();
                	}
            		if(flag){
            			me.getModuleWindow("增加",true).show();
            		}
        		}
        	},
        	edit:function(me){
        		me.fn.loadDataToModuleForm(me,{formTitle:"编辑",readOnly:false});
        	},
        	remove:function(me){
        		debugger;
        		var rows = me.getModuleResultGrid().getSelectionModel().getSelection();
        		if(rows == null || rows.length < 1){
        			FN_warning("请选择一条记录(支持多条).");
        			return;
        		}
        		var ids = [];
        		for(var i in rows){
        			ids.push(rows[i].data["id"]);
        		}
        		Ext.MessageBox.confirm(__TIP_TITLE,"本次共删除[<span style='color:red;'>"+ids.length+"</span>]条记录,您确认要操作吗?",function(btn){
            		if(btn == 'yes'){
            			Ext.Ajax.request({
            				url : me.getAPI().remove,
            				method : "POST",
            				params : {
            					ids : ids
            				},
            				timeout : 30000,// 30秒超时,
            				waitTitle : __TIP_TITLE,
            				waitMsg : "正在提交请求，请稍候......",
            				success : function(o, c) {
            					var res = Ext.decode(o.responseText);
    							EXT_ALERT({msg:res.msg,icon:(res.success ? Ext.MessageBox.INFO : Ext.MessageBox.ERROR),fn:function(){
    								if(res.success){
    	                        		me.getModuleResultStore().load();
    	                        	}
    							}});
            				},  
                            failure:EXT_AJAX_CALLBACK_FAILURE
            			});
            		}  
            	});
        	},
        	list:function(me){
        		debugger;
    			var loadTip = new Ext.LoadMask(Ext.getBody(), {
    				msg : "正在加载数据，请稍后..."
    			});
    			loadTip.show();
    			me.getModuleResultGrid().getStore().load({
    				start : 0,
    				limit : me.GRID_PAGE_SIZE
    			});
    			loadTip.hide();
        	},
        	save:function(me){
        		debugger;
        		var moduleForm = me.getModuleForm();
    			if(moduleForm.getForm().isValid()){
    				var data = moduleForm.getForm().getValues();
    				var encryptFieldArr = data.encryptFieldArr;
    				if(encryptFieldArr){
    					/**/
    					var arr = [];
    					if(Ext.typeOf(encryptFieldArr) == "string"){
    						arr.push(encryptFieldArr);
    					}else if(Ext.typeOf(encryptFieldArr) == "array"){
    						arr = encryptFieldArr;
    					}
    					for(var i in arr){
    						try{
    							data[arr[i]] = App.util.Security.encryptByAES(data[arr[i]],__SESSION_USER_SALT);
    						}catch(e){
    							throw "加密异常.";
    						}
    					}
    					data.encryptFields = arr.join(",");
    				}else{
    					data.encryptFields = "NULL";
    				}
    				Ext.Ajax.request({
        				url : me.getAPI().save,
        				params:data,
        				timeout : 30000,// 30秒超时,
        				waitTitle : __TIP_TITLE,
        				waitMsg : "正在保存,请稍候...",
        				success : function(o, c) {
        					var res = Ext.decode(o.responseText);
        					Ext.MessageBox.show({
    							title : __TIP_TITLE,
    							msg : res.msg,
    							buttons : Ext.MessageBox.OK,
    							icon : res.success ? Ext.MessageBox.INFO : Ext.MessageBox.ERROR,
    							fn:function(){
    								if(res.success){
    		                    		me.getModuleWindow().close();
    		                    		me.getModuleResultStore().load();
    		                    	}
    							}
    						});
        				},  
                        failure:EXT_AJAX_CALLBACK_FAILURE
        			});
                }else{  
                    Ext.Msg.alert(__TIP_TITLE,"请按提示正确录入.");  
                }  
    		
        	},
        	importExcel:function(me){
        		var filePath = new Ext.form.FileUploadField({
    				fieldLabel : "导入文件路径<font color = 'red'><b>*</b></font>",
    				buttonText : "选择...",
    				name : "file",
    				emptyText : "请选择导入文件...",
    				labelStyle : "text-align : right"
    			});
        		var importBtn = new Ext.Button({
    				text : "提交",
    				handler : function() {
    					var fileType = ".xlsx|.xls|"
    					var filePathValue = filePath.getValue();
    					if (filePathValue == null || filePathValue == "") {
    						Ext.Msg.alert("提示", "请选择要导入的Excel文件.");
    						return false;
    					}
    					var v = filePathValue.substring(filePathValue
    							.lastIndexOf("."));
    					if (fileType.indexOf(v + "|") == -1) {
    						Ext.Msg.alert("提示", "请选择Excel格式.");
    						return;
    					}
    					importPanel.getForm().standardSubmit = false;
    					importPanel.getForm().submit({
    						method : "POST",
    						async : true,
    						waitMsg : '正在导入,请稍候...',
    						success : function(form, action) {
    							debugger;
    							importWin.close();
    							me.getModuleResultStore().load();
    							var data = action.result.data;
    							if(data && data.length > 0){
    								var msg = data[0];
    								Ext.Array.remove(data,msg);
    								if(data.length > 0){
    									Ext.MessageBox.confirm(__TIP_TITLE,msg + ",查看明细?",function(btn){
            			            		if(btn == 'yes'){
            			            			showImportResult(data);
            			            		}  
            			            	});
    								}else{
    									Ext.Msg.alert(__TIP_TITLE,"导入成功");
    								}
    							}else{
    								Ext.Msg.alert(__TIP_TITLE,"导入失败");
    							}
    						},
    						failure : function(form, action) {
    							Ext.Msg.alert("提示", action.result.data);
    							return;
    						}
    					});
    				}
    			});
    			
    			var closeBtn = new Ext.Button({
    				text : "取消",
    				handler : function() {
    					importWin.close();
    				}
    			});
    			var importPanel = Ext.create("Ext.form.Panel", {
    				labelAlign : "right",
    				border : false,
    				autoHeight : true,
    				frame : true,
    				fileUpload : true,
    				enctype : "multipart/form-data",
    				url : me.getAPI().import,
    				items : [ {
    					layout : "column",
    					border : false,
    					items : [ {
    						columnWidth : .98,
    						layout : 'form',
    						border : false,
    						items : [ filePath ]
    					} ]
    				} ],
    				buttons : [ importBtn, {text:"取消",handler:function(){importWin.close();}} ],
    				buttonAlign : "center"
    			});
    			var importWin = new Ext.Window({
    				title : "导入",
    				border : false,
    				autoDestroy : false,
    				closeAction : "close",
    				hideMode : "destory",
    				resizable : false,
    				modal : true,
    				layout : "fit",
    				autoScroll : true,// 自动显示滚动条
    				width : 480,
    				height : 100,
    				items : [ importPanel ]
    			});
    			importWin.show();
    			function showImportResult(data){
    				var arr = [];
    				for(var i in data){
    					arr.push({msg:data[i]});
    				}
    	        	var win = new Ext.Window({
	        				border : false,
	        				autoDestroy : false,
	        				closeAction : "close",
	        				hideMode : "destory",
	        				resizable : false,
	        				modal : true,
	        				layout : "fit",
	        				autoScroll : true,
	        				width : 480,
	        				height : 320,
	        				items : [ Ext.create('Ext.grid.Panel',{
	    	    	        	anchor : "100% 70%",
	    	    	    		autoScroll : true,
	    	    	    		stateful: true,
	    	    	    		store:Ext.create("Ext.data.Store", {fields: ['msg'],data:arr}),
	    	    	            columns:[new Ext.grid.RowNumberer(),{text:"信息",flex:2,dataIndex:'msg',align:"center"}]
	    	    	        }) ],
	    	    	        buttons : [{text:"关 闭",pressed:true,handler:function(){debugger;win.close();}}],
	    	    	        buttonAlign:"center"
	        			});
    	        	win.show();
    	        }
        	},
        	downloadTemplate:function(me){
        		/*
    			Ext.MessageBox.show({
                    title: '下载模板',
                    msg: "上部分为筛选条件,下部分表格装载请求到的数据,表格上下为可用菜单或工具;<br/>" +
                    		"1,录入筛选条件[非必填],左键单击'查询';<br/>" +
                    		"2,数据表格中将根据筛选条件加载数据;<br/>" +
                    		"3,底部分页菜单用于适应用户阅读需求及习惯;<br/>" +
                    		"4,有任何操作疑问,请联系系统运营人员;",
                    icon: Ext.MessageBox.INFO,
                    buttons : Ext.MessageBox.OK
                });
                */
        		window.open(me.getAPI().downloadTemplate);
        	},
        	exportExcel:function(){
    			Ext.MessageBox.show({
                    title: '导出',
                    msg: "上部分为筛选条件,下部分表格装载请求到的数据,表格上下为可用菜单或工具;<br/>" +
                    		"1,录入筛选条件[非必填],左键单击'查询';<br/>" +
                    		"2,数据表格中将根据筛选条件加载数据;<br/>" +
                    		"3,底部分页菜单用于适应用户阅读需求及习惯;<br/>" +
                    		"4,有任何操作疑问,请联系系统运营人员;",
                    icon: Ext.MessageBox.INFO,
                    buttons : Ext.MessageBox.OK
                });
        	},
        	help:function(){
    			Ext.MessageBox.show({
                    title: '使用说明',
                    msg: "上部分为筛选条件,下部分表格装载请求到的数据,表格上下为可用菜单或工具;<br/>" +
                    		"1,录入筛选条件[非必填],左键单击'查询';<br/>" +
                    		"2,数据表格中将根据筛选条件加载数据;<br/>" +
                    		"3,底部分页菜单用于适应用户阅读需求及习惯;<br/>" +
                    		"4,有任何操作疑问,请联系系统运营人员;",
                    icon: Ext.MessageBox.INFO,
                    buttons : Ext.MessageBox.OK
                });
        	}
        },
        extendFnBeforeShowModuleAddWindow:function(){
        	var me = this;
        	Ext.each(me.getModuleForm().getForm().getFields().items,function(item){
    			if(item.xtype == "checkboxfield"){
    				item.disable();
    			}
    		});
        	return true;
        },
        extendFnBeforeShowModuleEditWindow:function(){
        	var me = this;
        	return true;
        },
        getBaseAPI:function(){
        	var me = this;
        	var api = {
                    save  : __ctxPath + me.code + '/save.do',
                    list    : __ctxPath + me.code + '/list.do',
                    update  : __ctxPath + me.code + '/update.do', 
                    remove : __ctxPath + me.code + '/remove.do',
                    detail : __ctxPath + me.code + '/detail.do',
                    loadDict:__ctxPath + '/loadDict.do',
                    import:__ctxPath + me.code + '/import.do',
                    downloadTemplate:__ctxPath + me.code + '/downloadTemplate.do'
                  
        	};
        	return api;
        },
        getExtendAPI:function(){
        	return {};
        },
        getAPI:function(){
        	var me = this;
        	var baseAPI = me.getBaseAPI();
        	var extendAPI = me.getExtendAPI();
        	Ext.apply(baseAPI,extendAPI);
        	return baseAPI;
        },
        getModuleModel:function(){
        	var me = this;
        	var modelName = me.className;
        	if(!Ext.ModelManager.getModel(modelName)){
        		var fields = me.getBaseModelFields().concat(me.getModuleModelFields());
            	Ext.define(modelName, {  
                    extend:'Ext.data.Model',  
                    fields:fields
                });
        	}
        	return Ext.ModelManager.getModel(modelName);
        },
        getModuleQueryForm:function(){
        	var me = this;
        	var queryFormId = me.module + ".query.form";
        	debugger;
        	return Ext.getCmp(queryFormId) || new Ext.form.FormPanel({
        		id:queryFormId,
            	anchor:"100% 30%",
                flex:1,
                title:'查询条件',
                xtype: 'form',
                frame: false,
//                bodyPadding: 10,
                bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
                layout:'anchor',
                fieldDefaults:{
                    labelStyle:'font-weight: sold;text-align:right',
                    labelWidth: 70
                },  
                defaultType:'textfield', 
                buttonAlign:'center',
                buttons:[{text:'查询',pressed:true,width:80,handler:function(){me.fn.list(me);}},
                         {text:'重置',pressed:true,width:80,handler:function(){me.getModuleQueryForm().getForm().reset();}} 
                ],
                items: [new Ext.form.FieldSet({
            		collapsible : false,
            		anchor : '98%',
            		border:false,
            		items : [ {
            			layout : 'column',
            			border : false,
            			defaults:{
            				columnWidth:.25,layout:'form',border:false
            			},
            			items:me.getModuleQueryFormFields()
            		}]
            	})] 
            });
        },
        getStoreLoadConfig:function(cfg){
        	Ext.apply(cfg,{callback:function(records, operation, success){
        		debugger;
    	        if(!success){
    	        	var json = operation.request.scope.reader.jsonData;
    	        	EXT_AJAX_ERROR(json);
    	        }
        	}});
        	return  cfg;
        },
        getModuleResultStore:function(){
        	var me = this;
        	var resultStoreId = me.module + ".result.store";
        	var res = Ext.getStore(resultStoreId);
        	debugger;
        	if(!res){
        		res = Ext.create('Ext.data.Store', {  
                    autoLoad: true,
                    autoDestroy: true,
                    storeId: resultStoreId,  
                    model:me.getModuleModel(),
                    pageSize:me.GRID_PAGE_SIZE,
                    proxy : new Ext.data.HttpProxy({
            			url : me.getAPI().list,
            			type: 'ajax',
            			reader : {
                			type: 'json',
                			root: 'data.list',
                			idProperty: 'id',
                			totalProperty: 'data.total',
                			messageProperty:"error"
                		},
            			actionMethods:{
                            create: "POST",
                            read: "POST",
                            update: "POST",
                            destroy: "POST"
                        }
            		}),
        	        listeners:{
        				beforeload:function(s,o,e) {
        						debugger;
        						Ext.apply(s.proxy.extraParams, me.getModuleQueryForm().getForm().getValues()); 
        				}
        	        }
                });
        	}
        	return res;
        },
        getModuleResultGrid:function(){
        	var me = this;
        	var resultGridId = me.module + ".result.grid";
        	var resultGrid = Ext.getCmp(resultGridId);
        	debugger;
        	if(!resultGrid){
        		resultGrid = Ext.create('Ext.grid.Panel',{
        			id:resultGridId,
    	        	title:'结果列表',
    	        	anchor : "100% 70%",
    	        	store: me.getModuleResultStore(), 
    	    		autoScroll : true,
    	    		stateful: true,
    	    		selType: "checkboxmodel",
    	            multiSelect: true,
    	            cellTip : true,
    	    		viewConfig : {
    	    			emptyText : '没有您想要的数据！',
    	    			loadMask : true,
    		            stripeRows: true,
    		            enableTextSelection: true
    	    		},
    	    		loadMask : {
    	    			msg : "正在加载数据，请稍侯……"
    	    		},
    	//            layout: 'column',
    	            columns:me.getModuleResultGridColumns() ,
    	            tbar: me.getGridTopBar(),
    	            bbar: Ext.create('Ext.PagingToolbar', {
    	                store: me.getModuleResultStore(),
    	                displayInfo: true
    	            }),listeners:{
    	            	afterrender:function(thiz,opt){
    	            		if (!this.cellTip) {
    	                        return;
    	                    }
    	            		var view = this.getView();
    	            		debugger;
    	                    this.tip = new Ext.ToolTip({
    	                        target: view.el,
    	                        delegate : '.x-grid-cell-inner',
    	                        trackMouse: true, 
    	                        renderTo: document.body, 
    	                        ancor : 'top',
    	                        listeners: {  
    	                            beforeshow: function updateTipBody(tip) {
    	                                //取cell的值
    	                                //fireFox  tip.triggerElement.textContent
    	                                //IE  tip.triggerElement.innerText 
    	                                var tipText = (tip.triggerElement.innerText || tip.triggerElement.textContent);
    	                                if (Ext.isEmpty(tipText) || Ext.isEmpty(tipText.trim()) ) {
    	                                    return false;
    	                                }
    	                                debugger;
    	                                tip.update(tipText);
    	                            }
    	                        }
    	                    });
    	            	}
    	            }
    	        });  
        	}
        	return resultGrid;
        },
        GRID_PAGE_SIZE:20,
        REQUIRED_FIELD_HTML:"<span style='color:red;font-weight:bold' data-qtip='必填'>* </span>",
        getBooleanStore:function(){
        	debugger;
        	var me = this;
        	var storeId = "BOOLEAN_STORE";
        	var res = Ext.data.StoreManager.lookup(storeId);
        	res = Ext.getStore(storeId);
        	if(!res){
        		res = Ext.create('Ext.data.Store', {
            		storeId:storeId,
            		autoLoad: true,
                    fields: ['id', 'name'],
                    data : [
                            {"id":"1", "name":"是"},
                            {"id":"0", "name":"否"}
                    ]
                });
        	}
        	return res;
        },
        getModuleForm:function(){
        	var me = this;
        	var moduleFormId = me.module + ".module.form";
        	debugger;
        	return Ext.getCmp(moduleFormId) || new Ext.form.FormPanel({
        		id:moduleFormId,
    			anchor:"100% 100%",
                flex:1,
                xtype: 'form',
                frame: false,
                bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
                fieldDefaults:{
                    labelStyle:'font-weight: sold;text-align:right',
                    labelWidth: 85
                },  
                items: [new Ext.form.FieldSet({
            		collapsible : false,
            		anchor : '90%',
            		border:false,
            		items : [ {
            			layout : 'column',
            			border : false,
            			defaults:{
            				columnWidth:1,layout:'form',border:false,defaultType:'textfield'
            			},
            			items:me.getModuleFormFields()
            		}]
            	})]
            });
        },
        getModuleWindow:function(title,canSave){
        	var me = this;
        	title = title || "添加";
        	var moduleWinId = me.module + ".module.win";
    		var moduleWin = Ext.getCmp(moduleWinId);
    		if(!moduleWin){
    			var moduleForm =  me.getModuleForm();
    			moduleWin = new Ext.Window({
    				id:moduleWinId,
        			title : title,
        			width : 420,
        			height : 400,
        			closable : true,
        			plain : true,
        			modal : true,
        			autoDestroy : true,
        			closeAction : 'close',
        			layout : 'anchor',
        			buttonAlign:'center',
        			items : [ moduleForm ],
        			listeners:{'beforeshow':function(){moduleWin.center();}},
        			buttons : [
    					{id:me.module + ".module.win.btn.save",text:"保 存",pressed:true,hidden:true,handler:function(){
    						me.fn.save(me);
    						}},
    					{text:"关 闭",pressed:true,handler:function(){debugger;moduleWin.close();}}
        			]
        		});
    		}
    		moduleWin.setTitle(title);
    		if(canSave){
    			Ext.getCmp(me.module + ".module.win.btn.save").show();
    		}else{
    			Ext.getCmp(me.module + ".module.win.btn.save").hide();
    		}
    		return moduleWin;
        },
        getBaseGridTopBar:function(){
        	var me = this;
        	return [
        	         {xtype:'button',text:'增加',pressed:true,tooltip:"增加一条记录.",handler:function(){me.fn.add(me);}},
        	         {xtype:'button',text:'修改',pressed:true,tooltip:"修改一条记录.",handler:function(){me.fn.edit(me);}},
        	         {xtype:'button',text:'删除',pressed:true,tooltip:"删除选择的记录.",handler:function(){me.fn.remove(me);}},
        	         {xtype:'button',text:'查看',pressed:true,tooltip:"查看详情.",handler:function(){me.fn.detail(me);}},
        	         {xtype:'button',text:'导入',pressed:true,tooltip:"导入Excel数据文件.",handler:function(){me.fn.importExcel(me);}},
        	         {xtype:'button',text:'下载导入模板',pressed:true,tooltip:"下载导入模板,填充数据后导入.",handler:function(){me.fn.downloadTemplate(me);}},
        	         {xtype:'button',text:'导出',disabled:true,pressed:true,tooltip:"导出为Excel数据文件.",handler:function(){me.fn.exportExcel(me);}}
        	       ];
        },
        getGridTopBar:function(){
        	var me = this;
        	return me.getBaseGridTopBar().concat(me.getExtendGridTopBar());
        },
        createWindow:function(){
        	var me = this;
        	var desktop = this.app.getDesktop();  
        	var moduleDesktopWindowId = me.module + ".desktop.win";
            var moduleDesktopWindow = desktop.getWindow(moduleDesktopWindowId);  
        	if(!moduleDesktopWindow){
        		moduleDesktopWindow = desktop.createWindow({
    	            id: moduleDesktopWindowId,
    	            title:me.name,
    	            width:900,  
    	            height:500,
    	            iconCls: me.launcher.iconSmall,
    	            animCollapse:false,
    	            constrainHeader:true,
    	            layout: 'anchor',
    	            items: [me.getModuleQueryForm(),me.getModuleResultGrid()],
    	            tbar:new Ext.Toolbar({items:[{text:'说 明',pressed:true,tooltip:"查看使用说明",handler:me.fn.help}]})
    	        });
        	}
        	return moduleDesktopWindow;
        },
        getModuleStore:function(){
        	var me = this;
        	var storeId = me.module + ".module.store";
        	var res = Ext.getStore(storeId);
        	if(!res){
        		res = Ext.create('Ext.data.Store', {
        			storeId:storeId,
        			autoLoad: false,
                    proxy : new Ext.data.HttpProxy({
            			url : me.getAPI().detail,
            			type: 'ajax',
            			reader : {
                			type: 'json',
                			root: 'data',
                			idProperty: 'id'
                		},
            			actionMethods:{
                            read: "POST",
                        }
            		}),
            		model:me.getModuleModel(),
                });
        	}
        	return res;
        },
        toEncryptOrDecrypt:function(thiz, newValue, oldValue, eOpts){
        	/*
        	console.log("change:",oldValue,newValue);
        	var me = this;
        	debugger;
        	var form = me.getModuleForm().getForm();
        	var checked = thiz.checked;
        	var formValues = form.getValues();
        	var v = formValues[thiz.inputValue];
        	debugger;
        	if(v == "" || v == undefined){
        		return false;
        	}
        	if (checked) {
    			v = App.util.Security.encryptByAES(v,__SESSION_USER_SALT);
            } else {
            	v = App.util.Security.decryptByAES(v,__SESSION_USER_SALT);
            }
        	
        	formValues[thiz.inputValue] = v;
        	form.setValues(formValues);
        	thiz.setValue(newValue);
        	return true;
        	*/
        	debugger;
        	var me = this;
        	var fieldIdPrefix = me.getModuleFormFieldIdPrefix();
        	var ele = Ext.getCmp(fieldIdPrefix+ thiz.inputValue);
    		var hideEle = Ext.getCmp(fieldIdPrefix + thiz.inputValue + ".hide");
        	if(newValue){
    			hideEle.show();
    			hideEle.setValue(ele.getValue());
    			ele.hide();
    		}else{
    			hideEle.hide();
    			ele.show();
    		}
        },
        getModuleFormBaseFields:function(){
        	var me = this;
        	var fieldIdPrefix = me.getModuleFormFieldIdPrefix();
        	return [	{layout:'column',items:[{name:'remark',xtype:'textarea',fieldLabel:'备注',maxLength:500,columnWidth:.87,style:'margin:1px'}]},
        	        	{layout:'column',items:[{id:fieldIdPrefix+"createTime",name:'createTime',readOnly:true,hidden:true,fieldLabel:'创建时间',columnWidth:.87,style:'margin:1px'}]},
        	        	{layout:'column',items:[{id:fieldIdPrefix+"updateTime",name:'updateTime',readOnly:true,hidden:true,fieldLabel:'最后更新时间',columnWidth:.87,style:'margin:1px'}]},
        	        	{items:[{name:'id',xtype:'hidden'}]}];
        },
        getModuleFormFieldIdPrefix:function(){
        	return this.module+".module.field.";
        },
        
        getModuleFormEncryptFieldIdPrefix:function(){
        	return this.getModuleFormFieldIdPrefix() + "encryptFieldArr.";
        },
        moduleFormEncryptFieldListener:function(me,t){
        	debugger;
    		var oldValue = me.getModuleForm().getForm().getValues()[t.getName()];
    		var newValue = t.getValue();
    		var encryptEle = Ext.getCmp(me.getModuleFormEncryptFieldIdPrefix() + t.getName());
				if(t.getValue() != ""){
					encryptEle.enable();
					debugger;
					if(oldValue != newValue && encryptEle.getValue()){
						me.toEncryptOrDecrypt(encryptEle, true, true);
					}
					
				}else{
					encryptEle.setValue(false);
					encryptEle.disable();
				}
        },
        getBaseModelFields:function(){
        	return ["id","valid","remark","createTime","createUserId","updateTime","updateUserId"];
        }
        
        /*****************************************************/
        ,
        getExtendGridTopBar:function(){
        	return [];
        }
    });  