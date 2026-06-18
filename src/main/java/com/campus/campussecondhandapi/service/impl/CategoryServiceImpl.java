package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.entity.Category;
import com.campus.campussecondhandapi.mapper.CategoryMapper;
import com.campus.campussecondhandapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类服务实现类
 * <p>实现分类列表查询功能，委托Mapper查询全部分类数据</p>
 *
 * @author campus
 * @see CategoryService
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    /** {@inheritDoc} */
    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectAll();
    }
}
