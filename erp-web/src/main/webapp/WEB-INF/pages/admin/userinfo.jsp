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
	$(function(){
		$("body").css({visibility:"visible"});
		$('#save').click(function(){
			var r = $('#empForm').form("validate");
			if(r){
			$('#empForm').submit();
			}
		});
	});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="empForm" method="post" action="empAction_add.action">
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>真实姓名:</td><td><input type="text" name="name" id="name" class="easyui-validatebox" required="true" /></td>
					<td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td></tr>
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
					<input class="easyui-combobox" name="dep.id"  
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
</body>
</html>