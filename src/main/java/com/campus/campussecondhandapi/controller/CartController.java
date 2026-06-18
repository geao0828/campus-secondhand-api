package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.CartItem;
import com.campus.campussecondhandapi.service.CartItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 购物车控制器
 * <p>提供购物车相关RESTful API，包括查看购物车、添加商品、更新数量、移除商品和清空购物车</p>
 * <p>所有接口均需要登录，通过JWT token获取当前用户ID</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartItemService cartItemService;
    
    /**
     * 获取当前用户的购物车列表
     *
     * @param request HTTP请求，用于获取当前用户ID
     * @return 购物车项列表
     */
    @GetMapping
    public Result<List<CartItem>> getCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<CartItem> cart = cartItemService.getCartByUserId(userId);
        return Result.success(cart);
    }
    
    /**
     * 添加商品到购物车，请求体格式：{productId, quantity}
     *
     * @param cartData 包含商品ID和数量的请求体
     * @param request  HTTP请求
     * @return 操作结果
     */
    @PostMapping
    public Result<Void> addToCart(@RequestBody Map<String, Integer> cartData, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long productId = Long.valueOf(cartData.get("productId").toString());
        Integer quantity = cartData.get("quantity");
        
        cartItemService.addToCart(userId, productId, quantity);
        return Result.success();
    }
    
    /**
     * 更新购物车中指定商品的数量，请求体格式：{quantity}
     *
     * @param productId 商品ID
     * @param cartData  包含新数量的请求体
     * @param request   HTTP请求
     * @return 操作结果
     */
    @PutMapping("/{productId}")
    public Result<Void> updateCartItem(@PathVariable Long productId, 
                                      @RequestBody Map<String, Integer> cartData,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Integer quantity = cartData.get("quantity");
        
        cartItemService.updateCartItem(userId, productId, quantity);
        return Result.success();
    }
    
    /**
     * 从购物车中移除指定商品
     *
     * @param productId 商品ID
     * @param request   HTTP请求
     * @return 操作结果
     */
    @DeleteMapping("/{productId}")
    public Result<Void> removeFromCart(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartItemService.removeFromCart(userId, productId);
        return Result.success();
    }
    
    /**
     * 清空当前用户的所有购物车商品
     *
     * @param request HTTP请求
     * @return 操作结果
     */
    @DeleteMapping
    public Result<Void> clearCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartItemService.clearCart(userId);
        return Result.success();
    }
}
