package com.campus.campussecondhandapi.controller;

import com.campus.campussecondhandapi.common.Result;
import com.campus.campussecondhandapi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 * <p>提供图片上传接口，支持商品图片和用户头像上传</p>
 * <p>上传文件存储至项目本地目录，支持jpg、png、webp、gif格式，单文件限制5MB</p>
 *
 * @author campus
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    
    @Autowired
    private UploadService uploadService;
    
    /**
     * 上传图片
     * @param file 图片文件
     * @param type 图片类型：product(商品图) / avatar(头像)
     * @return 图片URL和类型
     */
    @PostMapping("/image")
    public Result<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "product") String type) {
        
        try {
            if (file.isEmpty()) {
                return Result.error("文件不能为空");
            }
            
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return Result.error("文件名不能为空");
            }
            
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String[] allowedExtensions = {"jpg", "jpeg", "png", "webp", "gif"};
            boolean allowed = false;
            for (String ext : allowedExtensions) {
                if (ext.equals(extension)) {
                    allowed = true;
                    break;
                }
            }
            
            if (!allowed) {
                return Result.error("仅支持 jpg、png、webp、gif 格式");
            }
            
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("文件类型必须是图片");
            }
            
            if (file.getSize() > 5 * 1024 * 1024) {
                return Result.error("文件大小不能超过5MB");
            }
            
            String url = uploadService.uploadImage(file, type);
            
            Map<String, String> data = new HashMap<>();
            data.put("url", url);
            data.put("type", type);
            
            return Result.success(data);
            
        } catch (Exception e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}
