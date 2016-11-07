package com.nh.esb.manweb.cmd;

import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

public class WsConfigCmdHandler implements INhCmdHandler {

	public void execHandler(NhCmdRequest request, NhCmdResult result) {
		System.out.println("get config");
		String test="[{\"uuid\":\"123\",\"sysid\":\"B\",\"ip\":\"localhost\",\"port\":\"8080\",\"url\":\"http://localhost:8080/demo-service/webservice/nhCmdService\"}]";
		result.setResultData(test);
	}

}
