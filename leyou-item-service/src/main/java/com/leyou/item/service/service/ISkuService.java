package com.leyou.item.service.service;

import com.leyou.item.api.domain.Sku;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
public interface ISkuService {
    /**
     * 根据SpuId查询sku
     * @param spuId
     * @return
     */
    List<Sku> getSkuBySpuId(Long spuId);
}
