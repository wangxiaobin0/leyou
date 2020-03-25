package com.leyou.search.api;

import com.leyou.item.api.domain.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author
 * @date 2020/3/22
 */
@FeignClient("leyou-item")
@RequestMapping("/item/sku")
public interface ISkuApi {
    @GetMapping("/list")
    public ResponseEntity<List<Sku>> getSkuBySpuId(@RequestParam("id") Long spuId);
}
