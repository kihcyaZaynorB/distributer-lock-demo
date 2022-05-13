package io.sutsaehpeh.zookeeper.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Spu.graph",
                attributeNodes = {
                        @NamedAttributeNode(value = "skuList")
                }
        )
)
@Table(name = "spu")
public class Spu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spu_id", unique = true, nullable = false)
    private Long spuId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "spu_id")
    @JsonBackReference
    private List<Sku> skuList = new ArrayList<>();

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spu spu = (Spu) o;
        return Objects.equal(spuId, spu.spuId) && Objects.equal(name, spu.name) && Objects.equal(price, spu.price) && Objects.equal(skuList, spu.skuList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(spuId, name, price, skuList);
    }
}
