package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.entity.User;

/**
 * 用户服务接口
 * <p>定义用户相关的业务操作，包括登录认证、注册、查看和更新用户信息</p>
 *
 * @author campus
 */
public interface UserService {
    
    /**
     * 用户登录，验证用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    User login(String username, String password);
    
    /**
     * 用户注册，密码自动加密存储
     *
     * @param user 用户信息
     * @return 注册成功的用户信息
     */
    User register(User user);
    
    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserInfo(Long userId);
    
    /**
     * 更新用户信息
     *
     * @param user 更新的用户数据
     * @return 更新后的用户信息
     */
    User updateUserInfo(User user);
}
