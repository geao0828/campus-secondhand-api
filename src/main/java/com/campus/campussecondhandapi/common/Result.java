package com.campus.campussecondhandapi.common;

import lombok.Data;

/**
 * 统一响应结果封装类
 * <p>所有API接口的统一返回格式，包含状态码、提示信息和数据体</p>
 * <p>提供静态工厂方法快速构建成功/失败响应</p>
 *
 * @param <T> 响应数据的泛型类型
 * @author campus
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
