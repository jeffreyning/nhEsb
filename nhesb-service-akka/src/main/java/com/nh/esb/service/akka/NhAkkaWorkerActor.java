package com.nh.esb.service.akka;

import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

import akka.actor.UntypedActor;

public class NhAkkaWorkerActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {

		try{
		NhCmdRequest nhCmdRequest=(NhCmdRequest) message;
		NhCmdResult nhCmdResult=NhAkkaCmdService.execNhCmd(nhCmdRequest);
		getSender().tell(nhCmdResult, getSelf());

		}finally{
			getContext().stop(this.getSelf());
		}		
		
	}

}
