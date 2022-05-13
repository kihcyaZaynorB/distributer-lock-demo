package io.sutsaehpeh.zookeeper.warehouse.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", unique = true, nullable = false)
    private Long stockId;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "total_stock", nullable = false)
    private Integer totalStock;

    @Column(name = "stock_volume", nullable = false)
    private Integer stockVolume;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id", referencedColumnName = "sku_id", insertable = false, updatable = false)
    private Sku sku;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getStockVolume() {
        return stockVolume;
    }

    public void setStockVolume(Integer stockVolume) {
        this.stockVolume = stockVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equal(stockId, stock.stockId) && Objects.equal(skuId, stock.skuId) && Objects.equal(totalStock, stock.totalStock) && Objects.equal(stockVolume, stock.stockVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stockId, skuId, totalStock, stockVolume);
    }
}
