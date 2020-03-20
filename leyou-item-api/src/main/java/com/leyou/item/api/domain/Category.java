package com.leyou.item.api.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品分类
 * @author
 * @date 2020/3/19
 */
@Data
@Table(name = "tb_category")
public class Category implements Serializable {
    /**
     * 分类唯一表示
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 分类名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 父级分类id
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 是否是父级分类
     */
    @Column(name = "is_parent")
    private Boolean isParent;
    /**
     * 排序级别
     */
    @Column(name = "sort")
    private Integer sort;
}
