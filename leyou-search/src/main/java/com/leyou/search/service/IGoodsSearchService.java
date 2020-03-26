package com.leyou.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leyou.common.api.domain.PageResult;
import com.leyou.search.domain.Goods;
import com.leyou.search.domain.SearchRequest;

import java.util.List;

/**
 * @author
 * @date 2020/3/22
 */
public interface IGoodsSearchService {

    /**
     *
     *  根据关键字查询Goods集合
     * @param key
     * @param page
     * @param rows
     * @throws JsonProcessingException
     */
    void insertGoodsData(String key, Integer page, Integer rows) throws JsonProcessingException;

    /**
     *
     * @param request
     * @return
     */
    PageResult<Goods> getGoodsListByKey(SearchRequest request);

    /**
     * 处理消息队列发来的新增或更新商品信息消息
     * @param id
     */
    void postAndPutGoods(Long id) throws JsonProcessingException;

    /**
     * 处理消息队列发来的删除商品信息消息
     * @param id
     */
    void deleteGoods(Long id);
}
