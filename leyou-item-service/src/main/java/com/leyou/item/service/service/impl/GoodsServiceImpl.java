package com.leyou.item.service.service.impl;

import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.api.domain.Sku;
import com.leyou.item.api.domain.Spu;
import com.leyou.item.api.domain.SpuDetail;
import com.leyou.item.api.domain.Stock;
import com.leyou.item.service.dao.ISkuDao;
import com.leyou.item.service.dao.ISpuDao;
import com.leyou.item.service.dao.ISpuDetailDao;
import com.leyou.item.service.dao.IStockDao;
import com.leyou.item.service.service.IGoodsService;
import com.leyou.item.service.service.ISpuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.genid.GenId;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    ISpuDao spuDao;

    @Resource
    ISpuDetailDao spuDetailDao;

    @Resource
    ISkuDao skuDao;

    @Resource
    IStockDao stockDao;

    /**
     * 1. 添加SPU
     * 2. 获取SPU id
     * 3. 添加SpuDetail
     * 4. 遍历skus
     *      4.1. 添加SKU
     *      4.2. 添加stock
     * @param spuBO
     */
    @Override
    @Transactional
    public void postGoods(SpuBO spuBO) {
        //0.初始化SpuBo
        spuBO.setId(null);
        spuBO.setSaleable(true);
        spuBO.setValid(true);
        spuBO.setCreateTime(new Date());
        spuBO.setLastUpdateTime(spuBO.getCreateTime());
        //1.添加SPU
        spuDao.insertSelective(spuBO);
        //2.获取spuId
        Long spuId = spuBO.getId();
        //3.添加SpuDetail
        SpuDetail spuDetail = spuBO.getSpuDetail();
        spuDetail.setSpuId(spuId);
        spuDetailDao.insertSelective(spuDetail);

        //4. 添加Sku和Stock
        insertSkuAndStock(spuBO);
    }

    @Override
    @Transactional
    public void putGoods(SpuBO spuBO) {
        //1. 删除原来的Sku和Stock
        deleteSkuAndStock(spuBO.getId());
        //2. 添加Sku和Stock
        insertSkuAndStock(spuBO);
        //3. 更新Spu
        spuBO.setSaleable(null);
        spuBO.setValid(null);
        spuBO.setCreateTime(null);
        spuBO.setLastUpdateTime(new Date());
        spuDao.updateByPrimaryKeySelective(spuBO);
        //4.更新SpuDetail
        spuDetailDao.updateByPrimaryKeySelective(spuBO.getSpuDetail());
    }

    @Override
    @Transactional
    public void deleteGoods(Long spuId) {
        //  1.删除Sku和Stock
        deleteSkuAndStock(spuId);
        //  2.删除Spu
        spuDao.deleteByPrimaryKey(spuId);
        //  3.删除SpuDetail
        spuDetailDao.deleteByPrimaryKey(spuId);
    }

    void deleteSkuAndStock(Long spuId) {
        // 1.删除原来的sku
        // 1.1. 根据spuId查询所有的SkuId
        List<Long> skuIdList = skuDao.selectSkuIdListBySpuId(spuId);
        if (skuIdList.size() > 0) {
            //1.2. 删除所有的Stock
            Example example = new Example(Stock.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("skuId", skuIdList);
            stockDao.deleteByExample(example);
            //1.3. 删除所有的sku
            Sku sku = new Sku();
            sku.setSpuId(spuId);
            skuDao.delete(sku);
        }

    }
    void insertSkuAndStock(SpuBO spuBO) {
        Long spuId = spuBO.getId();
        //4. 循环skus
        List<Sku> skus = spuBO.getSkus();
        for (Sku sku : skus) {
            //4.1. 添加SKU
            sku.setSpuId(spuId);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuDao.insertSelective(sku);
            //4.2. 添加Stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockDao.insertSelective(stock);
        }
    }
}
