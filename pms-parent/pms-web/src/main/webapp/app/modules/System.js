Ext.define('App.modules.System', {  
    extend: 'Ext.ux.desktop.Module',  
    requires: [  
	    'Ext.window.MessageBox'
    ],  
    id:'system',
    code:"system",
    name:"系统管理",
    init : function(){
    	 Ext.QuickTips.init();
        this.launcher = {  
            text: this.name,
            iconCls:this.code
        };  
    },  
    createWindow:function(){
    	var winid = this.code + "-win";
    	var desktop = this.app.getDesktop();  
        var moduleWin = desktop.getWindow(winid);  
        var required = '<span style="color:red;font-weight:bold" data-qtip="必填">* </span>';
    	
    	if(!moduleWin){
    		var MODULE_ID = this.id.substring(this.id.indexOf("_") + 1);
	    	var MODULE_CODE = this.code;
	    	var ModuleObject = function(id,code,name){
	    		this.api = {
	                load    : __ctxPath + '/dictionary/load.do',
	    		};
	    		this.openWubWin = function(code){
	    			alert(code);
	    		}
	    		this.generateMenu = function(code,name){
	    			return "<dl id='x-shortcuts'>" +
	    						"<dt id='grid-win-shortcut'>" +
	    							"<a onclick='javascript:alert(\"TODO\")'>" +
	    								"<img src='"+__ctxPath + "/app/resources/modules/" + MODULE_CODE +"/" + code + "/img/menu.png' />" +
	    								"<div>" + name + "</div>" +
	    							"</a>" +
	    						"</dt>" +
	    					"</dl>";
	    		};
	    		this.load = function(){
	    			Ext.Ajax.request({
						url : mo.api.load,
						method : "POST",
						params : {
							parentId : id
						},
						timeout : 30000,// 30秒超时,
						waitTitle : "请稍候",
						waitMsg : "正在提交请求，请稍候......",
						success : function(o, c) {
							var res = Ext.decode(o.responseText);
							if(res.code > 0){
								objectForm.getForm().reset();
								Ext.each(res.data,function(r){
									Ext.getCmp(MODULE_CODE + "-form-items").add({items:[{border:false,height:65,html:mo.generateMenu(r.code,r.name)}]});
						    	});
								objectForm.doLayout();
							}
						}
					});
	    		}
	    		this.help = function(){
	    		};
	    	};
	    	var mo = new ModuleObject(MODULE_ID);
	    	mo.load();
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
            		anchor : '100%',
            		border:false,
            		items : [ {
            			id:MODULE_CODE + "-form-items",
            			layout : 'column',
            			border : false,
            			defaults:{
            				columnWidth:.10,layout:'column',border:false
            			},
            			items:[
//            			       {items:[{border:false,height:65,html:mo.generateMenu("dictionary","字典管理")}]},
            			       
        			    ]
            		}]
            	})]
            });
	        
	        moduleWin = desktop.createWindow({
	            id: winid,
	            title:this.name,
	            width:720,  
	            height:500,
	            iconCls: this.code + "_win",
	            animCollapse:false,
	            constrainHeader:true,
	            layout: {
	                type: 'hbox',
	                pack: 'start',
	                align: 'stretch'
	            },
	            items: [objectForm],
	            tbar:toolbar
	        });
    	}
    	return moduleWin;
    }  
});  