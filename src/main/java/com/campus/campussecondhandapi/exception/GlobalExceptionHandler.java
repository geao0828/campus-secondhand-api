package com.campus.campussecondhandapi.exception;

import com.campus.campussecondhandapi.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>统一捕获并处理控制器层抛出的异常，返回统一格式的错误响应</p>
 * <p>处理RuntimeException和通用Exception，避免服务器内部错误信息直接暴露</p>
 *
 * @author campus
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        return Result.error("服务器内部错误: " + e.getMessage());
    }
}
