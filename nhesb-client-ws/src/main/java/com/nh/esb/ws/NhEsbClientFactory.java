package com.nh.esb.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.nh.esb.core.INhCmdService;
import com.nh.esb.core.INhConfigService;
import com.nh.esb.core.NhEsbAddress;

/**
 * 
 * @author ninghao
 *
 */
public class NhEsbClientFactory {
	
	private static Map passMap=new HashMap();
	public static Map getPassMap() {
		return passMap;
	}

	public void setPassMap(Map passMap) {
		NhEsbClientFactory.passMap = passMap;
	}
	private static String configUrl;
	private static Boolean remoteConfigFlag=false;
	public static Boolean getRemoteConfigFlag() {
		return remoteConfigFlag;
	}

	public  void setRemoteConfigFlag(Boolean remoteConfigFlag) {
		NhEsbClientFactory.remoteConfigFlag = remoteConfigFlag;
	}

	public static String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		NhEsbClientFactory.configUrl = configUrl;
	}
	private static Map addressMap = new HashMap();

	public static Map getAddressMap() {
		return addressMap;
	}

	public  void setAddressMap(Map addressMap) {
		NhEsbClientFactory.addressMap = addressMap;
	}
	public  void setAddressMap4Bean(NhEsbAddress address) {
		String sysId=address.getSysid();
		addressMap.put(sysId, address);
	}
	public  void setAddressMap4BeanList(List<NhEsbAddress> addressList) {
		for(NhEsbAddress address:addressList){
		String sysId=address.getSysid();
		addressMap.put(sysId, address);
		}
	}
	public static INhCmdService getClient(String sysId) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INhCmdService.class);
		NhEsbAddress address = (NhEsbAddress) addressMap.get(sysId);
		if (address == null) {
			return null;
		}

		factory.setAddress(address.getUrl());
		INhCmdService nhCmdService = (INhCmdService) factory.create();
		INhCmdService retService=new NhCxfCmdService(nhCmdService);
		return retService;
	}
	
	public static INhCmdService getClient(NhEsbAddress address) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INhCmdService.class);
		if (address == null) {
			return null;
		}

		factory.setAddress(address.getUrl());
		INhCmdService nhCmdService = (INhCmdService) factory.create();
		INhCmdService retService=new NhCxfCmdService(nhCmdService);
		return retService;
	}
	
	public static INhConfigService getConfigClient(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INhConfigService.class);
		factory.setAddress(configUrl);
		INhConfigService nhConfigService = (INhConfigService) factory.create();
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
				NhEsbClientFactory.getAddressMap().put(sysid, address);

			}
		}
	}
}
