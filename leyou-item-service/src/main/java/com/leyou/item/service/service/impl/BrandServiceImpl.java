package com.leyou.item.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import com.leyou.item.api.dto.BrandDTO;
import com.leyou.item.service.dao.IBrandDao;
import com.leyou.item.service.service.IBrandService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
@Service
public class BrandServiceImpl implements IBrandService {

    @Resource
    IBrandDao brandDao;

    @Override
    public PageResult<Brand> getBrandListTurnPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(key)) {
            criteria.andLike("name", "%" + key +  "%").orEqualTo("letter", key);
        }
        sortBy = sortBy + (desc ? " Desc" : "");
        example.setOrderByClause(sortBy);
        PageHelper.startPage(page, rows, sortBy);
        List<Brand> brandList = brandDao.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        return new PageResult<Brand>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    @Override
    @Transactional
    public void postBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        int i = brandDao.insertSelective(brand);

        for (Long cid : cids) {
            brandDao.postBrandCategory(cid, brand.getId());
        }
    }

    @Override
    @Transactional
    public void putBrand(Brand brand, List<Long> cids) {
        //删除原分类
        brandDao.deleteBrandCategory(brand.getId());
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", brand.getId());
        //更新品牌
        brandDao.updateByExampleSelective(brand, example);
        //更新分类
        for (Long cid : cids) {
            brandDao.postBrandCategory(cid, brand.getId());
        }
    }

    @Override
    @Transactional
    public void deleteBrand(Long bid) {
        Brand brand = new Brand();
        brand.setId(bid);
        brandDao.delete(brand);
        brandDao.deleteBrandCategory(bid);
    }

    @Override
    public List<Brand> getBrandByCategoryId(Long cid) {
        List<Brand> brandList = brandDao.getBrandByCategoryId(cid);
        return brandList;
    }

    @Override
    public List<Brand> getBrandByIds(List<Long> ids) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        List<Brand> brandList = brandDao.selectByExample(example);
        return brandList;
    }
}
