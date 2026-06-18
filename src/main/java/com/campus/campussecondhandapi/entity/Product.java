package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 * <p>对应数据库 products 表，存储二手商品信息</p>
 * <p>包含商品基本信息、价格、库存、状态标记（热门/新品）以及关联卖家信息</p>
 *
 * @author campus
 */
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String category;
    private String image;
    private String images;
    private String description;
    private String condition;
    private LocalDateTime publishTime;
    private Boolean isHot;
    private Boolean isNew;
    private Integer stock;
    private String status;
    private Long sellerId;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 关联卖家信息
    private User seller;
}
