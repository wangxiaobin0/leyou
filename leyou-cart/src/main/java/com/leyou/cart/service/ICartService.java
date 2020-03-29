package com.leyou.cart.service;

import com.leyou.cart.domain.Cart;

import java.util.List;

/**
 * @author
 * @date 2020/3/28
 */
public interface ICartService {
    /**
     * 添加单个商品到购物车
     * @param cart
     */
    Boolean addCart(Cart cart);

    /**
     * 查询购物车中所有商品
     * @return
     */
    List<Cart> getCartList();

    /**
     * 根据Id删除购物车商品
     * @param id
     * @return
     */
    Boolean deleteCarts(Long id);

    /**
     * 更新购物车数量
     * @param cart
     */
    void updateCart(Cart cart);

    /**
     * 添加未登录时的加到购物车里的商品
     * @param cartList
     */
    void addCartList(List<Cart> cartList);
}
