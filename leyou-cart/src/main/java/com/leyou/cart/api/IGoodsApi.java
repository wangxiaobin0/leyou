package com.leyou.cart.api;

import com.leyou.item.api.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @date 2020/3/28
 */
@FeignClient("leyou-item")
public interface IGoodsApi {
    /**
     * 根据SkuId查询sku
     * @param skuId
     * @return
     */
    @GetMapping("/item/sku/{skuId}")
    Sku getSkuBySkuId(@PathVariable("skuId") Long skuId);
}
