package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Review;
import com.campus.campussecondhandapi.entity.User;
import com.campus.campussecondhandapi.mapper.ReviewMapper;
import com.campus.campussecondhandapi.mapper.UserMapper;
import com.campus.campussecondhandapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价服务实现类
 * <p>实现商品评价的业务逻辑，发表评价前检查用户是否已评价过该商品</p>
 *
 * @author campus
 * @see ReviewService
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Review> getProductReviews(Long productId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Review> reviews = reviewMapper.selectByProductId(productId, offset, pageSize);
        long total = reviewMapper.countByProductId(productId);
        return new PageResult<>(reviews, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public Review createReview(Long userId, Long productId, Integer rating, String content) {
        int exists = reviewMapper.existsByUserIdAndProductId(userId, productId);
        if (exists > 0) {
            throw new RuntimeException("已评价过此商品");
        }
        
        Review review = new Review();
        review.setProductId(productId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setContent(content);
        
        reviewMapper.insert(review);
        return review;
    }
}
