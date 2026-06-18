package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.Favorite;
import com.campus.campussecondhandapi.entity.Order;
import com.campus.campussecondhandapi.entity.Product;
import com.campus.campussecondhandapi.entity.User;
import com.campus.campussecondhandapi.service.FavoriteService;
import com.campus.campussecondhandapi.service.OrderService;
import com.campus.campussecondhandapi.service.ProductService;
import com.campus.campussecondhandapi.service.UserService;
import com.campus.campussecondhandapi.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * <p>提供用户认证、信息管理、收藏、发布商品、订单等用户相关API</p>
 * <p>登录和注册接口为公开接口，其他接口需要登录并携带JWT token</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 用户登录，请求体格式：{username, password}
     *
     * @param loginData 登录数据
     * @return 包含token和用户信息的响应
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        User user = userService.login(username, password);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        
        return Result.success(result);
    }
    
    /**
     * 用户注册，请求体为User对象
     *
     * @param user 用户信息
     * @return 包含token和用户信息的响应
     */
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        String token = jwtUtil.generateToken(registeredUser.getId(), registeredUser.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", registeredUser);
        
        return Result.success(result);
    }
    
    /**
     * 获取当前登录用户信息
     *
     * @param request HTTP请求
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }
    
    /**
     * 更新当前登录用户信息
     *
     * @param user    更新的用户数据
     * @param request HTTP请求
     * @return 更新后的用户信息
     */
    @PutMapping("/info")
    public Result<User> updateUserInfo(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        User updatedUser = userService.updateUserInfo(user);
        return Result.success(updatedUser);
    }
    
    /**
     * 获取当前用户发布的商品列表
     *
     * @param request HTTP请求
     * @return 用户发布的商品列表
     */
    @GetMapping("/products")
    public Result<List<Product>> getMyProducts(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Product> products = productService.getProductsBySellerId(userId);
        return Result.success(products);
    }
    
    /**
     * 获取当前用户的收藏列表
     *
     * @param request HTTP请求
     * @return 收藏列表
     */
    @GetMapping("/favorites")
    public Result<List<Favorite>> getMyFavorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(userId);
        return Result.success(favorites);
    }
    
    /**
     * 添加商品到收藏
     *
     * @param productId 商品ID
     * @param request   HTTP请求
     * @return 操作结果
     */
    @PostMapping("/favorites/{productId}")
    public Result<Void> addFavorite(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteService.addFavorite(userId, productId);
        return Result.success();
    }
    
    /**
     * 取消收藏商品
     *
     * @param productId 商品ID
     * @param request   HTTP请求
     * @return 操作结果
     */
    @DeleteMapping("/favorites/{productId}")
    public Result<Void> removeFavorite(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteService.removeFavorite(userId, productId);
        return Result.success();
    }
    
    /**
     * 获取当前用户的订单列表，支持按状态筛选
     *
     * @param status  订单状态筛选
     * @param request HTTP请求
     * @return 订单列表
     */
    @GetMapping("/orders")
    public Result<List<Order>> getMyOrders(
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.getOrdersByBuyerId(userId, status, 1, 1000).getList());
    }
}
