package com.leyou.item.service.service;

import com.leyou.item.api.bo.SpuBO;

/**
 * @author
 * @date 2020/3/21
 */
public interface IGoodsService {

    /**
     * 新增商品
     * @param spuBO
     */
    void postGoods(SpuBO spuBO);

    /**
     * 更新商品
     * @param spuBO
     */
    void putGoods(SpuBO spuBO);

    /**
     * 删除商品
     * @param spuId
     */
    void deleteGoods(Long spuId);
}
