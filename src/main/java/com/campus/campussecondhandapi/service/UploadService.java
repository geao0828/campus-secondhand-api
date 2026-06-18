package com.campus.campussecondhandapi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务接口
 * <p>定义文件上传和删除的业务操作，底层基于本地文件系统实现</p>
 * <p>支持商品图片（product）和用户头像（avatar）两种类型</p>
 *
 * @author campus
 */
public interface UploadService {
    
    /**
     * 上传图片到本地存储
     * @param file 图片文件
     * @param type 图片类型：product / avatar
     * @return 图片访问URL
     */
    String uploadImage(MultipartFile file, String type);
    
    /**
     * 删除图片
     * @param objectName 对象名称（完整路径）
     * @param type 图片类型
     */
    void deleteImage(String objectName, String type);
}
