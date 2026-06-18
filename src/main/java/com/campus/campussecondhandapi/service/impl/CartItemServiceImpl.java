package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.entity.CartItem;
import com.campus.campussecondhandapi.entity.Product;
import com.campus.campussecondhandapi.mapper.CartItemMapper;
import com.campus.campussecondhandapi.mapper.ProductMapper;
import com.campus.campussecondhandapi.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车服务实现类
 * <p>实现购物车的增删改查逻辑，添加商品时自动判断是否已存在并累加数量</p>
 *
 * @author campus
 * @see CartItemService
 */
@Service
public class CartItemServiceImpl implements CartItemService {
    
    @Autowired
    private CartItemMapper cartItemMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    /** {@inheritDoc} */
    @Override
    public List<CartItem> getCartByUserId(Long userId) {
        return cartItemMapper.selectByUserId(userId);
    }
    
    /** {@inheritDoc} */
    @Override
    public void addToCart(Long userId, Long productId, Integer quantity) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        CartItem existItem = cartItemMapper.selectByUserIdAndProductId(userId, productId);
        if (existItem != null) {
            cartItemMapper.updateQuantity(userId, productId, existItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItemMapper.insert(cartItem);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void updateCartItem(Long userId, Long productId, Integer quantity) {
        CartItem cartItem = cartItemMapper.selectByUserIdAndProductId(userId, productId);
        if (cartItem == null) {
            throw new RuntimeException("购物车商品不存在");
        }
        cartItemMapper.updateQuantity(userId, productId, quantity);
    }
    
    /** {@inheritDoc} */
    @Override
    public void removeFromCart(Long userId, Long productId) {
        cartItemMapper.deleteByUserIdAndProductId(userId, productId);
    }
    
    /** {@inheritDoc} */
    @Override
    public void clearCart(Long userId) {
        cartItemMapper.deleteByUserId(userId);
    }
}
