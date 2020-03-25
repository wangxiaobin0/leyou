package com.leyou.search.api;

import com.leyou.item.api.domain.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author
 * @date 2020/3/24
 */
@FeignClient("leyou-item")
@RequestMapping("/item/brand")
public interface IBrandApi {
    @GetMapping("/ids")
    List<Brand> getBrandByIds(@RequestParam("ids") List<Long> ids);
}
