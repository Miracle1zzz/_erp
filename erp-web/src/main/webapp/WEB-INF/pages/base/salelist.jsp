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
	var myIndex = -1; //全局变量，始终等于选中行
	// 工具栏
	var toolbar = [ {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	},
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-remove',
		handler : doDelete
	},{
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : function(){
		
			var rows = $("#grid").datagrid("getSelections");
			if(rows.length == 1){
				var row = rows[0];
				//获得指定行索引
				myIndex = $("#grid").datagrid("getRowIndex",row);
			}else{
				$.messager.alert("提示信息", "请选择一行数据进行操作！", "warning");
			}
			$("#grid").datagrid("beginEdit",myIndex);
		}
	} ,{
		id : 'button-save',
		text : '保存',
		iconCls : 'icon-save',
		handler : function(){
			$("#grid").datagrid("endEdit",myIndex);
		}
	},
	{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	},{
		id : 'button-association',
		text : '关联客户',
		iconCls : 'icon-sum',
		handler : doAssociations
	}
	];
	
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'orderNum',
		title : '订单编号',
		width : 120,
		align : 'center',editor: {
			type: "validatebox",
			options:{}
		}
	}, {
		field : 'supplier.name',
		title : '供应商',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.supplier.name;
		},editor: {
			type: "combobox",
			options:{}
		}
	},{
		field : 'creater.name',
		title : '制单人',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.creater.name;
		},editor: {
			type: "combobox",
			options:{}
		}
	},{
		field : 'createTimeView',
		title : '制单时间',
		width : 120,
		align : 'center',editor: {
			type: "datebox",
			options:{}
		}
	},{
		field : 'totalNum',
		title : '订单总量',
		width : 80,
		align : 'center',editor: {
			type: "numberbox",
			options:{}
		}
	},{
		field : 'totalPrice',
		title : '订单总金额',
		width : 80,
		align : 'center',editor: {
			type: "numberbox",
			options:{}
		}
	},{
		field : 'typeView',
		title : '订单状态',
		width : 80,
		align : 'center',editor: {
			type: "combobox",
			options:{}
		}
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
			pagination : true,
			url : "saleAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
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

	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的订单！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的订单吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var order = rows[i]; //json对象
						var id = order.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "orderAction_deleteBatch.action?ids=" + ids;
				}
			});
		}
	}
	function doAdd(){
		location.href = "page_base_saleadd.action";
	}
	function doDblClickRow(rowIndex, rowData) {
		//使用form表单对象的load方法回显数据
		$("#orderEditForm").form("load", rowData);
	}
	function doExport(){
		//发送请求，请求action，进行文件下载
			$.messager.confirm("导出确认", "你确定要导出订单数据吗？", function(r) {
				if (r) {
					window.location.href = "orderAction_exportXls.action";
				}
			});
	}
	function doAssociations(){
		//获取数据表格所有选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length != 1){
			$.messager.alert("提示信息","请选中一行定区操作","warning");
		}else{
			$('#customerWindow').window('open');
			//清理下拉框
			$("#noassociationSelect").empty();
			$("#associationSelect").empty();
			//发送ajax请求，请求定区action，在定区action中通过crm代理对象实现对crm的远程调用
			var url_1 = "saleAction_findListNotAssociation.action";
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
			
			var url_2 = "saleAction_findListHasAssociation.action";
			var decidedzoneId = rows[0].id;
			$.post(url_2,{"id":decidedzoneId},function(data){
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
	           			<option value="211">未审核</option>
	           			<option value="221">审核通过</option>
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
	
	<!-- 关联客户窗口 -->
	<div modal=true class="easyui-window" title="关联客户窗口" id="customerWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 400px;height: 300px;">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="customerForm" action="decidedzoneAction_assigncustomerstodecidedzone.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="3">关联客户</td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="id" id="customerDecidedZoneId" />
							<select id="noassociationSelect" multiple="multiple" size="10"></select>
						</td>
						<td>
							<input type="button" value="》》" id="toRight"><br/>
							<input type="button" value="《《" id="toLeft">
							<script type="text/javascript">
								$(function(){
									//为左右按钮绑定事件
									$("#toRight").click(function(){
										$("#associationSelect").append($("#noassociationSelect option:selected"));
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
										$("#customerForm").submit();
									});
								});
							</script>
						</td>
						<td>
							<select id="associationSelect" name="customerIds" multiple="multiple" size="10"></select>
						</td>
					</tr>
					<tr>
						<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联客户</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>