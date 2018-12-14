//
//Ext.define('Ext.ux.ComboBoxTree', {					extend : 'Ext.form.field.Picker',
//					requires : [ 'Ext.tree.Panel' ],
//					alias : [ 'widget.comboboxtree' ],
//					multiSelect : false,
//					multiCascade : true,
//					rootVisible : true,
//					submitValue : '',
//					pathValue : '',
//					pathArray : [],
//					selectNodeModel : 'all',
//					initComponent : function() {
//						var self = this;
//						self.selectNodeModel = Ext
//								.isEmpty(self.selectNodeModel) ? 'all'
//								: self.selectNodeModel;
//						Ext.apply(self, {
//							fieldLabel : self.fieldLabel,
//							labelWidth : self.labelWidth
//
//						});
//						self.callParent();
//					},
//					createPicker : function () {
//var self = this;
//self.picker = Ext.create('Ext.tree.Panel', {
//																																						height : self.treeHeight == null ? 200
//													: self.treeHeight,
//											autoScroll : true,
//											floating : true,
//											focusOnToFront : false,
//											shadow : true,
//											ownerCt : this.ownerCt,
//											useArrows : false,
//											store : self.store,
//											rootVisible : this.rootVisible,
//											viewConfig : {
//												onCheckboxChange : function(e,
//														t) {
//													if (self.multiSelect) {
//														var item = e
//																.getTarget(
//																		this
//																				.getItemSelector(),
//																		this
//																				.getTargetEl()), record;
//														if (item) {
//															record = this
//																	.getRecord(item);
//															var check = !record
//																	.get('checked');
//															record.set(
//																	'checked',
//																	check);
//															if (self.multiCascade) {
//																if (check) {
//																	record
//																			.bubble(function(
//																					parentNode) {
//																				if ('Root' != parentNode
//																						.get('text')) {
//																					parentNode
//																							.set(
//																									'checked',
//																									true);
//																				}
//																			});
//																	record
//																			.cascadeBy(function(
//																					node) {
//																				node
//																						.set(
//																								'checked',
//																								true);
//																				node
//																						.expand(true);
//																			});
//																} else {
//																	record
//																			.cascadeBy(function(
//																					node) {
//																				node
//																						.set(
//																								'checked',
//																								false);
//																			});
//																	record
//																			.bubble(function(
//																					parentNode) {
//																				if ('Root' != parentNode
//																						.get('text')) {
//																					var flag = true;
//																					for (var i = 0; i < parentNode.childNodes.length; i++) {
//																						var child = parentNode.childNodes[i];
//																						if (child
//																								.get('checked')) {
//																							flag = false;
//																							continue;
//																						}
//																					}
//																					if (flag) {
//																						parentNode
//																								.set(
//																										'checked',
//																										false);
//																					}
//																				}
//																			});
//																}
//															}
//														}
//														var records = self.picker
//																.getView()
//																.getChecked(), names = [], values = [];
//														Ext.Array
//																.each(
//																		records,
//																		function(
//																				rec) {
//																			names
//																					.push(rec
//																							.get('text'));
//																			values
//																					.push(rec
//																							.get('id'));
//																		});
//														self.submitValue = values
//																.join(',');
//														self.setValue(names
//																.join(','));
//													}
//												}
//											}
//										});
//						self.picker.on({
//							itemclick : function(view, recore, item, index, e,
//									object) {
//								var selModel = self.selectNodeModel;
//								var isLeaf = recore.data.leaf;
//								var isRoot = recore.data.root;
//								var view = self.picker.getView();
//								if (!self.multiSelect) {
//									if ((isRoot) && selModel != 'all') {
//										return;
//									} else if (selModel == 'exceptRoot'
//											&& isRoot) {
//										return;
//									} else if (selModel == 'folder' && isLeaf) {
//										return;
//									} else if (selModel == 'leaf' && !isLeaf) {
//										var expand = recore.get('expanded');
//										if (expand) {
//											view.collapse(recore);
//										} else {
//											view.expand(recore);
//										}
//										return;
//									}
//
//									self.submitValue = recore.get('id');
//									self.setValue(recore.get('text'));
//									self.eleJson = Ext.encode(recore.raw);
//									self.collapse();
//								}
//							}
//						});
//						return self.picker;
//					},
//					listeners : {
//						expand : function(field, eOpts) {
//							var picker = this.getPicker();
//							if (!this.multiSelect) {
//								if (this.pathValue != '') {
//									picker.expandPath(this.pathValue, 'id',
//											'/', function(bSucess, oLastNode) {
//												picker.getSelectionModel()
//														.select(oLastNode);
//											});
//								}
//							} else {
//								if (this.pathArray.length > 0) {
//									for (var m = 0; m < this.pathArray.length; m++) {
//										picker.expandPath(this.pathArray[m],
//												'id', '/', function(bSucess,
//														oLastNode) {
//													oLastNode.set('checked',
//															true);
//												});
//									}
//								}
//							}
//						}
//					},
//					clearValue : function() {
//						this.setDefaultValue('', '');
//					},
//
//					getEleJson : function() {
//						if (this.eleJson == undefined) {
//							this.eleJson = [];
//						}
//						return this.eleJson;
//					},
//					getSubmitValue : function() {
//						if (this.submitValue == undefined) {
//							this.submitValue = '';
//						}
//						return this.submitValue;
//					},
//					getDisplayValue : function() {
//						if (this.value == undefined) {
//							this.value = '';
//						}
//						return this.value;
//					},
//					setPathValue : function(pathValue) {
//						this.pathValue = pathValue;
//					},
//					setPathArray : function(pathArray) {
//						this.pathArray = pathArray;
//					},
//					setDefaultValue : function(submitValue, displayValue) {
//						this.submitValue = submitValue;
//						this.setValue(displayValue);
//						this.eleJson = undefined;
//
//						this.pathArray = [];
//					},
//					alignPicker : function() {
//						var me = this, picker, isAbove, aboveSfx = '-above';
//						if (this.isExpanded) {
//							picker = me.getPicker();
//							if (me.matchFieldWidth) {
//								picker.setWidth(me.bodyEl.getWidth());
//							}
//							if (picker.isFloating()) {
//								picker.alignTo(me.inputEl, "", me.pickerOffset); // ""->tl
//								isAbove = picker.el.getY() < me.inputEl.getY();
//								me.bodyEl[isAbove ? 'addCls' : 'removeCls']
//										(me.openCls + aboveSfx);
//								picker.el[isAbove ? 'addCls' : 'removeCls']
//										(picker.baseCls + aboveSfx);
//							}
//						}
//					}
//				});
