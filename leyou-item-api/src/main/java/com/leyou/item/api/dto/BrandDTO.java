package com.leyou.item.api.dto;

import com.leyou.item.api.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 新增品牌DTO
 * @author
 * @date 2020/3/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Brand brand;
        private String cids;
}
