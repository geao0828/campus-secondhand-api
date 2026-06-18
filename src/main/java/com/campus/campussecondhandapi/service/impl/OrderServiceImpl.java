package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Order;
import com.campus.campussecondhandapi.entity.Product;
import com.campus.campussecondhandapi.mapper.OrderMapper;
import com.campus.campussecondhandapi.mapper.ProductMapper;
import com.campus.campussecondhandapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单服务实现类
 * <p>实现订单全生命周期管理，包括创建、支付、确认收货和取消订单</p>
 * <p>创建订单时自动校验商品状态和库存，取消订单时自动回滚库存</p>
 * <p>订单ID采用时间戳+序列号格式生成，保证唯一性</p>
 *
 * @author campus
 * @see OrderService
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    private final AtomicInteger counter = new AtomicInteger(0);
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Order> getOrdersByBuyerId(Long buyerId, String status, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Order> orders = orderMapper.selectByBuyerId(buyerId, status, offset, pageSize);
        long total = orderMapper.countByBuyerId(buyerId, status);
        return new PageResult<>(orders, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public Order getOrderById(String orderId) {
        return orderMapper.selectById(orderId);
    }
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public Order createOrder(Long buyerId, Long productId, Integer quantity, String address) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (product.getStatus().equals("inactive")) {
            throw new RuntimeException("商品已下架");
        }
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }
        
        Order order = new Order();
        order.setId(generateOrderId());
        order.setProductId(productId);
        order.setProductName(product.getName());
        order.setProductImage(product.getImage());
        order.setPrice(product.getPrice());
        order.setQuantity(quantity);
        order.setStatus("pending");
        order.setBuyerId(buyerId);
        order.setSellerId(product.getSellerId());
        order.setAddress(address);
        
        orderMapper.insert(order);
        
        productMapper.updateStock(productId, product.getStock() - quantity);
        
        return orderMapper.selectById(order.getId());
    }
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public Order payOrder(String orderId, Long buyerId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getBuyerId().equals(buyerId)) {
            throw new RuntimeException("无权操作此订单");
        }
        if (!order.getStatus().equals("pending")) {
            throw new RuntimeException("订单状态错误");
        }
        
        orderMapper.updateStatus(orderId, "shipped");
        return orderMapper.selectById(orderId);
    }
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public Order confirmReceive(String orderId, Long buyerId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getBuyerId().equals(buyerId)) {
            throw new RuntimeException("无权操作此订单");
        }
        if (!order.getStatus().equals("shipped")) {
            throw new RuntimeException("订单状态错误");
        }
        
        orderMapper.updateStatus(orderId, "completed");
        return orderMapper.selectById(orderId);
    }
    
    /** {@inheritDoc} */
    @Override
    @Transactional
    public Order cancelOrder(String orderId, Long buyerId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getBuyerId().equals(buyerId)) {
            throw new RuntimeException("无权操作此订单");
        }
        if (!order.getStatus().equals("pending")) {
            throw new RuntimeException("只能取消待付款订单");
        }
        
        orderMapper.updateStatus(orderId, "cancelled");
        
        Product product = productMapper.selectById(order.getProductId());
        if (product != null) {
            productMapper.updateStock(order.getProductId(), product.getStock() + order.getQuantity());
        }
        
        return orderMapper.selectById(orderId);
    }
    
    /**
     * 生成唯一订单ID，格式为“ORD + 时间戳 + 序列号”
     *
     * @return 订单ID
     */
    private String generateOrderId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int count = counter.incrementAndGet();
        return "ORD" + timestamp + String.format("%03d", count % 1000);
    }
}
