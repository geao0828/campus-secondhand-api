package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏Mapper接口
 * <p>提供收藏表的数据库操作，支持按用户查询、判断是否已收藏、添加和删除收藏记录</p>
 * <p>对应MyBatis XML映射文件：FavoriteMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface FavoriteMapper {
    
    /** 根据用户ID查询收藏列表 */
    List<Favorite> selectByUserId(Long userId);
    
    /** 检查用户是否已收藏指定商品，返回匹配记录数 */
    int existsByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    /** 插入收藏记录 */
    int insert(Favorite favorite);
    
    /** 删除用户对指定商品的收藏 */
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
