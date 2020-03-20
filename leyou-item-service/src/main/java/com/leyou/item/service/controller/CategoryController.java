package com.leyou.item.service.controller;

import com.leyou.item.api.domain.Brand;
import com.leyou.item.api.domain.Category;
import com.leyou.item.service.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
@RestController
public class CategoryController {

    @Autowired
    ICategoryService categoryService;
    /**
     * 根据父级id查询分类
     * @param parentId
     * @return
     */
    @GetMapping("/category/list")
    public ResponseEntity<List<Category>> getCategoryListByParentId(@RequestParam("pid") Long parentId) {
        if (parentId == null || parentId < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Category> categoryList = categoryService.getCategoryListByParentId(parentId);
        return ResponseEntity.ok(categoryList);
    }

}
