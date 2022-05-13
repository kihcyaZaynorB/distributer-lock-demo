package io.sutsaehpeh.zookeeper.warehouse.dao;

import io.sutsaehpeh.zookeeper.warehouse.dao.hier.SkuHierRepository;
import io.sutsaehpeh.zookeeper.warehouse.entity.Sku;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SkuRepository extends JpaRepository<Sku, Long>, JpaSpecificationExecutor<Sku>, SkuHierRepository {

    @EntityGraph(value = "Sku.graph", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Optional<Sku> findById(Long skuId);
}
