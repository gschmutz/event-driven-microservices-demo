package com.trivadis.ms.sample.order.service;

import com.trivadis.ms.sample.order.model.OrderDO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service Layer should be used for Transactional processes
 * 
 * Calls Repository Layers
 * 
 */
@Service
public interface OrderService {

    public List<OrderDO> findAll();
    public OrderDO findById(String id);

    public void createOrder(OrderDO product);
    public void modifyOrder(OrderDO product);
    public void removeOrder(OrderDO product);
}
