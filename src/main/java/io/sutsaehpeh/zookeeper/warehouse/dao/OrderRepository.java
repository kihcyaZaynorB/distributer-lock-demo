package io.sutsaehpeh.zookeeper.warehouse.dao;

import io.sutsaehpeh.zookeeper.warehouse.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @EntityGraph(value = "Order.graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Order> findById(Long orderId);
}
