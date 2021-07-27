package com.trivadis.ms.sample.customer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class CustomerManagementApplication {
	@Value(value = "${topic.customer.name}")
	private String customerTopic;

	@Value(value = "${topic.customer.partitions}")
	private Integer customerTopicPartitions;

	@Value(value = "${topic.customer.replication-factor}")
	private short customerTopicReplicationFactor;

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}

	@Bean
	public NewTopic customerTopic() {
		return TopicBuilder.name(customerTopic).partitions(customerTopicPartitions).replicas(customerTopicReplicationFactor).build();
	}
}
