Ext.define('App.modules.Category', {  
    extend: 'Ext.ux.desktop.Module',  
    requires: [  
	    'Ext.tree.*',
	    'Ext.data.*',
	    'Ext.window.MessageBox'
    ],  
    id:'category',
    code:'',
    name:'',
    init : function(){
    	 Ext.QuickTips.init();
        this.launcher = {  
            text: '类别管理',
            iconCls:'category'
        };  
    },  
    createWindow:function(){
    	var winid = MODULE_CODE + "-win";
    	var desktop = this.app.getDesktop();  
        var moduleWin = desktop.getWindow(winid);  
        var required = '<span style="color:red;font-weight:bold" data-qtip="必填">* </span>';
    	
    	if(!moduleWin){
	    	var me = this;
	    	var MODULE_ID = me.id.substring(me.id.indexOf("_") + 1);
	    	var MODULE_CODE = me.code;
	    	var ModuleObject = function(){
	    		this.api = {
	                        save  : __ctxPath + MODULE_CODE + '/save.do',
	                        load    : __ctxPath + MODULE_CODE + '/load.do',
	                        detail    : __ctxPath + MODULE_CODE + '/detail.do',
	                        remove : __ctxPath + MODULE_CODE + '/remove.do'
	    		};
	    		this.help = function(){
	    			Ext.MessageBox.show({
                        title: '使用说明',
                        msg: "左侧为树形菜单;右侧为查看/修改/增加控制面板;<br/>" +
                        		"1,左键单击树形菜单前面的'三角形',若该节点有子节点则加载;<br/>" +
                        		"2,左键单击树形菜单,该节点详细数据加载至右侧面板中,可编辑后保存;<br/>" +
                        		"3,右键单击树形菜单,快捷菜单中的'添加子类别',将在该节点下添加子节点,请在右侧的面板中录入数据;<br/>" +
                        		"4,右键单击树形菜单,快捷菜单中的'删除',若该节点有子节点,提示不能删除,否则根据提示可删除该节点;<br/>" +
                        		"5,右键单击树形菜单空白处时,可添加根节点;<br/>" +
                        		"6,有任何操作疑问,请联系系统运营人员;",
                        icon: Ext.MessageBox.INFO,
                        buttons : Ext.MessageBox.OK
                    });
	    		};
	    	};
	    	var mo = new ModuleObject();
	    	
	        Ext.define('Srx.model.Category', {  
	            extend:'Ext.data.Model',  
	            fields:[
					{name:'id'},{name:'code'},{name:'name'},
					{name:'parent'},{name:'leafs'},
					{name:'sort'},{name:'remark'},{name:'valid'},
					{name:'createTime'},{name:'modifyTime'}
	            ],
	            idProperty :"id",
	        });  
	        
	        var store = Ext.create('Ext.data.TreeStore', {
	        	model : 'Srx.model.Category',
	        	nodeParam : 'parentId',
	            proxy : new Ext.data.HttpProxy({
	    			url : mo.api.load,
	    			type: 'ajax',
	    			reader : {
	        			type: 'json',
//	        			root: 'list',
	        			idProperty: 'id'
	        		},
	    			actionMethods:{
	                    read: "POST",
	                }
	    		}),
	            root : {  
	                name : '类别管理',  
	                id : -1,  
	                expanded : true
	            },  
	            folderSort : true,  
	            sorters : [ {  
	                property : 'sort',  
	                direction : 'ASC'
	            } ] 
	        });

	        var tree = Ext.create('Ext.tree.Panel', {
	        	flex:4,
	            store: store,
	            rootVisible: false,
	            useArrows: true,
	            frame: true,
	            autoScroll:true,
	            hideHeaders: true,
	            columns: [
	                      { xtype: 'treecolumn',dataIndex: 'name', flex: 1 }
	                  ],
	            dockedItems: [{
	                xtype: 'toolbar',
	                items: {
	                    text: 'TODO',
	                    handler: function(){
	                        var records = tree.getView().getChecked(),
	                            names = [];
	                        
	                        Ext.Array.each(records, function(rec){
	                            names.push(rec.get('name'));
	                        });
	                        
	                        Ext.MessageBox.show({
	                            title: 'Selected Nodes',
	                            msg: names.join('<br />'),
	                            icon: Ext.MessageBox.INFO
	                        });
	                    }
	                }
	            }],listeners:{
	            	"itemclick":function(thiz, record, item, index, e, eOpts ){
	            		objectForm.getForm().load({
							url : mo.api.detail,
							method:"POST",
							params : {
								id : record.data.id
							},
							root : 'data',
							success : function(form, action) {
								var res = Ext.decode(action.response.responseText);
								var parent = res.data.parent;
								form.findField("parent.name").setValue(parent ? parent.name : "");
								form.findField("parent.id").setValue(parent ? parent.id : "");
								Ext.getCmp(MODULE_NAME + "_BTN_SAVE").enable();
								console.log(form.findField("id"));
							},
							failure : function(form, action) {
								Ext.Msg.alert("提示", "数据加载失败");
							}
						});
	            	},
	            	"itemcontextmenu":function(thiz, record, item, index, e, eOpts ){
	            		console.log(thiz, record, item, index, e, eOpts);
	            		e.preventDefault();
//	    				record.select();
	            		itemcontextmenu.showAt(e.getXY());
	            	},
	            	"containercontextmenu":function(thiz, e, eOpts ){
	            		console.log(thiz, e, eOpts);
	            		e.preventDefault();
//	    				record.select();
	            		containercontextmenu.showAt(e.getXY());
	            	}
	            }
	        });
	        
	    	var itemcontextmenu = new Ext.menu.Menu({
	    		items : [{
	    					text : '添加子类别',
	    					handler : function() {
	    						var node = tree.getSelectionModel().getLastSelected();

	    						Ext.getCmp(MODULE_CODE + "_BTN_SAVE").enable();
	    						
	    						objectForm.getForm().reset();
	    						objectForm.getForm().findField('parent.id').setValue(node.data.id);
	    						objectForm.getForm().findField('parent.name').setValue(node.data.name);
	    						objectForm.getForm().findField('sort').setValue(parseInt(node.data.leafs) + 1);
	    					}
	    				},{
	    					text : '删除',
	    					handler : function() {
	    						var node = tree.getSelectionModel().getLastSelected();
	    						if(!node){
	    							return;
	    						}
	    						if(node.data.leafs){
	    							Ext.MessageBox.show({
										title : "友情提示",
										msg : "当前选择目录存在子节点,请先删除子节点.",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
	    							return;
	    						}
	    						Ext.MessageBox.confirm("友情提示","数据可贵,谨慎操作.您确定要删除[<font color=red>" + node.data.name + "</font>]吗?",function(v){
	    							if(v == "yes"){
	    								Ext.Ajax.request({
	    									url : mo.api.remove,
	    									method : "POST",
	    									params : {
	    										id : node.data.id
	    									},
	    									timeout : 30000,// 30秒超时,
	    									waitTitle : "请稍候",
	    									waitMsg : "正在提交请求，请稍候......",
	    									success : function(o, c) {
	    										var res = Ext.decode(o.responseText);
	    										Ext.MessageBox.show({
	    													title : "友情提示",
	    													msg : res.msg,
	    													buttons : Ext.MessageBox.OK,
	    													icon : res.success ? Ext.MessageBox.INFO
	    															: Ext.MessageBox.ERROR
	    												});
	    										if(res.success){
	    											store.load();
	    										}
	    									}
	    								});
	    							}
	    						},this);
	    					}
	    				}]
	    	});
	    	var containercontextmenu = new Ext.menu.Menu({
	    		items : [{
	    					text : '添加类别',
	    					handler : function() {
	    						Ext.getCmp(MODULE_CODE + "_BTN_SAVE").enable();
	    						objectForm.getForm().reset();
	    						objectForm.getForm().findField('sort').setValue(tree.getRootNode().childNodes.length + 1);
	    					}
	    				}]
	    	});
	        var objectForm =  new Ext.form.FormPanel({
                flex:6,
                xtype: 'form',
                frame: false,
                bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
                fieldDefaults:{
                    labelStyle:'font-weight: sold;text-align:right',
                    labelWidth: 75
                },  
                defaultType:'textfield', 
                items: [new Ext.form.FieldSet({
            		collapsible : false,
            		anchor : '90%',
            		border:false,
            		items : [ {
            			layout : 'column',
            			border : false,
            			defaults:{
            				columnWidth:1.0,layout:'form',border:false
            			},
            			items:[
            			       {items:[{name:'id',xtype:'hidden'}]},
            			       {items:[{name:'parent.id',xtype:'hidden'}]},
            			       {items:[{name:'code',xtype:'textfield',fieldLabel:'代码',beforeLabelTextTpl:required,
            			    	   allowBlank:false,maxLength:50}]},
            			       {items:[{name:'name',xtype:'textfield',fieldLabel:'名称',beforeLabelTextTpl:required,
            			    	   allowBlank:false,maxLength:50}]},
            			       {items:[{name:'parent.name',xtype:'displayfield',fieldLabel:'所属类别'}]},
    			    	   	   {items:[{name:'sort',xtype:'numberfield',fieldLabel:'排序',allowDecimals:false,
    			    	   			minValue:1,maxValue:999,beforeLabelTextTpl:required,allowBlank:false}]},
    			    	   	   {
		       		                xtype: 'radiogroup',
		       		                fieldLabel: '是否启用',
		       		                layout:'column',
		       		                defaults: {
		       		                	columnWidth:.2, name: 'valid'
		       		                },
		       		                items: [{inputValue: '1',boxLabel: '启用',checked: true}, 
		       		                        {inputValue: '0',boxLabel: '禁用'}
		       		                ]
		       		           },
			       		       {items:[{name:'remark',xtype:'textarea',fieldLabel:'备注',maxLength:500}]},
			       		       {items:[{name:'createTime',xtype:'displayfield',fieldLabel:'创建时间'}]},
			       		       {items:[{name:'modifyTime',xtype:'displayfield',fieldLabel:'更新时间'}]}
        			    ]
            		}]
            	})],
            	buttonAlign:"center",
            	buttons:[{id:MODULE_CODE + "_BTN_SAVE",text:"保 存",pressed:true,disabled:true,handler:function(){
					console.log(objectForm.getForm().getValues());
					if(objectForm.getForm().isValid()){
						objectForm.getForm().submit({  
                            url: mo.api.save,  
                            submitEmptyText: false,  
                            waitTitle:'请等待',  
                            waitMsg: '正在提交...',  
                            success:function(form,action){
                            	var res = action.result;
                            	Ext.MessageBox.show({
									title : "提示",
									msg : res.msg,
									buttons : Ext.MessageBox.OK,
									icon : res.success ? Ext.MessageBox.INFO : Ext.MessageBox.ERROR
								});
                            	
                            	if(res.success){
                            		objectForm.getForm().reset();
                            		store.load();
                            	}
                            },  
                            failure:function(form,action){  
                                Ext.Msg.alert('提示',"error");
                            }  
                        });  
                    }else{  
                        Ext.Msg.alert("提示","请按提示正确录入.");  
                    }  
				}},
				{text:"重 置",pressed:true,handler:function(){objectForm.getForm().reset();}},
				{text:'说 明',pressed:true,handler:mo.help}]
            });
	        
	        
	        moduleWin = desktop.createWindow({
	            id: winid,
	            title:'类别管理',
	            width:900,  
	            height:500,
	            iconCls: MODULE_CODE + "_win",
	            animCollapse:false,
	            constrainHeader:true,
	            layout: {
	                type: 'hbox',
	                pack: 'start',
	                align: 'stretch'
	            },
	            items: [tree,objectForm],
	            tbar:toolbar
	        });
    	}
    	return moduleWin;
    }  
});  