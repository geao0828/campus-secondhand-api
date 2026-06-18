package com.campus.campussecondhandapi.mapper;

import com.campus.campussecondhandapi.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类Mapper接口
 * <p>提供分类表的数据库操作，支持查询全部分类和按ID查询</p>
 * <p>对应MyBatis XML映射文件：CategoryMapper.xml</p>
 *
 * @author campus
 */
@Mapper
public interface CategoryMapper {
    
    /** 查询所有分类列表 */
    List<Category> selectAll();
    
    /** 根据ID查询分类 */
    Category selectById(String id);
}
