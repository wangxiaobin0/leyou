package com.leyou.item.service.controller;

import com.leyou.item.service.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author
 * @date 2020/3/25
 */
@Controller
public class ItemController {

    @Autowired
    IItemService itemService;

    @GetMapping("/{spuId}")
    public String getItemBySpuId(@PathVariable("spuId") Long spuId, Model model) {
        Map<String, Object> map = itemService.getItemBySpuId(spuId);
        model.addAllAttributes(map);
        return "item";
    }
}
