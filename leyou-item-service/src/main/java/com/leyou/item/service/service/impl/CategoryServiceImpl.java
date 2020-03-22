package com.leyou.item.service.service.impl;

import com.leyou.item.api.domain.Category;
import com.leyou.item.service.dao.ICategoryDao;
import com.leyou.item.service.service.ICategoryService;
import com.sun.deploy.net.proxy.pac.PACFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/19
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    ICategoryDao categoryDao;

    @Override
    public List<Category> getCategoryListByParentId(Long parentId) {
        Category category = new Category();
        category.setParentId(parentId);
        return categoryDao.select(category);
    }

    @Override
    public List<Category> getCategoryByBrandId(Long bid) {
        List<Category> categoryList = categoryDao.getCategoryByBrandId(bid);
        return categoryList;
    }

    @Override
    public List<String> getCategoryNameByIds(List<Long> ids) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        List<Category> categoryList = categoryDao.selectByExample(example);
        List<String> names = new ArrayList<String>();
        for (Category category : categoryList) {
            names.add(category.getName());
        }
        return names;
    }
}
