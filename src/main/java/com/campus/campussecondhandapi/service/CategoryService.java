package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.entity.Category;

import java.util.List;

/**
 * 商品分类服务接口
 * <p>定义商品分类相关的业务操作，提供获取全部分类列表的功能</p>
 *
 * @author campus
 */
public interface CategoryService {
    
    /**
     * 获取所有商品分类列表
     *
     * @return 分类列表
     */
    List<Category> getAllCategories();
}
