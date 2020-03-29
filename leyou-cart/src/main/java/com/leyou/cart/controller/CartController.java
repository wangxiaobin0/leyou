package com.leyou.cart.controller;

import com.leyou.cart.domain.Cart;
import com.leyou.cart.service.ICartService;
import com.leyou.item.api.domain.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/3/28
 */
@RestController
public class CartController {
    @Autowired
    ICartService cartService;
    @PostMapping("/cart")
    public ResponseEntity addCart(@RequestBody Cart cart) {
        Boolean addCart = cartService.addCart(cart);
        if (addCart) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/carts")
    public ResponseEntity addCartList(@RequestBody List cartsList) {
        cartService.addCartList(cartsList);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCartList() {
        List<Cart> cartList = cartService.getCartList();
        return ResponseEntity.ok(cartList);
    }

    @DeleteMapping("/cart/{skuId}")
    public ResponseEntity deleteCarts(@PathVariable("skuId") Long id) {
        Boolean b = cartService.deleteCarts(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/cart")
    public ResponseEntity updateCart(@RequestBody Cart cart) {
        cartService.updateCart(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
