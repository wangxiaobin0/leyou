package com.leyou.common.api.domain;

import com.sun.scenario.effect.impl.prism.PrImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 * @author
 * @date 2020/3/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long totalNum;
    private Integer totalPage;
    private List<T> data;
}
