package com.campus.campussecondhandapi.entity;

import lombok.Data;

/**
 * 商品图片实体类
 * <p>对应数据库 product_images 表，存储商品的多张图片信息，支持图片排序</p>
 *
 * @author campus
 */
@Data
public class ProductImage {
    private Long id;
    private Long productId;
    private String url;
    private Integer sort;
}
