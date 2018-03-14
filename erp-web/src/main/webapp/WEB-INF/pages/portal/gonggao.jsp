<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/datagrid-detailview.js"></script>
<div style="padding:10px;">
	<table style="height:200px" id="grid">
		</table>
		<script type="text/javascript">
			$('#grid').datagrid({
				rownumbers : true,
				remoteSort : false,
				nowrap : false,
				fitColumns : true,
				sortName : 'priority',
				sortOrder: 'asc',
				pagination : true,
				pageList : [ 4, 6, 8 ],
				url : 'noticeAction_pageQuery.action',
				columns : [ [
					{
						field : 'priorityView',
						title : '优先级',
						width : 60,
						align : 'center',
						sortable : true
					},
					{
						field : 'priority',
						title : '优先级',
						width : 60,
						align : 'center',
						sortable : true,
						hidden: 'true'
					},
					{
						field : 'title',
						title : '主题',
						width : 160,
						align : 'center',
						sortable : true
					},
					{
						field : 'despatcher.name',
						title : '发送人',
						formatter : function(data, row, index) {
							return row.despatcher.name;
						},
						width : 120,
						align : 'center',
						sortable : true
					},
					{
						field : 'timeView',
						title : '发送日期',
						width : 120,
						sortable : true
					},
				] ],
				view: detailview,
				detailFormatter: function(rowIndex, rowData){
					return '<table><tr>' +
					'<td style="border:0">' +
					'<p>詳細信息: ' + rowData.content + '</p>' +
					'</td>' +
					'</tr></table>';
	}
			});
		</script>
	
</div>