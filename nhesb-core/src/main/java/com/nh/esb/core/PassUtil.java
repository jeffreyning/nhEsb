package com.nh.esb.core;

public class PassUtil {
	public static String createMd5(NhCmdRequest nhCmdRequest,String pass) throws Exception{
		String read=nhCmdRequest.getUser()+"-"+
				nhCmdRequest.getRequestId()+"-"+
				nhCmdRequest.getSendTime()+"-"+
				pass+"-"+
				nhCmdRequest.getCmdData();
		StringBuilder sb = new StringBuilder();
		java.security.MessageDigest md5 = java.security.MessageDigest.getInstance( "MD5" );
		md5.update( read.getBytes("UTF-8") );
		for (byte b : md5.digest()) {
			sb.append(String.format("%X", b));
		}		
		return sb.toString();
	}
}
