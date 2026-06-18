package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏实体类
 * <p>对应数据库 favorites 表，记录用户收藏的商品，用户与商品多对多关系的中间表</p>
 *
 * @author campus
 */
@Data
public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createTime;
}
