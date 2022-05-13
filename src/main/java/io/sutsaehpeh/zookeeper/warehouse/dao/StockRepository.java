package io.sutsaehpeh.zookeeper.warehouse.dao;

import io.sutsaehpeh.zookeeper.warehouse.dao.hier.StockHierRepository;
import io.sutsaehpeh.zookeeper.warehouse.entity.Stock;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock>, StockHierRepository {

    @EntityGraph(value = "Stock.graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Stock> findById(Long stockId);

    @Modifying
    @Query(value = "update Stock set stockVolume = stockVolume - ?2 where stockId = ?1")
    void deductStock(Long stockId, Integer quantity);
}
