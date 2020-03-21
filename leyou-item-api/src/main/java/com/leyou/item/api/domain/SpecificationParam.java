package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author
 * @date 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_spec_param")
public class SpecificationParam implements Serializable {
    /**
     * 规格参数分组参数id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 所属商品分类
     */
    @Column(name = "cid")
    private Long cid;

    /**
     * 所属规则参数分组
     */
    @Column(name = "group_id")
    private Long group_id;

    /**
     * 参数名
     */
    @Column(name = "name")
    private String name;

    /**
     * 是否是数字
     */
    @Column(name = "digital")
    private Boolean digital;

    /**
     * 单位. 数字类型参数必输
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 是否是SKU通用参数
     */
    @Column(name = "generic")
    private Boolean generic;

    /**
     * 是否用于搜索参数
     */
    @Column(name = "searching")
    private Boolean searching;

    /**
     * 数字类型参数用于搜索时的区间. 比如价格1000-15000
     */
    @Column(name = "segments")
    private String segments;
}
