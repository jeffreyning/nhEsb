package com.nh.esb.writelog;

import java.util.HashMap;
import java.util.Map;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NhEsbWriteLog();
		Map logMap=new HashMap();
		logMap.put("cmdName", "some");
		logMap.put("cmdData","this is data");
		NhEsbWriteLog.writeLog(logMap);

	}

}
