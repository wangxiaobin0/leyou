package com.leyou.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.auth.api.domain.UserInfo;
import com.leyou.cart.api.IGoodsApi;
import com.leyou.cart.domain.Cart;
import com.leyou.cart.interceptor.AuthInterceptor;
import com.leyou.item.api.domain.Sku;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/28
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    IGoodsApi goodsApi;


    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String LEYOU_CART_PREFIX = "leyou:cart:";
    @Override
    public Boolean addCart(Cart cart) {
        ThreadLocal threadLocal = AuthInterceptor.getThreadLocal();
        UserInfo userInfo = (UserInfo) threadLocal.get();
        String key = LEYOU_CART_PREFIX + userInfo.getId();
//        redisTemplate.opsForHash().put();
        //判断是否该商品是否已经添加购物车
        Boolean hasSku = redisTemplate.opsForHash().hasKey(key, cart.getSkuId());
        if (hasSku) {
            Object o = redisTemplate.opsForHash().get(key, cart.getSkuId());
            cart = objectMapper.convertValue(o, Cart.class);
            Integer num = cart.getNum();
            cart.setNum(num + 1);
        } else {
            Sku sku = goodsApi.getSkuBySkuId(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setNum(cart.getNum());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            cart.setImages(sku.getImages() == null ? "" : sku.getImages());
        }
        try {
            redisTemplate.opsForHash().put(key, cart.getSkuId(), cart);
            return true;
        } catch (Exception e) {
            System.out.println("添加购物失败");
            return false;
        }
    }

    @Override
    public List<Cart> getCartList() {
        ThreadLocal threadLocal = AuthInterceptor.getThreadLocal();
        UserInfo userInfo = (UserInfo) threadLocal.get();
        String key = LEYOU_CART_PREFIX + userInfo.getId();
        List values = redisTemplate.opsForHash().values(key);
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        List<Cart> cartList = new ArrayList<>();
        for (Object value : values) {
            cartList.add(objectMapper.convertValue(value, Cart.class));
        }
        return cartList;
    }

    @Override
    public Boolean deleteCarts(Long id) {
        ThreadLocal threadLocal = AuthInterceptor.getThreadLocal();
        UserInfo userInfo = (UserInfo) threadLocal.get();
        String key = LEYOU_CART_PREFIX + userInfo.getId();
        redisTemplate.opsForHash().delete(key, id);
        return null;
    }

    @Override
    public void updateCart(Cart cart) {
        ThreadLocal threadLocal = AuthInterceptor.getThreadLocal();
        UserInfo userInfo = (UserInfo) threadLocal.get();
        String key = LEYOU_CART_PREFIX + userInfo.getId();
        Object o = redisTemplate.opsForHash().get(key, cart.getSkuId());
        Cart c = objectMapper.convertValue(o, Cart.class);
        c.setNum(cart.getNum());

        redisTemplate.opsForHash().put(key, cart.getSkuId(), c);
    }

    @Override
    public void addCartList(List<Cart> cartList) {
        cartList.forEach((cart) -> {
            if (cart != null) {
                this.addCart(cart);
            }
        });
    }
}
