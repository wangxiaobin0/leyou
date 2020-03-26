package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @date 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_spu")
public class Spu implements Serializable {
    /**
     * Spu id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品子标题
     */
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
     * 是否上架
     */
    private Boolean saleable;

    /**
     * 是否删除
     */
    private Boolean valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;
}
