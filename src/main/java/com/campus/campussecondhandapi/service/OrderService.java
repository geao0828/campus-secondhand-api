package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Order;

/**
 * 订单服务接口
 * <p>定义订单相关的业务操作，包括订单查询、创建、支付、确认收货和取消等功能</p>
 *
 * @author campus
 */
public interface OrderService {
    
    /**
     * 根据买家ID分页查询订单列表
     *
     * @param buyerId  买家ID
     * @param status   订单状态筛选
     * @param page     页码
     * @param pageSize 每页数量
     * @return 分页订单结果
     */
    PageResult<Order> getOrdersByBuyerId(Long buyerId, String status, int page, int pageSize);
    
    /**
     * 根据订单ID获取订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情
     */
    Order getOrderById(String orderId);
    
    /**
     * 创建订单，自动校验商品状态和库存
     *
     * @param buyerId   买家ID
     * @param productId 商品ID
     * @param quantity  购买数量
     * @param address   收货地址
     * @return 创建的订单
     */
    Order createOrder(Long buyerId, Long productId, Integer quantity, String address);
    
    /**
     * 支付订单，将状态更新为待收货
     *
     * @param orderId 订单ID
     * @param buyerId 买家ID（校验权限）
     * @return 更新后的订单
     */
    Order payOrder(String orderId, Long buyerId);
    
    /**
     * 确认收货，将状态更新为已完成
     *
     * @param orderId 订单ID
     * @param buyerId 买家ID（校验权限）
     * @return 更新后的订单
     */
    Order confirmReceive(String orderId, Long buyerId);
    
    /**
     * 取消订单，仅待付款状态可取消，自动回滚库存
     *
     * @param orderId 订单ID
     * @param buyerId 买家ID（校验权限）
     * @return 更新后的订单
     */
    Order cancelOrder(String orderId, Long buyerId);
}
