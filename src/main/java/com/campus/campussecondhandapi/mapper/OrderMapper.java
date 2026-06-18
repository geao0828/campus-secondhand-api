package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 * <p>提供订单表的数据库操作，支持分页查询、统计、状态更新及按卖家查询等操作</p>
 * <p>对应MyBatis XML映射文件：OrderMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface OrderMapper {
    
    /** 根据买家ID分页查询订单列表，支持状态筛选 */
    List<Order> selectByBuyerId(@Param("buyerId") Long buyerId, 
                               @Param("status") String status,
                               @Param("offset") int offset, 
                               @Param("pageSize") int pageSize);
    
    /** 统计买家的订单数量，支持状态筛选 */
    long countByBuyerId(@Param("buyerId") Long buyerId, @Param("status") String status);
    
    /** 根据订单ID查询订单详情 */
    Order selectById(String id);
    
    /** 插入新订单 */
    int insert(Order order);
    
    /** 更新订单状态 */
    int updateStatus(@Param("id") String id, @Param("status") String status);
    
    /** 根据卖家ID查询订单列表 */
    List<Order> selectBySellerId(Long sellerId);
}
