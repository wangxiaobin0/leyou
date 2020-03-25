package com.leyou.item.service.controller;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import com.leyou.item.api.dto.BrandDTO;
import com.leyou.item.service.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity postBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.postBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity putBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.putBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{bid}")
    public ResponseEntity deleteBrand(@PathVariable("bid") Long bid){
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> getBrandByCategoryId(@PathVariable("cid") Long cid){
        List<Brand> brandList = brandService.getBrandByCategoryId(cid);
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Brand>> getBrandByIds(@RequestParam("ids") List<Long> ids) {
        List<Brand> brandList =  brandService.getBrandByIds(ids);
        return ResponseEntity.ok(brandList);
    }

}
