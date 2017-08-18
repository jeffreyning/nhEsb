package com.project.util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author ninghao
 *
 */
public class ZooKeeperOperUtil {
    private static final int SESSION_TIME   = 2000;     
    public static ZooKeeper zooKeeper=null;  
    public static String zooKeeperPath="";
    
    public static String getZooKeeperPath() {
		return zooKeeperPath;
	}
	public  void setZooKeeperPath(String zooKeeperPath) {
		ZooKeeperOperUtil.zooKeeperPath = zooKeeperPath;
	}
	public static ZooKeeper getZooKeeperInstance() throws Exception{
    	if(zooKeeper!=null){
    		return zooKeeper;
    	}else{
    		ZooKeeperOperUtil.connect(zooKeeperPath);
    		return zooKeeper;
    	}
    }
    private static CountDownLatch countDownLatch=new CountDownLatch(1);  
 
    public static void connect(String hosts) throws IOException, InterruptedException{     
           zooKeeper = new ZooKeeper(hosts,SESSION_TIME,new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
		        if(event.getState()==KeeperState.SyncConnected){  
		            countDownLatch.countDown();  
		        } 					
			}
        	   
           });     
           countDownLatch.await();     
     }     


	
    public void close() throws InterruptedException{     
        zooKeeper.close();     
    }  

    public static List<String> getChild(String path) throws Exception{     
        List<String> list=getZooKeeperInstance().getChildren(path, false);  
        return list;
    }  
      
    public static byte[] getData(String path) throws Exception {     
        return  getZooKeeperInstance().getData(path, false,null);     
    }   
    
}
