package com.leyou.item.service.controller;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import com.leyou.item.service.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    IBrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> getBrandListTurnPage(@RequestParam("key") String key,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("rows") Integer rows,
                                                            @RequestParam("sortBy") String sortBy,
                                                            @RequestParam("desc") Boolean desc) {
        System.out.println("查库");
        PageResult<Brand> brandList = brandService.getBrandListTurnPage(key, page, rows, sortBy, desc);
        return ResponseEntity.ok(brandList);
    }
}
