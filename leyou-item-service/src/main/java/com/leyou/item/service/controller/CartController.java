package com.leyou.item.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author
 * @date 2020/3/28
 */
@Controller
public class CartController {
    @RequestMapping("/cart")
    public String goToCart() {
        return "cart";
    }
    @RequestMapping("/order")
    public String goToOrderInfo() {
        return "getOrderInfo";
    }
}
