Ext.define('MyDesktop.App', {  
    extend: 'Ext.ux.desktop.App',  
    requires: [
		'Ext.ux.desktop.ShortcutModel',
		'Ext.window.MessageBox',
		'Ext.ux.desktop.SystemStatus',
		'Ext.ux.desktop.VideoWindow',
		'Ext.ux.desktop.GridWindow',
		'Ext.ux.desktop.TabWindow',
		'Ext.ux.desktop.AccordionWindow',
		'Ext.ux.desktop.Notepad',
		'Ext.ux.desktop.BogusMenuModule',
		'Ext.ux.desktop.BogusModule',
		
		//'MyDesktop.Blockalanche',
		'Ext.ux.desktop.Settings',
		'App.extend.Settings',
		'App.extend.TimePickerField',
		'App.extend.DateTimePicker',
		'App.extend.DateTimeField',
		'App.extend.ComboboxTree',
               ],
    menus:[],
    wallpapers:[],
    defaultWallpaper:"",
    defaultDesktopStartIcon:"",
    modules:[],
    constructor:function(cfg){
    	var me = this;
    	debugger;
    	if(cfg && cfg._menus && cfg._menus.length > 0){
    		console.log(cfg._menus);
    		Ext.each(cfg._menus,function(r){
        		if (r.module) {
        			/**/
    				me.requires.push(r.module);
    				var icon48 = r.icon48.code.substring(r.icon48.code.indexOf(".") + 1);
    				var icon16 = r.icon16.code.substring(r.icon16.code.indexOf(".") + 1);
    				var className = r.module + ".model." + r.code;
    	    		me.menus.push({name:r.name,iconCls:icon48,viewIconCls:icon48,module:r.code + "_" + r.id});
    	    		me.modules.push(Ext.create(r.module,{id:r.code + "_" + r.id ,code:r.code,name:r.name,icon48:icon48,icon16:icon16,className:className,module:r.module}));

    			}
        	});
    	}
    	Ext.apply( me,cfg);
    	me.init();
    },
    init: function() {
        this.callParent();  
    },  
    //调用模块的方法，这里必须需要  
    getModules : function(){
    	var me = this;
//    	me.modules.push(
//    			new Ext.ux.desktop.SystemStatus(),
//    			new Ext.ux.desktop.Notepad(),
//    			new Ext.ux.desktop.VideoWindow(),
//    			new Ext.ux.desktop.GridWindow(),
//    			new Ext.ux.desktop.TabWindow(),
//    			new Ext.ux.desktop.AccordionWindow(),
//    			new Ext.ux.desktop.BogusMenuModule(),
//    			new Ext.ux.desktop.BogusModule());
    	return me.modules;
    },  
  
    getDesktopConfig: function () {  
        var me = this, ret = me.callParent();  
        return Ext.apply(ret, {  
            contextMenuItems: [{ 
                text: '壁纸设置',   
                handler: me.onSettings,
                scope: me
            }/*,{
                text: '样式设置',
                handler: me.onCssSettings,
                scope: me  
            }*/],  
  
            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',  
                data:this.menus
            }),  
            wallpaper: 'app/resources/wallpaper/' + me.defaultWallpaper,
            wallpaperStretch: true
        });  
    },  
  
  
    // config for the start menu  
    getStartConfig : function() {                   //开始菜单的设置（右边导航栏）  
        var me = this, ret = me.callParent();  
        return Ext.apply(ret, {  
            title: '导航栏',  
            iconCls: this.defaultDesktopStartIcon,//'user',  
            height: 300,  
            toolConfig: {  
                width: 100,  
                items: [{  
                    text:'壁纸设置',  
                    iconCls:'settings',  
                    handler: me.onSettings,  
                    scope: me  
                },'-',{  
                    text:'退出系统',  
                    iconCls:'logout',  
                    handler: me.onLogout,  
                    scope: me  
                }]  
            }  
        });  
    },  
  
    getTaskbarConfig: function () {                 //快速启动栏的设置  
    	debugger;
        var ret = this.callParent();  
        return Ext.apply(ret, {  
//            quickStart: [{  
//                name: 'XX配置',   
//                iconCls: 'accordion',   
//                module: 'XXX'                       //参数是自己开发模块的id  
//            },{  
//                name: 'XX界面',   
//                iconCls: 'icon-grid',   
//                module: 'XXX'  
//            }],  
            trayItems: [{  
                xtype: 'trayclock',
                flex: 1  
            }],startButtonIconCls:this.defaultDesktopStartIcon 
        });  
    },  
  
  
    onLogout: function () {                         //退出系统的操作  
        Ext.Msg.confirm('退出', '确认退出系统?',function(button){  
            if(button=="yes"){  
            	location.href=__ctxPath + "logout.do";
            }  
        });  
    },  
  
    onSettings: function () {               //设置壁纸的操作  
        var dlg = new /*Ext.ux.desktop*/App.extend.Settings({        //这里Fly.modules.Settings是自己写的类。另有详细解释  
            desktop: this.desktop,
            wallpapers:this.wallpapers
        });  
        dlg.show();  
    },  
      
    onCssSettings: function () {  
        Ext.create("Ext.window.Window",{  
            title:'样式设置',  
            modal: true,  
            width: 640,  
            height: 480,  
            border: false,  
            layout:'fit'  
        }).show();  
    }  
});  