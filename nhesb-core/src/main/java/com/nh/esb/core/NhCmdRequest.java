package com.nh.esb.core;

import java.io.Serializable;
import java.util.UUID;
/**
 * 
 * @author ninghao
 *
 */
public class NhCmdRequest implements Serializable {
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
private String requestId=UUID.randomUUID().toString();
private String bizId;
private String fromSysId;
private String toSysId;
private String user;
private String callType;

private String fromModule;
public String getFromModule() {
	return fromModule;
}
public void setFromModule(String fromModule) {
	this.fromModule = fromModule;
}

private String remark;
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}


public String getCallType() {
	return callType;
}
public void setCallType(String callType) {
	this.callType = callType;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
public String getSendTime() {
	return sendTime;
}
public void setSendTime(String sendTime) {
	this.sendTime = sendTime;
}
private String passWord;
private String sendTime;

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
