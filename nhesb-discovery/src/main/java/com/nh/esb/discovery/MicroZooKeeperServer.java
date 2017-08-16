package com.nh.esb.discovery;


import org.apache.zookeeper.CreateMode;
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
	private String rootNode="systemRoot";
	private String systemId = "default";
	private String systemAddress = "default";


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

	private ZooKeeper zk;
	public String zookeeperUrl="localhost:2181";


	public void connectZookeeper() throws Exception {
		ZooKeeper zk = new ZooKeeper(zookeeperUrl, 5000, new Watcher() {
			public void process(WatchedEvent event) {

			}
		});
        if (zk.exists("/"+rootNode, false) == null) {
        	zk.create("/"+rootNode , null,Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (zk.exists("/"+rootNode+"/"+systemId, false) == null) {
        	zk.create("/"+rootNode+"/"+systemId , null,  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }        
		String createdPath = zk.create("/"+rootNode+"/"+systemId+"/" , systemAddress.getBytes("utf-8"), 
			Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

	}
	
	public static void main(String[] args) throws Exception {
		
		MicroZooKeeperServer as = new MicroZooKeeperServer();
		as.connectZookeeper();
		Thread.sleep(Long.MAX_VALUE);
	}
}