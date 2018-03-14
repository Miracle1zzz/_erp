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
	//工具栏
	var toolbar = [
	{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	},
	{
		id : 'button-show',
		text : '显示仓库库存分布图',
		iconCls : 'icon-search',
		handler : doShow
	}
	];
	// 定义列
	var columns = [ [ {
		field : 'id'
	}, {
		field : 'store.name',
		title : '仓库名称',
		width : 120,
		align : 'center',
		formatter: function(data,row,index){
			return row.store.name
		}
	}, {
		field : 'typeView',
		title : '操作类别',
		width : 80,
		align : 'center'
	} , {
		field : 'operTimeView',
		title : '操作时间',
		width : 120,
		align : 'center'
	},{
		field : 'emp.name',
		title : '操作人',
		width : 120,
		align : 'center',
		formatter: function(data,row,index){
			return row.emp.name
		}
	},{
		field : 'num',
		title : '数量',
		width : 80,
		align : 'center'
	}
	] ];

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
			url : "operDetailAction_pageQuery.action",
			idField : 'id',
			columns : columns,
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
	function doExport(){
		//发送请求，请求action，进行文件下载
			$.messager.confirm("删除确认", "你确定要导出选中的仓库明细数据吗？", function(r) {
				if (r) {
					window.location.href = "storeDetailAction_exportXls.action";
				}
			});
	}
	function doShow(){
		$('#editShowWindow').window("open");
		$.post("storeDetailAction_findGoodsGroupByStore.action",function(data){
		
		 $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '仓库库存在所有仓库中占比'
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '仓库库存占比',
            data: data
        }]
    });
		});
		
	}
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">

	<div region="center" border="false" style="height: 50%;width: 100%">
		<form id="searchStoreForm">
			<table>
				<tr>
					<td>仓库名称：</td>
					<td><input type="text" name="store.name" class="easyui-validatebox" /></td>
					<td>操作类别:</td>
					<td><select name="type" id="type" class="easyui-combobox" style="width: 145px;">
	           			<option value="-1">-----请选择-----</option>
	           			<option value="1">入库</option>
	           			<option value="2">出库</option>
	           		</select></td>
	           		<td>操作时间:</td><td><input type="text" name="operTime" id="operTime" class="easyui-datebox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td><td><input type="text" name="lastOperTime" id="operTime" class="easyui-datebox" /></td>
					<td>
						<div class="datagrid-toolbar">
							<a id="serach" href="#" class="easyui-linkbutton" plain="true"
								data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
				</tr>
				<tr>
					<td>操作人：</td>
					<td><input type="text" name="emp.name" class="easyui-validatebox" /></td>
					<td>货物名称：</td>
					<td><input type="text" name="goods.name" class="easyui-validatebox" /></td>
					<td>数量:</td><td><input type="text" name="num" id="num" class="easyui-numberbox" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td><td><input type="text" name="maxNum" id="num" class="easyui-numberbox" /></td>
				</tr>
			</table>
		</form>
		<table id="grid" style="width: 1330px; height: 552px;"></table>
		</div>
		
	<div class="easyui-window" title="对仓库进行添加或者修改" id="addStoreWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
		</div>
	</div>
</body>
</html>
