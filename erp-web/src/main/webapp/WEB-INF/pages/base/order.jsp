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
	},
	{
		id : 'button-redo',
		text : '详情',
		iconCls : 'icon-redo',
		handler : doDetail
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}
	];
	
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
		title : '订单总量',
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
			pagination : true,
			url : "orderAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
	
		// 编辑部门窗口
		$('#addOrderWindow').window({
			title : '添加订单',
			width : 800,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
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
	
	function doDetail(){
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length != 1) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择一条订单数据进行操作！", "warning");
		} else {
			 $(function(r){
				if (r) {
					//确定,发送请求
					for (var i = 0; i < rows.length; i++) {
						var order = rows[i]; //json对象
						var id = order.id;
					}
					location.href = "orderAction_buyDetail.action?orderId=" + id;
				}
			});
		}
	}

	function doAdd(){
		$('#addOrderWindow').window("open");
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
	
	<div class="easyui-window" title="对订单进行添加或者修改" id="addOrderWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			<a id="new" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-add'">添加</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="OrderaddForm" method="post" action="orderAction_add.action">
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="5">基本信息</td></tr>
	           	<tr><td align="center">供应商：</td>
						<td colspan="4"><input class="easyui-combobox" name="supplier.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',url:'supplierAction_listajax.action',
    							onSelect: function(rec){
    								$('#goodstypeedit').combobox('clear');
									$('#goodstypeedit').combobox('loadData', {});
									$('#goodsedit').combobox('clear');
									$('#goodsedit').combobox('loadData', {});
									$('#price').numberbox('setValue',{});
									$('#total').numberbox('setValue',{});
									$('#total2').numberbox('setValue',{});
   								 var url = 'goodstypeAction_listajaxById.action?supplierid='+rec.id;
    							$('#goodstypeedit').combobox('reload', url);
    							$('#nums').numberbox('setValue',1);
    						}" required="true"/></td>
    						
    							</tr>
    			<tr align="center">
						<td width="25%" height="30">商品类别</td>
						<td width="25%">商品名称</td>
						<td width="10%">采购数量</td>
						<td width="15%">单价</td>
						<td width="15%">总计</td>
					</tr>
    			<tr align="center">
    				<td><input class="easyui-combobox" id="goodstypeedit" name="goodstype.id"  
    							data-options="valueField:'id',textField:'name',mode:'remote',
    							url:'goodstypeAction_listajax.action',                   
    							onSelect: function(res){
    								$('#goodsedit').combobox('clear');
									$('#goodsedit').combobox('loadData', {});
    								
   								 var url = 'goodsAction_listajaxById.action?goodstypeid='+res.id;
   								 $('#nums').numberbox('setValue',1);
    							$('#goodsedit').combobox('reload', url);
    							
    						}
    							"  required="true"/></td>
    				<td><input class="easyui-combobox" id="goodsedit" name="goodsId"  
    							data-options="valueField:'id',textField:'name',mode:'remote',
    							url:'goodsAction_listajax.action'" required="true"/></td>
    				<td> 
    					<script type="text/javascript">
    						$(function(){
    							$("#goodsedit").combobox({
    								onSelect: function(res){
   								 	var url_2 = 'goodsAction_findPriceById.action?goodsid='+res.id;
   								 	$.post(url_2,function(data){
   								 		for(var i=0;i<data.length;i++){
   								 			var inPriceView = data[i].inPriceView;
   								 			var inPrice = data[i].inPrice;
   								 		}
   								 		$("#price").numberbox('setValue',inPriceView);
   								 		$("#total").numberbox('setValue', inPriceView);
   								 		$('#total2').numberbox('setValue', inPriceView);
   								 	});
   								 	}
    							});
    							
   								
    						});
    					</script>
    				<input type="text" name="nums" id="nums" class="easyui-numberbox" value="1" required="true"/></td>
    				<td><input type="text" name="price" id="price" class="easyui-numberbox"  max="100000000" precision="2" required="true"/></td>
    				<td>
    				<input type="text" name="total" id="total" class="easyui-numberbox"  max="100000000" precision="2" readonly="readonly"/>
    				<script type="text/javascript">
    					$(function(){
    						 $("#nums").live("keyup",function(){
    							var $nums = $(this);
    							var price = $('#price').numberbox('getValue');
    							var total = $nums.val() * price;
    							$('#total').numberbox('setValue', total);
    							$('#total2').numberbox('setValue', total);
    						}); 
    						//$("#total").numberbox('setValue', 206.12);
    						$("#save").click(function(){
    							var r = $("#OrderaddForm").form('validate');
    							if(r){
    								$("#OrderaddForm").submit();
    							}
    						});
    						
    					});
    				</script>
    				</td>
    				
    			</tr>
    			<tr class="title" id="finalTr">
    				<td colspan="4" align="right">总计:</td>
    				<td align="center"><input type="text" name="total2" id="total2" class="easyui-numberbox"  max="100000000" precision="2" readonly="readonly"/></td>
    				</tr>				
           </table>
       </form>
	</div>
	</div>
</body>
</html>