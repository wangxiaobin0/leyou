package com.leyou.item.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.directory.SearchResult;
import java.io.Serializable;

/**
 * @author
 * @date 2020/3/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMessage implements Serializable {
    private Long id;
    private String type;
}
