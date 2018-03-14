<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<script
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/datagrid-detailview.js"></script>
	
<script type="text/javascript">
	// 工具栏
	var toolbar = [ {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	} ];
	
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'name',
		title : '商品名称',
		width : 120,
		align : 'center'
	}, {
		field : 'producer',
		title : '生产厂家',
		width : 120,
		align : 'center'
	} ] ];
	// 定义标题栏
	var columns = [ [ 
	{
		field : 'origin',
		title : '生产厂地',
		width : 120,
		align : 'center',
	}, {
		field : 'inPriceView',
		title : '进价',
		width : 120,
		align : 'center'
	}, {
		field : 'outPriceView',
		title : '售价',
		width : 80,
		align : 'center'
	}, {
		field : 'operTimeView',
		title : '操作时间',
		width : 120,
		align : 'center'
	}, {
		field : 'goodstype.name',
		title : '商品类型',
		width : 80,
		align : 'center',
		formatter : function(data, row, index) {
			return row.goodstype.name;
		}
	},
	{
		field : 'goodstype.id',
		title : '商品类型',
		width : 80,
		align : 'center',
		formatter : function(data, row, index) {
			return row.goodstype.id;
		},
		hidden:'true'
	}
	] ];
	$(function() {
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pagination : true,
			toolbar : toolbar,
			url : "goodsAction_pageQuery.action",
			idField : 'id',
			frozenColumns : frozenColumns,
			
			columns : columns,
			onDblClickRow : doDblClickRow,
			view: detailview,
				detailFormatter: function(rowIndex, rowData){
					return '<table align="left"><tr>' +
					'<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/'+rowData.image+'" style="height:100px;"></td>' +
					'<td style="border:0;">' +
					'<p style="margin:10px;font-weight:bold;">商品名称: ' + rowData.name + '</p>' +
					'<p style="margin:10px;font-weight:bold;">生产厂家: ' + rowData.producer + '</p>' +
					'</td>' +
					'<td style="border:0;margin:10px;">' +
					'<p style="margin:10px;font-weight:bold;">生产场地: ' + rowData.origin + '</p>' +
					'<p style="margin:10px;font-weight:bold;">销售价格: ' + rowData.outPriceView + '元</p>' +
					'</td>' +
					'</tr></table>';

			}
		});

		//页面加载完成后，调用OCUpload 插件的方法
		$("#button-import").upload({
			action : 'goodsAction_importXls.action',
			name : 'goodsFile'
		});
		$('#addGoodsWindow').window({
			title : '新增商品',
			width : 800,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		$('#editGoodsWindow').window({
			title : '修改商品',
			width : 800,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		$("body").css({
			visibility : "visible"
		});

		//定义一个工具方法，用于将指定的form表单输入值转为json对象
		$.fn.serializeJson = function() {
			var serializeObj = {};
			var array = this.serializeArray();
			$(array).each(function() {
				if (serializeObj[this.name]) {
					if ($.isArray(serializeObj[this.name])) {
						serializeObj[this.name].push(this.value);
					} else {
						serializeObj[this.name] = [ serializeObj[this.name], this.value ];
					}
				} else {
					serializeObj[this.name] = this.value;
				}
			});
			return serializeObj;
		};
		$("#serach").click(function() {
			//调用数据表格的load方法，重新发送一次Ajax请求，并提交参数
			var values = $("#searchgoodsForm").serializeJson();
			$("#grid").datagrid("load", values);
		});
	});

	function doAdd() {
		$('#addGoodsWindow').window("open");
	}
	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的商品！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的商品吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var goods = rows[i]; //json对象
						var id = goods.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "goodsAction_delete.action?ids=" + ids;
				}
			});
		}
	}

	function doDblClickRow(rowIndex, rowData) {
		//打开修改部门窗口
		$('#editGoodsWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#goodsEditForm").form("load", rowData);
		$("#goodstypeedit").combobox('select',rowData.goodstype.name);
	}
	
	function doExport(){
		//发送请求，请求action，进行文件下载
			$.messager.confirm("导出确认", "你确定要导出商品数据吗？", function(r) {
				if (r) {
					window.location.href = "goodsAction_exportXls.action";
				}
			});
	}
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<form id="searchgoodsForm" >
			<table >
				<tr>
					<td>
					商品类别:</td>
					<td><input type="text" name="goodstype.name" id="name"
						class="easyui-validatebox" /></td>
					<td>商品名称:</td>
					<td><input type="text" name="name" id="name"
						class="easyui-validatebox" /></td>
					<td>产地:</td>
					<td><input type="text" name="origin" id="origin"
						class="easyui-validatebox" /></td>
					<td>生产厂家:</td>
					<td><input type="text" name="producer" id="producer"
						class="easyui-validatebox" /></td>
					<td>
						<div class="datagrid-toolbar">
							<a id="serach" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
				<tr>
					<td>进货单价:</td>
					<td><input type="text" name="inPrice" id="inPrice"
						class="easyui-numberbox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td>
					<td><input type="text" name="inPriceMax" id="inPrice"
						class="easyui-numberbox" /></td>

					<td>销售单价:</td>
					<td><input type="text" name="outPrice" id="outPrice"
						class="easyui-numberbox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td>
					<td><input type="text" name="outPriceMax" id="outPrice"
						class="easyui-numberbox" /></td>

				</tr>
			</table>
		</form>
		<table id="grid" style="width: 1330px; height: 552px;"></table>
	</div>
	<div class="easyui-window" title="对商品进行添加或者修改" id="addGoodsWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px" >
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="add" icon="icon-add" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="goodsAddForm" method="post" action="goodsAction_add.action" enctype="multipart/form-data">
				<table class="table-edit" width="95%" align="center">
					<tr>
						<td>供应商:</td>
						<td><input class="easyui-combobox" id="supplier"
							name="supplier.id"
							data-options="valueField:'id',textField:'name',mode:'remote',
							url:'supplierAction_listajax.action',
							
							onSelect: function(rec){
							 	$('#goodstype').combobox('clear');
								$('#goodstype').combobox('loadData', {});
   								 var url = 'goodstypeAction_listajaxById.action?supplierid='+rec.id;
    							$('#goodstype').combobox('reload', url);
    						}" /></td>
						<td>商品类别:</td>
						<td><input class="easyui-combobox" id="goodstype"
							name="goodstype.id"
							data-options="valueField:'id',textField:'name',mode:'remote',url:'goodstypeAction_listajax.action'" /></td>
					</tr>
					<tr>
						<td>商品名称:</td>
						<td><input type="text" name="name" id="name"
							class="easyui-validatebox" required="true" /></td>
						<td>产地:</td>
						<td><input type="text" name="origin" id="origin"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>生产厂家:</td>
						<td><input type="text" name="producer" id="producer"
							class="easyui-validatebox" required="true" /></td>
						<td>选择图片:</td>
						<td><input type="file" name="file"/></td>
					</tr>
					<tr>
						<td>进货单价:</td>
						<td><input type="text" name="inPrice" id="inPrice"
							class="easyui-numberbox" required="true" /></td>
						<td>销售单价:</td>
						<td><input type="text" name="outPrice" id="outPrice"
							class="easyui-numberbox" required="true" /></td>
					</tr>
					<tr>
						<td>库存最小值:</td>
						<td><input type="text" name="minNum" id="minNum" style="width:140px;"
							class="easyui-numberspinner" required="true"  data-options="min:10,max:100,editable:true"/></td>
						<td>库存最大值:</td>
						<td><input type="text" name="maxNum" id="maxNum" style="width:140px;"
							class="easyui-numberspinner" required="true" data-options="min:100,max:1000,editable:true" /></td>
					</tr>
					<tr>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#add").click(function() {
									var v = $("#goodsAddForm").form("validate");
									if (v) {
										$("#goodsAddForm").submit();
									}
								});
							});
						</script>商品描述:</td>
						<td colspan="3"><textarea name="description" style="width:80%"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="对商品进行添加或者修改" id="editGoodsWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="goodsEditForm" method="post"
				action="goodsAction_edit.action">
				<input type="hidden" name="id" />
				<table class="table-edit" width="95%" align="center">
					<tr>
						<td>供应商:</td>
						<td><input class="easyui-combobox" id="supplieredit"
							name="supplier.id"
							data-options="valueField:'id',textField:'name',mode:'remote',
							url:'supplierAction_listajax.action',
							onSelect: function(rec){
								 $('#goodstypeedit').combobox('clear');
								 $('#goodstypeedit').combobox('loadData', {});
   								 var url = 'goodstypeAction_listajaxById.action?supplierid='+rec.id;
    							$('#goodstypeedit').combobox('reload', url);
    						}" /></td>
						<td>商品类别:</td>
						<td><input class="easyui-combobox" id="goodstypeedit"
							name="goodstype.id"
							data-options="valueField:'id',textField:'name',mode:'remote',url:'goodstypeAction_listajax.action'" /></td>
					</tr>
					<tr>
						<td>商品名称:</td>
						<td><input type="text" name="name" id="name"
							class="easyui-validatebox" required="true" /></td>
						<td>产地:</td>
						<td><input type="text" name="origin" id="origin"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>生产厂家:</td>
						<td><input type="text" name="producer" id="producer"
							class="easyui-validatebox" required="true" /></td>
						<td>生产厂家:</td>
						<td><input type="text" name="producer" id="producer"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>进货单价:</td>
						<td><input type="text" name="inPrice" id="inPrice"
							class="easyui-validatebox" required="true" /></td>
						<td>销售单价:</td>
						<td><input type="text" name="outPrice" id="outPrice"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>库存最小值:</td>
						<td><input type="text" name="minNum" id="minNum" style="width:140px;"
							class="easyui-numberspinner" required="true"  data-options="min:10,max:100,editable:true"/></td>
						<td>库存最大值:</td>
						<td><input type="text" name="maxNum" id="maxNum" style="width:140px;"
							class="easyui-numberspinner" required="true" data-options="min:100,max:1000,editable:true" /></td>
					</tr>
					<tr>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#edit").click(function() {
									var v = $("#goodsEditForm").form("validate");
									if (v) {
										$("#goodsEditForm").submit();
									}
								});
							});
						</script>商品描述:</td>
						<td colspan="3"><textarea name="description" style="width:80%"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>