package com.leyou.item.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author
 * @date 2020/3/19
 */
@Data
@Table(name = "tb_brand")
public class Brand {
    /**
     * 品牌id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌图片地址
     */
    private String image;

    /**
     * 品牌首字母
     */
    private Character letter;
}
