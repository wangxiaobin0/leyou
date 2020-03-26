package com.leyou.item.service.service.impl;

import com.leyou.item.api.domain.*;
import com.leyou.item.service.service.*;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author
 * @date 2020/3/25
 */
@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    ISpuService spuService;

    @Autowired
    ISkuService skuService;

    @Autowired
    ISpecificationService specificationService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IBrandService brandService;


    /**
     * 页面显示的内容
     *  1. Spu
     *  2.Sku的特殊参数
     *  3. SpuDetail
     *  4. SpecGroup
     *  5. 特殊参数Spec_param
     *  5. 分类名
     *  6. 品牌名
     * @param spuId
     * @return
     */
    @Override
    public Map<String, Object> getItemBySpuId(Long spuId) {
        //查询spu
        Spu spu = spuService.getSpuById(spuId);
        //查询spuDetail(规格参数,售后,包装清单等)
        SpuDetail spuDetail = spuService.getSpuDetail(spuId);
        // Sku
        List<Sku> skuList = skuService.getSkuBySpuId(spuId);
        //SpecGroup
        List<SpecificationGroup> groupList = specificationService.getSpecGroupByCategoryId(spu.getCid3());
        groupList.forEach((group)->{
            group.setParams(specificationService.getSpecificationParamByGroupId(null, spu.getCid3(), null, false));
        });
        //特殊参数
        Map<Long, String> paramMap = new HashMap<>();
        List<SpecificationParam> params = specificationService.getSpecificationParamByGroupId(null, spu.getCid3(), null, false);
        params.forEach((param) -> {
            paramMap.put(param.getId(), param.getName());
        });
        //分类名
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = categoryService.getCategoryNameByIds(cids);
        List<Map<Long, String>> cnameList = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<Long, String> map = new HashMap<>();
            map.put(cids.get(i), names.get(i));
        }
        //品牌
        List<Brand> brandByIds = brandService.getBrandByIds(Arrays.asList(spu.getBrandId()));
        Brand brand = brandByIds.get(0);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cnameList", cnameList);
        resultMap.put("brand", brand);
        resultMap.put("spu", spu);
        resultMap.put("spuDetail", spuDetail);
        resultMap.put("skus", skuList);
        resultMap.put("groups", groupList);
        resultMap.put("params", params);

        return resultMap;
    }
}
