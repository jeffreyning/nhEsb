package com.nh.esb.core;

public class NhCmdRequest {
	public NhCmdRequest(){
		
	}
	public NhCmdRequest(String cmdName,String cmdData){
		this.cmdName=cmdName;
		this.cmdData=cmdData;
	}
	public NhCmdRequest(String cmdName,String cmdData,String bizId){
		this.cmdName=cmdName;
		this.cmdData=cmdData;
		this.bizId=bizId;
	}
private String cmdData;
private String cmdName;
private String requestId;
private String bizId;
private String fromSysId;
private String toSysId;
public String getFromSysId() {
	return fromSysId;
}
public void setFromSysId(String fromSysId) {
	this.fromSysId = fromSysId;
}
public String getToSysId() {
	return toSysId;
}
public void setToSysId(String toSysId) {
	this.toSysId = toSysId;
}
public String getRequestId() {
	return requestId;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
public String getBizId() {
	return bizId;
}
public void setBizId(String bizId) {
	this.bizId = bizId;
}
public String getCmdData() {
	return cmdData;
}
public void setCmdData(String cmdData) {
	this.cmdData = cmdData;
}
public String getCmdName() {
	return cmdName;
}
public void setCmdName(String cmdName) {
	this.cmdName = cmdName;
}


}
