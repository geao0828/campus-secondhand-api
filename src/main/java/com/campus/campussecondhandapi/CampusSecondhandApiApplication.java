package com.campus.campussecondhandapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园二手交易平台后端API启动类
 * <p>基于Spring Boot框架，集成MyBatis、Spring Security、JWT认证、MinIO文件存储等技术</p>
 *
 * @author campus
 */
@SpringBootApplication
public class CampusSecondhandApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSecondhandApiApplication.class, args);
    }

}
