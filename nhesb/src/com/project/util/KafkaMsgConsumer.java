package com.project.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import com.nh.micro.rule.engine.core.GroovyExecUtil;

/**
 * 
 * @author ninghao
 *
 */
public class KafkaMsgConsumer extends Thread {
	private final ConsumerConnector consumer;
	private String topic = "test";
	private String zkUrl = "localhost:2181,localhost:3181,localhost:4181";
	private String groupId = "0";

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getZkUrl() {
		return zkUrl;
	}

	public void setZkUrl(String zkUrl) {
		this.zkUrl = zkUrl;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	public KafkaMsgConsumer() {
		consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(createConsumerConfig());
		this.start();
	}

	private ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();

		props.put("zookeeper.connect", zkUrl);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "10000");
		return new ConsumerConfig(props);
	}

	public void run() {
		Map<String, Integer> topickMap = new HashMap<String, Integer>();
		topickMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer
				.createMessageStreams(topickMap);
		KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();

		while (true) {
			try {
				if (it.hasNext()) {
					String recvStr = new String(it.next().message(), "utf-8");
					GroovyExecUtil.execGroovyRetObj("micro_log_platform","saveLog", recvStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}