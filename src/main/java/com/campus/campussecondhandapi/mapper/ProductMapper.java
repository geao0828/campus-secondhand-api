package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper接口
 * <p>提供商品表的数据库操作，支持分页查询、搜索、分类筛选、热门/新品查询、CRUD及库存更新</p>
 * <p>对应MyBatis XML映射文件：ProductMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface ProductMapper {
    
    /** 分页查询商品列表，支持关键词、分类和排序 */
    List<Product> selectAll(@Param("keyword") String keyword, 
                           @Param("category") String category, 
                           @Param("sortBy") String sortBy,
                           @Param("offset") int offset, 
                           @Param("pageSize") int pageSize);
    
    /** 统计商品总数，支持关键词和分类筛选 */
    long countAll(@Param("keyword") String keyword, @Param("category") String category);
    
    /** 根据ID查询商品详情 */
    Product selectById(Long id);
    
    /** 分页查询热门商品 */
    List<Product> selectHotProducts(@Param("offset") int offset, @Param("pageSize") int pageSize);
    
    /** 分页查询最新商品 */
    List<Product> selectNewProducts(@Param("offset") int offset, @Param("pageSize") int pageSize);
    
    /** 根据分类ID分页查询商品，支持关键词和排序 */
    List<Product> selectByCategory(@Param("categoryId") String categoryId,
                                  @Param("keyword") String keyword,
                                  @Param("sortBy") String sortBy,
                                  @Param("offset") int offset,
                                  @Param("pageSize") int pageSize);
    
    /** 统计指定分类下的商品数量 */
    long countByCategory(@Param("categoryId") String categoryId, @Param("keyword") String keyword);
    
    /** 插入新商品 */
    int insert(Product product);
    
    /** 更新商品信息 */
    int update(Product product);
    
    /** 根据ID删除商品 */
    int deleteById(Long id);
    
    /** 根据卖家ID查询商品列表 */
    List<Product> selectBySellerId(Long sellerId);
    
    /** 更新商品库存 */
    int updateStock(@Param("id") Long id, @Param("stock") int stock);
}
