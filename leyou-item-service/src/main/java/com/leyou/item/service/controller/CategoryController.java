package com.leyou.item.service.controller;

import com.leyou.item.api.domain.Brand;
import com.leyou.item.api.domain.Category;
import com.leyou.item.service.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;
    /**
     * 根据父级id查询分类
     * @param parentId
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getCategoryListByParentId(@RequestParam("pid") Long parentId) {
        if (parentId == null || parentId < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Category> categoryList = categoryService.getCategoryListByParentId(parentId);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> getCategoryByBrandId(@PathVariable("bid") Long bid){
        List<Category> categoryList = categoryService.getCategoryByBrandId(bid);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getCategoryNameByIds(@RequestParam("ids") List<Long> ids) {
        List<String> nameList = categoryService.getCategoryNameByIds(ids);
        return ResponseEntity.ok(nameList);
    }
    @GetMapping("/all/level")
    public ResponseEntity<List<Category>> getCategoryNameById(@RequestParam("id") Long id) {
        List<Category> nameList = categoryService.getCategoryNameById(id);
        return ResponseEntity.ok(nameList);
    }
}
