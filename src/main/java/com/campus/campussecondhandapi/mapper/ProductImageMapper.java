package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品图片Mapper接口
 * <p>提供商品图片表的数据库操作，支持按商品查询、单张/批量插入和按商品删除图片</p>
 * <p>对应MyBatis XML映射文件：ProductImageMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface ProductImageMapper {
    
    /** 根据商品ID查询图片列表 */
    List<ProductImage> selectByProductId(Long productId);
    
    /** 插入单张商品图片 */
    int insert(ProductImage productImage);
    
    /** 批量插入商品图片 */
    int insertBatch(@Param("list") List<ProductImage> images);
    
    /** 根据商品ID删除所有图片记录 */
    int deleteByProductId(Long productId);
}
