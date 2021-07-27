package com.trivadis.ms.sample.order;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.config.TopicBuilder;

@ComponentScan(basePackages = { "com.trivadis.ms.sample.order","com.trivadis.ms.sample.customer" })

@SpringBootApplication
public class OrderManagementApplication {

	@Value(value = "${topic.order.name}")
	private String orderTopic;

	@Value(value = "${topic.order.partitions}")
	private Integer orderTopicPartitions;

	@Value(value = "${topic.order.replication-factor}")
	private short orderTopicReplicationFactor;

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
	}

	@Bean
	public NewTopic orderTopic() {
		return TopicBuilder.name(orderTopic).partitions(orderTopicPartitions).replicas(orderTopicReplicationFactor).build();
	}

}
