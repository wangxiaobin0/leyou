package com.leyou.item.service.service;

import com.leyou.item.api.domain.Category;

import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
public interface ICategoryService {

    /**
     * 根据父级id查询分类
     * @param parentId
     * @return
     */
    List<Category> getCategoryListByParentId(Long parentId);
}
