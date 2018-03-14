<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户信息</title>
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
	var editIndex;
	//存放正在编辑的行数据
	function doAdd() {
		//1.判断如果有正在编辑的行数据结束编辑
		if (editIndex != undefined) {
			$("#grid").datagrid("endEdit", editIndex);
		} else {
			//2.否则进行创建一行数据
			$('#grid').datagrid('insertRow', {
				index : 0, // 索引从0开始
				row : {} //插入一个空行
			});
			//3.如果有默认数据就添加默认数据  并开启编辑
			$("#grid").datagrid('beginEdit', 0);
			editIndex = 0;
		}
	}

	function doSave() {
		$("#grid").datagrid('endEdit', editIndex);
	}

	function doCancel() {
		if (editIndex != undefined) {
			$("#grid").datagrid('cancelEdit', editIndex);
			if ($('#grid').datagrid('getRows')[editIndex].id == undefined) {
				$("#grid").datagrid('deleteRow', editIndex);
			}
			editIndex = undefined;
		}
	}

	//工具栏
	var toolbar = [ {
		id : 'button-add',
		text : '新增一行',
		iconCls : 'icon-edit',
		handler : doAdd
	}, {
		id : 'button-cancel',
		text : '取消编辑',
		iconCls : 'icon-cancel',
		handler : doCancel
	}, {
		id : 'button-save',
		text : '保存',
		iconCls : 'icon-save',
		handler : doSave
	} ];
	// 定义列
	var columns = [ [ {
		field : 'id',
		title : '客户编号',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				required : true
			}
		}
	}, {
		field : 'name',
		title : '客户姓名',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				required : true
			}
		}
	}, {
		field : 'station',
		title : '客户单位',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				required : true
			}
		}
	}, {
		field : 'telephone',
		title : '客户电话',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				validType:'telephone',
				required : true
			}
		}
	}, {
		field : 'address',
		title : '客户地址',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				required : true
			}
		}
	}, {
		field : 'email',
		title : '客户Email',
		width : 120,
		align : 'center',
		editor : {
			type : 'validatebox',
			options : {
				validType:'email',
				required : true
			}
		}
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 运单数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList : [ 5, 10, 15 ],
			pageSize : 5,
			pagination : true,
			toolbar : toolbar,
			url : "",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
			onAfterEdit : function(rowIndex, rowData, changes) {
				//4.结束编辑flag至为undefined
				console.info(rowData);
				//将数据保存到数据库中
				$.post("", rowData, function(data) {
					if (data) {
						$.messager.show({
							title : '恭喜',
							msg : "提交成功",
							timeout : 2000
						});
					} else {
						$.messager.show({
							title : "警告",
							msg : "修改失败",
							timeout : 2000
						});
						//刷新当前数据表
						$('#grid').datagrid('load');
					}
				});
				//将数据还原
				editIndex = undefined;
			}
		});
	});

	function doDblClickRow(rowIndex, rowData) {
		console.info(rowIndex);
		$('#grid').datagrid('beginEdit', rowIndex);
		editIndex = rowIndex;
	}
	function doSearch(value, name) {
		//alert(value+"--"+name);
		//目标：将这两个值传入后台，刷新列表  
		$("#grid").datagrid("load", {
			//指定两个参数的名字，随便写。。。  这里定义的两个参数会被传递到后台
			"fieldName" : name,
			"fieldValue" : value
		});
	}
	$(function() {
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
</script>
</head>

<body class="easyui-layout" style="visibility:hidden;">
	<!-- 搜索框区域 -->
	<div region="north">
		<input class="easyui-searchbox" style="width:500px"
			data-options="searcher:doSearch,prompt:'请输入关键字',menu:'#mm'" />
		<!-- 菜单 -->
		<div id="mm">
			<div name="arriveCity" data-options="iconCls:'icon-ok'">到达地</div>
			<div name="goodsType">寄托物类型</div>
		</div>
	</div>


	<div region="center" border="false">
		<table id="grid"></table>
	</div>
</body>

</html>
