package io.sutsaehpeh.zookeeper.warehouse.service;

import io.sutsaehpeh.zookeeper.warehouse.entity.Order;
import io.sutsaehpeh.zookeeper.warehouse.request.CreateOrderRequest;

public interface OrderService {


    Order createOrder(CreateOrderRequest request);
}
