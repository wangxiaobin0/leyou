package com.leyou.item.service.dao;

import com.leyou.item.api.domain.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author
 * @date 2020/3/19
 */
public interface IBrandDao extends Mapper<Brand> {
    /**
     *
     * 新增品牌
     * @param cid
     * @param id
     */
    @Insert("insert into tb_category_brand(category_id, brand_id) values(#{cid}, #{id})")
    int postBrandCategory(@Param("cid") Long cid, @Param("id") Long id);

    /**
     * 删除品牌与分类的联系
     * @param bid
     */
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    void deleteBrandCategory(@Param("bid") Long bid);
}
