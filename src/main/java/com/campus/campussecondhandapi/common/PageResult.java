package com.campus.campussecondhandapi.common;

import lombok.Data;
import java.util.List;

/**
 * 分页结果封装类
 * <p>用于封装分页查询的结果，包含数据列表、总记录数、当前页码和每页大小</p>
 *
 * @param <T> 数据列表的泛型类型
 * @author campus
 */
@Data
public class PageResult<T> {
    private List<T> list;
    private long total;
    private int page;
    private int pageSize;
    
    public PageResult(List<T> list, long total, int page, int pageSize) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }
}
