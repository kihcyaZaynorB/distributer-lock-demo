package io.sutsaehpeh.zookeeper.warehouse.service.impl;

import io.sutsaehpeh.zookeeper.common.exception.BusinessException;
import io.sutsaehpeh.zookeeper.warehouse.dao.SkuRepository;
import io.sutsaehpeh.zookeeper.warehouse.dao.SpuRepository;
import io.sutsaehpeh.zookeeper.warehouse.dao.StockRepository;
import io.sutsaehpeh.zookeeper.warehouse.entity.Sku;
import io.sutsaehpeh.zookeeper.warehouse.entity.Spu;
import io.sutsaehpeh.zookeeper.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private SpuRepository spuRepository;

    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public Spu findSpuById(Long spuId) {
        return spuRepository.findById(spuId).orElseThrow(() -> new BusinessException("spu not found"));
    }


    public Sku findSkuById(Long skuId) {
        return skuRepository.findById(skuId).orElseThrow(() -> new BusinessException("sku not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void outOfWarehouse(Long stockId, Integer quantity) {
        stockRepository.deductStock(stockId, quantity);
    }
}
