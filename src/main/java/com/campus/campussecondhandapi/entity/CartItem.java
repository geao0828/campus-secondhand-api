package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 购物车项实体类
 * <p>对应数据库 cart_items 表，记录用户购物车中的商品及数量</p>
 *
 * @author campus
 */
@Data
public class CartItem {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createTime;
}
