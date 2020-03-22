package com.leyou.item.api.bo;

import com.leyou.item.api.domain.Sku;
import com.leyou.item.api.domain.Spu;
import com.leyou.item.api.domain.SpuDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 页面显示的分类和品牌是中文,所以不能直接用domain
 * @AUTHOR
 * @DATE 2020/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuBO extends Spu {
    /**
     * 分类
     */
    private String cname;
    /**
     * 品牌
     */
    private String bname;

    /**
     * SPU详情
     */
    private SpuDetail spuDetail;

    /**
     * SKU集合
     */
    private List<Sku> skus;
}
