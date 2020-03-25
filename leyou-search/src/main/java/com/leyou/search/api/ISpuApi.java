package com.leyou.search.api;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @date 2020/3/22
 */
@FeignClient("leyou-item")
@RequestMapping("/item/spu")
public interface ISpuApi {
    @GetMapping("/page")
    public PageResult getSpuBOByPage(@RequestParam(value = "key", required = false) String key,
                                                     @RequestParam(value = "saleable", required = false) Boolean saleable,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("rows") Integer rows);
    @GetMapping("/detail/{spuId}")
    public ResponseEntity<SpuDetail> getSpuDetail(@PathVariable("spuId") Long spuId);
}
