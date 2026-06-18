package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评价Mapper接口
 * <p>提供评价表的数据库操作，支持按商品分页查询评价、统计评价数、发表评价和重复评价检查</p>
 * <p>对应MyBatis XML映射文件：ReviewMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface ReviewMapper {
    
    /** 根据商品ID分页查询评价列表 */
    List<Review> selectByProductId(@Param("productId") Long productId, 
                                  @Param("offset") int offset, 
                                  @Param("pageSize") int pageSize);
    
    /** 统计指定商品的评价总数 */
    long countByProductId(Long productId);
    
    /** 插入评价记录 */
    int insert(Review review);
    
    /** 检查用户是否已评价过指定商品，返回匹配记录数 */
    int existsByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
