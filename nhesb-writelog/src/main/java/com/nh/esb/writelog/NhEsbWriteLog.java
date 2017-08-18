package com.nh.esb.writelog;

import java.util.*;
 
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
 
public class NhEsbWriteLog {
	public static Producer<String, String> producer=null;
	public static String topicName="test";
	public static String localIp="";
	public static String brokerList="localhost:9092";
	public NhEsbWriteLog(){
        Properties props = new Properties();
        props.put("metadata.broker.list", brokerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<String, String>(config);
	}

    public static void writeLog(Map logMap){
		String cmdName=(String) logMap.get("cmdName");
		String cmdData=(String) logMap.get("cmdData");
		String bizId=(String) logMap.get("bizId");
		String callType=(String) logMap.get("callType");
		String ext1=(String) logMap.get("ext1");
		String ext2=(String) logMap.get("ext2");
		String ext3=(String) logMap.get("ext3");
		String ext4=(String) logMap.get("ext4");
		String ext5=(String) logMap.get("ext5");
		
		String fromModule=(String) logMap.get("fromModule");
		String fromSysId=(String) logMap.get("fromSysId");
		String remark=(String) logMap.get("remark");
		String requestId=(String) logMap.get("requestId");
		String sendTime=(String) logMap.get("sendTime");
		
		String subName=(String) logMap.get("subName");
		String toSysId=(String) logMap.get("toSysId");
    	StringBuilder logMsg=new StringBuilder("");
    	logMsg.append("{\"dummy\":\"\"");
    	if(cmdName!=null){
    		logMsg.append(",\"cmdName\":\"").append(cmdName).append("\"");
    	}
    	if(cmdData!=null){
    		logMsg.append(",\"cmdData\":\"").append(cmdData).append("\"");
    	}
    	if(bizId!=null){
    		logMsg.append(",\"bizId\":\"").append(bizId).append("\"");
    	}
    	if(callType!=null){
    		logMsg.append(",\"callType\":\"").append(callType).append("\"");
    	}
    	
    	if(fromModule!=null){
    		logMsg.append(",\"fromModule\":\"").append(fromModule).append("\"");
    	}
    	if(fromSysId!=null){
    		logMsg.append(",\"fromSysId\":\"").append(fromSysId).append("\"");
    	}
    	if(remark!=null){
    		logMsg.append(",\"remark\":\"").append(remark).append("\"");
    	}
    	if(requestId!=null){
    		logMsg.append(",\"requestId\":\"").append(requestId).append("\"");
    	}    	
    	if(sendTime!=null){
    		logMsg.append(",\"sendTime\":\"").append(sendTime).append("\"");
    	}   
    	
    	
    	if(ext1!=null){
    		logMsg.append(",\"ext1\":\"").append(ext1).append("\"");
    	}
    	if(ext2!=null){
    		logMsg.append(",\"ext2\":\"").append(ext2).append("\"");
    	}
    	if(ext3!=null){
    		logMsg.append(",\"ext3\":\"").append(ext3).append("\"");
    	}
    	if(ext4!=null){
    		logMsg.append(",\"ext4\":\"").append(ext4).append("\"");
    	}    	
    	if(ext5!=null){
    		logMsg.append(",\"ext5\":\"").append(ext5).append("\"");
    	}  
    	
    	if(subName!=null){
    		logMsg.append(",\"subName\":\"").append(subName).append("\"");
    	}    	
    	if(toSysId!=null){
    		logMsg.append(",\"toSysId\":\"").append(toSysId).append("\"");
    	}  
    	logMsg.append("}");
    	
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topicName, localIp, logMsg.toString());
        producer.send(data);	
    }
}