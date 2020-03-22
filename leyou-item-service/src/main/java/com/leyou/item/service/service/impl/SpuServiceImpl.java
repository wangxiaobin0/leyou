package com.leyou.item.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.api.domain.Brand;
import com.leyou.item.api.domain.Spu;
import com.leyou.item.api.domain.SpuDetail;
import com.leyou.item.service.dao.IBrandDao;
import com.leyou.item.service.dao.ICategoryDao;
import com.leyou.item.service.dao.ISpuDao;
import com.leyou.item.service.dao.ISpuDetailDao;
import com.leyou.item.service.service.IBrandService;
import com.leyou.item.service.service.ICategoryService;
import com.leyou.item.service.service.ISpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@Service
public class SpuServiceImpl implements ISpuService {
    @Resource
    ISpuDao spuDao;

    @Resource
    ISpuDetailDao spuDetailDao;

    @Resource
    ICategoryService categoryService;

    @Resource
    IBrandDao brandDao;

    @Override
    public PageResult<List<SpuBO>> getSpuBOByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        PageHelper.startPage(page, rows);
        List<Spu> spuList = spuDao.selectByExample(example);
        List<SpuBO> spuBOList = new ArrayList<SpuBO>();
        for (Spu spu : spuList) {
            SpuBO spuBO = new SpuBO();
            BeanUtils.copyProperties(spu, spuBO);

            //根据cid123 查询名称
            List<String> names = categoryService.getCategoryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            //查询品牌名
            Brand brand = brandDao.selectByPrimaryKey(spu.getBrandId());

            spuBO.setCname(org.apache.commons.lang.StringUtils.join(names,'/'));
            spuBO.setBname(brand.getName());
            spuBOList.add(spuBO);
        }
        PageInfo pageInfo = new PageInfo(spuBOList);
        PageResult spuBOPageResult = new PageResult<SpuBO>(pageInfo.getTotal(), pageInfo.getPages(), spuBOList);
        return spuBOPageResult;
    }

    @Override
    public SpuDetail getSpuDetail(Long spuId) {
        SpuDetail spuDetail = spuDetailDao.selectByPrimaryKey(spuId);
        return spuDetail;
    }
}
