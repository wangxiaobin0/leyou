package com.leyou.item.service.service.impl;

import com.leyou.item.api.domain.SpecificationGroup;
import com.leyou.item.api.domain.SpecificationParam;
import com.leyou.item.service.dao.ISpecificationGroupDao;
import com.leyou.item.service.dao.ISpecificationParamDao;
import com.leyou.item.service.service.ISpecificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@Service
public class SpecificationServiceImpl implements ISpecificationService {

    @Resource
    ISpecificationGroupDao specificationGroupDao;

    @Resource
    ISpecificationParamDao specificationParamDao;

    @Override
    public List<SpecificationGroup> getSpecGroupByCategoryId(Long cid) {
        Example example = new Example(SpecificationGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cid", cid);
        List<SpecificationGroup> specificationGroupList = specificationGroupDao.selectByExample(example);
        return specificationGroupList;
    }

    @Override
    @Transactional
    public void deleteSpecification(Long id) {
        Example example = new Example(SpecificationParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group_id", id);
        //删除分组
        specificationGroupDao.deleteByPrimaryKey(id);
        //删除分组参数
        specificationParamDao.deleteByExample(example);
    }

    @Override
    public void putSpecification(SpecificationGroup specificationGroup) {
        specificationGroupDao.updateByPrimaryKeySelective(specificationGroup);
    }

    @Override
    public void postSpecification(SpecificationGroup specificationGroup) {
        specificationGroupDao.insertSelective(specificationGroup);
    }

    @Override
    public List<SpecificationParam> getSpecificationParamByGroupId(Long gid) {
        Example example = new Example(SpecificationParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("group_id", gid);
        List<SpecificationParam> specificationParamList = specificationParamDao.selectByExample(example);
        return specificationParamList;
    }

    @Override
    public void postSpecificationParam(SpecificationParam specificationParam) {
        specificationParamDao.insertSelective(specificationParam);
    }

    @Override
    public void putSpecificationParam(SpecificationParam specificationParam) {
        specificationParamDao.updateByPrimaryKey(specificationParam);
    }

    @Override
    public void deleteSpecificationParam(Long pid) {
        specificationParamDao.deleteByPrimaryKey(pid);
    }
}
