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
	// 工具栏
	var toolbar = [  {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'name',
		title : '供应商名称',
		width : 120,
		align : 'center'
	} ,{
		field : 'address',
		title : '供应商地址',
		width : 120,
		align : 'center'
	}] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'contact',
		title : '联系人',
		width : 120,
		align : 'center',
	}, {
		field : 'telephone',
		title : '联系方式',
		width : 120,
		align : 'center'
	}, {
		field : 'needs',
		title : '送货方式',
		width : 80,
		align : 'center',
		formatter : function(data, row, index) {
			if (data == "0") {
				return "送货";
			} else if(data == "1") {
				return "自提";
			}else{
				return "暂无数据";
			}
		}
	}] ] ;
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			//fit : true,
			pagination : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "supplierAction_pageQuery.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		$('#addSupplierWindow').window({
			title : '新增供应商',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		$('#editSupplierWindow').window({
			title : '修改供应商',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		
		$("body").css({visibility:"visible"});
		
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
			var values = $("#searchSupplierForm").serializeJson();
			$("#grid").datagrid("load", values);
		});
	});
	
	function doAdd() {
		$('#addSupplierWindow').window("open");
	}
	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的供应商！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的供应商吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var supplier = rows[i]; //json对象
						var id = supplier.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "supplierAction_deleteBatch.action?ids=" + ids;
				}
			});
		}
	}
	
	function doDblClickRow(rowIndex, rowData) {
		//打开修改部门窗口
		$('#editSupplierWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#supplierEditForm").form("load", rowData);
	}
	
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<form id="searchSupplierForm">
			<table>
				<tr>
					<td>供应商名：</td>
					<td><input type="text" name="name" class="easyui-validatebox" /></td>
					<td>联系人：</td>
					<td><input type="text" name="contact"
						class="easyui-validatebox" /></td>
					<td>联系电话：</td>
					<td><input type="text" name="telephone" class="easyui-validatebox" /></td>
					<td>送货方式:</td>
					<td><select name="needs" id="needs" class="easyui-combobox"
						style="width: 145px;">
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
		<table id="grid" style="width: 1330px; height: 577px;"></table>
	</div>
	<div class="easyui-window" title="对供应商进行添加或者修改" id="addSupplierWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="add" icon="icon-add" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="supplierAddForm" method="post"
				action="supplierAction_add.action">
				<table class="table-edit" width="95%" align="center">
					<tr>
						<td>供应商名:</td>
						<td><input type="text" name="name" id="name"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>供应商地址:</td>
						<td><input type="text" name="address" id="address"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>联系人:</td>
						<td><input type="text" name="contact" id="contact"
							class="easyui-validatebox" required="true" /></td>
							</tr>
							<tr>
						<td>联系方式:</td>
						<td><script type="text/javascript">
							$(function() {
								$('#add').click(function(){
								var r = $('#supplierAddForm').form("validate");
								if(r){
									$('#supplierAddForm').submit();
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
					<tr>
						<td>送货方式:</td>
						<td><select name="needs" id="needs" class="easyui-combobox"
							style="width: 145px;">
								<option value="0">送货</option>
								<option value="1">自提</option>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="对供应商进行添加或者修改" id="editSupplierWindow"
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
			<form id="supplierEditForm" method="post" action="supplierAction_edit.action">
				<input type="hidden" name="id" />
				<table class="table-edit" width="95%" align="center">
					<tr>
						<td>供应商名:</td>
						<td><input type="text" name="name" id="name"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>供应商地址:</td>
						<td><input type="text" name="address" id="address"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>联系人:</td>
						<td><input type="text" name="contact" id="contact"
							class="easyui-validatebox" required="true" /></td>
							</tr>
							<tr>
						<td>联系方式:</td>
						<td><script type="text/javascript">
							$(function() {
								$('#edit').click(function(){
								var r = $('#supplierEditForm').form("validate");
								if(r){
									$('#supplierEditForm').submit();
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
					<tr>
						<td>送货方式:</td>
						<td><select name="needs" id="needs" class="easyui-combobox"
							style="width: 150px;">
								<option value="0">送货</option>
								<option value="1">自提</option>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>