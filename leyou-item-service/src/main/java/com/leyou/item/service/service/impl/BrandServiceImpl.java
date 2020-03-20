package com.leyou.item.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import com.leyou.item.service.dao.IBrandDao;
import com.leyou.item.service.service.IBrandService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
}
