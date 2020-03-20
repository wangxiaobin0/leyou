package com.leyou.item.service.dao;

import com.leyou.item.api.domain.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
public interface ICategoryDao extends Mapper<Category> {
    /**
     * 根据品牌id查询分类
     * @param bid
     * @return
     */
    @Select("select * from tb_category c left join tb_category_brand cb on c.id = cb.category_id where cb.brand_id=#{bid}")
    List<Category> getCategoryByBrandId(@Param("bid") Long bid);
}
