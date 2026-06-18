package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.entity.Product;
import com.campus.campussecondhandapi.mapper.ProductMapper;
import com.campus.campussecondhandapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现类
 * <p>实现商品的查询、搜索、分页、发布、编辑和删除等业务逻辑</p>
 * <p>发布商品时自动设置初始状态，删除商品时校验卖家身份权限</p>
 *
 * @author campus
 * @see ProductService
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Product> getProducts(String keyword, String category, String sortBy, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Product> products = productMapper.selectAll(keyword, category, sortBy, offset, pageSize);
        long total = productMapper.countAll(keyword, category);
        return new PageResult<>(products, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Product> getHotProducts(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Product> products = productMapper.selectHotProducts(offset, pageSize);
        long total = productMapper.countAll(null, null);
        return new PageResult<>(products, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Product> getNewProducts(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Product> products = productMapper.selectNewProducts(offset, pageSize);
        long total = productMapper.countAll(null, null);
        return new PageResult<>(products, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public PageResult<Product> getProductsByCategory(String categoryId, String keyword, String sortBy, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Product> products = productMapper.selectByCategory(categoryId, keyword, sortBy, offset, pageSize);
        long total = productMapper.countByCategory(categoryId, keyword);
        return new PageResult<>(products, total, page, pageSize);
    }
    
    /** {@inheritDoc} */
    @Override
    public Product publishProduct(Product product) {
        product.setStatus("active");
        product.setIsHot(false);
        product.setIsNew(true);
        productMapper.insert(product);
        return productMapper.selectById(product.getId());
    }
    
    /** {@inheritDoc} */
    @Override
    public Product updateProduct(Long id, Product product) {
        Product existProduct = productMapper.selectById(id);
        if (existProduct == null) {
            throw new RuntimeException("商品不存在");
        }
        product.setId(id);
        productMapper.update(product);
        return productMapper.selectById(id);
    }
    
    /** {@inheritDoc} */
    @Override
    public void deleteProduct(Long id, Long sellerId) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        if (product.getSellerId() == null) {
            throw new RuntimeException("商品数据异常：sellerId为空");
        }
        
        if (sellerId == null) {
            throw new RuntimeException("用户未登录");
        }
        
        if (!product.getSellerId().equals(sellerId)) {
            throw new RuntimeException("无权删除此商品，只有卖家可以删除");
        }
        
        productMapper.deleteById(id);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Product> getProductsBySellerId(Long sellerId) {
        return productMapper.selectBySellerId(sellerId);
    }
}
