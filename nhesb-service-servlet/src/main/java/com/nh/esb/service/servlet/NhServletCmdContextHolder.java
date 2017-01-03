package com.nh.esb.service.servlet;

public class NhServletCmdContextHolder {
public static ThreadLocal<NhServletCmdContext> nhServletCmdContext=new ThreadLocal<NhServletCmdContext>();

public static ThreadLocal<NhServletCmdContext> getNhServletCmdContext() {
	return nhServletCmdContext;
}

public static void setNhServletCmdContext(
		ThreadLocal<NhServletCmdContext> nhServletCmdContext) {
	NhServletCmdContextHolder.nhServletCmdContext = nhServletCmdContext;
}
public static void removeNhServletCmdContext(){
	NhServletCmdContextHolder.nhServletCmdContext.remove();
}
}
