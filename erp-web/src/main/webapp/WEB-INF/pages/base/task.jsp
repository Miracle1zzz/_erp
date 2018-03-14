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
	var toolbar = [ {
		id : 'button-undo',
		text : '结单',
		iconCls : 'icon-undo',
		handler : doComplete
	} ];
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'orderTypeView',
		title : '订单类别',
		width : 120,
		align : 'center'
	}, {
		field : 'supplier.name',
		title : '供应商',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.supplier.name;
		}
	},{
		field : 'supplier.needsView',
		title : '发货方式',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.supplier.needsView;
		}
	},{
		field : 'completer.name',
		title : '联系人',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.completer.name;
		}
	},{
		field : 'completer.telephone',
		title : '联系方式',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.completer.telephone;
		}
	},{
		field : 'typeView',
		title : '订单状态',
		width : 80,
		align : 'center',
	}] ];

	$(function() {
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar:toolbar,
			singleSelect: true,
			pagination : true,
			url : "orderAction_pageQueryTasks.action",
			idField : 'id',
			columns : columns,
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
			var values = $("#searchorderForm").serializeJson();
			$("#grid").datagrid("load", values);
		});
	});
	//结单
	function doComplete(){
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择一行数据进行操作！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("结单确认", "你确定此订单需要结单吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var task = rows[i]; //json对象
						var id = task.id;
					}
					location.href = "orderAction_assignTask.action?taskid=" + id;
				}
			});
		}
	}

</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<form id="searchorderForm">
			<table>
				<tr>
					<td>订单类别:</td>
					<td><select name="orderType" id="orderType"
						class="easyui-combobox" style="width: 145px;">
							<option value="-1">-----请选择-----</option>
							<option value="1">采购</option>
							<option value="2">销售</option>
							<option value="3">采购退货</option>
							<option value="4">销售退货</option>
					</select></td>

					<td>发货方式:</td>
					<td><select name="supplier.needs" id="needs"
						class="easyui-combobox" style="width: 145px;">
							<option value="-1">-----请选择-----</option>
							<option value="0">送货</option>
							<option value="1">自提</option>
					</select></td>
					<td>
						<div class="datagrid-toolbar">
							<a id="serach" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<table id="grid" style="width: 1330px; height: 552px;">
		</table>
	</div>
</body>
</html>