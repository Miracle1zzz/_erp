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
		$('#addDepWindow').window("open");
	}

	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的部门！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的部门吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var emp = rows[i]; //json对象
						var id = emp.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "depAction_deleteBatch.action?ids=" + ids;
				/**
				$.post("depAction_deleteBatch.action",{"ids":ids},function(data){
					alert(data);
				});
				**/
				}
			});
		}
	}
	//工具栏
	var toolbar = [
	<shiro:hasPermission name="dep-add">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</shiro:hasPermission>
	<shiro:hasPermission name="dep-delete">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	} 
	</shiro:hasPermission>
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
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 部门信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			//pageList : [ 30, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "depAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加部门窗口
		$('#addDepWindow').window({
			title : '添加部门',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 编辑部门窗口
		$('#editDepWindow').window({
			title : '修改部门',
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
			var values = $("#searchDepForm").serializeJson();
			$("#grid").datagrid("load", values);
		});

	});

	
	function doDblClickRow(rowIndex, rowData) {
		//打开修改部门窗口
		$('#editDepWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#editDepForm").form("load", rowData);
	}
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">

	<div region="center" border="false" style="height: 50%;width: 100%">
		<form id="searchDepForm">
			<table>
				<tr>
					<td>部门名称：</td>
					<td><input type="text" name="name" class="easyui-validatebox" /></td>
					<td>手机号：</td>
					<td><input type="text" name="telephone"
						class="easyui-validatebox" /></td>
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
	<div class="easyui-window" title="对部门进行添加或者修改" id="addDepWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<shiro:hasPermission name="dep-edit">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
						plain="true">保存</a>
				</shiro:hasPermission>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addDepForm" action="depAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">部门信息</td>
					</tr>
					<!-- TODO 这里完善部门添加 table -->
					<tr>
						<td>部门名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><script type="text/javascript">
							$(function() {
								$("#save").click(function() {
									var v = $("#addDepForm").form("validate");
									if (v) {
										//$("#addDepForm").form("submit");
										$("#addDepForm").submit();
									}
								});
								var reg = /^1[3|4|5|7|8][0-9]{9}$/;
								//扩展手机号校验规则
								$.extend($.fn.validatebox.defaults.rules, {
									telphone : {
										validator : function(value, param) {
											return reg.test(value);
										},
										message : '手机号输入有误！'
									}
								});
						
							});
						</script> <input type="text" data-options="validType:'telphone'"
							name="telephone" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="easyui-window" title="对部门进行添加或者修改" id="editDepWindow"
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
			<form id="editDepForm" action="depAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">部门信息</td>
					</tr>
					<!-- TODO 这里完善部门添加 table -->
					<tr>
						<td>部门名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><script type="text/javascript">
							$(function() {
								$("#edit").click(function() {
									var v = $("#editDepForm").form("validate");
									if (v) {
										//$("#addDepForm").form("submit");
										$("#editDepForm").submit();
									}
								});
								var reg = /^1[3|4|5|7|8][0-9]{9}$/;
								//扩展手机号校验规则
								$.extend($.fn.validatebox.defaults.rules, {
									telphone : {
										validator : function(value, param) {
											return reg.test(value);
										},
										message : '手机号输入有误！'
									}
								});
						
							});
						</script> <input type="text" data-options="validType:'telphone'"
							name="telephone" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
