package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * <p>提供用户表的数据库操作，支持按用户名/ID查询、插入新用户和更新用户信息</p>
 * <p>对应MyBatis XML映射文件：UserMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface UserMapper {
    
    /** 根据用户名查询用户 */
    User selectByUsername(String username);
    
    /** 根据ID查询用户 */
    User selectById(Long id);
    
    /** 插入新用户 */
    int insert(User user);
    
    /** 更新用户信息 */
    int update(User user);
}
