Ext.define('App.extend.ComboboxTree', {
    extend : Ext.form.field.Picker,
    alias: ['widget.combotree'], 
    requires : [Ext.tree.Panel], 
    store:{},
    tree: {},
    select:{},
    selectNodeModel : 'leaf',
    config: { 
        maxPickerWidth: 200, 
        maxPickerHeight: 200, 
        minPickerHeight: 100
    },
    initComponent : function() {
        var self = this;
        Ext.apply(self, {
            fieldLabel : self.fieldLabel,
            labelWidth : self.labelWidth     
        });
        self.selectNodeModel = Ext.isEmpty(self.selectNodeModel) ? 'all' : self.selectNodeModel;
        self.callParent();
        this.tree= new Ext.tree.Panel({
            rootVisible: false, 
            width: self.maxPickerWidth, 
            height:self.maxPickerHeight,
            autoScroll : true,
            floating : true,
            focusOnToFront : false,
            shadow : false,
            useArrows : true,
            store : this.store,
            rootVisible : false,
            listeners:{ }
        });
        this.tree.on('itemclick', function (view, record, item, index, e,
				object) {
            var selModel = self.selectNodeModel;
			var isLeaf = record.data.leaf;
			var isRoot = record.data.root;
			var view = self.picker.getView();
			if (!self.multiSelect) {
				if ((isRoot) && selModel != 'all') {
					return;
				} else if (selModel == 'exceptRoot'
						&& isRoot) {
					return;
				} else if (selModel == 'folder' && isLeaf) {
					return;
				} else if (selModel == 'leaf' && !isLeaf) {
					var expand = record.get('expanded');
					if (expand) {
						view.collapse(record);
					} else {
						view.expand(record);
					}
					return;
				}

				self.submitValue = record.get('id');
				self.setValue(record.get('text'));
				self.eleJson = Ext.encode(record.raw);
				self.collapse();
			}
        });
    },
    createPicker : function() {
        var self = this;
        self.picker = this.tree;
        self.picker.on({
            checkchange : function() {
                var records = self.picker.getView().getChecked(), names = [], values = [];
                Ext.Array.each(records, function(rec) {
                    names.push(rec.get('id'));//rec.get('text')
                    values.push(rec.get('id'));
                });
                self.setRawValue(values.join(';'));// 隐藏值
                self.setValue(names.join(';'));// 显示值
                self.picker.hide();                 //[目前单选,该批次代码、tree的itemclick事件去掉则多选]
                Ext.Array.each(records, function(record) {  //[目前单选,该批次代码、tree的itemclick事件去掉则多选]
                    record.set('checked', false);       //[目前单选,该批次代码、tree的itemclick事件去掉则多选]
                });                     //[目前单选,该批次代码、tree的itemclick事件去掉则多选]
            }
        });
        return self.picker;
    },
    alignPicker : function() {
        var me = this, picker, isAbove, aboveSfx = '-above';
        if (this.isExpanded) {
            picker = me.getPicker();
            if (me.matchFieldWidth) {
                picker.setWidth(me.bodyEl.getWidth());
            }
            if (picker.isFloating()) {
                picker.alignTo(me.inputEl, me.pickerOffset);// ->tl
                isAbove = picker.el.getY() < me.inputEl.getY();
                me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls+ aboveSfx);
                picker.el[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
            }
        }
    }
});