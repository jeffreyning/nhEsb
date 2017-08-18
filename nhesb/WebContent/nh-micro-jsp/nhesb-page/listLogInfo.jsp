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
		var content=sels.cmdData;
		$("#detailContent").val(content);
    	$("#detailDialog").dialog('open').dialog('setTitle', '详情');
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
						field : 'requestId',
						title : '报文流水号',
						width : 50

					},
					{
						field : 'bizId',
						title : '业务标识',
						width : 50

					},					
					{
						field : 'fromSysId',
						title : '客户端系统标识',
						width : 50,
						sortable:true
					},
					{
						field : 'toSysId',
						title : '服务端系统标识',
						width : 50
					},
					{
						field : 'cmdName',
						title : '命令标识',
						width : 50
					},					
					{
						field : 'subName',
						title : '子命令标识',
						width : 50
					},
					{
						field : 'cmdData',
						title : '报文内容',
						width : 150
					},					
					{
						field : 'timestamp',
						title : '调用时间戳',
						width : 50
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
		style="height: 75px">
		<form id="searchForm">
			<table id="searchTable">
				<tr>
					<td>客户端系统标识</td>
					<td><input type="text" id="_fromSysId" name="_fromSysId" /></td>
					<td>服务端系统标识</td>
					<td><input type="text" id="_toSysId" name="_toSysId" /></td>	
					<td>报文请求标识</td>
					<td><input type="text" id="_requestId" name="_requestId" /></td>
				</tr>
				<tr>
					<td>业务标识</td>
					<td><input type="text" id="_bizId" name="_bizId" /></td>					
					<td>消息内容</td>
					<td><input type="text" id="_log_msg" name="_log_msg" /></td>						
				</tr>
				<tr>
					<td>命令标识</td>
					<td><input type="text" id="_cmdName" name="_cmdName" /></td>					
					<td>子命令内容</td>
					<td><input type="text" id="_subName" name="_subName" /></td>						
				</tr>				
				<tr>
				
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