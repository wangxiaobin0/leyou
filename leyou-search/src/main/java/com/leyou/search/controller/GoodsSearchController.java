package com.leyou.search.controller;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.leyou.common.api.domain.PageResult;
        import com.leyou.search.domain.Goods;
        import com.leyou.search.domain.SearchRequest;
        import com.leyou.search.service.IGoodsSearchService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.List;

/**
 * @author
 * @date 2020/3/22
 */
@RestController
public class GoodsSearchController {

    @Autowired
    IGoodsSearchService goodsSearchService;
    /**
     * 导入数据
     * 搜索流程:
     *      1. 根据关键字搜索Spu(已有) id subTitle,
     *      2. 根据Spu查询spuDetail(已有)
     *      3. 根据Spu查询Sku(已有)
     *      4. 根据根据分类查询可以作为搜索条件的字段
     * @return
     */
    @GetMapping("/goods")
    public ResponseEntity<List<Goods>> insertGoodsData() throws JsonProcessingException {
        goodsSearchService.insertGoodsData(null, 1, 200);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/page")
    public ResponseEntity<PageResult<Goods>> getGoodsListByKey(@RequestBody SearchRequest request) {
        PageResult<Goods> goodsListByKey = goodsSearchService.getGoodsListByKey(request);
        return ResponseEntity.ok(goodsListByKey);
    }
}
