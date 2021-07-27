package com.trivadis.ms.sample.order.event;

import com.trivadis.avro.customer.v1.Customer;
import com.trivadis.ms.sample.customer.service.CustomerConverter;
import com.trivadis.ms.sample.customer.service.CustomerDO;
import com.trivadis.ms.sample.customer.service.CustomerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEventConsumer.class);
	
    @Autowired
	private CustomerService customerService;

	@KafkaListener(topics = "${topic.customer.name}", groupId = "oderMs_CustomerConsumer")
	public void receive(ConsumerRecord<Long, Customer> consumerRecord) {
		Customer customer = consumerRecord.value();
		// Customer product = (Customer) SpecificData.get().deepCopy(Customer.SCHEMA$,
		// consumerRecord.value());
		LOGGER.info("received payload='{}'", customer.toString());
		
		CustomerDO customerDO = CustomerConverter.convert(customer);
		customerService.modifyCustomer(customerDO);
	}
}
