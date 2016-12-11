<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath=request.getContextPath(); %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>/js/easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务地址列表</title>
<script type="text/javascript">
var url="<%=basePath%>/address/addressList.do";
function add(){
	$("#showForm").form("clear");
	$("#submitFlag").val("add");
	$("#showDialog").dialog("open").dialog("setTitle","添加记录");
}
function modify(){
	var sel=$("#dataList").datagrid("getSelected");
	if(sel==null){
	return;
	}
	$("#showForm").form("clear");
	$("#showForm").form("load",sel);
	$("#submitFlag").val("modify");
	$("#showDialog").dialog("open").dialog("setTitle","修改记录");	
}
function remove(){
	var sel=$("#dataList").datagrid("getSelected");
	if(sel==null){
	return;
	}	
	var url="<%=basePath%>/address/removeAddress.do";
	var uuid=sel.uuid;
	$.ajax({
		url:url,
		type:"post",
		data:{"uuid":uuid},
		success:function(data,status){
			$.messager.alert("提示","操作成功");
			$("#dataList").datagrid("reload");
			},
		error:function(data,status){
			$.messager.alert("提示","操作失败");
			}
		});		
}
$(function(){
	$("#dataList").datagrid({
		url:url,
		method:'POST',
		columns:[[
		{field:"uuid",title:"id",hidden:true},
		{field:"sysid",title:"系统标识",width:100},
		{field:"ip",title:"ip",width:100},
		{field:"port",title:"port",width:100},
		{field:"url",title:"访问路径",width:300}
		]],
		toolbar:[
		         {id:"add",text:"添加",handler:add},
		         {id:"add",text:"修改",handler:modify},
		         {id:"add",text:"删除",handler:remove}
			 		]
			
		});
	
});

function submitDialog(){
	var url="<%=basePath%>/address/createAddress.do";
	var param=$("#showForm").serialize();
	$.ajax({
		url:url,
		type:"post",
		data:param,
		success:function(data,status){
			$.messager.alert("提示","操作成功");
			$("#dataList").datagrid("reload");
			},
		error:function(data,status){
			$.messager.alert("提示","操作失败");
			}
		});
	$("#showForm").form("clear");
	$("#showDialog").dialog("close");
}
</script>
</head>
<body>
<table id="dataList">
</table>
<div id="showDialog" class="easyui-dialog" closed="true" style="width:350px">
<form id="showForm">
<input id="submitFlag" name="submitFlag" type="hidden"></input> 
<input name="uuid" type="hidden"></input>
<p style="width:150px">ip</p><input class="easyui-validatebox" name="ip"></input>
<p style="width:150px">port</p><input class="easyui-validatebox" name="port"></input>
<p style="width:150px">url</p><input class="easyui-validatebox" name="url"></input>
<p style="width:150px">系统标识</p><input class="easyui-validatebox" name="sysid"></input>
</form>
<a class="easyui-linkbutton" onclick="submitDialog()">确定</a>
</div>
</body>
</html>