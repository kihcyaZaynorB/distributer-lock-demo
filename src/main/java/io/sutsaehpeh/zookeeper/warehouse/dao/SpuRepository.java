package io.sutsaehpeh.zookeeper.warehouse.dao;

import io.sutsaehpeh.zookeeper.warehouse.dao.hier.SpuHierRepository;
import io.sutsaehpeh.zookeeper.warehouse.entity.Spu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpuRepository extends JpaRepository<Spu, Long>, JpaSpecificationExecutor<Spu>, SpuHierRepository {


    @EntityGraph(value = "Spu.graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Spu> findById(Long spuId);
}
