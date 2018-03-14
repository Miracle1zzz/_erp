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
		$('#addStoreWindow').window("open");
	}

	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的仓库！", "warning");
		} else {
			//选中了仓库,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的仓库吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的仓库的id
					for (var i = 0; i < rows.length; i++) {
						var emp = rows[i]; //json对象
						var id = emp.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "storeAction_deleteBatch.action?ids=" + ids;
				/**
				$.post("storeAction_deleteBatch.action",{"ids":ids},function(data){
					alert(data);
				});
				**/
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
		title : '仓库名称',
		width : 120,
		align : 'center'
	}, {
		field : 'emp.name',
		title : '仓库管理员',
		width : 120,
		align : 'center',
		formatter: function(data,row,index){
			return row.emp.name
		}
	} , {
		field : 'address',
		title : '仓库地址',
		width : 120,
		align : 'center'
	}] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 仓库信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			//pageList : [ 30, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "storeAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加仓库窗口
		$('#addStoreWindow').window({
			title : '添加仓库',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 编辑仓库窗口
		$('#editStoreWindow').window({
			title : '修改仓库',
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
			var values = $("#searchStoreForm").serializeJson();
			$("#grid").datagrid("load", values);
		});

	});

	
	function doDblClickRow(rowIndex, rowData) {
		//打开修改仓库窗口
		$('#editStoreWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#editStoreForm").form("load", rowData);
		$("#empedit").combobox('select',rowData.emp.name);
	}
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">

	<div region="center" border="false" style="height: 50%;width: 100%">
		<form id="searchStoreForm">
			<table>
				<tr>
					<td>仓库名称：</td>
					<td><input type="text" name="name" class="easyui-validatebox" /></td>
					<td>仓库管理员</td>
						<td>
						 <input class="easyui-combobox" name="emp.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'empAction_listajax.action'" /></td>
					<td>
						<div class="datagrid-toolbar">
							<a id="serach" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<table id="grid" style="width: 1330px; height: 577px;"></table>
		</div>
	<div class="easyui-window" title="对仓库进行添加或者修改" id="addStoreWindow"
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
			<form id="addStoreForm" action="storeAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">仓库信息</td>
					</tr>
					<!-- TODO 这里完善仓库添加 table -->
					<tr>
						<td>仓库名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>仓库管理员</td>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#save").click(function() {
									var v = $("#addStoreForm").form("validate");
									if (v) {
										//$("#addStoreForm").form("submit");
										$("#addStoreForm").submit();
									}
								});
							});
						</script> <input class="easyui-combobox" name="emp.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'empAction_listajax.action'" /></td>
					</tr>
					<tr>
						<td>仓库地址</td>
						<td><input type="text" name="address" class="easyui-validatebox"
							required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="easyui-window" title="对仓库进行添加或者修改" id="editStoreWindow"
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
			<form id="editStoreForm" action="storeAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">仓库信息</td>
					</tr>
					<!-- TODO 这里完善仓库添加 table -->
					<tr>
						<td>仓库名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>仓库管理员</td>
						<td>
						<script type="text/javascript">
							$(function() {
								$("#edit").click(function() {
									var v = $("#editStoreForm").form("validate");
									if (v) {
										//$("#addStoreForm").form("submit");
										$("#editStoreForm").submit();
									}
								});
							});
						</script> <input class="easyui-combobox" id="empedit" name="emp.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'empAction_listajax.action'" /></td>
					</tr>
					<tr>
						<td>仓库地址</td>
						<td><input type="text" name="address" class="easyui-validatebox"
							required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
