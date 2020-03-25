package com.leyou.search.domain;

import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/3/24
 */
@Data
@NoArgsConstructor
public class SearchResult extends PageResult {
    private List<Map<String, Object>> categoryList;
    private List<Brand> brandList;
    private List<Map<String, Object>> specs;

    public SearchResult(Long totalNum, Integer totalPage, List data, List<Map<String, Object>> categoryList, List<Brand> brandList, List<Map<String, Object>> specs) {
        super(totalNum, totalPage, data);
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specs = specs;
    }

    public SearchResult(List<Map<String, Object>> categoryList, List<Brand> brandList, List<Map<String, Object>> specs) {
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.specs = specs;
    }
}
