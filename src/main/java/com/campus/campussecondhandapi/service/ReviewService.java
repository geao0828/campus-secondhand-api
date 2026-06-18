package com.campus.campussecondhandapi.service;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Review;

/**
 * 评价服务接口
 * <p>定义商品评价相关的业务操作，包括查询评价列表和发表评价</p>
 *
 * @author campus
 */
public interface ReviewService {
    
    /**
     * 分页获取指定商品的评价列表
     *
     * @param productId 商品ID
     * @param page      页码
     * @param pageSize  每页数量
     * @return 分页评价结果
     */
    PageResult<Review> getProductReviews(Long productId, int page, int pageSize);
    
    /**
     * 发表商品评价，每个用户对同一商品只能评价一次
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param rating    评分
     * @param content   评价内容
     * @return 创建的评价
     */
    Review createReview(Long userId, Long productId, Integer rating, String content);
}
