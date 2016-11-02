package com.nh.esb.core;

public class NhCmdResult {
private Integer resultStatus;
private String resultCode;
private String resultData;
private String errMsg;
public String getErrMsg() {
	return errMsg;
}
public void setErrMsg(String errMsg) {
	this.errMsg = errMsg;
}
public Integer getResultStatus() {
	return resultStatus;
}
public void setResultStatus(Integer resultStatus) {
	this.resultStatus = resultStatus;
}
public String getResultCode() {
	return resultCode;
}
public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
}
public String getResultData() {
	return resultData;
}
public void setResultData(String resultData) {
	this.resultData = resultData;
}
}
