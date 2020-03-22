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

    /**
     * 根据品牌id查询分类
     * @param bid
     * @return
     */
    List<Category> getCategoryByBrandId(Long bid);

    /**
     * 根据id集合查询分类名
     * @param ids
     * @return
     */
    List<String> getCategoryNameByIds(List<Long> ids);
}
