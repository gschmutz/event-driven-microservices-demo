package com.trivadis.ms.sample.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trivadis.ms.sample.customer.converter.CustomerConverter;
import com.trivadis.ms.sample.customer.event.CustomerEventProducer;
import com.trivadis.ms.sample.customer.model.CustomerDO;
import com.trivadis.ms.sample.customer.repository.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerEventProducer customerEventProducer;
	
	public List<CustomerDO> findAll() {
		return null;
//		return customerRepository.findAll();
	}
	
	public CustomerDO findById(String id) {
		return customerRepository.findById(id);
	}
	
	public List<CustomerDO> findCustomersByName(String searchString) {
		return customerRepository.findCustomersByNameRegex(searchString);
	}
		
	@Override
	public void createCustomer(CustomerDO customer) {
		/*
		 * Persist customer 
		 */
		//customer.setId(UUID.randomUUID());
		//customerRepository.save(customer);
		
		/*
		 * convert the data to an Avro customer and publish it on the event hub
		 */
		com.trivadis.avro.customer.v1.Customer avro = CustomerConverter.convert(customer);
		customerEventProducer.produce(avro);
	}
	
	@Override
	public void modifyCustomer(CustomerDO customer) {
		/*
		 * Persist customer 
		 */
		// customerRepository.save(customer);

		/* 
		 * convert the data to an Avro customer and publish it on the event hub
		 */
		com.trivadis.avro.customer.v1.Customer avro = CustomerConverter.convert(customer);
		customerEventProducer.produce(avro);
	}	
	
	@Override
	public void removeCustomer(CustomerDO customer) {
		/*
		 * Persist customer 
		 */
		//customerRepository.remove(customer);

		/* 
		 * convert the data to an Avro customer and publish it on the event hub
		 */
		com.trivadis.avro.customer.v1.Customer avro = CustomerConverter.convert(customer);
		customerEventProducer.produce(avro);
	}
	
	 
}
