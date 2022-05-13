package io.sutsaehpeh.zookeeper.web.controller;

import io.sutsaehpeh.zookeeper.warehouse.entity.Sku;
import io.sutsaehpeh.zookeeper.warehouse.entity.Spu;
import io.sutsaehpeh.zookeeper.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/warehouse")
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/spu/{spu-id}")
    public ResponseEntity<Spu> findSpuById(@PathVariable("spu-id") Long spuId) {
        return ResponseEntity.ok(warehouseService.findSpuById(spuId));
    }

    @GetMapping("/sku/{sku-id}")
    public ResponseEntity<Sku> findSkuById(@PathVariable("sku-id") Long skuId) {
        return ResponseEntity.ok(warehouseService.findSkuById(skuId));
    }
}
