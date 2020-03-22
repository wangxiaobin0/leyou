package com.leyou.item.service.service;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.api.domain.SpuDetail;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author
 * @date 2020/3/21
 */
public interface ISpuService {

    /**
     * 根据条件分页查询SpuBO
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<List<SpuBO>>  getSpuBOByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    public SpuDetail getSpuDetail(Long spuId);
}
