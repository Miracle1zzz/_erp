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
		id : 'button-association',
		text : '入库',
		iconCls : 'icon-sum',
		handler : doAssociations
	} ];
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'orderNum',
		title : '订单号',
		width : 120,
		align : 'center'
	}, {
		field : 'completer.name',
		title : '跟单人',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.completer.name;
		}
	}
	,{
		field : 'completer.telephone',
		title : '联系方式',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.completer.telephone;
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
		field : 'totalNum',
		title : '订单总量',
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
			toolbar:toolbar,
			singleSelect: true,
			pagination : true,
			url : "orderAction_pageQueryInStore.action",
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
	//入库
	function doAssociations(){
		//获取数据表格所有选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length != 1){
			$.messager.alert("提示信息","请选中一行定区操作","warning");
		}else{
			$('#storeWindow').window('open');
			//清理下拉框
			$("#noassociationSelect").empty();
			$("#associationSelect").empty();
			//发送ajax请求，请求定区action，在定区action中通过crm代理对象实现对crm的远程调用
			var url_1 = "orderAction_findListStoreNotAssociation.action";
			$.post(url_1,function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var address = data[i].address;
					name = name + "(" +address+ ")";
					//////
					$("#noassociationSelect").append("<option value='"+id+"'>"+name+"</option>");
				}
			});
			var url_2 = "orderAction_findListStoreHasAssociation.action";
			var id = rows[0].id;
			$.post(url_2,{"id":id},function(data){
				for(var i=0;i<data.length;i++){
					var id = data[i].id;
					var name = data[i].name;
					var address = data[i].address;
					name = name + "(" +address+ ")";
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
					<td>订单号:</td>
					<td><input type="text" name="orderNum" id="orderNum"
						class="easyui-validatebox" /></td>

					<td>跟单人:</td>
					<td><input type="text" name="completer.name" id="completer"
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
		<table id="grid" style="width: 1330px; height: 577px;">
		</table>
	</div>
	
	<div modal=true class="easyui-window" title="关联仓库窗口" id="storeWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 400px;height: 300px;">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="storeForm" action="orderAction_assignStore.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="3">选择仓库</td>
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
											$.messager.alert("提示信息","你已选择了一个仓库","warning");
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
										$("#storeForm").submit();
									});
								});
							</script>
						</td>
						<td>
							<select id="associationSelect" name="storeIds" size="10"></select>
						</td>
					</tr>
					<tr>
						<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联仓库</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>