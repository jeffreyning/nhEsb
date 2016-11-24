package com.nh.esb.service.akka;

import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class NhAkkaReceiverActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		NhCmdRequest nhCmdRequest=(NhCmdRequest) message;
		Timeout timeout = new Timeout(Duration.create(60, "seconds"));
		final ActorRef sender=getSender();
		final ActorRef ref=NhAkkaCmdService.system.actorOf(Props.create(NhAkkaWorkerActor.class));
		Future<Object> future = Patterns.ask(ref, nhCmdRequest, timeout);
		future.onComplete(new OnComplete<Object>(){
	        public void onComplete(Throwable t, Object result){
	          sender.tell(result, ref);
	        }
	    }, NhAkkaCmdService.system.dispatcher());

	}

}
