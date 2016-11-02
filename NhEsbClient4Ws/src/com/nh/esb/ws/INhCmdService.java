package com.nh.esb.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;


@WebService
public interface INhCmdService {
	public NhCmdResult execNhCmd(@WebParam(name="nhCmdRequest") NhCmdRequest nhCmdRequest);
}
