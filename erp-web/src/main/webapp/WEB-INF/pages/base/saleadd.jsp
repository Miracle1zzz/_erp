<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var editIndex;
	function doAdd() {
		if (editIndex != undefined) {
			$("#grid").datagrid("endEdit", editIndex);
		} else {
			$('#grid').datagrid('insertRow', {
				index : 0, // 索引从0开始
				row : {} //插入一个空行
			});
			$("#grid").datagrid('beginEdit', 0);
			editIndex = 0;
		}
	}

	function doSave() {
		$("#grid").datagrid('endEdit', editIndex);
	}

	function doCancel() {
		if (editIndex != undefined) {
			$("#grid").datagrid('cancelEdit', editIndex);
			if ($('#grid').datagrid('getRows')[editIndex].id == undefined) {
				$("#grid").datagrid('deleteRow', editIndex);
			}
			editIndex = undefined;
		}
	}
	var toolbar = [ {
		id : 'button-add',
		text : '新增一行',
		iconCls : 'icon-edit',
		handler : doAdd
	}, {
		id : 'button-cancel',
		text : '取消编辑',
		iconCls : 'icon-cancel',
		handler : doCancel
	}, {
		id : 'button-save',
		text : '保存',
		iconCls : 'icon-save',
		handler : doSave
	} ];
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'supplierId',
		title : '供应商·',
		width : 120,
		align : 'center',
		editor : {
			type : 'combobox',
			options : {
				valueField : 'id',
				textField : 'name',
				mode : 'remote',
				url : 'supplierAction_listajax.action',
				onSelect : function(data) {
					var row = $('#grid').datagrid('getSelected');
					var rowIndex = $('#grid').datagrid('getRowIndex', row);

					var thisTarget = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'supplierId'
					}).target;
					var value = thisTarget.combobox('getValue');

					var target = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'goodstypeId'
					}).target;
					var target2 = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'goodsId'
					}).target;
					var target3 = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'nums'
					}).target;
					target.combobox('clear'); //清除原来的数据  
					target2.combobox('clear'); //清除原来的数据  
					var url2 = 'goodstypeAction_listajaxById.action?supplierid=' + value;
					target.combobox('reload', url2); //联动下拉列表重载  
					target3.numberbox('setValue', 1);
				},
				required : true
			}
		}
	}, {
		field : 'goodstypeId',
		title : '商品类别',
		width : 120,
		align : 'center',
		editor : {
			type : 'combobox',
			options : {
				valueField : 'id',
				textField : 'name',
				mode : 'remote',
				url : 'goodstypeAction_listajax.action',
				onSelect : function(res) {
					var row = $('#grid').datagrid('getSelected');
					var rowIndex = $('#grid').datagrid('getRowIndex', row);

					var thisTarget = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'goodstypeId'
					}).target;
					var value = thisTarget.combobox('getValue');

					var target = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'goodsId'
					}).target;
					var target1 = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'nums'
					}).target;
					target.combobox('clear'); //清除原来的数据  
					var url = 'goodsAction_listajaxById.action?goodstypeid=' + res.id;
					target.combobox('reload', url); //联动下拉列表重载  
					target1.numberbox('setValue', 1);
				},
				required : true
			}
		}
	}, {
		field : 'goodsId',
		title : '商品名称',
		width : 120,
		align : 'center',
		editor : {
			type : 'combobox',
			options : {
				valueField : 'id',
				textField : 'name',
				mode : 'remote',
				url : 'goodsAction_listajax.action',
				onSelect : function(res) {
					var row = $('#grid').datagrid('getSelected');
					var rowIndex = $('#grid').datagrid('getRowIndex', row);

					var thisTarget = $('#grid').datagrid('getEditor', {
						'index' : rowIndex,
						'field' : 'goodsId'
					}).target;
					var value = thisTarget.combobox('getValue');
					//target.combobox('clear'); //清除原来的数据  
					var url_2 = 'goodsAction_findPriceById.action?goodsid=' + res.id;
					$.post(url_2, function(data) {
						for (var i = 0; i < data.length; i++) {
							var outPriceView = data[i].outPriceView;
							var outPrice = data[i].outPrice;
							var target1 = $('#grid').datagrid('getEditor', {
								'index' : rowIndex,
								'field' : 'price'
							}).target;
							var target2 = $('#grid').datagrid('getEditor', {
								'index' : rowIndex,
								'field' : 'total'
							}).target;
							var target3 = $('#grid').datagrid('getEditor', {
							'index' : rowIndex,
							'field' : 'nums'
						}).target;
							target1.numberbox('setValue', outPriceView);
							target2.numberbox('setValue', outPriceView);
							target3.numberbox('setValue', 1);
						}
					});
				},
				required : true
			}
		}
	}
		, {
			field : 'nums',
			title : '数量',
			width : 120,
			align : 'center',
			editor : {
				type : 'numberbox',
				options : {
					onChange : function(newValue, oldValue) {
						var row = $('#grid').datagrid('getSelected');
						var rowIndex = $('#grid').datagrid('getRowIndex', row);
						var thisTarget = $('#grid').datagrid('getEditor', {
							'index' : rowIndex,
							'field' : 'nums'
						}).target;
						var target1 = $('#grid').datagrid('getEditor', {
							'index' : rowIndex,
							'field' : 'price'
						}).target;
						var value = target1.numberbox('getValue');
						var target2 = $('#grid').datagrid('getEditor', {
							'index' : rowIndex,
							'field' : 'total'
						}).target;
						target2.numberbox('setValue', value * newValue);

					},
					required : true
				}
			}
		}, {
			field : 'price',
			title : '单价',
			width : 120,
			align : 'center',
			editor : {
				type : 'numberbox',
				options : {
					required : true
				}
			}
		}, {
			field : 'total',
			title : '总价',
			width : 120,
			align : 'center',
			editor : {
				type : 'numberbox',
				options : {
					required : true
				}
			}
		} ] ];

	$(function() {
		$("body").css({
			visibility : "visible"
		});

		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList : [ 5, 10, 15 ],
			pageSize : 5,
			pagination : true,
			toolbar : toolbar,
			singleSelect: true,
			url : "",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
			onAfterEdit : function(rowIndex, rowData, changes) {
				console.info(rowData);
				$.post("saleAction_inputSalesOrder.action", rowData, function(data) {
					if (data) {
						$.messager.show({
							title : '恭喜',
							msg : "提交成功",
							timeout : 2000
						});
					} else {
						$.messager.show({
							title : "警告",
							msg : "修改失败",
							timeout : 2000
						});
						$('#grid').datagrid('load');
					}
				});
				editIndex = undefined;
			}
		});
	});

	function doDblClickRow(rowIndex, rowData) {
		console.info(rowIndex);
		$('#grid').datagrid('beginEdit', rowIndex);
		editIndex = rowIndex;
	}

	function doSearch(value, name) {
		$("#grid").datagrid("load", {
			"fieldName" : name,
			"fieldValue" : value
		});
	}
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">
	<div region="north">
		<input class="easyui-searchbox" style="width:500px"
			data-options="searcher:doSearch,prompt:'è¯·è¾å¥å³é®å­',menu:'#mm'" />
		<!-- èå -->
		<div id="mm">
			<div name="arriveCity" data-options="iconCls:'icon-ok'">
				å°è¾¾å°</div>
			<div name="goodsType">å¯æç©ç±»å</div>
		</div>
	</div>


	<div region="center" border="false">
		<table id="grid"></table>
	</div>
</body>

</html>
