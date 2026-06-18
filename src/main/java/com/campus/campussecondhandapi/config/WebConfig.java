package com.campus.campussecondhandapi.config;

import com.campus.campussecondhandapi.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 * <p>配置跨域资源共享（CORS）和注册JWT认证拦截器</p>
 * <p>拦截器对需要登录的接口进行token验证，排除登录注册、公开查询等接口</p>
 *
 * @author campus
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private JwtInterceptor jwtInterceptor;
    
    @Value("${upload.base-path}")
    private String uploadBasePath;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
    /**
     * 配置静态资源映射，使上传的文件可通过 /uploads/** 路径访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadBasePath + "/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 用户模块 - 登录注册（无需Token）
                        "/user/login",
                        "/user/register",
                        
                        // 商品模块 - 公开查询（无需Token）
                        "/products",
                        "/products/hot",
                        "/products/new",
                        "/products/search",
                        
                        // 分类模块（无需Token）
                        "/categories",
                        "/categories/**",
                        
                        // 上传文件访问（无需Token）
                        "/uploads/**"
                );
    }
}
