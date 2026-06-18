package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.Category;
import com.campus.campussecondhandapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制器
 * <p>提供分类列表查询和单个分类详情查询接口</p>
 * <p>分类接口为公开接口，无需登录即可访问</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 获取所有商品分类列表
     *
     * @return 分类列表
     */
    @GetMapping
    public Result<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }
    
    /**
     * 根据ID获取单个分类详情
     *
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public Result<Category> getCategory(@PathVariable String id) {
        Category category = categoryService.getAllCategories().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        return Result.success(category);
    }
}
