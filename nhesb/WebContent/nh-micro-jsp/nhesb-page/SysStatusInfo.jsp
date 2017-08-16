<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
String sysId=request.getParameter("sysId");
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>esb系统信息管理</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/nh-micro-jsp/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/nh-micro-jsp/js/easyui/themes/icon.css">
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/json2.js"></script>
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/nh-micro-jsp/js/zTree/js/jquery.ztree.core-3.4.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/nh-micro-jsp/js/zTree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript">

$(function(){
	$('#infoList').datagrid({
		nowrap:true,
		striped:true,
		pagination : true,
		fitColumns: true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url:"<%=path%>/NhEsbServiceServlet?cmdName=Groovy&subName=nhesb_sys_list&groovySubName=getInfoBySysId&sysId=<%=sysId%>",
		columns:[[

					{
						field : 'node_id',
						title : '服务节点标识',
						width : 100

					},
					{
						field : 'node_address',
						title : '服务节点地址',
						width : 150

					}			

				]],
		        toolbar : [ {
					id : "refresh",
					text : "刷新",
					iconCls : "icon-reload",
					handler : function() {
						refresh();
					}
				
				}],
        rownumbers:false,
        singleSelect:true
		
	});
});

//条件查询
function ReQuery(){
	var data = $('#searchForm').serializeObject();
	$('#infoList').datagrid('reload',data);
}

//重置查询条件
function clearForm(){
	$('#searchForm').form('clear');
}

//刷新
function refresh(){
	var querydata = $('#searchForm').serializeObject();
	$('#infoList').datagrid('reload',querydata);
}






	
	
</script>
</head>
<body id="userRole" class="easyui-layout">

	<div id="roleQuery" class="dQueryMod" region="north"
		style="height: 55px">
		<form id="searchForm">
			<table id="searchTable">
				<tr>
					<td>服务标识：</td>
					<td><input type="text" id="userId" name="userId"
						class="dInputText" /></td>
					<td><a href="#" class="easyui-linkbutton dRbtnSearch"
						iconCls="icon-search" onclick="ReQuery()">查询</a><a href="#"
						class="easyui-linkbutton dRbtnClean" iconCls="icon-redo"
						onclick="clearForm()">清空</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="roleList" region="center">
		<div class="easyui-tabs l_listwid" id="accountTab">
			<table id="infoList"></table>
		</div>
	</div>



</body>
</html>