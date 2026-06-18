package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.Order;
import com.campus.campussecondhandapi.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单控制器
 * <p>提供订单全生命周期管理API，包括创建订单、查看订单列表/详情、支付、确认收货和取消订单</p>
 * <p>订单状态流转：pending（待付款）→ shipped（待收货）→ completed（已完成）/ cancelled（已取消）</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 获取当前用户的订单列表，支持按状态筛选和分页
     *
     * @param status   订单状态筛选（默认all）
     * @param page     页码
     * @param pageSize 每页数量
     * @param request  HTTP请求
     * @return 分页订单结果
     */
    @GetMapping
    public Result<PageResult<Order>> getOrders(
            @RequestParam(required = false, defaultValue = "all") String status,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            HttpServletRequest request) {
        
        Long userId = (Long) request.getAttribute("userId");
        PageResult<Order> result = orderService.getOrdersByBuyerId(userId, status, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取订单详情
     *
     * @param id      订单ID
     * @param request HTTP请求
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderDetail(@PathVariable String id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.getOrderById(id);
        return Result.success(order);
    }
    
    /**
     * 创建订单，请求体格式：{productId, quantity, address}
     *
     * @param orderData 订单数据
     * @param request   HTTP请求
     * @return 创建的订单
     */
    @PostMapping
    public Result<Order> createOrder(@RequestBody Map<String, Object> orderData, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long productId = Long.valueOf(orderData.get("productId").toString());
        Integer quantity = Integer.valueOf(orderData.get("quantity").toString());
        String address = (String) orderData.get("address");
        
        Order order = orderService.createOrder(userId, productId, quantity, address);
        return Result.success(order);
    }
    
    /**
     * 支付订单，将订单状态从待付款更新为待收货
     *
     * @param id      订单ID
     * @param request HTTP请求
     * @return 更新后的订单
     */
    @PostMapping("/{id}/pay")
    public Result<Order> payOrder(@PathVariable String id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.payOrder(id, userId);
        return Result.success(order);
    }
    
    /**
     * 确认收货，将订单状态从待收货更新为已完成
     *
     * @param id      订单ID
     * @param request HTTP请求
     * @return 更新后的订单
     */
    @PostMapping("/{id}/confirm")
    public Result<Order> confirmReceive(@PathVariable String id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.confirmReceive(id, userId);
        return Result.success(order);
    }
    
    /**
     * 取消订单，仅待付款状态的订单可取消
     *
     * @param id      订单ID
     * @param request HTTP请求
     * @return 更新后的订单
     */
    @PostMapping("/{id}/cancel")
    public Result<Order> cancelOrder(@PathVariable String id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.cancelOrder(id, userId);
        return Result.success(order);
    }
}
