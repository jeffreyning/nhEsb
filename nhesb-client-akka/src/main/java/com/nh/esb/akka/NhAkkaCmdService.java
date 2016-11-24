package com.nh.esb.akka;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

public class NhAkkaCmdService implements INhCmdService {
	public NhAkkaCmdService(){};
	public NhAkkaCmdService(String url){
		this.url=url;
	}
	public String url="";
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) throws Exception  {
		ActorRef ref=null;
		try{
		ref=NhEsbAkkaClientFactory.system.actorFor(url);
        Timeout timeout = new Timeout(Duration.create(60, "seconds"));
        Future<Object> future = Patterns.ask(ref, nhCmdRequest, timeout);
        NhCmdResult nhCmdResult = (NhCmdResult) Await.result(future, timeout.duration());
        return nhCmdResult;
        }finally{

        }
	}

}
