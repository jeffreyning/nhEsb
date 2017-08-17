package com.project.util;

import java.util.HashMap; 
import java.util.List;   
import java.util.Map;   
import java.util.Properties;   
     
import kafka.consumer.ConsumerConfig;   
import kafka.consumer.ConsumerIterator;   
import kafka.consumer.KafkaStream;   
import kafka.javaapi.consumer.ConsumerConnector;  
   
public class KafkaMsgConsumer extends Thread{   
        private final ConsumerConnector consumer;   
        private final String topic;   
     
        public static void main(String[] args) {   
        	KafkaMsgConsumer consumerThread = new KafkaMsgConsumer("order_test");   
            consumerThread.start();   
        }   
        public KafkaMsgConsumer(String topic) {   
            consumer =kafka.consumer.Consumer   
                    .createJavaConsumerConnector(createConsumerConfig());   
            this.topic =topic;   
        }   
     
    private static ConsumerConfig createConsumerConfig() {   
        Properties props = new Properties();   
  
        props.put("zookeeper.connect","172.16.10.71:2181"); 
        props.put("group.id", "0");   
        props.put("zookeeper.session.timeout.ms","10000");   
        return new ConsumerConfig(props);   
    }   
     
    public void run(){   
        Map<String,Integer> topickMap = new HashMap<String, Integer>();   
        topickMap.put(topic, 1);   
        Map<String, List<KafkaStream<byte[],byte[]>>>  streamMap =consumer.createMessageStreams(topickMap);   
        KafkaStream<byte[],byte[]>stream = streamMap.get(topic).get(0);   
        ConsumerIterator<byte[],byte[]> it =stream.iterator();   
        System.out.println("*********Results********");   
        while(true){   
            if(it.hasNext()){ 
                   
                System.err.println("get data:" +new String(it.next().message()));   
            } 
            try {   
                Thread.sleep(1000);   
            } catch (InterruptedException e) {   
                e.printStackTrace();   
            }   
        }   
    }   
}