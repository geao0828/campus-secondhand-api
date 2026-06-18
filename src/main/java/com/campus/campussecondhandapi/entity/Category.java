package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品分类实体类
 * <p>对应数据库 categories 表，存储商品分类信息，包含分类名称、图标和排序权重</p>
 *
 * @author campus
 */
@Data
public class Category {
    private String id;
    private String name;
    private String icon;
    private Integer sort;
    private LocalDateTime createdAt;
}
