package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.entity.User;
import com.campus.campussecondhandapi.mapper.UserMapper;
import com.campus.campussecondhandapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * <p>实现用户注册、登录、信息查询和更新等业务逻辑</p>
 * <p>登录和注册时使用BCrypt算法对密码进行加密处理，返回结果自动脱敏密码字段</p>
 *
 * @author campus
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /** {@inheritDoc} */
    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        user.setPassword(null);
        return user;
    }
    
    /** {@inheritDoc} */
    @Override
    public User register(User user) {
        User existUser = userMapper.selectByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userMapper.insert(user);
        user.setPassword(null);
        return user;
    }
    
    /** {@inheritDoc} */
    @Override
    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
    
    /** {@inheritDoc} */
    @Override
    public User updateUserInfo(User user) {
        userMapper.update(user);
        return getUserInfo(user.getId());
    }
}
