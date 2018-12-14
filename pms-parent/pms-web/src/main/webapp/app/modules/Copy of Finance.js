Ext.define('App.modules.Finance', {  
    extend: 'Ext.ux.desktop.Module',  
    requires: [  
	    'Ext.data.JsonStore',
	    'Ext.util.Format',
	    'Ext.grid.Panel',
	    'Ext.grid.RowNumberer'
    ],  
    id:'finance',
    init : function(){
        this.launcher = {  
            text:this.name,
            iconCls:this.code
        };  
    },  
    createWindow:function(){
    	var desktop = this.app.getDesktop();  
        var moduleWin = desktop.getWindow(this.code + "-win");  
        var required = '<span style="color:red;font-weight:bold" data-qtip="必填">* </span>';
    	
    	if(!moduleWin){
    		
    		/*收支类型*/
    		var typeStore = new Ext.data.SimpleStore({data:[[1,"收入"],[0,"支出"]],fields:["id","name"]});
    		
    		var me = this;
	    	var MODULE_ID = me.id.substring(me.id.indexOf("_") + 1);
	    	var MODULE_CODE = me.code;
	    	var MODULE_NAME = me.name;
	    	/*定义MODEL*/
	    	Ext.define('Srx.model.' + MODULE_CODE, {  
	            extend:'Ext.data.Model',  
	            fields:["id","amount","payWay","payer","payee","usedFor","dealTime","dealAddress",
	                    "witness","type","privateKey","promptMsg","category","createUser","fromUser","toUser",
	                    "createTime","modifyTime"]
	        });
	    	/*模块封装对象*/
	    	var ModuleObject = function(){
	    		this.api = {
	    				loadCategory  : __ctxPath + MODULE_CODE + '/categoryList.do',
	    				loadPayWay  : __ctxPath + '/dictionary/payWayList.do',
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
    		var categoryStore = Ext.create('Ext.data.TreeStore', {
	        	nodeParam : 'parentId',
	            proxy : new Ext.data.HttpProxy({
	    			url : mo.api.loadCategory,
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
	    		multiSelect:false,
	            root : {  
//	                name : "a",  
	                id : -1,  
	                expanded : true
	            },  
	            folderSort : true,  
	            sorters : [ {  
	                property : 'sort',  
	                direction : 'ASC'
	            } ],
	            fields:[{name:"id"},{name:"text",mapping:"name"},{name:"parentId"}],
	        });
    		var payWayStore = Ext.create('Ext.data.Store', {
    			autoLoad: true,
	            proxy : new Ext.data.HttpProxy({
	    			url : mo.api.loadPayWay,
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
	            fields:["id","name"],
	        });
    		
    		
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
								{
									   xtype: 'radiogroup',
									   fieldLabel: '收入/支出',
									   layout:'column',
									   allowBlank:false,
									   blankText:"请选择[收入/支出]",
									   
									   defaults: {columnWidth:.2,name:'type'},
									   beforeLabelTextTpl:required,
									   items:[
									          {inputValue: '1',boxLabel: '收入'}, 
									          {inputValue: '0',boxLabel: '支出'}
									   ]
								},
								{items:[{  
							                xtype: 'combotree',  
							                name: 'comboboxtree', 
							                fieldLabel: '类别',
							                anchor:'100%',
							                beforeLabelTextTpl:required,
							                editable:false,
							                allowBlank:false,
							                emptyText:"请选择[收入/支出]",
							                labelStyle:'font-weight: sold;text-align:right',
							                labelWidth: 75,
							                maxPickerHeight:160,
							                store:categoryStore
							            }
								]},
	        			       {items:[{
	        			    	   name:'amount',xtype:'numberfield',fieldLabel:'金额',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,minValue: 0.01}]},
	        			    	   {items:[{name:'usedFor',xtype:'textfield',fieldLabel:'用途',maxLength:100}]},
	        			       {items:[{name:'payer',xtype:'textfield',fieldLabel:'付款人',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,maxLength:50}]},
        			    	   {items:[{name:'fromUser.id',xtype:'numberfield',fieldLabel:'支付方编号'}]},
	        			       {items:[{name:'payee',xtype:'textfield',fieldLabel:'收款方',beforeLabelTextTpl:required,
	        			    	   allowBlank:false,maxLength:50}]},
	        			       {items:[{name:'toUser.id',xtype:'numberfield',fieldLabel:'收款方编号'}]},
	        			       {items:[{  
						                xtype: 'combo',
						                fieldLabel: '支付方式',
						                anchor:'100%',
						                beforeLabelTextTpl:required,
						                editable:false,
						                allowBlank:false,
						                emptyText:"请选择[支付方式]",
						                valueField:"id",
						                displayField:"name",
						                hiddenName:"payWay",
						                triggerAction : "all",
						                store:payWayStore
						            }
								]},
	        			       
	        			       {items:[{name:'dealTime',xtype:'datetimefield',fieldLabel:'成交时间',increment: 30,format:'Y-m-d H:i:s',}]},
	        			    	   
	        			       {items:[{name:'dealAddress',xtype:'textfield',fieldLabel:'成交地点',maxLength:50}]},
	        			       {items:[{name:'witness',xtype:'textfield',fieldLabel:'知情人',maxLength:50}]},
	        			       {items:[{name:'privateKey',xtype:'textfield',fieldLabel:'加密密钥',maxLength:50}]},
	        			       {items:[{name:'promptMsg',xtype:'textfield',fieldLabel:'提示信息',maxLength:50}]},
	        			       
	        			       {
		       		                xtype: 'radiogroup',
		       		                fieldLabel: '是否有效',
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
	    			title : "添加财务记录",
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
	                labelWidth: 80
	            },  
	            defaultType:'textfield', 
	            buttonAlign:'center',
	            buttons:[{text:'查询',pressed:true,width:80,handler:function(){mo.list(this);}},
	                     {text:'重置',pressed:true,width:80,handler:function(){queryForm.getForm().reset();}} 
	            ],keys : [ {
	    			key : 13,
	    			fn : function(){mo.list(this);}
	    		} ],
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
	    			       {items:[{name:'payer',xtype:'textfield',fieldLabel:'付款人'}]},
	    			       {items:[{name:'payee',xtype:'textfield',fieldLabel:'收款人'}]},
	    			       {items:[{  
					                xtype: 'combo',
					                fieldLabel: '收支类型',
					                editable:false,
					                emptyText:"请选择",
					                valueField:"id",
					                displayField:"name",
					                hiddenName:"type",
					                triggerAction : "all",
					                store:typeStore
					            }
							]},
	    			       
	    			       {items:[{  
					                xtype: 'combo',
					                fieldLabel: '支付方式',
					                editable:false,
					                emptyText:"请选择",
					                valueField:"id",
					                displayField:"name",
					                hiddenName:"payWay",
					                triggerAction : "all",
					                store:payWayStore
					            }
							]},
							{items:[{name:'amount1',xtype:'numberfield',fieldLabel:'金额(>=)',minValue:0.01,emptyText:"元"}]},
							{items:[{name:'amount2',xtype:'numberfield',fieldLabel:'金额(<)',minValue:0.01,emptyText:"元"}]},
//	    			       {items:[{name:'dealTime1',xtype:'datetimefield',fieldLabel:'成交时间(>=)',increment: 30,format:'Y-m-d H:i:s',}]},
//	    			       {items:[{name:'dealTime2',xtype:'datetimefield',fieldLabel:'成交时间(<)',increment: 30,format:'Y-m-d H:i:s',}]},
	    			       {items:[{name:'createTime1',xtype:'datetimefield',fieldLabel:'记录时间(>=)',increment: 30,format:'Y-m-d H:i:s',}]},
	    			       {items:[{name:'createTime2',xtype:'datetimefield',fieldLabel:'记录时间(<)',increment: 30,format:'Y-m-d H:i:s',}]}
	    			    ]
	        		}]
	        	})] 
	        });
	        var gridStore = Ext.create('Ext.data.Store', {  
	            autoLoad: true,
	            autoDestroy: true,
	            storeId: MODULE_CODE + 'Store',  
	            model:'Srx.model.' + MODULE_CODE,
	            pageSize:mo.pageSize,
	            proxy : new Ext.data.HttpProxy({
	    			url : mo.api.list,
	    			type: 'ajax',
	    			reader : {
	        			type: 'json',
	        			root: 'data.list',
	        			totalProperty:"data.total",
	        			idProperty: 'id'
	        		},
	    			actionMethods:{
	                    read: "POST",
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
	        console.log(MODULE_CODE);
	        var resultGrid = Ext.create('Ext.grid.Panel',{
	        	title:'结果列表',
	        	anchor : "100% 70%",
	        	store: gridStore, 
	    		autoScroll : true,
	    		stateful: true,
	            multiSelect: true,
	            columnLines : true,
	            stripeRows : true,
	            frame : true,
	    		viewConfig : {
	    			emptyText : '没有您想要的数据！',
	    			loadMask : true,
		            stripeRows: true,
		            enableTextSelection: true
	    		},
	    		loadMask : {
	    			msg : "正在加载数据，请稍侯……"
	    		},
	    		
	            columns: [new Ext.grid.RowNumberer(),
					{text:"付款人",dataIndex:'payer',align:"center"},
					{text:"收款人",dataIndex:'payee',align:"center"},
					{text:"金额",flex:1,dataIndex:'amount',align:"center"},
					{text:"支付方式",flex:1,dataIndex:'payWay',align:"center"},
					{text:"收支类型",flex:.5,dataIndex:'type',align:"center",renderer:function(v){return v && v > 0 ? "收入" : "支出"}},
					{text:"用途",flex:1,dataIndex:'usedFor',align:"center"},
//					{text:"成交地点",dataIndex:'dealAddress',align:"center"},
//					{text:"成交时间",dataIndex:'dealTime',align:"center"},
					{text:"记录人",flex:1,dataIndex:'createUser',align:"center"},
					{text:"创建时间",dataIndex:'createTime',align:"center"},
					{
		                menuDisabled: true,
		                sortable: false,
		                xtype: 'actioncolumn',
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