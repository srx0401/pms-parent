/*! 
 * Ext JS Library 4.0 
 * Copyright(c) 2006-2011 Sencha Inc. 
 * licensing@sencha.com 
 * http://www.sencha.com/license 
 */  
  
Ext.define('App.extend.Settings', {  
    extend: 'Ext.window.Window',  
  
    uses: [  
    'Ext.tree.Panel',  
    'Ext.tree.View',  
    'Ext.form.field.Checkbox',  
    'Ext.layout.container.Anchor',  
    'Ext.layout.container.Border',  
  
    'Ext.ux.desktop.Wallpaper'  
    ],  
  
    layout: 'anchor',  
    title: '壁纸设置',  
    modal: true,  
    width: 640,  
    height: 480,  
    border: false,  
    initComponent: function () { 
        var me = this;  
        Ext.define('WallpaperModel', {  
            extend: 'Ext.data.Model',  
            fields: [{  
                name: 'text'  
            },{  
                name: 'img'  
            }]  
        });  
          
        me.selected = me.desktop.getWallpaper();  
        me.stretch = me.desktop.wallpaper.stretch;  
  
        me.preview = Ext.create('widget.wallpaper');        //调用extjs中core中的壁纸模板类  
        me.preview.setWallpaper(me.selected);  
        me.tree = me.createTree();  
  
        me.buttons = [  
        {  
            text: '确定',   
            handler: me.onOK,   
            scope: me  
        },  
        {  
            text: '取消',   
            handler: me.close,   
            scope: me  
        }  
        ];  
  
        me.items = [  
        {  
            anchor: '0 -30',  
            border: false,  
            layout: 'border',  
            items: [  
            me.tree,  
            {  
                xtype: 'panel',  
                title: '预览',  
                region: 'center',  
                layout: 'fit',  
                items: [ me.preview ]  
            }  
            ]  
        },  
        {  
            xtype: 'checkbox',  
            boxLabel: '拉伸适合屏幕',  
            checked: me.stretch,  
            listeners: {  
                change: function (comp) {  
                    me.stretch = comp.checked;  
                }  
            }  
        }  
        ];  
  
        me.callParent();  
    },  
  
    createTree : function() {  
        var me = this;  
        function initWallpaper(){
        	var res = [{text:"None",iconCls:'',leaf:true}];
        	Ext.each(me.wallpapers,function(o){
        		var code = o.code.substring(o.code.indexOf(".") + 1);
        		var text = o.name.substring(o.name.indexOf("-") + 1);
        		res.push({img:code,text:text,iconCls:'',leaf:true});
        	});
        	return res;
        }
        var tree = new Ext.tree.Panel({  
            title: '壁纸',  
            rootVisible: false,  
            lines: false,  
            autoScroll: true,  
            width: 150,  
            region: 'west',  
            split: true,  
            minWidth: 100,  
            listeners: {  
                afterrender: {
                    fn: this.setInitialSelection,   
                    delay: 100  
                },  
                select: this.onSelect,  
                scope: this  
            },  
            store: new Ext.data.TreeStore({  
                model: 'WallpaperModel',            //初始化壁纸  
                root: {  
                    text:'壁纸',  
                    expanded: true,  
                    children:initWallpaper() 
                }  
            })  
        });  
  
        return tree;  
    },  
  
    getTextOfWallpaper: function (path) {  
        var text = path, slash = path.lastIndexOf('/');  
        if (slash >= 0) {  
            text = text.substring(slash+1);  
        }  
        var dot = text.lastIndexOf('.');  
        text = Ext.String.capitalize(text.substring(0, dot));  
        text = text.replace(/[-]/g, ' ');  
        return text;  
    },  
  
    onOK: function () {  
        var me = this;  
        if (me.selected) {  
            me.desktop.setWallpaper(me.selected, me.stretch);  
        }  
        me.destroy();  
    },  
  
    onSelect: function (tree, record) {  
        var me = this;  
  
        if (record.data.img) {              //指定壁纸的路径  
            me.selected = 'app/resources/wallpaper/' + record.data.img;  
        } else {  
            me.selected = Ext.BLANK_IMAGE_URL;  
        }  
  
        me.preview.setWallpaper(me.selected);  
    },  
  
    setInitialSelection: function () {  
        var s = this.desktop.getWallpaper();  
        if (s) {  
            var path = '/Wallpaper/' + this.getTextOfWallpaper(s);  
            this.tree.selectPath(path, 'text');  
        }  
    }  
});  