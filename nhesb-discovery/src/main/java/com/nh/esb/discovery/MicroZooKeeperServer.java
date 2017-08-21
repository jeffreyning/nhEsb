package com.nh.esb.discovery;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author ninghao
 *
 */
public class MicroZooKeeperServer {
	private ZooKeeper zk=null;
	private String rootNode="systemRoot";
	private String systemId = "default";
	private String systemAddress = "default";
	private List<Map> methodList=new ArrayList();
	
	public MicroZooKeeperServer() throws Exception{
		this.zk = new ZooKeeper(zookeeperUrl, 5000, new Watcher() {
			public void process(WatchedEvent event) {

			}
		});	
		setAddress2Zoo();
	}


	public List<Map> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<Map> methodList) {
		this.methodList = methodList;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemAddress() {
		return systemAddress;
	}

	public void setSystemAddress(String systemAddress) {
		this.systemAddress = systemAddress;
	}

	public String getZookeeperUrl() {
		return zookeeperUrl;
	}

	public void setZookeeperUrl(String zookeeperUrl) {
		this.zookeeperUrl = zookeeperUrl;
	}


	public String zookeeperUrl="localhost:2181";


	public void setAddress2Zoo() throws Exception {

        if (zk.exists("/"+rootNode, false) == null) {
        	zk.create("/"+rootNode , null,Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (zk.exists("/"+rootNode+"/"+systemId, false) == null) {
        	zk.create("/"+rootNode+"/"+systemId , null,  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }        
		String createdPath = zk.create("/"+rootNode+"/"+systemId+"/" , systemAddress.getBytes("utf-8"), 
			Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		setMethodList2Zoo();

	}
	
	public void setMethodList2Zoo() throws Exception{
		if(methodList==null){
			return;
		}
		for(Map row:methodList){
			String methodId=(String) row.get("methodId");
			String methodAddress=(String) row.get("methodAddress");
			String saveId=systemId+"--"+methodId;
	        if (zk.exists("/"+rootNode, false) == null) {
	        	zk.create("/"+rootNode , null,Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	        }
	        if (zk.exists("/"+rootNode+"/"+saveId, false) == null) {
	        	zk.create("/"+rootNode+"/"+saveId , null,  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	        }        
			String createdPath = zk.create("/"+rootNode+"/"+saveId+"/" , methodAddress.getBytes("utf-8"), 
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);			
		}
	}

}