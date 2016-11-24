package com.nh.esb.akka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import akka.actor.ActorSystem;

import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.INhConfigService;
import com.nh.esb.core.NhEsbAddress;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;



public class NhEsbAkkaClientFactory {
	private static String configUrl;
	public static ActorSystem system=null;
	static{
        Config config=ConfigFactory.parseString("akka.actor.provider = \"akka.remote.RemoteActorRefProvider\"")
        		.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port = 2558"))
        		.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname  = localhost"))
        		.withFallback(ConfigFactory.load());
        system = ActorSystem.create("nhesbClient",config);	
	}
	private static Boolean remoteConfigFlag=false;
	public static Boolean getRemoteConfigFlag() {
		return remoteConfigFlag;
	}

	public  void setRemoteConfigFlag(Boolean remoteConfigFlag) {
		NhEsbAkkaClientFactory.remoteConfigFlag = remoteConfigFlag;
	}

	public static String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		NhEsbAkkaClientFactory.configUrl = configUrl;
	}
	private static Map addressMap = new HashMap();

	public static Map getAddressMap() {
		return addressMap;
	}

	public  void setAddressMap(Map addressMap) {
		NhEsbAkkaClientFactory.addressMap = addressMap;
	}
	public  void setAddressMap4Bean(NhEsbAddress address) {
		String sysId=address.getSysid();
		addressMap.put(sysId, address);
	}
	public static INhCmdService getClient(String sysId) {

		NhEsbAddress address = (NhEsbAddress) addressMap.get(sysId);
		if (address == null) {
			return null;
		}

		INhCmdService nhCmdService =new NhAkkaCmdService(address.getUrl());
		return nhCmdService;
	}
	public static INhConfigService getConfigClient(){
		INhConfigService nhConfigService = new NhAkkaConfigService(configUrl);
		return nhConfigService;
	}
	public static void init(){
		if(remoteConfigFlag==false){
			return;
		}
		INhConfigService nhConfigService=getConfigClient();
		List<NhEsbAddress> addressList=nhConfigService.getAddressList();
		if(addressList!=null){
			for(NhEsbAddress address:addressList){
				String sysid=address.getSysid();
				NhEsbAkkaClientFactory.getAddressMap().put(sysid, address);

			}
		}
	}	
	
	
}
