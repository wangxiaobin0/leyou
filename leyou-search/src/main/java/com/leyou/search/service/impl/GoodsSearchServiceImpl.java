package com.leyou.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.common.api.domain.PageResult;
import com.leyou.item.api.bo.SpuBO;
import com.leyou.item.api.domain.*;
import com.leyou.search.api.*;
import com.leyou.search.domain.Goods;
import com.leyou.search.domain.SearchRequest;
import com.leyou.search.domain.SearchResult;
import com.leyou.search.respository.IGoodsSearchRepository;
import com.leyou.search.service.IGoodsSearchService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author
 * @date 2020/3/22
 */
@Service
public class GoodsSearchServiceImpl implements IGoodsSearchService {

    @Autowired
    ICategoryApi categoryApi;

    @Autowired
    ISpuApi spuApi;

    @Autowired
    ISkuApi skuApi;

    @Autowired
    IBrandApi brandApi;

    @Autowired
    ISpecificationApi specificationApi;

    @Autowired
    IGoodsSearchRepository goodsSearchRepository;

    @Override
    public PageResult<Goods> getGoodsListByKey(SearchRequest request) {
        //关键字为null, 直接返回空
        if (StringUtils.isEmpty(request.getKey())) {
            return null;
        }
        //构建搜索条件
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //从all中按关键字搜索
        //MatchQueryBuilder basicBuilder = QueryBuilders.matchQuery("all", request.getKey());
        //根据关键字和过滤条件查询
        QueryBuilder basicBuilder = getBoolQueryBuilder(request);
        builder.withQuery(basicBuilder);
        //过滤搜索结果
        builder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle", "specs"}, null));
        //分页信息
        builder.withPageable(PageRequest.of(request.getPage() - 1, SearchRequest.ROWS));
        //聚合搜索分类和品牌等规格参数. 显示所有的搜索选项肯定不合适, 因为可能跟用户搜索的无关, 所以要在按关键字搜索的基础上进行聚合
        //按cid3进行分类聚合
        builder.addAggregation(AggregationBuilders.terms("category").field("cid3"));
        //按brandId进行品牌聚合
        builder.addAggregation(AggregationBuilders.terms("brand").field("brandId"));
        AggregatedPage<Goods> goodsPage = (AggregatedPage) goodsSearchRepository.search(builder.build());
        Aggregation category = goodsPage.getAggregation("category");
        List<Map<String, Object>> categoryList = prepareCategory(category);
        Aggregation brand = goodsPage.getAggregation("brand");
        List<Brand> brandList = prepareBrand(brand);
        List<Map<String, Object>> specs = null;
        if (categoryList.size() == 1) {
            specs = aggregationSpecs((Long) categoryList.get(0).get("id"), basicBuilder);
        }
        SearchResult pageResult = new SearchResult(goodsPage.getTotalElements(), goodsPage.getTotalPages(), goodsPage.getContent(), categoryList, brandList, specs);
        return pageResult;
    }

    @Override
    public void insertGoodsData(String key, Integer page, Integer rows) throws JsonProcessingException {
        PageResult spuBOList = spuApi.getSpuBOByPage(key, true, page, rows);
        List<Goods> goodsList = new ArrayList<>();
        List data = spuBOList.getData();
        ObjectMapper mapper = new ObjectMapper();
        List<SpuBO> spuBOs = new ArrayList<>();
        for (Object o : data) {
            SpuBO spuBO = mapper.convertValue(o, SpuBO.class);
            spuBOs.add(spuBO);
        }
        for (SpuBO spuBo : spuBOs) {
            goodsList.add(getGoodsBySpu((SpuBO) spuBo));
        }
        goodsSearchRepository.saveAll(goodsList);
    }

    public QueryBuilder getBoolQueryBuilder(SearchRequest request) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("all", request.getKey()));
        Map<String, String> filters = request.getFilter();
        if (CollectionUtils.isEmpty(filters)) {
            return boolQuery;
        }
        filters.forEach((key, value)->{
            String name = "";
            if ("分类".equals(key)) {
                name = "cid3";
            } else if ("品牌".equals(key)) {
                name = "brandId";
            } else {
                name = "specs." + key + ".keyword";
            }
            boolQuery.filter(QueryBuilders.termQuery(name, value));
        });
        return boolQuery;
    }

    /**
     * 当分类聚合为1时对规则参数进行聚合
     * 聚合规格参数
     *      1. 按照分类聚合的结果为1才进行进行规格参数聚合, 因为不同分类的话规格参数不一致,没必要聚合
     *      2. 根据categoryId查询可以用于搜索的spec_param集合
     *      3. 遍历添加spec_param聚合
     *      4. 由于添加的是spec是json格式, 所以聚合的关键字为spec.规格参数名.keyword
     *      5. 循环遍历聚合结果, 封装为List<Map<String, Object>>, key为规格参数名, Object其实是List<String>, 规格参数值列表
     * @param cid
     * @param builder
     * @return
     */
    List<Map<String, Object>> aggregationSpecs(Long cid, QueryBuilder builder) {

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        //添加基本查询
        searchQueryBuilder.withQuery(builder);
        //不查询参数
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        List<Map<String, Object>> specList = new ArrayList<>();
        // 查询用于搜索的规格参数spec_param
        List<SpecificationParam> paramList = specificationApi.getSpecificationParamByGroupId(null, cid, null, true);
        // 循环把所有规格参数加入到聚合中
        paramList.forEach((param) -> {
            //specs数据存储的是json格式, 所以 聚合字段 = 变量名.字段名.keyword
            searchQueryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs." + param.getName() + ".keyword"));
        });
        //进行聚合
        AggregatedPage<Goods> search = (AggregatedPage) goodsSearchRepository.search(searchQueryBuilder.build());
        //将聚合结果
        Map<String, Aggregation> aggregationMap = search.getAggregations().getAsMap();

        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("k", entry.getKey());
            List<String> list = new ArrayList<>();
            List<StringTerms.Bucket> buckets = ((StringTerms) entry.getValue()).getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
                list.add(bucket.getKeyAsString());
            }
            map.put("options", list);
            specList.add(map);
        }
        return specList;
    }


    /**
     * 组装搜索框 品牌数据
     *
     * @param aggregation
     * @return
     */
    List<Brand> prepareBrand(Aggregation aggregation) {
        List<Long> bids = new ArrayList<>();
        List<LongTerms.Bucket> buckets = ((LongTerms) aggregation).getBuckets();
        buckets.forEach((bucket) -> {
            bids.add(bucket.getKeyAsNumber().longValue());
        });
        List<Brand> brandList = brandApi.getBrandByIds(bids);
        return brandList;
    }

    /**
     * 组装搜索框的分类属性
     *
     * @param aggregation
     * @return
     */
    List<Map<String, Object>> prepareCategory(Aggregation aggregation) {
        //获取所有桶
        List<LongTerms.Bucket> buckets = ((LongTerms) aggregation).getBuckets();
        List<Long> cids = new ArrayList<>();
        //将桶中所有品牌的id加到list中做查询
        buckets.forEach((bucket) -> {
            cids.add(bucket.getKeyAsNumber().longValue());
        });
        List<Map<String, Object>> categories = new ArrayList<>();
        //根据ids查询名称集合
        List<String> names = categoryApi.getCategoryNameByIds(cids).getBody();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }

    /**
     * 组装Goods数据做新增数据使用
     *
     * @param spuBO
     * @return
     * @throws JsonProcessingException
     */
    public Goods getGoodsBySpu(SpuBO spuBO) throws JsonProcessingException {
        //0. 创建Goods
        Goods goods = new Goods();
        //1. 获取分类名
        Long spuId = spuBO.getId();
        ResponseEntity<List<String>> categoryNameByIds = categoryApi.getCategoryNameByIds(Arrays.asList(spuBO.getCid1(), spuBO.getCid2(), spuBO.getCid3()));
        List<String> categoryNames = categoryNameByIds.getBody();
        //2. 查询Sku集合
        ResponseEntity<List<Sku>> skuBySpuId = skuApi.getSkuBySpuId(spuId);
        List<Sku> skuList = skuBySpuId.getBody();
        //3. 取出price集合
        List<Long> price = new ArrayList<>();
        for (Sku sku : skuList) {
            price.add(sku.getPrice());
        }
        //4. json化Sku集合
        ObjectMapper mapper = new ObjectMapper();
        String skus = mapper.writeValueAsString(skuList);
        //5. 获取所有spec_param
        // 5.1. 根据分类获取所有的规格参数
        List<SpecificationParam> specParamList = specificationApi.getSpecificationParamByGroupId(null, spuBO.getCid3(), null, true);
        // 5.2. 根据spuId获取spuDetail
        ResponseEntity detail = spuApi.getSpuDetail(spuId);

        SpuDetail spuDetail = (SpuDetail) detail.getBody();
        //  通用属性
        Map<Long, Object> genericSpec = mapper.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        //  特殊属性
        Map<Long, Object> specialSpec = mapper.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, Object>>() {
        });
        Map<String, Object> specs = new HashMap<>();
        for (SpecificationParam param : specParamList) {
            //通用属性
            if (param.getGeneric()) {
                //通用属性值
                String value = "";
                try {
                    value = genericSpec.get(param.getId()).toString();

                } catch (Exception e) {
                    continue;
                }
                //通用属性是数字类型
                if (param.getDigital()) {
                    //获取区间
                    value = chooseInterval(value, param);
                }
                specs.put(param.getName(), value);
            } else {
                specs.put(param.getName(), specialSpec.get(param.getId()));
            }
        }

        goods.setId(spuBO.getId());
        goods.setAll(spuBO.getTitle() + spuBO.getBname() + StringUtils.join(categoryNames, " "));
        goods.setSubTitle(spuBO.getSubTitle());
        goods.setCid1(spuBO.getCid1());
        goods.setCid2(spuBO.getCid2());
        goods.setCid3(spuBO.getCid3());
        goods.setBrandId(spuBO.getBrandId());
        goods.setCreateTime(spuBO.getCreateTime());
        goods.setLastUpdateTime(spuBO.getLastUpdateTime());
        goods.setSkus(skus);
        goods.setPrice(price);
        goods.setSpecs(specs);
        return goods;
    }

    /**
     * 组装有区间的数字规格参数
     *
     * @param value
     * @param param
     * @return
     */
    String chooseInterval(String value, SpecificationParam param) {
        String result = "其他";
        double num = Double.parseDouble(value);
        String[] split = param.getSegments().split(",");
        for (String s : split) {
            String[] nums = s.split("-");
            double begin = Double.parseDouble(nums[0]);
            double end = Double.MAX_VALUE;
            if (nums.length == 2) {
                end = Double.parseDouble(nums[1]);
            }
            if (num > begin && num < end) {
                if (nums.length == 1) {
                    result = begin + param.getUnit() + "以上";
                } else if (begin == 0) {
                    result = end + param.getUnit() + "以下";
                } else {
                    result = s + param.getUnit();
                }
            }
        }
        return result;
    }

    @Override
    public void postAndPutGoods(Long id) throws JsonProcessingException {
        Spu spu = spuApi.getSpuById(id);
        SpuBO spuBO = new SpuBO();
        BeanUtils.copyProperties(spu, spuBO);
        Goods goods = getGoodsBySpu(spuBO);
        goodsSearchRepository.save(goods);
    }

    @Override
    public void deleteGoods(Long id) {
        goodsSearchRepository.deleteById(id);
    }
}
