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
     * 根据关键字查询Goods集合
     * @param key
     * @param page
     * @param rows
     * @return
     */
    void insertGoodsData(String key, Integer page, Integer rows) throws JsonProcessingException;

    PageResult<Goods> getGoodsListByKey(SearchRequest request);

}
