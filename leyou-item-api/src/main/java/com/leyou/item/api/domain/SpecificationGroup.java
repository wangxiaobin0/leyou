package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_spec_group")
public class SpecificationGroup implements Serializable {
    /**
     * 规格参数分组id
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
     * 规格参数分组名
     */
    @Column(name = "name")
    private String name;

    @Transient
    private List<SpecificationParam> params;
}
