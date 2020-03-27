package com.leyou.search.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leyou.item.api.domain.GoodsMessage;
import com.leyou.search.domain.Goods;
import com.leyou.search.service.IGoodsSearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author
 * @date 2020/3/26
 */
@Component
public class GoodsListener {

    @Autowired
    IGoodsSearchService goodsSearchService;


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("leyou.item"), exchange = @Exchange(value = "leyou.item"), key = {"leyou.item.insert","leyou.item.update"})
    })
    void postAndPutListener(GoodsMessage msg) throws JsonProcessingException {
        System.out.println("收到消息:修改或新增" + msg);
        if (msg.getId() == null) {
            return;
        }
        goodsSearchService.postAndPutGoods(msg.getId());
    }
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("leyou.item"), exchange = @Exchange(value = "leyou.item"), key = {"leyou.item.delete"})
    })
    void deleteListener(GoodsMessage msg) {
        System.out.println("收到消息:删除" + msg);
        goodsSearchService.deleteGoods(msg.getId());
    }

}
