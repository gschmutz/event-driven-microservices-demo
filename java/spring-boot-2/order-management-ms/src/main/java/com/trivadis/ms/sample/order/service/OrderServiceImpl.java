package com.trivadis.ms.sample.order.service;

import com.trivadis.ms.sample.order.converter.OrderConverter;
import com.trivadis.ms.sample.order.event.OrderEventProducer;
import com.trivadis.ms.sample.order.model.OrderDO;
import com.trivadis.ms.sample.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderEventProducer orderEventProducer;
	
	public List<OrderDO> findAll() {
		return null;
//		return orderRepository.findAll();
	}
	
	public OrderDO findById(String id) {
		return orderRepository.findById(id);
	}
	
	@Override
	public void createOrder(OrderDO order) {
		//order.setId(UUID.randomUUID());
//		orderRepository.save(order);
		
		com.trivadis.avro.order.v1.Order avro = OrderConverter.convert(order);
		avro.setCreatedAt(0L);
		orderEventProducer.produce(avro);
	}
	
	@Override
	public void modifyOrder(OrderDO order) {
//		orderRepository.save(order);

		com.trivadis.avro.order.v1.Order avro = OrderConverter.convert(order);
		orderEventProducer.produce(avro);
	}	
	
	@Override
	public void removeOrder(OrderDO order) {
		//orderRepository.remove(order);

		//com.soaringclouds.avro.order.v1.Order avro = OrderConverter.convert(order);
		//orderEventProducer.produce(avro);
	}
	
	 
}
