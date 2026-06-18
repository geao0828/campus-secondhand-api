package com.campus.campussecondhandapi.service.impl;

import com.campus.campussecondhandapi.service.UploadService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 文件上传服务实现类
 * <p>基于本地文件系统实现文件的上传和删除功能</p>
 * <p>文件存储路径格式：{basePath}/{type}/{timestamp}-{uuid}.{ext}，支持product和avatar两种类型</p>
 *
 * @author campus
 * @see UploadService
 */
@Service
public class UploadServiceImpl implements UploadService {
    
    @Value("${upload.base-path}")
    private String basePath;
    
    @Value("${upload.url-prefix}")
    private String urlPrefix;
    
    /**
     * 应用启动时确保上传目录存在
     */
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(basePath, "product"));
            Files.createDirectories(Paths.get(basePath, "avatar"));
        } catch (IOException e) {
            throw new RuntimeException("无法创建上传目录：" + e.getMessage());
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public String uploadImage(MultipartFile file, String type) {
        try {
            if (!"product".equals(type) && !"avatar".equals(type)) {
                throw new RuntimeException("type参数只能是product或avatar");
            }
            
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            // 生成文件名：时间戳-UUID.扩展名
            String fileName = System.currentTimeMillis() + "-" + 
                             UUID.randomUUID().toString().replace("-", "") + 
                             extension;
            
            // 完整存储路径：basePath/type/fileName
            Path targetDir = Paths.get(basePath, type);
            Files.createDirectories(targetDir);
            Path targetPath = targetDir.resolve(fileName);
            
            // 保存文件到本地
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            // 返回可访问的URL
            return urlPrefix + "/" + type + "/" + fileName;
            
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败：" + e.getMessage());
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void deleteImage(String objectName, String type) {
        try {
            // objectName 格式：type/fileName，例如：product/1717056123456-abc12def.jpg
            Path filePath = Paths.get(basePath, objectName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("文件删除失败：" + e.getMessage());
        }
    }
}
