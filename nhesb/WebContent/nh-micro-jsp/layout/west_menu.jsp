<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path=request.getContextPath();
%>

[
{
	"id":1,
	"text":"系统管理",
	"iconCls":"icon-channels",
	"children":[{
		"id":12,
		"text":"用户列表",
		"iconCls":"icon-nav",
		"attributes":{
		  "url":"/<%=path %>/nh-micro-jsp/user-manager/listUserInfo.jsp"
		  }
	},{
		"id":16,
		"text":"角色列表",
		"iconCls":"icon-nav",
		"attributes":{
		  "url":"/<%=path %>/nh-micro-jsp/user-manager/listRoleInfo.jsp"
		  }
	},
	{
		"id":14,
		"text":"字典列表",
		"iconCls":"icon-nav",
		"attributes":{
		  "url":"/<%=path %>/nh-micro-jsp/dictionary-page/listDictionaryInfo.jsp"
		  }
	}	  
	]
}
,
{
	"id":2,
	"text":"ESB管理",
	"iconCls":"icon-channels",
	"children":[{
		"id":21,
		"text":"系统列表",
		"iconCls":"icon-nav",
		"attributes":{
		  "url":"/<%=path %>/nh-micro-jsp/nhesb-page/listEsbSysInfo.jsp"
		  }
	}  
	]
}
	
]