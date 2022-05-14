package io.sutsaehpeh.zookeeper.web.controller;

import io.sutsaehpeh.zookeeper.warehouse.entity.Order;
import io.sutsaehpeh.zookeeper.warehouse.request.CreateOrderRequest;
import io.sutsaehpeh.zookeeper.warehouse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrderWithRedissonLock(request));
    }
}
