package com.nh.esb.core;

import java.io.Serializable;

/**
 * 
 * @author ninghao
 *
 */
public class NhCmdResult implements Serializable {
	private Integer resultStatus=INhCmdConst.STATUS_SUCCESS;
private String resultCode=INhCmdConst.CODE_SUCCESS;
private String resultData;
private String errMsg="";
private String requestId;
public String getRequestId() {
	return requestId;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
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
