package com.leyou.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车
 * @author
 * @date 2020/3/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private Long userId;
    private Long skuId;
    private String title;
    private String images;
    private Long price;
    private Integer num;
    private String ownSpec;
}
