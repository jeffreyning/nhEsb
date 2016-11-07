<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务地址列表</title>
</head>
<body>
<table>
<tr>
<th style="width:150px">系统标识</th>
<th style="width:150px">系统IP或域名</th>
<th style="width:150px">服务端口</th>
</tr>
<c:forEach var="row" items="${addressList }">
<tr>
<td>${row.sysid }</td>
<td>${row.ip }</td>
<td>${row.port }</td>
</tr>
</c:forEach>
</table>
</body>
</html>