package com.nh.esb.core;

import java.io.Serializable;

/**
 * 
 * @author ninghao
 *
 */
public class NhEsbAddress implements Serializable{
private String uuid;
private String sysid;
private String ip;
private String url;
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getUuid() {
	return uuid;
}
public void setUuid(String uuid) {
	this.uuid = uuid;
}
public String getSysid() {
	return sysid;
}
public void setSysid(String sysid) {
	this.sysid = sysid;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public String getPort() {
	return port;
}
public void setPort(String port) {
	this.port = port;
}
private String port;
}
