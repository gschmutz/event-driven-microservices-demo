package com.trivadis.ms.sample.order.repository;

import com.trivadis.ms.sample.order.model.OrderDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImpl implements OrderRepository {

	@Override
	public OrderDO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDO> findOrdersByNameRegex(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDO> findOrdersBySearchString(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

}
