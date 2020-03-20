package com.leyou.item.service.service;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
public interface IBrandService {
    /**
     * 根据条件分页查询查询品牌
     * @param key 查询关键字
     * @param page 当前页数
     * @param rows 页面显示数
     * @param sortBy 排序参数
     * @param desc 是否逆序
     * @return
     */
    PageResult<Brand> getBrandListTurnPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);
}
