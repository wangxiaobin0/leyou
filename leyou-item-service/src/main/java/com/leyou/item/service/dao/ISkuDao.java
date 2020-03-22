package com.leyou.item.service.dao;

import com.leyou.item.api.domain.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
public interface ISkuDao extends Mapper<Sku> {
    /**
     * 根据SpuId查询所有的SkuId
     * @param spuId
     * @return
     */
    @Select("select id from tb_sku where spu_id = #{spuId}")
    List<Long> selectSkuIdListBySpuId(@Param("spuId") Long spuId);
}
