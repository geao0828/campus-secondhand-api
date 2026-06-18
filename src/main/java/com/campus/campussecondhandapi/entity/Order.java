package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * <p>对应数据库 orders 表，存储交易订单信息</p>
 * <p>订单状态：pending（待付款）、shipped（待收货）、completed（已完成）、cancelled（已取消）</p>
 *
 * @author campus
 */
@Data
public class Order {
    private String id;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private String status;
    private Long buyerId;
    private Long sellerId;
    private String address;
    private Boolean reviewed;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
