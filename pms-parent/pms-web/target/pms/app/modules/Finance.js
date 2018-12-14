Ext.define('App.modules.Finance', {  
    extend: 'App.extend.ModuleWindow',
        
        getModuleModelFields:function(){
        	return ["amount","payWay","payer","payee","usedFor","dealTime","dealAddress","witness"
        	        ,"type","privateKey","promptMsg","typeId","encryptFields"];
        },
        getModuleQueryFormFields:function(){
        	var me = this;
        	return [
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
				         store:me.getTypeStore(),
				         trigger1Cls:'x-form-clear-trigger',    
 		                 trigger2Cls:'x-form-arrow-trigger',  
 		                 onTrigger1Click:function(){this.clearValue();this.fireEvent('clear',this);},  
 		                 onTrigger2Click:function(){this.onTriggerClick();}
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
				         store:me.getPayWayStore(),
	 		                trigger1Cls:'x-form-clear-trigger',    
	 		                trigger2Cls:'x-form-arrow-trigger',  
	 		                onTrigger1Click:function(){this.clearValue();this.fireEvent('clear',this);},  
	 		                onTrigger2Click:function(){this.onTriggerClick();}
				     }
					]},
					{items:[{name:'amount1',xtype:'numberfield',fieldLabel:'金额(>=)',minValue:0.01,emptyText:"元"}]},
					{items:[{name:'amount2',xtype:'numberfield',fieldLabel:'金额(<)',minValue:0.01,emptyText:"元"}]},
					//{items:[{name:'dealTime1',xtype:'datetimefield',fieldLabel:'成交时间(>=)',increment: 30,format:'Y-m-d H:i:s',}]},
					//{items:[{name:'dealTime2',xtype:'datetimefield',fieldLabel:'成交时间(<)',increment: 30,format:'Y-m-d H:i:s',}]},
					//{items:[{name:'createTime1',xtype:'datetimefield',fieldLabel:'记录时间(>=)',increment: 30,format:'Y-m-d H:i:s',}]},
					//{items:[{name:'createTime2',xtype:'datetimefield',fieldLabel:'记录时间(<)',increment: 30,format:'Y-m-d H:i:s',}]}

    		];
        },
        getModuleResultGridColumns:function(){
        	var me = this;
        	return [new Ext.grid.RowNumberer(),
    			{text:"付款人",dataIndex:'payer',align:"center"},
				{text:"收款人",dataIndex:'payee',align:"center"},
				{text:"金额",flex:1,dataIndex:'amount',align:"center"},
				{text:"支付方式",flex:1,dataIndex:'payWay',align:"center"},
				{text:"收支类型",flex:.5,dataIndex:'type',align:"center",renderer:function(v){return v && v > 0 ? "收入" : "支出"}},
				{text:"用途",flex:1,dataIndex:'usedFor',align:"center"},
//				{text:"成交地点",dataIndex:'dealAddress',align:"center"},
//				{text:"成交时间",dataIndex:'dealTime',align:"center"},
				{text:"记录人",flex:1,dataIndex:'createUser',align:"center"},
				{text:"创建时间",dataIndex:'createTime',align:"center"}
    			];
        },
        getTypeStore:function(){
        	var me = this;
        	var storeId = "FINANCE_TYPE_STORE";
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
        getPayWayStore:function(){
        	var me = this;
        	var storeId = "FINANCE_PAY_WAY_STORE";
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
                        extraParams:{dataTypeCode:"PAY_WAY"}
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
	   xtype: 'radiogroup',
	   fieldLabel: '收入/支出',
	   layout:'column',
	   allowBlank:false,
	   blankText:"请选择[收入/支出]",
	   
	   defaults: {columnWidth:.2,name:'type'},
	   beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
	   items:[
	          {inputValue: '1',boxLabel: '收入'}, 
	          {inputValue: '0',boxLabel: '支出'}
	   ]
},
/*
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
]},*/{items:[{
	   name:'amount',xtype:'numberfield',fieldLabel:'金额',beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
	   allowBlank:false,minValue: 0.01}]},
	   {items:[{name:'usedFor',xtype:'textfield',fieldLabel:'用途',maxLength:100}]},
   {items:[{name:'payer',xtype:'textfield',fieldLabel:'付款人',beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
	   allowBlank:false,maxLength:50}]},
   {items:[{name:'fromUser.id',xtype:'numberfield',fieldLabel:'支付方编号'}]},
   {items:[{name:'payee',xtype:'textfield',fieldLabel:'收款方',beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
	   allowBlank:false,maxLength:50}]},
   {items:[{name:'toUser.id',xtype:'numberfield',fieldLabel:'收款方编号'}]},
   {items:[{  
            xtype: 'combo',
            fieldLabel: '支付方式',
            anchor:'100%',
            beforeLabelTextTpl:me.REQUIRED_FIELD_HTML,
            editable:false,
            allowBlank:false,
            emptyText:"请选择[支付方式]",
            valueField:"id",
            displayField:"name",
            hiddenName:"payWay",
            triggerAction : "all",
            store:me.getPayWayStore()
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
      {items:[{name:'remark',xtype:'textarea',fieldLabel:'备注',maxLength:500}]}

    			    ];
        	return moduleFields.concat(me.getModuleFormBaseFields());
        }
});  