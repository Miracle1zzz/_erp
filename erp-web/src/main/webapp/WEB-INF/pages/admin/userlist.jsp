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
		field : 'username',
		title : '用户名',
		width : 80,
		align : 'center'
	} ,{
		field : 'name',
		title : '真实姓名',
		width : 80,
		align : 'center'
	}] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		align : 'center',
		formatter : function(data, row, index) {
			if (data == "0") {
				return "男";
			} else if(data == "1"){
				return "女";
			}else{
				return "暂无数据";
			}
		}
	}, {
		field : 'birthdayView',
		title : '生日日期',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '电话',
		width : 120,
		align : 'center'
	}, {
		field : 'email',
		title : '电子邮件',
		width : 120,
		align : 'center'
	}, {
		field : 'dep.name',
		title : '所属部门',
		width : 80,
		align : 'center',
		formatter : function(data,row ,index){
			return row.dep.name;
		}
	},
	{
		field : 'dep.id',
		title : '所属部门',
		width : 80,
		align : 'center',
		formatter : function(data,row ,index){
			return row.dep.id;
		},
		hidden: 'true'
	}
	
	] ] ;
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pagination : true,
			toolbar : toolbar,
			url : "empAction_pageQuery.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 编辑部门窗口
		$('#editEmpWindow').window({
			title : '修改用户',
			width : 1000,
			modal : true,
			shadow : true,
			closed : true,
			height : 500,
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
			var values = $("#searchEmpForm").serializeJson();
			$("#grid").datagrid("load", values);
		});
	});
	
	function doAdd() {
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}
	function doDelete() {
		//获取数据表格中所有选中的行，返回数组对象
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length == 0) {
			//没有选中记录，弹出提示
			$.messager.alert("提示信息", "请选择需要删除的员工！", "warning");
		} else {
			//选中了部门,弹出确认框
			$.messager.confirm("删除确认", "你确定要删除选中的员工吗？", function(r) {
				if (r) {
					var array = new Array();
					//确定,发送请求
					//获取所有选中的部门的id
					for (var i = 0; i < rows.length; i++) {
						var emp = rows[i]; //json对象
						var id = emp.id;
						array.push(id);
					}
					var ids = array.join(","); //1,2,3,4,5
					location.href = "empAction_deleteBatch.action?ids=" + ids;
				}
			});
		}
	}
	
	function doDblClickRow(rowIndex, rowData) {
		//打开修改部门窗口
		$('#editEmpWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#empEditForm").form("load", rowData);
		$("#dep").combobox('select',rowData.dep.name);
	}
	
</script>		
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <div region="center" border="false">
    	<form id ="searchEmpForm">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="username" class="easyui-validatebox" /></td>
				<td>真实姓名：</td>
				<td><input type="text" name="name" class="easyui-validatebox" /></td>
				<td>手机号：</td>
				<td><input type="text" name="telephone" class="easyui-validatebox"/></td>
						<td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="-1">-----请选择-----</option>
	           			<option value="0">男</option>
	           			<option value="1">女</option>
	           		</select>
	           	</td>
					<td>
						<div class="datagrid-toolbar">
						<a id="serach"  href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
						</div>
					</td>
			</tr>
			<tr>
				<td>电子邮件：</td>
				<td><input type="text" name="email" class="easyui-validatebox"/></td>
				<td>出生日期:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;~</td><td><input type="text" name="lastbirthday" id="birthday" class="easyui-datebox" /></td>
				
				<td>所属部门:</td><td>
					<input class="easyui-combobox" name="dep.id" style="width: 150px;"
    							data-options="valueField:'id',textField:'name',url:'depAction_listAjax.action'" />  
				</td>
			</tr>
		</table>
		</form>
    	<table id="grid" style="width: 1330px; height: 552px;"></table>
	</div>
	<div class="easyui-window" title="对用户进行添加或者修改" id="editEmpWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="empEditForm" method="post" action="empAction_edit.action">
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" readonly="readonly"/></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[6]" readonly="readonly" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>真实姓名:</td><td><input type="text" name="name" id="name" class="easyui-validatebox" required="true" /></td>
					<td>生日:</td><td><input type="text" name="birthdayView" id="birthday" class="easyui-datebox" /></td></tr>
	           	<tr><td>电子邮箱:</td><td><input type="text" name="email" id="email" class="easyui-validatebox" data-options="required:true,validType:'email'" /></td>
					<td>电话号码:</td><td>
					<script type="text/javascript">
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
					<input type="text" data-options="validType:'telphone'"
							name="telephone" class="easyui-validatebox" required="true" /></td></tr>
	           	<tr><td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="0">男</option>
	           			<option value="1">女</option>
	           		</select>
	           	</td>
					<td>所属部门:</td><td>
					<input class="easyui-combobox" id="dep" name="dep.id"  
    							data-options="valueField:'id',textField:'name',url:'depAction_listAjax.action'" />  
				</td></tr>
				<tr>
					<td>地址</td>
					<td colspan="3">
						<input type="text" name="address" id="address" style="width:80%" class="easyui-validatebox" required="true" />
					</td>
				</tr>
				<tr>
					<td>选择角色</td>
					<td colspan="3" id="roleTD">
						<script type="text/javascript">
							$(function(){
								//页面加载完成后发送ajax请求，获取所有的角色数据
								$.post('roleAction_listajax.action',function(data) {
									//在Ajax回掉函数，解析json数据
									for(var i=0;i<data.length;i++){
										var id = data[i].id;
										var name = data[i].name;
										$("#roleTD").append('<input type="checkbox" id="'+id+'" name="roleIds" value="'+id+'"><label for="'+id+'">'+name+'</label>');
									}
								});
							});
						</script>
					</td>
				</tr>
           
           </table>
       </form>
	</div>
	</div>
</body>
</html>