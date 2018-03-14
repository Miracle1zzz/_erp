<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<script type="text/javascript">
	function doAdd() {
		//alert("增加...");
		$('#addGoodstypeWindow').window("open");
	}

	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的商品类别！", "warning");
		} else {
			//选中了商品类别,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的商品类别吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的商品类别的id
					for (var i = 0; i < rows.length; i++) {
						var emp = rows[i]; //json对象
						var id = emp.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "goodstypeAction_deleteBatch.action?ids=" + ids;
				}
			});
		}
	}
	//工具栏
	var toolbar = [
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	} 
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'supplier.name',
		title : '所属供应商',
		width : 160,
		align : 'center',
		formatter: function(data,row,index){
					return row.supplier.name;
		} 
	},{
		field : 'supplier.id',
		title : '所属供应商',
		width : 160,
		align : 'center',
		formatter: function(data,row,index){
					return row.supplier.id;
		},
		hidden: 'true' 
	}
	 ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 商品类别信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			//pageList : [ 30, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "goodstypeAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加商品类别窗口
		$('#addGoodstypeWindow').window({
			title : '添加商品类别',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 编辑商品类别窗口
		$('#editGoodstypeWindow').window({
			title : '修改商品类别',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
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
			var values = $("#searchGoodstypeForm").serializeJson();
			$("#grid").datagrid("load", values);
		});

	});

	
	function doDblClickRow(rowIndex, rowData) {
		//打开修改商品类别窗口
		$('#editGoodstypeWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#editGoodstypeForm").form("load", rowData);
		//对combobox进行数据回显
		$("#supplier").combobox('select',rowData.supplier.id);
	}
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
	<form id ="searchGoodstypeForm">
		<table>
			<tr>
				<td>商品类别名称：</td>
				<td><input type="text" name="name" class="easyui-validatebox" /></td>
				<td>
						<div class="datagrid-toolbar">
						<a id="serach"  href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
		</table>
		</form>
		<table id="grid" style="width: 1330px; height: 577px;"></table>
	</div>
	<div class="easyui-window" title="对商品类别进行添加或者修改" id="addGoodstypeWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
						plain="true">保存</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addGoodstypeForm" action="goodstypeAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">商品类别信息</td>
					</tr>
					<!-- TODO 这里完善商品类别添加 table -->
					<tr>
						<td>供应商：</td>
						<td><input class="easyui-combobox" name="supplier.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'supplierAction_listajax.action'" /></td>
					</tr>
					<tr>
						<td>商品类别名称：</td>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#save").click(function() {
									var v = $("#addGoodstypeForm").form("validate");
									if (v) {
										$("#addGoodstypeForm").submit();
									}
								});
							});
						</script>
						<input type="text" name="name" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="easyui-window" title="对商品类别进行添加或者修改" id="editGoodstypeWindow"
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
			<form id="editGoodstypeForm" action="goodstypeAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">商品类别信息</td>
					</tr>
					<!-- TODO 这里完善商品类别添加 table -->
					<tr>
						<td>供应商：</td>
						<td><input class="easyui-combobox" id="supplier" name="supplier.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'supplierAction_listajax.action'" />
    							</td>
					</tr>
					<tr>
						<td>商品类别名称：</td>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#edit").click(function() {
									var v = $("#editGoodstypeForm").form("validate");
									if (v) {
										//$("#addDepForm").form("submit");
										$("#editGoodstypeForm").submit();
									}
								});
							});
						</script>
						<input type="text" name="name" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
