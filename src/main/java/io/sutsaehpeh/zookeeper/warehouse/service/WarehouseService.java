package io.sutsaehpeh.zookeeper.warehouse.service;

import io.sutsaehpeh.zookeeper.warehouse.entity.Sku;
import io.sutsaehpeh.zookeeper.warehouse.entity.Spu;

public interface WarehouseService {

    Spu findSpuById(Long id);


    Sku findSkuById(Long id);

    void outOfWarehouse(Long stockId, Integer quantity);
}
