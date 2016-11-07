package com.nh.esb.core;

import javax.jws.WebParam;
import javax.jws.WebService;



@WebService
public interface INhCmdService {
	public NhCmdResult execNhCmd(@WebParam(name="nhCmdRequest") NhCmdRequest nhCmdRequest);
}
