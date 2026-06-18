package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Product;

import java.util.List;

/**
 * 商品服务接口
 * <p>定义商品相关的业务操作，包括商品查询、搜索、分类筛选、发布、编辑、删除等</p>
 *
 * @author campus
 */
public interface ProductService {
    
    /**
     * 分页查询商品列表，支持关键词和分类筛选
     *
     * @param keyword  搜索关键词
     * @param category 分类
     * @param sortBy   排序方式
     * @param page     页码
     * @param pageSize 每页数量
     * @return 分页商品结果
     */
    PageResult<Product> getProducts(String keyword, String category, String sortBy, int page, int pageSize);
    
    /**
     * 根据ID获取商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    Product getProductById(Long id);
    
    /**
     * 分页获取热门商品列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 分页热门商品结果
     */
    PageResult<Product> getHotProducts(int page, int pageSize);
    
    /**
     * 分页获取最新商品列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 分页新品结果
     */
    PageResult<Product> getNewProducts(int page, int pageSize);
    
    /**
     * 根据分类ID分页查询商品列表
     *
     * @param categoryId 分类ID
     * @param keyword    搜索关键词
     * @param sortBy     排序方式
     * @param page       页码
     * @param pageSize   每页数量
     * @return 分页商品结果
     */
    PageResult<Product> getProductsByCategory(String categoryId, String keyword, String sortBy, int page, int pageSize);
    
    /**
     * 发布新商品
     *
     * @param product 商品信息
     * @return 创建的商品
     */
    Product publishProduct(Product product);
    
    /**
     * 更新商品信息
     *
     * @param id      商品ID
     * @param product 更新的商品数据
     * @return 更新后的商品
     */
    Product updateProduct(Long id, Product product);
    
    /**
     * 删除商品，仅卖家本人可删除
     *
     * @param id       商品ID
     * @param sellerId 卖家ID（校验权限）
     */
    void deleteProduct(Long id, Long sellerId);
    
    /**
     * 根据卖家ID获取其发布的商品列表
     *
     * @param sellerId 卖家ID
     * @return 商品列表
     */
    List<Product> getProductsBySellerId(Long sellerId);
}
