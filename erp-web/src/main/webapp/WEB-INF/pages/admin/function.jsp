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
<script type="text/javascript">
	$(function() {
		$("#grid").datagrid({
			toolbar : [
				{
					id : 'add',
					text : '添加权限',
					iconCls : 'icon-add',
					handler : function() {
						location.href = '${pageContext.request.contextPath}/page_admin_function_add.action';
					}
				},
				{
					id : 'button-delete',
					text : '删除',
					iconCls : 'icon-cancel',
					handler : doDelete
				}
			],
			url : 'functionAction_pageQuery.action',
			pagination : true,
			fit : true,
			onDblClickRow : doDblClickRow,
			columns : [ [
				{
					field : 'id',
					title : '编号',
					checkbox : true,
					width : 200
				},
				{
					field : 'name',
					title : '名称',
					width : 200
				},
				{
					field : 'description',
					title : '描述',
					width : 200,
					align : 'center'
				},
				{
					field : 'generatemenu',
					title : '是否生成菜单',
					width : 200,
					align : 'center',
					formatter : function(data, row, index) {
						if (data == "0") {
							return "否";
						} else {
							return "是";
						}
					}
				},
				{
					field : 'zindex',
					title : '优先级',
					width : 200
				},
				{
					field : 'page',
					title : '路径',
					width : 200
				}
			] ]
		});
	});
	
	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的功能权限！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的功能权限吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var row = rows[i]; //json对象
						var id = row.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "functionAction_deleteBatch.action?ids=" + ids;
				}
			});
		}
	}
	
	function doDblClickRow(rowIndex, rowData) {
		/* //打开修改部门窗口
		$('#editDepWindow').window("open");
		//使用form表单对象的load方法回显数据
		 */
		location.href = '${pageContext.request.contextPath}/page_admin_function_add.action';
		$("#functionForm").form("load", rowData);
	}
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
</body>
</html>