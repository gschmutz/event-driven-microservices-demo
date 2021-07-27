package com.trivadis.ms.sample.order.event;

import com.trivadis.avro.order.v1.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
	@Autowired
	private KafkaTemplate<Long, Order> kafkaTemplate;
	
	@Value("${topic.order.name}")
	String kafkaTopic;
	
	public void produce(Order order) {
		kafkaTemplate.send(kafkaTopic, order.getOrderId(), order);
	}
}
