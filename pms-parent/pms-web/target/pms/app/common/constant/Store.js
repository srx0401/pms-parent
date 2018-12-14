Ext.ns("StoreConstant");

StoreConstant.BOOLEAN_STORE = new Ext.data.SimpleStore({
		fields : [ 'code', 'name' ],
		data : [ [ '1', '是' ], [ '0', '否' ] ]
});
StoreConstant.BOOLEAN_STORE_WITH_ALL = new Ext.data.SimpleStore({
	fields : [ 'code', 'name' ],
	data : [ [ '', '全部' ], [ '1', '是' ], [ '0', '否' ] ]
});
