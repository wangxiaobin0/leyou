package com.leyou.search.api;

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
@RequestMapping("/item/category")
public interface ICategoryApi {
    @GetMapping("/names")
    public ResponseEntity<List<String>> getCategoryNameByIds(@RequestParam("ids") List<Long> ids);
}
