package com.leyou.item.service.service;

import com.leyou.item.api.domain.SpecificationGroup;
import com.leyou.item.api.domain.SpecificationParam;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
public interface ISpecificationService {

    /**
     * 根据产品分类查询规格参数分组
     * @param cid
     * @return
     */
    List<SpecificationGroup> getSpecGroupByCategoryId(Long cid);

    /**
     * 根据id删除规格参数分组
     * @param id
     */
    void deleteSpecification(Long id);

    /**
     * 修改规格参数分组
     * @param specificationGroup
     */
    void putSpecification(SpecificationGroup specificationGroup);

    /**
     * 新增规格参数分组
     * @param specificationGroup
     */
    void postSpecification(SpecificationGroup specificationGroup);

    /**
     * 根据分组id查询分组参数
     * @param gid
     * @return
     */
    List<SpecificationParam> getSpecificationParamByGroupId(Long gid, Long cid, Boolean generic, Boolean searching);

    /**
     * 新增分组参数
     * @param specificationParam
     */
    void postSpecificationParam(SpecificationParam specificationParam);

    /**
     * 修改分组参数
     * @param specificationParam
     */
    void putSpecificationParam(SpecificationParam specificationParam);

    /**
     * 删除分组参数
     * @param pid
     */
    void deleteSpecificationParam(Long pid);
}
