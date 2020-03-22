package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * @date 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_stock")
public class Stock {
    /**
     * SKU id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skuId;

    /**
     * 秒杀:可用库存
     */
    private Integer seckillStock;

    /**
     * 秒杀:总库存
     */
    private Integer seckillTotal;

    /**
     * 库存数量
     */
    private Integer stock;
}
