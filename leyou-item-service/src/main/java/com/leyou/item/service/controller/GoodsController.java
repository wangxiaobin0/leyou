package com.leyou.item.service.controller;

import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.service.service.IGoodsService;
import com.leyou.item.service.service.ISpuService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2020/3/21
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    /**
     * 1. 添加SPU
     * 2. 获取SPU id
     * 3. 添加SpuDeatail
     * 4. 添加所有Skus
     * 5. 添加所有 Stock
     * @param spuBO
     * @return
     */
    @PostMapping
    public ResponseEntity postGoods(@RequestBody SpuBO spuBO) {
        goodsService.postGoods(spuBO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity putGoods(@RequestBody SpuBO spuBO) {
        goodsService.putGoods(spuBO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{spuId}")
    public ResponseEntity deleteGoods(@PathVariable("spuId") Long spuId) {
        goodsService.deleteGoods(spuId);
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }
}
