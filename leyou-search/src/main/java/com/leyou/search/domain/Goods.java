package com.leyou.search.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/3/22
 */
@Data
@Document(indexName = "goods")
public class Goods implements Serializable {

    /**
     * Spu Id
     * 主键
     */
    @Id
    private Long id;

    /**
     * 商品的所有信息,包括标题,价格,颜色,分类,品牌等
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * Spu副标题.
     *  不添加索引, 促销信息不作为搜索关键字
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * 一级分类
     */
    private Long cid1;
    /**
     * 二级分类
     */
    private Long cid2;
    /**
     * 三级分类
     */
    private Long cid3;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    /**
     * Spu详情
     */
    //private SpuDetail spuDetail;

    /**
     * sku集合. 点击图片会切换的, 不添加索引
     */
    @Field(index = false, type = FieldType.Keyword)
    private String skus;

    /**
     * 把价格从sku中取出做聚合, 只要
     */
    private List<Long> price;

    /**
     * 可以搜索的规格参数. key为参数名, value为参数值
     */
    private Map<String, Object> specs;
}
