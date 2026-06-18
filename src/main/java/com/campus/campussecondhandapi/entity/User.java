package com.campus.campussecondhandapi.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * <p>对应数据库 users 表，存储用户账号信息</p>
 * <p>包含登录凭证、个人资料（头像、联系方式）、卖家评分和已售数量等字段</p>
 *
 * @author campus
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String phone;
    private String email;
    private String address;
    private Double rating;
    private Integer soldCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
