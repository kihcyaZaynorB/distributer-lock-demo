package io.sutsaehpeh.zookeeper.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Order.graph",
                attributeNodes = {
                        @NamedAttributeNode(value = "orderDetailList")
                }
        )
)
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    private Long orderId;


    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total", nullable = false)
    private Long total;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equal(orderId, order.orderId) && Objects.equal(userId, order.userId) && Objects.equal(total, order.total) && Objects.equal(createAt, order.createAt) && Objects.equal(orderDetailList, order.orderDetailList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId, userId, total, createAt, orderDetailList);
    }
}
