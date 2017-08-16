package com.nh.esb.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author ninghao
 *
 */
public class MicroZooKeeperClient {
	private String rootNode="systemRoot";
	private String systemId = "default";
	private ZooKeeper zk;
	private Stat stat = new Stat();
	public static Map systemMap=new HashMap();
	public static Map indexMap=new HashMap();
	public String zookeeperUrl="localhost:2181";


	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getZookeeperUrl() {
		return zookeeperUrl;
	}

	public void setZookeeperUrl(String zookeeperUrl) {
		this.zookeeperUrl = zookeeperUrl;
	}

	public static String getSystemAddress(String systemId){
		List addressList=(List) systemMap.get(systemId);
		if(addressList==null){
			return null;
		}
		int size=addressList.size();
		Integer indexPer=(Integer) indexMap.get(systemId);
		int indexInt=0;
		if(indexPer!=null){
			indexInt=indexPer;
		}
		if(indexInt>=size){
			indexInt=0;
		}
		String address=(String) addressList.get(indexInt);
		indexMap.put(systemId, indexInt+1);
		return address;
		
	}



	public void connectZookeeper() throws Exception {
		zk = new ZooKeeper(zookeeperUrl, 5000, new Watcher() {
			public void process(WatchedEvent event) {

				if (event.getType() == EventType.NodeChildrenChanged 
					&& ("/" + rootNode+"/"+systemId).equals(event.getPath())) {
					try {
						updateServerList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
        if (zk.exists("/"+rootNode, false) == null) {
        	zk.create("/"+rootNode , null,Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (zk.exists("/"+rootNode+"/"+systemId, false) == null) {
        	zk.create("/"+rootNode+"/"+systemId , null,  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }  
		updateServerList();
	}


	private void updateServerList() throws Exception {
		List<String> newServerList = new ArrayList<String>();
		List<String> subList = zk.getChildren("/"+rootNode+"/"+systemId, true);
		for (String subNode : subList) {
			String address=new String(zk.getData("/"+rootNode+"/"+systemId+"/"+subNode, false,stat),"UTF-8");
			newServerList.add(address);
		}
		System.out.println(newServerList);
		systemMap.put(systemId, newServerList);
	}


	public static void main(String[] args) throws Exception {
		MicroZooKeeperClient ac = new MicroZooKeeperClient();
		ac.connectZookeeper();
		Thread.sleep(Long.MAX_VALUE);
	}
}