package com.nh.esb.service.akka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.nh.esb.core.INhCmdConst;
import com.nh.esb.core.INhCmdHandler;
import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class NhAkkaCmdService implements ApplicationContextAware {
	private static ApplicationContext context;//声明一个静态变量保存
	private static Boolean checkPassFlag=false;//检查用户密码开关
	private static Map passMap=new HashMap();
	public static ActorSystem system = null;
	static {
        Config config=ConfigFactory.parseString("akka.actor.provider = \"akka.remote.RemoteActorRefProvider\"")
        		.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port = 2556"))
        		.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname  = localhost"))
        		.withFallback(ConfigFactory.load());
        system = ActorSystem.create("nhesbService",config);		
		system.actorOf(Props.create(NhAkkaReceiverActor.class),"nhCmdService");
	}
	public static Map getPassMap() {
		return passMap;
	}
	public static void setPassMap(Map passMap) {
		NhAkkaCmdService.passMap = passMap;
	}
	public static Boolean getCheckPassFlag() {
		return checkPassFlag;
	}
	public  void setCheckPassFlag(Boolean checkPassFlag) {
		NhAkkaCmdService.checkPassFlag = checkPassFlag;
	}
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		this.context=contex;
		
	}
	public static ApplicationContext getContext(){
		  return context;
		}
	public static NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest) {
		NhCmdResult result=new NhCmdResult();
		result.setRequestId(nhCmdRequest.getRequestId());
		if(checkPassFlag==true){
			String user=nhCmdRequest.getUser();
			String passWord=nhCmdRequest.getPassWord();
			String checkWord=(String) passMap.get(user);
			if(!passWord.equals(checkWord)){
				result.setResultStatus(INhCmdConst.STATUS_ERROR);
				result.setResultCode("password_error");
				return result;	
			}
		}
		String cmdName=nhCmdRequest.getCmdName();
		if(cmdName==null || "".equals(cmdName)){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("cmdname_null");
			return result;
		}

		String handlerName="ws"+cmdName+"CmdHandler";
		INhCmdHandler handler=null;
		try{
			handler=(INhCmdHandler) getContext().getBean(handlerName);
		}catch(Exception ex){

			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_not_found");
			return result;			
		}
		try{
			handler.execHandler(nhCmdRequest,result);
		}catch(Exception ex2){
			result.setResultStatus(INhCmdConst.STATUS_ERROR);
			result.setResultCode("handler_exec_error");
			return result;	
		}
		
		return result;
	}

}
