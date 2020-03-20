package com.leyou.item.service.service.impl;

import com.leyou.item.api.domain.Category;
import com.leyou.item.service.dao.ICategoryDao;
import com.leyou.item.service.service.ICategoryService;
import com.sun.deploy.net.proxy.pac.PACFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
