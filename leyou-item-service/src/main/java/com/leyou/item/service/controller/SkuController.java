package com.leyou.item.service.controller;

import com.leyou.item.api.domain.Sku;
import com.leyou.item.service.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    ISkuService skuService;

    @GetMapping("/list")
    public ResponseEntity<List<Sku>> getSkuBySpuId(@RequestParam("id") Long spuId){
        List<Sku> skuList = skuService.getSkuBySpuId(spuId);
        return ResponseEntity.ok(skuList);
    }

    @GetMapping("/{skuId}")
    public ResponseEntity<Sku> getSkuBySkuId(@PathVariable("skuId") Long skuId) {
        Sku sku = skuService.getSkuBySkuId(skuId);
        return ResponseEntity.ok(sku);
    }
}
