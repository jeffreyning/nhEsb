<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String indexName=request.getParameter("index_name");

%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面信息管理</title>
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



function openDetail(){  

	var sels = $("#infoList").datagrid("getSelected");
    if(sels==""||sels==null){
	    alert("请选择行");
    }else{
		var content=sels.fullMsg;
		$("#detailContent").val(content);
    	$("#detailDialog").dialog('open').dialog('setTitle', '日志详情');
    }
	
}
$(function(){
	$('#infoList').datagrid({
		nowrap:true,
		striped:true,
		pagination : true,
		fitColumns: true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url:"<%=path%>/NhEsbServiceServlet?cmdName=Groovy&subName=micro_log_platform&groovySubName=queryLog&index_name=<%=indexName%>",
		columns:[[
					{
						field : 'MX_LEVEL',
						title : '日志等级',
						width : 20

					},
					{
						field : 'MX_TIME',
						title : '日志记录时间',
						width : 30,
						sortable:true
					},
					{
						field : 'MX_LINE',
						title : '日志记录代码位置',
						width : 30
					},
					{
						field : 'MX_MSG',
						title : '日志消息简述',
						width : 100
					},					
					{
						field : 'fullMsg',
						title : '日志消息内容',
						width : 200
					},
					{
						field : 'oper',
						title : '操作',
						width : 50,
						formatter: function(value, row, index){
							var url="";
							var ret="<a href='javascript:openDetail();'>查看详细信息</a>";
							return ret;
						}						
					}					
				]],
        toolbar : [ ],
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

</script>
</head>
<body class="easyui-layout">
	<div id="infoQuery" class="dQueryMod" region="north"
		style="height: 55px">
		<form id="searchForm">
			<table id="searchTable">
				<tr>
					<td>日志级别</td>
					<td><input type="text" id="log_level" name="log_level" /></td>
					<td>日志消息内容</td>
					<td><input type="text" id="log_msg" name="log_msg" /></td>					
					<td><a href="#" class="easyui-linkbutton "
						iconCls="icon-search" onclick="ReQuery()">查询</a><a href="#"
						class="easyui-linkbutton" iconCls="icon-redo"
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

	<div id="detailDialog" class="easyui-dialog" modal="true"
		align="center"
		style="padding: 30px 10px 10px 10px; border: 0px; margin: 0px; width: 460px;"
		closed="true" resizable="true" inline="false">
		<input type="hidden" id="id" name="id" value="" />
		<textarea id="detailContent" rows="20" cols="150"></textarea>
		<div id="buttons" style="height: 30px; padding-top: 20px;">
			<a class="easyui-linkbutton dPbtnLight70"
				href="javascript:void(0)" onclick="$('#detailDialog').dialog('close');return false;">取消</a>				
		</div>
	</div>		

</body>
</html>