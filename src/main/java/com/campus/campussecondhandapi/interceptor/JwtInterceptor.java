package com.campus.campussecondhandapi.interceptor;

import com.campus.campussecondhandapi.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 * <p>拦截需要登录的请求，验证Authorization请求头中的JWT token有效性</p>
 * <p>解析token成功后将userId注入request属性，供后续控制器使用</p>
 * <p>自动跳过OPTIONS预检请求，对token过期、无效等情况返回401状态码</p>
 *
 * @author campus
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        
        String token = request.getHeader("Authorization");
        System.out.println("JWT拦截器 - 请求路径: " + request.getRequestURI());
        System.out.println("JWT拦截器 - Authorization头: " + (token != null ? token.substring(0, Math.min(20, token.length())) + "..." : "null"));
        
        if (token == null || token.isEmpty()) {
            System.out.println("JWT拦截器 - 错误: token为空");
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未授权，请先登录\",\"data\":null}");
            return false;
        }
        
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        try {
            System.out.println("JWT拦截器 - 开始解析token...");
            
            if (jwtUtil.isTokenExpired(token)) {
                System.out.println("JWT拦截器 - 错误: token已过期");
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"token已过期\",\"data\":null}");
                return false;
            }
            
            Long userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);
            System.out.println("JWT拦截器 - 解析成功: userId=" + userId + ", username=" + username);
            
            if (userId == null) {
                System.out.println("JWT拦截器 - 警告: userId为null，token内容可能有问题");
            }
            
            request.setAttribute("userId", userId);
            return true;
            
        } catch (Exception e) {
            System.out.println("JWT拦截器 - 错误: token解析失败");
            System.out.println("JWT拦截器 - 异常类型: " + e.getClass().getName());
            System.out.println("JWT拦截器 - 异常信息: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效\",\"data\":null}");
            return false;
        }
    }
}
