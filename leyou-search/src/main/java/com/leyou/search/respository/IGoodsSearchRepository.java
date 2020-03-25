package com.leyou.search.respository;

import com.leyou.search.domain.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author
 * @date 2020/3/23
 */
public interface IGoodsSearchRepository extends ElasticsearchRepository<Goods, Long> {
}
