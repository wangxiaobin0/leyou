package com.leyou.item.service.service.impl;

import com.leyou.item.api.domain.Sku;
import com.leyou.item.api.domain.Stock;
import com.leyou.item.service.dao.ISkuDao;
import com.leyou.item.service.dao.IStockDao;
import com.leyou.item.service.service.ISkuService;
import com.leyou.item.service.service.ISpuService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@Service
public class SkuServiceImpl implements ISkuService {
    @Resource
    ISkuDao skuDao;

    @Resource
    IStockDao stockDao;
    @Override
    public List<Sku> getSkuBySpuId(Long spuId) {
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", spuId);
        List<Sku> skuList = skuDao.selectByExample(example);
        for (Sku sku : skuList) {
            Stock stock = stockDao.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        }
        return skuList;
    }

    @Override
    public Sku getSkuBySkuId(Long skuId) {
        Sku sku = skuDao.selectByPrimaryKey(skuId);
        return sku;
    }
}
