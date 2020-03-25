package com.leyou.search.api;

import com.leyou.item.api.domain.SpecificationParam;
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
@RequestMapping("/item/spec")
public interface ISpecificationApi {
    @GetMapping("/params")
    List<SpecificationParam> getSpecificationParamByGroupId(@RequestParam(value = "gid", required = false) Long gid,
                                                                                   @RequestParam(value = "cid", required = false) Long cid,
                                                                                   @RequestParam(value = "generic", required = false) Boolean generic,
                                                                                   @RequestParam(value = "searching", required = false)  Boolean searching
    );
}
