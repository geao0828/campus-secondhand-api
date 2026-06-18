package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.entity.Favorite;
import com.campus.campussecondhandapi.mapper.FavoriteMapper;
import com.campus.campussecondhandapi.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏服务实现类
 * <p>实现用户收藏商品的业务逻辑，添加收藏前检查是否已收藏以避免重复</p>
 *
 * @author campus
 * @see FavoriteService
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    @Autowired
    private FavoriteMapper favoriteMapper;
    
    /** {@inheritDoc} */
    @Override
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteMapper.selectByUserId(userId);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addFavorite(Long userId, Long productId) {
        int exists = favoriteMapper.existsByUserIdAndProductId(userId, productId);
        if (exists > 0) {
            throw new RuntimeException("已收藏此商品");
        }
        
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favoriteMapper.insert(favorite);
    }
    
    /** {@inheritDoc} */
    @Override
    public void removeFavorite(Long userId, Long productId) {
        favoriteMapper.deleteByUserIdAndProductId(userId, productId);
    }
}
