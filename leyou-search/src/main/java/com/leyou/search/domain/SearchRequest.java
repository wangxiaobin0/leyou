package com.leyou.search.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author
 * @date 2020/3/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest implements Serializable {
    /**
     * 搜索关键字
     */
    private String key;

    /**
     * 页数
     */
    private Integer page = 0;
    /**
     * 每页显示数量
     */
    public static final Integer ROWS = 20;

    private Map<String, String> filter;
}
