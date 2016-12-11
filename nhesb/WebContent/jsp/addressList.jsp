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
$(function(){
	$("#dataList").datagrid({
		url:url,
		method:'POST',
		columns:[[
		{field:"uuid",title:"id"},
		{field:"sysid",title:"系统标识"},
		{field:"ip",title:"ip"},
		{field:"port",title:"port"},
		{field:"url",title:"访问路径"}
		]]
			
		});
	
});

</script>
</head>
<body>
<table id="dataList">
</table>
</body>
</html>