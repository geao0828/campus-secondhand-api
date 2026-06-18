package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品评价实体类
 * <p>对应数据库 reviews 表，存储用户对商品的评价信息，包含评分和评价内容</p>
 *
 * @author campus
 */
@Data
public class Review {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer rating;
    private String content;
    private LocalDateTime time;
}
