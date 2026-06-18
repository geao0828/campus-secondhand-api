package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.Review;
import com.campus.campussecondhandapi.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商品评价控制器
 * <p>提供商品评价列表查询和发表评价接口</p>
 * <p>查看评价为公开接口，发表评价需要登录且每个用户对同一商品只能评价一次</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    /**
     * 获取指定商品的评价列表，支持分页
     *
     * @param productId 商品ID
     * @param page      页码
     * @param pageSize  每页数量
     * @return 分页评价结果
     */
    @GetMapping
    public Result<PageResult<Review>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        
        PageResult<Review> result = reviewService.getProductReviews(productId, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 发表商品评价，需要登录，请求体格式：{rating, content}
     *
     * @param productId  商品ID
     * @param reviewData 评价数据（评分和内容）
     * @param request    HTTP请求
     * @return 创建的评价
     */
    @PostMapping
    public Result<Review> createReview(@PathVariable Long productId,
                                      @RequestBody Map<String, Object> reviewData,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Integer rating = Integer.valueOf(reviewData.get("rating").toString());
        String content = (String) reviewData.get("content");
        
        Review review = reviewService.createReview(userId, productId, rating, content);
        return Result.success(review);
    }
}
