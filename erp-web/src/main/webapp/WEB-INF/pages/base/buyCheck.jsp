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
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'orderNum',
		title : '订单编号',
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
		field : 'creater.name',
		title : '制单人',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.creater.name;
		}
	},{
		field : 'createTimeView',
		title : '制单时间',
		width : 120,
		align : 'center'
	},{
		field : 'totalNum',
		title : '订单订单总量',
		width : 80,
		align : 'center'
	},{
		field : 'totalPrice',
		title : '订单总金额',
		width : 80,
		align : 'center'
	},{
		field : 'typeView',
		title : '订单状态',
		width : 80,
		align : 'center',
	},{
		field : 'type',
		title : '审核',
		width : 80,
		align : 'center',
		formatter: operation
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
			singleSelect: true,
			pagination : true,
			url : "orderAction_pageQueryByOrdertype.action",
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


	function operation(data, row, index){
		
		if(data == '111'){
			return "<a onClick='doView()' target='_blank'>审核</a>";
		}else {
			return "<a style = 'color:red;'>已审核</a>";
		}
		
	}
	function doView(){
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要的订单数据进行操作！", "warning");
		} else {
			 $(function(r){
				if (r) {
					//确定,发送请求
					for (var i = 0; i < rows.length; i++) {
						var order = rows[i]; //json对象
						var id = order.id;
					}
					location.href = "orderAction_buyCheckDetail.action?orderId=" + id;
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
					<td>订单状态:</td>
					<td><select name="type" id="type" class="easyui-combobox" style="width: 145px;">
	           			<option value="-1">-----请选择-----</option>
	           			<option value="111">未审核</option>
	           			<option value="121">审核通过</option>
	           			<option value="120">审核驳回</option>
	           			<option value="131">采购中</option>
	           			<option value="141">入库中</option>
	           			<option value="199">结单</option>
	           		</select></td>
					<td>下单人:</td>
					<td><input type="text" name="creater.name" id="creater"
						class="easyui-validatebox" /></td>
					<td>总量:</td>
					<td><input type="text" name="totalNum" id="totalNum"
						class="easyui-numberbox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td>
					<td><input type="text" name="totalNumMax" id="totalNum"
						class="easyui-numberbox" /></td>
					<td>
						<div class="datagrid-toolbar">
							<a id="serach" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
				<tr>
					<td>下单时间:</td>
					<td><input type="text" name="createTime" id="createTime"
						class="easyui-datebox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td>
					<td><input type="text" name="createTimeLast" id="createTime"
						class="easyui-datebox" /></td>

					<td>总额:</td>
					<td><input type="text" name="totalPrice" id="totalPrice"
						class="easyui-numberbox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td>
					<td><input type="text" name="totalPriceMax" id="totalPrice"
						class="easyui-numberbox" /></td>

				</tr>
			</table>
		</form>
		<table id="grid" style="width: 1330px; height: 552px;">
		</table>
	</div>
</body>
</html>