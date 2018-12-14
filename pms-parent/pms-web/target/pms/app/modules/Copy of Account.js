Ext.define('App.modules.Account', {  
    extend: 'Ext.ux.desktop.Module',  
    requires: [  
	    'Ext.data.JsonStore',
	    'Ext.util.Format',
	    'Ext.grid.Panel',
	    'Ext.grid.RowNumberer'
    ],  
    id:'account',
    init : function(){
    	debugger;
        this.launcher = {  
            text:this.name,
            iconCls:this.icon
        };  
    },  
    createWindow:function(){
    	var desktop = this.app.getDesktop();  
        var moduleWin = desktop.getWindow(this.code + "-win");  
        var required = '<span style="color:red;font-weight:bold" data-qtip="必填">* </span>';
    	
    	if(!moduleWin){
	    	function add(){
	    		var moduleForm =  new Ext.form.FormPanel({
	    			anchor:"100% 100%",
	                flex:1,
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
	        			       {items:[{
	        			    	   name:'name',xtype:'textfield',fieldLabel:'账户名称',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,maxLength:100}]},
	        			       {items:[{name:'address',xtype:'textfield',fieldLabel:'访问地址',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,maxLength:100}]},
	        			       {items:[{name:'userName',xtype:'textfield',fieldLabel:'访问用户',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,maxLength:50}]},
	        			       {items:[{name:'password',xtype:'textfield',fieldLabel:'访问口令',maxLength:50}]},
	        			       {items:[{name:'category.id',xtype:'textfield',fieldLabel:'账户类别'}]},
	        			       {items:[{name:'privateKey',xtype:'textfield',fieldLabel:'密钥',maxLength:50}]},
	        			       {items:[{name:'promptMsg',xtype:'textfield',fieldLabel:'提示信息',maxLength:50}]},
	        			       
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
	        			    ]
	            		}]
	            	})] 
	            });
	    		var win = new Ext.Window({
	    			title : "添加账户",
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
	    			listeners:{'beforeshow':function(){win.center();}},
	    			buttons : [
						{text:"保 存",pressed:true,handler:function(){
							console.log(moduleForm.getForm().getValues());
							if(moduleForm.getForm().isValid()){
								moduleForm.getForm().submit({  
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
	                                		win.close();
	                                		gridStore.load();
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
						{text:"关 闭",pressed:true,handler:function(){win.close();}}
	    			]
	    		});
	    		win.show();
	    	};
	    	var me = this;
	    	var MODULE_ID = me.id.substring(me.id.indexOf("_") + 1);
	    	var MODULE_CODE = me.code;
	    	var MODULE_NAME = me.name;
	    	var ModuleObject = function(){
	    		this.api = {
	                        save  : __ctxPath + MODULE_CODE + '/save.do',
	                        list    : __ctxPath + MODULE_CODE + '/list.do',
	                        update  : __ctxPath + MODULE_CODE + '/update.do', 
	                        remove : __ctxPath + MODULE_CODE + '/remove.do'
	    		};
	    		this.pageSize = 20;
	    		this.add = function(){
	    			add();
	    		};
	    		this.remove = function(){
	    			alert("remove");
	    		};
	    		this.update = function(){
	    			alert("update");
	    		};
	    		this.list = function(o){
	    			console.log(o,resultGrid);
	    			
					var loadTip = new Ext.LoadMask(Ext.getBody(), {
						msg : "正在加载数据，请稍后..."
					});
					loadTip.show();
					resultGrid.getStore().load({
						params : {
							start : 0,
							limit : mo.pageSize
						}
					});
					loadTip.hide();
					/**/
	    		};
	    		this.help = function(){
	    			
	    			tooltip:"顶部为菜单工具;中上部为筛选条件[非必选],单击[查询]请求数据;<br/>中下部表格装载请求到的数据;<br/>底部为分页工具;",
	    			
	    			Ext.MessageBox.show({
                        title: '使用说明',
                        msg: "顶部为菜单工具,中上部为筛选条件,中下部表格装载请求到的数据,底部为分页工具;<br/>" +
                        		"1,录入筛选条件[非必填],左键单击'查询';<br/>" +
                        		"2,数据表格中将根据筛选条件加载数据;<br/>" +
                        		"3,底部分页菜单用于适应用户阅读需求及习惯;<br/>" +
                        		"4,有任何操作疑问,请联系系统运营人员;",
                        icon: Ext.MessageBox.INFO,
                        buttons : Ext.MessageBox.OK
                    });
	    		};
	    	};
	    	var mo = new ModuleObject();
	    	
	        Ext.define('Srx.model.Account', {  
	            extend:'Ext.data.Model',  
	            fields:[
					{name:'id'},{name:'name'},{name:'address'},{name:'userName'},{name:'password'},
					{name:'privateKey'},{name:'promptMsg'},{name:'remark'},{name:'valid'},
					{name:'createTime'},{name:'modifyTime'}
	            ]  
	        });  
	        var gridStore = Ext.create('Ext.data.Store', {  
	            autoLoad: true,
	            autoDestroy: true,
	            storeId: 'accountStore',  
	            model:'Srx.model.Account',
	            pageSize:mo.pageSize,
	            proxy : new Ext.data.HttpProxy({
	    			url : mo.api.list,
	    			type: 'ajax',
	    			reader : {
	        			type: 'json',
	        			root: 'data.list',
	        			idProperty: 'id'
	        		},
	    			actionMethods:{
	                    create: "POST",
	                    read: "POST",
	                    update: "POST",
	                    destroy: "POST"
	                }
	    		}),
		        listeners:{
					beforeload:{
						fn:function(s,o,e) {
							Ext.apply(s.proxy.extraParams, queryForm.getForm().getValues()); 
						}
					}
		        }
	        });
	        
	        var queryForm =  Ext.widget({
	        	anchor:"100% 30%",
	            flex:1,
	            title:'查询条件',
	            xtype: 'form',
	            frame: false,
	            id: 'queryForm',
	//            bodyPadding: 10,
	            bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
	            layout:'anchor',
	            fieldDefaults:{
	                labelStyle:'font-weight: sold;text-align:right',
	                labelWidth: 70
	            },  
	            defaultType:'textfield', 
	            buttonAlign:'center',
	            buttons:[{text:'查询',pressed:true,width:80,handler:function(){mo.list(this);}},
	                     {text:'重置',pressed:true,width:80,handler:function(){queryForm.getForm().reset();}} 
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
	        			items:[
	    			       {items:[{name:'name',xtype:'textfield',fieldLabel:'账户名称'}]},
	    			       {items:[{name:'address',xtype:'textfield',fieldLabel:'访问地址'}]},
	    			       {items:[{name:'userName',xtype:'textfield',fieldLabel:'访问用户'}]},
	    			       {items:[{name:'valid',xtype:'textfield',anchor:'100%',fieldLabel:'是否可用'}]}
	    			    ]
	        		}]
	        	})] 
	        });
	        var resultGrid = Ext.create('Ext.grid.Panel',{
	        	title:'结果列表',
	        	anchor : "100% 70%",
	        	store: gridStore, 
	    		autoScroll : true,
	    		stateful: true,
	    		
	            multiSelect: true,
	    		viewConfig : {
	    			emptyText : '没有您想要的数据！',
	    			loadMask : true,
		            stripeRows: true,
		            enableTextSelection: true
	    		},
	    		loadMask : {
	    			msg : "正在加载数据，请稍侯……"
	    		},
	    		
//	            layout: 'column',
	            columns: [new Ext.grid.RowNumberer(),
					{text:"账户名称",flex:1,dataIndex:'name',align:"center"},
					{text:"访问地址",flex:2,dataIndex:'address',align:"center"},
					{text:"访问用户",flex:1,dataIndex:'userName',align:"center"},
					{text:"访问口令",flex:1,dataIndex:'password',align:"center"},
					{text:"可用",flex:.5,dataIndex:'valid',align:"center",renderer:function(v){return v && v == 0 ? "否" : "是"}},
					{text:"创建时间",flex:1,dataIndex:'createTime',align:"center"},
					{text:"更新时间",flex:1,dataIndex:'modifyTime',align:"center"},
					{
		                menuDisabled: true,
		                sortable: false,
		                xtype: 'actioncolumn',
//		                flex:1,
		                align:"center",
		                items: [{
		                    icon   : __ctxPath + '/app/resources/shared/icons/fam/delete.gif',
		                    tooltip: '删除',
		                    handler: function(grid, rowIndex, colIndex) {
		                        var rec = store.getAt(rowIndex);
		                        alert("真的要删除[ " + rec.get('name') + "]吗?");
		                    }
		                }]
		            }
	            ],
	            bbar: Ext.create('Ext.PagingToolbar', {
	                store: gridStore,
	                displayInfo: true
	            })
	        });  
	            
	        var toolbar = new Ext.Toolbar({
	    		items:[ 
					{text:'添 加',pressed:true,tooltip:"添加",handler:function(){mo.add();}},
					{text:'说 明',pressed:true,tooltip:"查看使用说明",handler:mo.help}
	    		]
	    	});
	        
	        moduleWin = desktop.createWindow({
	            id: MODULE_CODE + "-win",
	            title:MODULE_NAME,
	            width:900,  
	            height:500,
	            iconCls: MODULE_CODE + "_win",
	            animCollapse:false,
	            constrainHeader:true,
	            layout: 'anchor',
	            items: [queryForm,resultGrid],
	            tbar:toolbar
	        });
    	}
    	return moduleWin;
    }  
});  