package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @date 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_spu_detail")
public class SpuDetail implements Serializable {

    /**
     * SPU id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spuId;

    /**
     * 商品详情
     */
    private String description;

    /**
     * sku通用spec_param
     */
    private String genericSpec;
    /**
     * spu特殊spec_param
     */
    private String specialSpec;
    /**
     * 包装清单
     */
    private String packingList;
    /**
     * 售后
     */
    private String afterService;
}
