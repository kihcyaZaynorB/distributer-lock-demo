package io.sutsaehpeh.zookeeper.warehouse.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Sku.graph",
                attributeNodes = {
                        @NamedAttributeNode(value = "stock", subgraph = "stock")
                }
        )
)
@Table(name = "sku")
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sku_id", unique = true, nullable = false, insertable = false)
    private Long skuId;

    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    @Column(name = "price", nullable = false)
    private Long price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "spu_id", referencedColumnName = "spu_id", insertable = false, updatable = false)
    private Spu spu;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sku sku = (Sku) o;
        return Objects.equal(skuId, sku.skuId) && Objects.equal(spuId, sku.spuId) && Objects.equal(price, sku.price) && Objects.equal(stock, sku.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(skuId, spuId, price, stock);
    }
}
