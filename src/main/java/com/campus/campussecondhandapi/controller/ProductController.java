package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.PageResult;
import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.entity.Product;
import com.campus.campussecondhandapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * <p>提供商品相关RESTful API，包括商品列表查询、分类查询、搜索、详情查看、发布、编辑和删除</p>
 * <p>查询类接口为公开接口，发布/编辑/删除接口需要登录并校验卖家身份</p>
 * <p>支持分页查询，不传pageSize时默认返回全部数据</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 获取商品列表，支持关键词搜索、分类筛选、排序和分页
     *
     * @param keyword  搜索关键词
     * @param category 分类筛选
     * @param sortBy   排序方式
     * @param page     页码
     * @param pageSize 每页数量，不传时返回全部数据
     * @return 分页商品结果
     */
    @GetMapping
    public Result<PageResult<Product>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "default") String sortBy,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize) {
        
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        
        PageResult<Product> result = productService.getProducts(keyword, category, sortBy, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    @GetMapping("/{id}")
    public Result<Product> getProductDetail(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Result.success(product);
    }
    
    /**
     * 获取热门商品列表
     *
     * @param page     页码
     * @param pageSize 每页数量，不传时返回全部数据
     * @return 分页热门商品结果
     */
    @GetMapping("/hot")
    public Result<PageResult<Product>> getHotProducts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize) {
        
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        
        PageResult<Product> result = productService.getHotProducts(page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 获取最新商品列表
     *
     * @param page     页码
     * @param pageSize 每页数量，不传时返回全部数据
     * @return 分页新品商品结果
     */
    @GetMapping("/new")
    public Result<PageResult<Product>> getNewProducts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize) {
        
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        
        PageResult<Product> result = productService.getNewProducts(page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 根据分类ID获取商品列表，支持关键词和排序
     *
     * @param categoryId 分类ID
     * @param keyword    搜索关键词
     * @param sortBy     排序方式
     * @param page       页码
     * @param pageSize   每页数量，不传时返回全部数据
     * @return 分页商品结果
     */
    @GetMapping("/category/{categoryId}")
    public Result<PageResult<Product>> getProductsByCategory(
            @PathVariable String categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "default") String sortBy,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize) {
        
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        
        PageResult<Product> result = productService.getProductsByCategory(categoryId, keyword, sortBy, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 搜索商品，按关键词和可选分类筛选
     *
     * @param keyword  搜索关键词
     * @param category 分类筛选
     * @param sortBy   排序方式
     * @param page     页码
     * @param pageSize 每页数量，不传时返回全部数据
     * @return 分页搜索结果
     */
    @GetMapping("/search")
    public Result<PageResult<Product>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "default") String sortBy,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) Integer pageSize) {
        
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        
        PageResult<Product> result = productService.getProducts(keyword, category, sortBy, page, pageSize);
        return Result.success(result);
    }
    
    /**
     * 发布新商品，需要登录，请求体包含商品名称、价格、分类、图片、描述等字段
     *
     * @param productData 商品数据
     * @param request     HTTP请求
     * @return 创建的商品
     */
    @PostMapping
    public Result<Product> publishProduct(@RequestBody Map<String, Object> productData, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        if (userId == null) {
            Object sellerObj = productData.get("seller");
            if (sellerObj instanceof Map) {
                Map<String, Object> seller = (Map<String, Object>) sellerObj;
                Object sellerIdObj = seller.get("id");
                if (sellerIdObj instanceof Number) {
                    userId = ((Number) sellerIdObj).longValue();
                }
            }
        }
        
        if (userId == null) {
            throw new RuntimeException("用户未登录，请先登录");
        }
        
        Product product = new Product();
        product.setName((String) productData.get("name"));
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new RuntimeException("商品名称不能为空");
        }
        
        Object priceObj = productData.get("price");
        if (priceObj instanceof Number) {
            product.setPrice(new BigDecimal(priceObj.toString()));
        } else if (priceObj != null) {
            product.setPrice(new BigDecimal(priceObj.toString()));
        }
        
        Object originalPriceObj = productData.get("originalPrice");
        if (originalPriceObj instanceof Number) {
            product.setOriginalPrice(new BigDecimal(originalPriceObj.toString()));
        } else if (originalPriceObj != null) {
            product.setOriginalPrice(new BigDecimal(originalPriceObj.toString()));
        }
        
        product.setCategory((String) productData.get("category"));
        product.setImage((String) productData.get("image"));
        
        Object imagesObj = productData.get("images");
        if (imagesObj != null) {
            try {
                product.setImages(objectMapper.writeValueAsString(imagesObj));
            } catch (Exception e) {
                product.setImages(imagesObj.toString());
            }
        }
        
        product.setDescription((String) productData.get("description"));
        product.setCondition((String) productData.get("condition"));
        
        Object stockObj = productData.get("stock");
        if (stockObj instanceof Number) {
            product.setStock(((Number) stockObj).intValue());
        } else if (stockObj != null) {
            product.setStock(Integer.parseInt(stockObj.toString()));
        }
        
        product.setSellerId(userId);
        Product createdProduct = productService.publishProduct(product);
        return Result.success(createdProduct);
    }
    
    /**
     * 编辑商品信息，需要登录，仅更新请求体中包含的字段
     *
     * @param id          商品ID
     * @param productData 更新的商品数据
     * @param request     HTTP请求
     * @return 更新后的商品
     */
    @PutMapping("/{id}")
    public Result<Product> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> productData, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        Product product = new Product();
        product.setId(id);
        
        if (productData.containsKey("name")) {
            product.setName((String) productData.get("name"));
        }
        
        Object priceObj = productData.get("price");
        if (priceObj instanceof Number) {
            product.setPrice(new BigDecimal(priceObj.toString()));
        } else if (priceObj != null) {
            product.setPrice(new BigDecimal(priceObj.toString()));
        }
        
        Object originalPriceObj = productData.get("originalPrice");
        if (originalPriceObj instanceof Number) {
            product.setOriginalPrice(new BigDecimal(originalPriceObj.toString()));
        } else if (originalPriceObj != null) {
            product.setOriginalPrice(new BigDecimal(originalPriceObj.toString()));
        }
        
        if (productData.containsKey("category")) {
            product.setCategory((String) productData.get("category"));
        }
        
        if (productData.containsKey("image")) {
            product.setImage((String) productData.get("image"));
        }
        
        Object imagesObj = productData.get("images");
        if (imagesObj != null) {
            try {
                product.setImages(objectMapper.writeValueAsString(imagesObj));
            } catch (Exception e) {
                product.setImages(imagesObj.toString());
            }
        }
        
        if (productData.containsKey("description")) {
            product.setDescription((String) productData.get("description"));
        }
        
        if (productData.containsKey("condition")) {
            product.setCondition((String) productData.get("condition"));
        }
        
        Object stockObj = productData.get("stock");
        if (stockObj instanceof Number) {
            product.setStock(((Number) stockObj).intValue());
        } else if (stockObj != null) {
            product.setStock(Integer.parseInt(stockObj.toString()));
        }
        
        if (productData.containsKey("status")) {
            product.setStatus((String) productData.get("status"));
        }
        
        if (productData.containsKey("isHot")) {
            Object isHotObj = productData.get("isHot");
            if (isHotObj instanceof Boolean) {
                product.setIsHot((Boolean) isHotObj);
            } else {
                product.setIsHot(Boolean.parseBoolean(isHotObj.toString()));
            }
        }
        
        if (productData.containsKey("isNew")) {
            Object isNewObj = productData.get("isNew");
            if (isNewObj instanceof Boolean) {
                product.setIsNew((Boolean) isNewObj);
            } else {
                product.setIsNew(Boolean.parseBoolean(isNewObj.toString()));
            }
        }
        
        Product updatedProduct = productService.updateProduct(id, product);
        return Result.success(updatedProduct);
    }
    
    /**
     * 删除商品，需要登录且只有卖家本人可以删除
     *
     * @param id      商品ID
     * @param body    可选的请求体，用于兑底获取用户ID
     * @param request HTTP请求
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id, 
                                      @RequestBody(required = false) Map<String, Object> body,
                                      HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        if (userId == null && body != null) {
            Object sellerObj = body.get("seller");
            if (sellerObj instanceof Map) {
                Map<String, Object> seller = (Map<String, Object>) sellerObj;
                Object sellerIdObj = seller.get("id");
                if (sellerIdObj instanceof Number) {
                    userId = ((Number) sellerIdObj).longValue();
                }
            }
            if (userId == null) {
                Object userIdObj = body.get("userId");
                if (userIdObj instanceof Number) {
                    userId = ((Number) userIdObj).longValue();
                }
            }
        }
        
        if (userId == null) {
            throw new RuntimeException("用户未登录，请先登录");
        }
        
        productService.deleteProduct(id, userId);
        return Result.success();
    }
}
