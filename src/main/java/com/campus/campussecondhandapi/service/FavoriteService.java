package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.entity.Favorite;

import java.util.List;

/**
 * 收藏服务接口
 * <p>定义用户收藏相关的业务操作，包括查询收藏列表、添加收藏和取消收藏</p>
 *
 * @author campus
 */
public interface FavoriteService {
    
    /**
     * 根据用户ID获取收藏列表
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<Favorite> getFavoritesByUserId(Long userId);
    
    /**
     * 添加商品到收藏
     *
     * @param userId    用户ID
     * @param productId 商品ID
     */
    void addFavorite(Long userId, Long productId);
    
    /**
     * 取消收藏商品
     *
     * @param userId    用户ID
     * @param productId 商品ID
     */
    void removeFavorite(Long userId, Long productId);
}
