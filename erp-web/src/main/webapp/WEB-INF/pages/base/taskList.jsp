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
	var toolbar = [
	{
		id : 'button-association',
		text : '关联司机',
		iconCls : 'icon-sum',
		handler : doAssociations
	}];
	
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
			toolbar : toolbar,
			singleSelect: true,
			pagination : true,
			url : "orderAction_pageQueryTask.action",
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


function doAssociations(){
		//获取数据表格所有选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length != 1){
			$.messager.alert("提示信息","请选中一行定区操作","warning");
		}else{
			$('#staffWindow').window('open');
			//清理下拉框
			$("#noassociationSelect").empty();
			$("#associationSelect").empty();
			//发送ajax请求，请求定区action，在定区action中通过crm代理对象实现对crm的远程调用
			var url_1 = "orderAction_findListNotAssociation.action";
			$.post(url_1,function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var telephone = data[i].telephone;
					name = name + "(" +telephone+ ")";
					//////
					$("#noassociationSelect").append("<option value='"+id+"'>"+name+"</option>");
				}
			});
			
			var url_2 = "orderAction_findListHasAssociation.action";
			var id = rows[0].id;
			$.post(url_2,{"id":id},function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var telephone = data[i].telephone;
					name = name + "(" +telephone+ ")";
					//////
					$("#associationSelect").append("<option value='"+id+"'>"+name+"</option>");
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
	           			<option value="121">审核通过</option>
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
	
	<div modal=true class="easyui-window" title="关联司机窗口" id="staffWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 400px;height: 300px;">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="staffForm" action="orderAction_assignStaff.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="3">关联司机</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="id" id="customerDecidedZoneId" />
							<select id="noassociationSelect" size="10"></select>
						</td>
						<td>
							<input type="button" value="》》" id="toRight"><br/>
							<input type="button" value="《《" id="toLeft">
							<script type="text/javascript">
								$(function(){
									//为左右按钮绑定事件
									$("#toRight").click(function(){
										var r = $("#associationSelect option").length;
										if(r>0){
											$.messager.alert("提示信息","你已选择了一个司机","warning");
										}else{
											$("#associationSelect").append($("#noassociationSelect option:selected"));
										}
									});
									$("#toLeft").click(function(){
										$("#noassociationSelect").append($("#associationSelect option:selected"));
									});
									//未关联客户绑定事件
									$("#associationBtn").click(function(){
										//获取选中定区信息
										var rows = $("#grid").datagrid("getSelections");
										var id = rows[0].id;
										$("input[name=id]").val(id);
										//提交表单前，将右侧下拉框选中
										$("#associationSelect option").attr("selected","selected");
										$("#staffForm").submit();
									});
								});
							</script>
						</td>
						<td>
							<select id="associationSelect" name="staffIds" size="10"></select>
						</td>
					</tr>
					<tr>
						<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联司机</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>