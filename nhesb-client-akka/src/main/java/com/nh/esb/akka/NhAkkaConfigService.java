package com.nh.esb.akka;

import java.util.List;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.nh.esb.core.INhConfigService;
import com.nh.esb.core.NhCmdResult;
import com.nh.esb.core.NhEsbAddress;

public class NhAkkaConfigService implements INhConfigService {
	public NhAkkaConfigService(){}
	public NhAkkaConfigService(String url){
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
	public List<NhEsbAddress> getAddressList() {
		List<NhEsbAddress> retList=null;
        try{
			ActorRef ref=NhEsbAkkaClientFactory.system.actorFor(url);
	        Timeout timeout = new Timeout(Duration.create(60, "seconds"));
	        Future<Object> future = Patterns.ask(ref, null, timeout);
	        retList = (List<NhEsbAddress>) Await.result(future, timeout.duration());

        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	
        }
        return retList;
	}

}
