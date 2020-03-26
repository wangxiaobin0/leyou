package com.leyou.item.service.service;

import java.util.Map;

/**
 * 商品详情页
 * @author
 * @date 2020/3/25
 */
public interface IItemService {

    /**
     * 根据SpuId查询商品详情页显示内容
     * @param spuId
     * @return
     */
    Map<String, Object> getItemBySpuId(Long spuId);
}
