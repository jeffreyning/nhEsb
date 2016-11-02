package test;

import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
import com.nh.esb.ws.INhCmdService;

public class TestUtil {
public static INhCmdService getNhCmdService() {
		return nhCmdService;
	}
	public void setNhCmdService(INhCmdService nhCmdService) {
		TestUtil.nhCmdService = nhCmdService;
	}
private static INhCmdService nhCmdService;
private static INhCmdService nhCmdService4Config;
public static INhCmdService getNhCmdService4Config() {
	return nhCmdService4Config;
}
public void setNhCmdService4Config(INhCmdService nhCmdService4Config) {
	TestUtil.nhCmdService4Config = nhCmdService4Config;
}
/*public static String execTest(){
	NhCmdRequest nhCmdRequest=new NhCmdRequest();
	nhCmdRequest.setCmdName("Test");
	NhCmdResult result=nhCmdService.execNhCmd(nhCmdRequest);
	return result.getResultData();
}
public static String execGetConfig(){
	NhCmdRequest nhCmdRequest=new NhCmdRequest();
	nhCmdRequest.setCmdName("Test");
	NhCmdResult result=nhCmdService.execNhCmd(nhCmdRequest);
	return result.getResultData();
}*/
}
