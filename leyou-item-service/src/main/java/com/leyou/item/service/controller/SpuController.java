package com.leyou.item.service.controller;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.api.domain.Spu;
import com.leyou.item.api.domain.SpuDetail;
import com.leyou.item.service.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    ISpuService spuService;
    @GetMapping("/page")
    public ResponseEntity<PageResult> getSpuBOByPage(@RequestParam(value = "key", required = false) String key,
                                                         @RequestParam(value = "saleable", required = false) Boolean saleable,
                                                         @RequestParam("page") Integer page,
                                                         @RequestParam("rows") Integer rows) {
        PageResult<List<SpuBO>> pageResult = spuService.getSpuBOByPage(key, saleable, page, rows);

        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/detail/{spuId}")
    public ResponseEntity<SpuDetail> getSpuDetail(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = spuService.getSpuDetail(spuId);
        return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("/{spuId}")
    public ResponseEntity<Spu> getSpuById(@PathVariable("spuId") Long spuId) {
        Spu spu = spuService.getSpuById(spuId);
        if (spu == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(spu);
    }
}
