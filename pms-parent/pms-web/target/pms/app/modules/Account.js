Ext.define('App.modules.Account', {  
    extend: 'App.extend.ModuleWindow',
        
        getModuleModelFields:function(){
        	return ["name","desc","address","userName","password","phoneNo","type","lastUseDate","enable","privateKey","promptMsg","typeId","encryptFields"];
        },
        getModuleQueryFormFields:function(){
        	var me = this;
        	return [
        	        	{items:[{name:'name',xtype:'textfield',fieldLabel:'账户名称'}]},
        	        	{items:[{  
    		                xtype: 'combo',
    		                id:me.module+".module.query.type",
    		                name:"typeId",
    		                fieldLabel: '账户类别',
    		                anchor:'100%',
    		                editable:false,
    		                emptyText:"请选择",
    		                valueField:"id",
    		                displayField:"name",
    		                hiddenName:"typeId",
    		                triggerAction : "all",
    		                store:me.getAccountTypeStore(),
    		                trigger1Cls:'x-form-clear-trigger',    
    		                trigger2Cls:'x-form-arrow-trigger',  
    		                onTrigger1Click:function(){this.clearValue();this.fireEvent('clear',this);},  
    		                onTrigger2Click:function(){this.onTriggerClick();}
    		            	}
        	        	]},
    			       
        	        	{items:[{name:'userName',xtype:'textfield',fieldLabel:'访问用户'}]},
        	        	{items:[{  
    			                xtype: 'combo',
    			                name:"enable",
    			                fieldLabel: '是否可用',
    			                anchor:'100%',
    			                editable:false,
    			                emptyText:"请选择",
    			                valueField:"id",
    			                displayField:"name",
    			                hiddenName:"enable",
    			                triggerAction : "all",
    			                store:me.getBooleanStore(),
    			                trigger1Cls:'x-form-clear-trigger',    
    			                trigger2Cls:'x-form-arrow-trigger',  
    			                onTrigger1Click:function(){this.clearValue();this.fireEvent('clear',this);},  
    			                onTrigger2Click:function(){this.onTriggerClick();}
    			            }
        	        	]},
        	        	{items:[{name:'phoneNo',xtype:'textfield',fieldLabel:'手机号码'}]},
        	        	{items:[{name:'address',xtype:'textfield',fieldLabel:'访问地址'}]},
    			    ];
        },
        getModuleResultGridColumns:function(){
        	var me = this;
        	return [new Ext.grid.RowNumberer(),
    			{text:"账户名称",flex:1,dataIndex:'name',align:"center"},
    			{text:"账户类型",flex:1,dataIndex:'type',align:"center",renderer:function(item){return item && item.name ? item.name : "";}},
    			{text:"访问地址",flex:2,dataIndex:'address',align:"center"},
    			//{text:"访问用户",flex:1,dataIndex:'userName',align:"center"},
    			//{text:"访问口令",flex:1,dataIndex:'password',align:"center"},
    			{text:"手机号码",flex:2,dataIndex:'phoneNo',align:"center",renderer:function(item){return item ? item.replace(/^(\d{4})\d+(\d{4})$/, "$1****$2") : "";}},
    			{text:"可用",flex:.5,dataIndex:'enable',align:"center",renderer:function(item){return item && item > 0 ? "是" : "否";}},
    			//{text:"创建时间",flex:1,dataIndex:'createTime',align:"center"},
    			//{text:"更新时间",flex:1,dataIndex:'modifyTime',align:"center"}
    			];
        },
        getAccountTypeStore:function(){
        	var me = this;
        	var storeId = "ACCOUNT_TYPE_STORE";
        	var res = Ext.getStore(storeId);
        	if(!res){
        		res = Ext.create('Ext.data.Store', {
        			storeId:storeId,
        			autoLoad: true,
                    proxy : new Ext.data.HttpProxy({
            			url : me.getAPI().loadDict,
            			type: 'ajax',
            			reader : {
                			type: 'json',
                			root: 'data',
                			idProperty: 'id'
                		},
            			actionMethods:{
                            read: "POST",
                        },
                        extraParams:{dataTypeCode:"ACCOUNT_TYPE"}
            		}),
                    fields:["id","name"],
                    listeners:{  
                        load:function(){
                        	debugger;
                        	var comb_type = Ext.getCmp(me.module+".module.field.type");
                        	if(comb_type){
                        		comb_type.setValue(comb_type.getValue());
                        	}
                        }  
                    }
                });
        	}
        	return res;
        },
        getModuleFormFields:function(){
        	debugger;
        	var me = this;
        	var fieldIdPrefix = me.getModuleFormFieldIdPrefix();
        	var encryptFieldIdPrefix = me.getModuleFormEncryptFieldIdPrefix();
        	var moduleFields = [
    						   {        
    								layout:'column',
    								items:[		{
    										   		name:'name',
    										   		xtype:'textfield',
    										   		fieldLabel:'账户名称',
    										   		emptyText:"请输入账户名称",
    										   		beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
    										   		allowBlank:false,
    										   		maxLength:100,
    										   		columnWidth:.87,
    										   		style:'margin:5px 1px 1px 1px;'
    										   }]
    					       },
    					       {	layout:'column',
   					    	   	items:[{xtype: 'combo',
   						                id:fieldIdPrefix+"type",
   						                name:"typeId",
   						                fieldLabel: '账户类别',
   						                anchor:'100%',
   						                editable:false,
   						                beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
   						                allowBlank:false,
   						                emptyText:"请选择",
   						                valueField:"id",
   						                displayField:"name",
   						                hiddenName:"typeId",
   						                triggerAction : "all",
   						                store:me.getAccountTypeStore(),
   						                columnWidth:.87,style:'margin:1px'}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{name:'address',fieldLabel:'访问地址',maxLength:50,columnWidth:.87,style:'margin:1px'}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{name:'desc',xtype:'textarea',fieldLabel:'账户描述',maxLength:500,columnWidth:.87,style:'margin:1px'}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{
    					    	   			id:fieldIdPrefix+"userName",
    					    	   			name:'userName',
    					    	   			fieldLabel:'访问用户',
    					    	   			beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
    					    	   			allowBlank:false,
    					    	   			maxLength:50,
    					    	   			columnWidth:.87,
    					    	   			style:'margin:1px',
    					    	   			listeners: {
    						   					blur:function(){me.moduleFormEncryptFieldListener(me,this)}
    						   				}},
    						   				{
    						    	   			id:fieldIdPrefix+"userName.hide",
    						    	   			name:'userName_hide',
    						    	   			inputType:"password",
    						    	   			hidden:true,
    						    	   			fieldLabel:'访问用户',
    						    	   			beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
    						    	   			columnWidth:.87,
    						    	   			style:'margin:1px',
    						    	   			listeners: {
    						    	   				focus:function(thiz, the, eOpts){
    						    	   					Ext.getCmp(encryptFieldIdPrefix+"userName").setValue(false);
    							   					}
    							   				}},
    					    	   	       {xtype:'checkboxfield',inputValue:'userName',name:'encryptFieldArr',id:encryptFieldIdPrefix+"userName",columnWidth:.13,boxLabel:'加密',style:'margin:1px',disabled:true,
    							    	   		listeners: {
    							    	   			change:function(thiz, newValue, oldValue, eOpts){
    							    	   				me.toEncryptOrDecrypt(thiz, newValue, oldValue, eOpts);
    							   					}
    							   				},}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{	
    					    	   				id:fieldIdPrefix+"password",
    					    	   				name:'password',
    					    	   				fieldLabel:'访问口令',
    					    	   				maxLength:50,
    					    	   				columnWidth:.87,
    					    	   				style:'margin:1px',
    							   				listeners: {
    							   					blur:function(){me.moduleFormEncryptFieldListener(me,this)}
    							   				}
    							    	   	},{
    						    	   			id:fieldIdPrefix+"password.hide",
    						    	   			name:'password_hide',
    						    	   			inputType:"password",
    						    	   			hidden:true,
    						    	   			fieldLabel:'访问口令',
    						    	   			columnWidth:.87,
    						    	   			style:'margin:1px',
    						    	   			listeners: {
    						    	   				focus:function(thiz, the, eOpts){
    						    	   					Ext.getCmp(encryptFieldIdPrefix+"password").setValue(false);
    							   					}
    							   				}
    							    	   	},{	
    							    	   		xtype:'checkboxfield',
    							    	   		id:encryptFieldIdPrefix+"password",
    							    	   		inputValue:'password',
    							    	   		name:'encryptFieldArr',
    							    	   		columnWidth:.13,
    							    	   		boxLabel:'加密',
    							    	   		style:'margin:1px',
    							    	   		disabled:true,
    							    	   		listeners: {
    							   					change:function(thiz, newValue, oldValue, eOpts){
    							   						me.toEncryptOrDecrypt(thiz, newValue, oldValue, eOpts);
    							   					}
    							   				},
    							    	   }]
    					       },
    					       
    					       {	layout:'column',
   					    	   	items:[{	
   					    	   				id:fieldIdPrefix+"phoneNo",
   					    	   				name:'phoneNo',
   					    	   				fieldLabel:'手机号码',
   					    	   				maxLength:50,
   					    	   				columnWidth:.87,
   					    	   				style:'margin:1px',
   							   				listeners: {
   							   					blur:function(){me.moduleFormEncryptFieldListener(me,this)}
   							   				}
   							    	   	},{
   						    	   			id:fieldIdPrefix+"phoneNo.hide",
   						    	   			name:'phoneNo_hide',
   						    	   			inputType:"phoneNo",
   						    	   			hidden:true,
   						    	   			fieldLabel:'手机号码',
   						    	   			columnWidth:.87,
   						    	   			style:'margin:1px',
   						    	   			listeners: {
   						    	   				focus:function(thiz, the, eOpts){
   						    	   					Ext.getCmp(encryptFieldIdPrefix+"phoneNo").setValue(false);
   							   					}
   							   				}
   							    	   	},{	
   							    	   		xtype:'checkboxfield',
   							    	   		id:encryptFieldIdPrefix+"phoneNo",
   							    	   		inputValue:'phoneNo',
   							    	   		name:'encryptFieldArr',
   							    	   		columnWidth:.13,
   							    	   		boxLabel:'加密',
   							    	   		style:'margin:1px',
   							    	   		disabled:true,
   							    	   		listeners: {
   							   					change:function(thiz, newValue, oldValue, eOpts){
   							   						me.toEncryptOrDecrypt(thiz, newValue, oldValue, eOpts);
   							   					}
   							   				},
   							    	   }]
   					       },
    					       {	layout:'column',
    					    	   	items:[{id:fieldIdPrefix+"privateKey",name:'privateKey',fieldLabel:'密钥',maxLength:50,columnWidth:.87,style:'margin:1px',listeners:{blur:function(){me.moduleFormEncryptFieldListener(me,this)}}},
    							    	   {
    						    	   			id:fieldIdPrefix+"privateKey.hide",
    						    	   			name:'privateKey_hide',
    						    	   			inputType:"password",
    						    	   			hidden:true,
    						    	   			fieldLabel:'密钥',
    						    	   			columnWidth:.87,
    						    	   			style:'margin:1px',
    						    	   			listeners: {
    						    	   				focus:function(thiz, the, eOpts){
    						    	   					Ext.getCmp(encryptFieldIdPrefix+"privateKey").setValue(false);
    							   					}
    							   				}
    							    	   	},
    					    	   	       {xtype:'checkboxfield',inputValue:'privateKey',name:'encryptFieldArr',id:encryptFieldIdPrefix+"privateKey",columnWidth:.13,boxLabel:'加密',style:'margin:1px',
    							    	   		listeners: {
    							   					change:function(thiz, newValue, oldValue, eOpts){
    							   						me.toEncryptOrDecrypt(thiz, newValue, oldValue, eOpts);
    							   					}
    							   				},
    					    	   	       }
    							    ]
    					       },
    					       {	layout:'column',
    					    	   	items:[{name:'promptMsg',fieldLabel:'提示信息',maxLength:50,columnWidth:.87,style:'margin:1px'}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{name:'lastUseDate',xtype:'datefield',fieldLabel:'最近使用日期',format:'Y-m-d H:i:s',editable:false,columnWidth:.87,style:'margin:1px'}]
    					       },
    					       {	layout:'column',
    					    	   	items:[{xtype:"radiogroup",fieldLabel:'是否可用',beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,allowBlank:false,
    							    	   items: [{name:'enable',inputValue: '1',boxLabel: '是',checked: true}, 
    							                   {name:'enable',inputValue: '0',boxLabel: '否'}]}
    					    	   	       ]
    					       }
    			    ];
        	return moduleFields.concat(me.getModuleFormBaseFields());
        }
});  