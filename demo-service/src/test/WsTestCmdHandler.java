package test;

import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
/**
 * 
 * @author ninghao
 *
 */
public class WsTestCmdHandler implements INhCmdHandler {

	public void execHandler(NhCmdRequest request, NhCmdResult result) {
		result.setResultData("this is test");
		
	}

}
