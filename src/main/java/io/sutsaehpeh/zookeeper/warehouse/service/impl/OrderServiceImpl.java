package io.sutsaehpeh.zookeeper.warehouse.service.impl;

import io.sutsaehpeh.zookeeper.common.exception.BusinessException;
import io.sutsaehpeh.zookeeper.util.TransactionHelper;
import io.sutsaehpeh.zookeeper.util.ZkClient;
import io.sutsaehpeh.zookeeper.warehouse.dao.OrderDetailRepository;
import io.sutsaehpeh.zookeeper.warehouse.dao.OrderRepository;
import io.sutsaehpeh.zookeeper.warehouse.dao.SkuRepository;
import io.sutsaehpeh.zookeeper.warehouse.entity.Order;
import io.sutsaehpeh.zookeeper.warehouse.entity.OrderDetail;
import io.sutsaehpeh.zookeeper.warehouse.entity.Sku;
import io.sutsaehpeh.zookeeper.warehouse.entity.Stock;
import io.sutsaehpeh.zookeeper.warehouse.request.CreateOrderRequest;
import io.sutsaehpeh.zookeeper.warehouse.service.OrderService;
import io.sutsaehpeh.zookeeper.warehouse.service.WarehouseService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private TransactionHelper transactionHelper;

    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    private ZkClient zkClient;


    // @Transactional annotation is not allowed here
    // since we should ensure that the scope of distribution lock
    // wraps spring transaction. Otherwise, the data consistent
    // would not be guaranteed. Moreover, we use a helper class
    // which will open new transactions to wrap our business code.
    @Override
    public Order createOrderWithZookeeperLock(CreateOrderRequest request) {
        Long userId = request.getUserId();
        Long skuId = request.getSkuId();
        Integer quantity = request.getQuantity();
        CuratorFramework client = zkClient.getZkClient();
        client.start();
        final InterProcessMutex mutex = new InterProcessMutex(client, "/lock/warehouse");
        try {
            mutex.acquire();
            // Open new transaction
            return transactionHelper.runInTx(() -> {
                Sku sku = skuRepository.findById(skuId).orElseThrow(() -> new BusinessException("sku is not found"));
                Stock stock = sku.getStock();
                Integer stockVolume = stock.getStockVolume();
                if (stockVolume < quantity) {
                    throw new BusinessException("sku is sold out");
                }
                Order order = new Order();
                order.setUserId(userId);
                order.setCreateAt(new Date());
                OrderDetail detail = new OrderDetail();
                detail.setSkuId(skuId);
                detail.setPrice(sku.getPrice());
                detail.setQuantity(quantity);
                order.setTotal(sku.getPrice() * quantity);
                orderRepository.save(order);
                detail.setOrderId(order.getOrderId());
                orderDetailRepository.save(detail);
                warehouseService.outOfWarehouse(stock.getStockId(), quantity);
                return order;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                mutex.release();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public Order createOrderWithRedissonLock(CreateOrderRequest request) {
        Long userId = request.getUserId();
        Long skuId = request.getSkuId();
        Integer quantity = request.getQuantity();
        RLock lock = redissonClient.getLock("warehouse:order:create");
        try {
            // Open new transaction
            lock.lock(10L, TimeUnit.SECONDS);
            return transactionHelper.runInTx(() -> {
                Sku sku = skuRepository.findById(skuId).orElseThrow(() -> new BusinessException("sku is not found"));
                Stock stock = sku.getStock();
                Integer stockVolume = stock.getStockVolume();
                if (stockVolume < quantity) {
                    throw new BusinessException("sku is sold out");
                }
                Order order = new Order();
                order.setUserId(userId);
                order.setCreateAt(new Date());
                OrderDetail detail = new OrderDetail();
                detail.setSkuId(skuId);
                detail.setPrice(sku.getPrice());
                detail.setQuantity(quantity);
                order.setTotal(sku.getPrice() * quantity);
                orderRepository.save(order);
                detail.setOrderId(order.getOrderId());
                orderDetailRepository.save(detail);
                warehouseService.outOfWarehouse(stock.getStockId(), quantity);
                return order;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
