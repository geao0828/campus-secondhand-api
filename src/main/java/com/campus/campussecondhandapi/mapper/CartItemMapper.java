package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车项Mapper接口
 * <p>提供购物车表的数据库操作，支持按用户查询、添加、更新数量、删除等操作</p>
 * <p>对应MyBatis XML映射文件：CartItemMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface CartItemMapper {
    
    /** 根据用户ID查询购物车列表 */
    List<CartItem> selectByUserId(Long userId);
    
    /** 根据用户ID和商品ID查询购物车项 */
    CartItem selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    /** 插入购物车项 */
    int insert(CartItem cartItem);
    
    /** 更新购物车中指定商品的数量 */
    int updateQuantity(@Param("userId") Long userId, @Param("productId") Long productId, @Param("quantity") int quantity);
    
    /** 删除用户购物车中的指定商品 */
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    /** 清空用户的所有购物车商品 */
    int deleteByUserId(Long userId);
}
