package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.entity.CartItem;

import java.util.List;

/**
 * 购物车服务接口
 * <p>定义购物车相关的业务操作，包括查看购物车、添加、更新、移除商品和清空购物车</p>
 *
 * @author campus
 */
public interface CartItemService {
    
    /**
     * 根据用户ID获取购物车列表
     *
     * @param userId 用户ID
     * @return 购物车项列表
     */
    List<CartItem> getCartByUserId(Long userId);
    
    /**
     * 添加商品到购物车，已存在则累加数量
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param quantity  数量
     */
    void addToCart(Long userId, Long productId, Integer quantity);
    
    /**
     * 更新购物车中指定商品的数量
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param quantity  新数量
     */
    void updateCartItem(Long userId, Long productId, Integer quantity);
    
    /**
     * 从购物车中移除指定商品
     *
     * @param userId    用户ID
     * @param productId 商品ID
     */
    void removeFromCart(Long userId, Long productId);
    
    /**
     * 清空用户的所有购物车商品
     *
     * @param userId 用户ID
     */
    void clearCart(Long userId);
}
