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
private String remark;

private String ext1;
private String ext2;
private String ext3;
private String ext4;
public String getExt1() {
	return ext1;
}
public void setExt1(String ext1) {
	this.ext1 = ext1;
}
public String getExt2() {
	return ext2;
}
public void setExt2(String ext2) {
	this.ext2 = ext2;
}
public String getExt3() {
	return ext3;
}
public void setExt3(String ext3) {
	this.ext3 = ext3;
}
public String getExt4() {
	return ext4;
}
public void setExt4(String ext4) {
	this.ext4 = ext4;
}
public String getExt5() {
	return ext5;
}
public void setExt5(String ext5) {
	this.ext5 = ext5;
}
private String ext5;

public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
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
