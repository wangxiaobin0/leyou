package com.leyou.item.service.controller;

import com.leyou.item.api.domain.SpecificationGroup;
import com.leyou.item.api.domain.SpecificationParam;
import com.leyou.item.service.service.ISpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 规格参数
 * @author
 * @date 2020/3/21
 */
@RestController
    @RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    ISpecificationService specificationService;

    /**
     * 根据分类id查询规格参数分组
     *  Spu: Standard Product Unit 标准化产品单元. 商品聚合的最小单元,是SKU的集合
     *  SKU: Stock Keeping Unit     库存两单元.    商品的最小不可分割单元.
     *  通俗的讲, SPU是某一款具体产品的集合.   SKU是SPU的不同实例.
     *             比如: iPhone 11就是SPU.  不同版本是SKU
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecificationGroup>> getSpecGroupByCategoryId(@PathVariable("cid") Long cid) {
        List<SpecificationGroup> specificationGroupList = specificationService.getSpecGroupByCategoryId(cid);
        return ResponseEntity.ok(specificationGroupList);
    }

    /**
     * 新增规则参数分组
     * @param specificationGroup
     * @return
     */
    @PostMapping("/group")
    public ResponseEntity postSpecification(@RequestBody SpecificationGroup specificationGroup) {
        specificationService.postSpecification(specificationGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格参数分组
     * @param specificationGroup
     * @return
     */
    @PutMapping("/group")
    public ResponseEntity putSpecification(@RequestBody SpecificationGroup specificationGroup) {
        specificationService.putSpecification(specificationGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除规格参数分组
     * @param id
     * @return
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteSpecification(@PathVariable("id") Long id) {
        specificationService.deleteSpecification(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分组id查询分组参数
     * @param gid
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecificationParam>> getSpecificationParamByGroupId(@RequestParam(value = "gid", required = false) Long gid,
                                                                                   @RequestParam(value = "cid", required = false) Long cid
                                                                                   //@RequestParam(value = "generic", required = false) Boolean generic,
                                                                                   //@RequestParam(value = "searching", required = false)  Boolean searching
    ) {
        List<SpecificationParam> specificationParamList = specificationService.getSpecificationParamByGroupId(gid, cid);

        if (specificationParamList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specificationParamList);
    }

    /**
     * 新增分组参数
     * @param specificationParam
     * @return
     */
    @PostMapping("/param")
    public ResponseEntity postSpecificationParam(@RequestBody SpecificationParam specificationParam) {
        specificationService.postSpecificationParam(specificationParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改分组参数
     * @param specificationParam
     * @return
     */
    @PutMapping("/param")
    public ResponseEntity putSpecificationParam(@RequestBody SpecificationParam specificationParam) {
        specificationService.putSpecificationParam(specificationParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/param/{pid}")
    public ResponseEntity deleteSpecificationParam(@PathVariable("pid") Long pid) {
        specificationService.deleteSpecificationParam(pid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
