package com.nh.esb.service.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 
 * @author ninghao
 *
 */
public class NhServletCmdContext {
public HttpServletRequest httpRequest=null;
public HttpServletResponse httpResponse=null;
public HttpSession httpSession=null;
public HttpServletRequest getHttpRequest() {
	return httpRequest;
}
public void setHttpRequest(HttpServletRequest httpRequest) {
	this.httpRequest = httpRequest;
}
public HttpServletResponse getHttpResponse() {
	return httpResponse;
}
public void setHttpResponse(HttpServletResponse httpResponse) {
	this.httpResponse = httpResponse;
}
public HttpSession getHttpSession() {
	return httpSession;
}
public void setHttpSession(HttpSession httpSession) {
	this.httpSession = httpSession;
}
}
