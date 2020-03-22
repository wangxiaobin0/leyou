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
@Table(name = "tb_sku")
public class Sku implements Serializable {
    /**
     * SKU id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * SPU id
     */
    private Long spuId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String images;

    /**
     * 价格
     */
    private Long price;

    /**
     * 特殊spec_param索引,用这个和spuId查ownSpec
     */
    private String indexes;
    /**
     * indexes对应的值, 规则参数显示用
     */
    private String ownSpec;

    /**
     * 是否有效
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    /**
     * 库存
     */
    @Transient
    private Integer stock;
}

