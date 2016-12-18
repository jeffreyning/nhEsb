package com.nh.esb.ws;

import java.security.NoSuchAlgorithmException;

import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
import com.nh.esb.core.PassUtil;

/**
 * 
 * @author ninghao
 *
 */
public class NhCxfCmdService implements INhCmdService {
	public NhCxfCmdService(INhCmdService nhCmdService){
		this.nhCmdService=nhCmdService;
	}
	private INhCmdService nhCmdService;

	@Override
	public NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) throws Exception {
		String userId=nhCmdRequest.getUser();
		if(userId!=null){
			String pass=(String) NhEsbClientFactory.getPassMap().get(userId);
			if(pass!=null){
				String md5=PassUtil.createMd5(nhCmdRequest,pass);
				nhCmdRequest.setPassWord(md5);			
			}
		}
		return nhCmdService.execNhCmd(nhCmdRequest);
		
	}

}
