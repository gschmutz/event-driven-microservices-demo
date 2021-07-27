package com.trivadis.ms.sample.order.api;

import com.trivadis.avro.order.v1.CurrencyEnum;
import com.trivadis.ms.sample.order.model.OrderDO;

public class OrderConverter {
	
	public static OrderApi convert (OrderDO order) {
		OrderApi value = new OrderApi();
		
		value.setOrderId(order.getId());
		value.setCustomerId(order.getCustomerId().toString());
		value.setCurrency(order.getCurrency());

		return value;
	}
	
	public static OrderDO convert (OrderApi order) {
		OrderDO value = new OrderDO();
		
		value.setId(order.getOrderId());
		value.setCustomerId(Long.valueOf(order.getCustomerId()));
		value.setCurrency(order.getCurrency());
		value.setStatus("SUCCESS");

		return value;
	}
	
}
