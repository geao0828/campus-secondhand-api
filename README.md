# 校园二手交易平台 - 后端 API

> 基于 Spring Boot 3 + MyBatis + MySQL 的校园二手交易平台后端接口服务，提供用户认证、商品管理、购物车、订单、评价、收藏等完整的 RESTful API。

---

## 目录

- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [环境要求](#环境要求)
- [快速启动](#快速启动)
- [数据库设计](#数据库设计)
- [API 接口概览](#api-接口概览)
- [统一响应格式](#统一响应格式)
- [认证机制](#认证机制)

---

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 编程语言 |
| Spring Boot | 3.0.2 | Web 应用框架 |
| Spring Security | - | 安全框架（提供 BCrypt 密码加密） |
| MyBatis | 3.0.3 | ORM 持久层框架 |
| MySQL | 8.x | 关系型数据库 |
| JJWT | 0.12.3 | JWT 令牌生成与解析 |
| Lombok | - | 简化 Java 代码 |
| Maven | - | 项目构建工具 |

---

## 项目结构

```
campus-secondhand-api/
├── src/main/java/com/campus/campussecondhandapi/
│   ├── common/                 # 公共类（统一响应、分页封装）
│   │   ├── Result.java             # 统一响应结果
│   │   └── PageResult.java         # 分页结果封装
│   ├── config/                 # 配置类
│   │   ├── SecurityConfig.java     # Spring Security 安全配置
│   │   └── WebConfig.java          # Web MVC 配置（CORS、拦截器、静态资源）
│   ├── controller/             # 控制器层
│   │   ├── UserController.java         # 用户控制器
│   │   ├── ProductController.java      # 商品控制器
│   │   ├── CategoryController.java     # 分类控制器
│   │   ├── CartController.java         # 购物车控制器
│   │   ├── OrderController.java        # 订单控制器
│   │   ├── ReviewController.java       # 评价控制器
│   │   └── UploadController.java       # 文件上传控制器
│   ├── entity/                 # 实体类
│   │   ├── User.java               # 用户
│   │   ├── Product.java            # 商品
│   │   ├── Order.java              # 订单
│   │   ├── CartItem.java           # 购物车项
│   │   ├── Category.java           # 分类
│   │   ├── Review.java             # 评价
│   │   ├── Favorite.java           # 收藏
│   │   └── ProductImage.java       # 商品图片
│   ├── exception/              # 异常处理
│   │   └── GlobalExceptionHandler.java # 全局异常处理器
│   ├── interceptor/            # 拦截器
│   │   └── JwtInterceptor.java     # JWT 认证拦截器
│   ├── mapper/                 # MyBatis Mapper 接口
│   ├── service/                # 业务逻辑层
│   │   ├── impl/                   # Service 实现类
│   │   └── *.java                  # Service 接口
│   ├── util/                   # 工具类
│   │   └── JwtUtil.java            # JWT 工具类
│   └── CampusSecondhandApiApplication.java  # 启动类
├── src/main/resources/
│   ├── mapper/                 # MyBatis XML 映射文件
│   ├── application.yml         # 应用配置文件
│   └── static/                 # 静态资源
├── uploads/                    # 上传文件存储目录
│   ├── avatar/                     # 用户头像
│   └── product/                    # 商品图片
├── sql/                        # 数据库脚本
│   └── database.sql                # 建库建表及初始数据 SQL
└── pom.xml                     # Maven 配置文件
```

---

## 环境要求

- **JDK** 17+
- **MySQL** 8.0+
- **Maven** 3.6+

---

## 快速启动

### 1. 初始化数据库

项目根目录 `sql/database.sql` 已包含完整的建库、建表及初始数据 SQL 脚本，直接执行即可

### 2. 修改数据库配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_market?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root        # 修改为你的数据库用户名
    password: 123456      # 修改为你的数据库密码
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

服务启动后访问：`http://localhost:8080`

---

## 数据库设计

### ER 关系图

```
user (用户)
  ├──< product (商品)       [seller_id]
  ├──< cart (购物车)        [user_id]
  ├──< order (订单-买家)    [buyer_id]
  ├──< order (订单-卖家)    [seller_id]
  ├──< review (评价)        [user_id]
  ├──< favorite (收藏)      [user_id]
  └──< address (收货地址)   [user_id]

product (商品)
  ├──< cart (购物车)        [product_id]
  ├──< order (订单)         [product_id]
  ├──< review (评价)        [product_id]
  └──< favorite (收藏)      [product_id]

category (分类)
  └──< product (商品)       [category]
```

### 数据表说明

| 表名 | 说明 | 核心字段 |
|------|------|---------|
| `user` | 用户表 | username, password, name, avatar, phone, rating |
| `product` | 商品表 | name, price, original_price, category, image, condition, stock |
| `category` | 商品分类表 | id, name, icon, sort |
| `cart` | 购物车表 | user_id, product_id, quantity |
| `order` | 订单表 | product_id, buyer_id, seller_id, status, price, quantity |
| `review` | 商品评价表 | product_id, user_id, rating, content |
| `favorite` | 收藏表 | user_id, product_id |
| `banner` | 轮播图表 | image, title, subtitle, link, sort, status |
| `address` | 收货地址表 | user_id, consignee, phone, detail, is_default |

> 完整建表语句及初始数据见 `sql/database.sql`

---

## API 接口概览

> 完整的接口文档请参阅 [Api.md](API.md)

**基础路径**: `http://localhost:8080`

| 模块 | 路径前缀 | 说明 | 认证 |
|------|----------|------|------|
| 用户模块 | `/user` | 登录、注册、信息管理、收藏、我的订单/商品 | 登录/注册公开，其余需登录 |
| 商品模块 | `/products` | 商品列表、详情、搜索、发布、编辑、删除 | 查询公开，增删改需登录 |
| 分类模块 | `/categories` | 分类列表、分类详情 | 公开 |
| 购物车模块 | `/cart` | 查看、添加、更新、移除、清空购物车 | 需登录 |
| 订单模块 | `/orders` | 创建、查看、支付、确认收货、取消订单 | 需登录 |
| 评价模块 | `/products/{id}/reviews` | 商品评价列表、发表评价 | 查看公开，发表需登录 |
| 文件上传 | `/upload` | 图片上传（商品图/头像） | 公开 |

---

## 统一响应格式

所有接口均返回统一的 JSON 格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| code | int | 状态码：`200` 成功，`500` 服务器错误，`401` 未授权 |
| message | String | 提示信息 |
| data | T | 响应数据（可为对象、数组或 null） |

**错误响应示例：**

```json
{
  "code": 500,
  "message": "商品名称不能为空",
  "data": null
}
```

---

## 认证机制

本项目采用 **JWT（JSON Web Token）** 进行身份认证。

### 认证流程

1. 用户通过 `/user/login` 或 `/user/register` 获取 JWT Token
2. 后续请求在 HTTP Header 中携带 Token：
   ```
   Authorization: Bearer <your_token>
   ```
3. 服务端通过 `JwtInterceptor` 拦截器验证 Token 有效性
4. 验证通过后将 `userId` 注入请求上下文，供 Controller 使用

### Token 配置

| 配置项 | 值 | 说明 |
|--------|------|------|
| 密钥 | 配置于 `application.yml` | `jwt.secret` |
| 有效期 | 86400000ms（24小时） | `jwt.expiration` |

### 公开接口（无需 Token）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/user/login` | 用户登录 |
| POST | `/user/register` | 用户注册 |
| GET | `/products` | 商品列表 |
| GET | `/products/{id}` | 商品详情 |
| GET | `/products/hot` | 热门商品 |
| GET | `/products/new` | 最新商品 |
| GET | `/products/search` | 商品搜索 |
| GET | `/products/category/{id}` | 分类商品 |
| GET | `/categories` | 分类列表 |
| GET | `/categories/{id}` | 分类详情 |
| GET | `/products/{id}/reviews` | 商品评价 |
| POST | `/upload/image` | 图片上传 |
| GET | `/uploads/**` | 上传文件访问 |

其余接口均需在请求头中携带有效的 JWT Token。完整接口详情请参阅 [Api.md](API.md)。
