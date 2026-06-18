# 校园二手交易平台 - API 接口文档

> **基础路径**: `http://localhost:8080`
>
> 需要认证的接口请在请求头中携带：`Authorization: Bearer <token>`

---

## 目录

- [1. 用户模块](#1-用户模块-user)
- [2. 商品模块](#2-商品模块-products)
- [3. 分类模块](#3-分类模块-categories)
- [4. 购物车模块](#4-购物车模块-cart)
- [5. 订单模块](#5-订单模块-orders)
- [6. 评价模块](#6-评价模块-productsproductidreviews)
- [7. 文件上传模块](#7-文件上传模块-upload)
- [统一响应格式](#统一响应格式)
- [认证机制](#认证机制)
- [数据模型](#数据模型)

---

## 1. 用户模块 `/user`

### 1.1 用户登录（公开）

```
POST /user/login
Content-Type: application/json
```

**请求体：**

```json
{
  "username": "string",
  "password": "string"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | **是** | 用户名 |
| password | String | **是** | 密码（明文） |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "user": {
      "id": 1,
      "username": "test",
      "name": "测试用户",
      "avatar": "http://localhost:8080/uploads/avatar/xxx.png",
      "phone": "13800138000",
      "email": "test@example.com",
      "address": null,
      "rating": 5.0,
      "soldCount": 0,
      "createdAt": "2024-01-01T00:00:00",
      "updatedAt": "2024-01-01T00:00:00"
    }
  }
}
```

**错误响应：**

```json
{
  "code": 500,
  "message": "用户名或密码错误",
  "data": null
}
```

---

### 1.2 用户注册（公开）

```
POST /user/register
Content-Type: application/json
```

**请求体：**

```json
{
  "username": "string",
  "password": "string",
  "name": "string",
  "phone": "string",
  "email": "string"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | **是** | 用户名 |
| password | String | **是** | 密码 |
| name | String | 否 | 昵称 |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |

**成功响应：** 同登录接口，返回 `token` 和 `user` 对象。

---

### 1.3 获取当前用户信息 🔒

```
GET /user/info
Authorization: Bearer <token>
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "test",
    "name": "测试用户",
    "avatar": "http://localhost:8080/uploads/avatar/xxx.png",
    "phone": "13800138000",
    "email": "test@example.com",
    "address": null,
    "rating": 5.0,
    "soldCount": 0,
    "createdAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00"
  }
}
```

---

### 1.4 更新用户信息 🔒

```
PUT /user/info
Authorization: Bearer <token>
Content-Type: application/json
```

**请求体（仅传入需要更新的字段）：**

```json
{
  "name": "新昵称",
  "avatar": "http://localhost:8080/uploads/avatar/xxx.png",
  "phone": "13900139000",
  "email": "new@example.com",
  "address": "XX大学X号宿舍楼XXX"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 昵称 |
| avatar | String | 否 | 头像URL |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |
| address | String | 否 | 地址 |

**成功响应：** 返回更新后的 `User` 对象。

---

### 1.5 获取我发布的商品 🔒

```
GET /user/products
Authorization: Bearer <token>
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "iPhone 15 Pro",
      "price": 5999.00,
      "originalPrice": 8999.00,
      "category": "digital",
      "image": "http://localhost:8080/uploads/product/xxx.png",
      "images": "[\"url1\",\"url2\"]",
      "description": "95新，无划痕",
      "condition": "95新",
      "publishTime": "2024-01-01T00:00:00",
      "isHot": false,
      "isNew": true,
      "stock": 1,
      "status": "on_sale",
      "sellerId": 1,
      "viewCount": 100,
      "createdAt": "2024-01-01T00:00:00",
      "updatedAt": "2024-01-01T00:00:00",
      "seller": null
    }
  ]
}
```

---

### 1.6 获取我的收藏列表 🔒

```
GET /user/favorites
Authorization: Bearer <token>
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "productId": 2,
      "createTime": "2024-01-01T00:00:00"
    }
  ]
}
```

---

### 1.7 添加收藏 🔒

```
POST /user/favorites/{productId}
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

### 1.8 取消收藏 🔒

```
DELETE /user/favorites/{productId}
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

### 1.9 获取我的订单 🔒

```
GET /user/orders?status=pending
Authorization: Bearer <token>
```

| 查询参数 | 类型 | 必填 | 说明 |
|----------|------|------|------|
| status | String | 否 | 订单状态筛选，可选值：`pending`、`shipped`、`completed`、`cancelled` |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": "ORD20240101000001",
      "productId": 1,
      "productName": "iPhone 15 Pro",
      "productImage": "http://localhost:8080/uploads/product/xxx.png",
      "price": 5999.00,
      "quantity": 1,
      "status": "pending",
      "buyerId": 1,
      "sellerId": 2,
      "address": "XX大学X号宿舍楼XXX",
      "reviewed": false,
      "createTime": "2024-01-01T00:00:00",
      "updateTime": "2024-01-01T00:00:00"
    }
  ]
}
```

---

## 2. 商品模块 `/products`

### 2.1 获取商品列表（公开）

```
GET /products
```

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| keyword | String | 否 | - | 搜索关键词 |
| category | String | 否 | - | 分类筛选 |
| sortBy | String | 否 | `default` | 排序方式 |
| page | int | 否 | `1` | 页码 |
| pageSize | Integer | 否 | - | 每页数量，不传返回全部 |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": 1,
        "name": "iPhone 15 Pro",
        "price": 5999.00,
        "originalPrice": 8999.00,
        "category": "digital",
        "image": "http://localhost:8080/uploads/product/xxx.png",
        "images": "[\"url1\",\"url2\"]",
        "description": "95新，无划痕",
        "condition": "95新",
        "publishTime": "2024-01-01T00:00:00",
        "isHot": false,
        "isNew": true,
        "stock": 1,
        "status": "on_sale",
        "sellerId": 1,
        "viewCount": 100,
        "createdAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00",
        "seller": {
          "id": 1,
          "username": "seller1",
          "name": "卖家昵称",
          "avatar": "http://localhost:8080/uploads/avatar/xxx.png",
          "rating": 4.8
        }
      }
    ],
    "total": 100,
    "page": 1,
    "pageSize": 10
  }
}
```

---

### 2.2 获取商品详情（公开）

```
GET /products/{id}
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | Long | 商品ID |

**成功响应：** 返回单个 `Product` 对象（含关联的 `seller` 信息）。

---

### 2.3 获取热门商品（公开）

```
GET /products/hot
```

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| page | int | 否 | `1` | 页码 |
| pageSize | Integer | 否 | - | 每页数量，不传返回全部 |

**成功响应：** 同商品列表，返回 `PageResult<Product>`。

---

### 2.4 获取最新商品（公开）

```
GET /products/new
```

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| page | int | 否 | `1` | 页码 |
| pageSize | Integer | 否 | - | 每页数量，不传返回全部 |

**成功响应：** 同商品列表，返回 `PageResult<Product>`。

---

### 2.5 按分类获取商品（公开）

```
GET /products/category/{categoryId}
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| categoryId | String | 分类ID |

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| keyword | String | 否 | - | 搜索关键词 |
| sortBy | String | 否 | `default` | 排序方式 |
| page | int | 否 | `1` | 页码 |
| pageSize | Integer | 否 | - | 每页数量，不传返回全部 |

**成功响应：** 同商品列表，返回 `PageResult<Product>`。

---

### 2.6 搜索商品（公开）

```
GET /products/search
```

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| keyword | String | **是** | - | 搜索关键词 |
| category | String | 否 | - | 分类筛选 |
| sortBy | String | 否 | `default` | 排序方式 |
| page | int | 否 | `1` | 页码 |
| pageSize | Integer | 否 | - | 每页数量，不传返回全部 |

**成功响应：** 同商品列表，返回 `PageResult<Product>`。

---

### 2.7 发布商品 🔒

```
POST /products
Authorization: Bearer <token>
Content-Type: application/json
```

**请求体：**

```json
{
  "name": "iPhone 15 Pro",
  "price": 5999.00,
  "originalPrice": 8999.00,
  "category": "digital",
  "image": "http://localhost:8080/uploads/product/xxx.png",
  "images": ["url1", "url2"],
  "description": "95新，无划痕",
  "condition": "95新",
  "stock": 1
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | **是** | 商品名称 |
| price | Number | **是** | 售价 |
| originalPrice | Number | 否 | 原价 |
| category | String | 否 | 分类ID |
| image | String | 否 | 封面图URL |
| images | Array | 否 | 图片URL数组 |
| description | String | 否 | 商品描述 |
| condition | String | 否 | 成色（如"95新"） |
| stock | int | 否 | 库存数量 |

**成功响应：** 返回创建的 `Product` 对象。

**错误响应：**

```json
{
  "code": 500,
  "message": "商品名称不能为空",
  "data": null
}
```

---

### 2.8 编辑商品 🔒

```
PUT /products/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | Long | 商品ID |

**请求体（仅传入需要更新的字段）：**

```json
{
  "name": "iPhone 15 Pro Max",
  "price": 6999.00,
  "originalPrice": 9999.00,
  "category": "digital",
  "image": "http://localhost:8080/uploads/product/xxx.png",
  "images": ["url1", "url2", "url3"],
  "description": "99新，无任何使用痕迹",
  "condition": "99新",
  "stock": 2,
  "status": "on_sale",
  "isHot": true,
  "isNew": false
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 否 | 商品名称 |
| price | Number | 否 | 售价 |
| originalPrice | Number | 否 | 原价 |
| category | String | 否 | 分类ID |
| image | String | 否 | 封面图URL |
| images | Array | 否 | 图片URL数组 |
| description | String | 否 | 商品描述 |
| condition | String | 否 | 成色 |
| stock | int | 否 | 库存数量 |
| status | String | 否 | 商品状态 |
| isHot | Boolean | 否 | 是否热门 |
| isNew | Boolean | 否 | 是否新品 |

**成功响应：** 返回更新后的 `Product` 对象。

---

### 2.9 删除商品 🔒

```
DELETE /products/{id}
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | Long | 商品ID |

> 仅卖家本人可删除自己的商品。

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

## 3. 分类模块 `/categories`

### 3.1 获取所有分类（公开）

```
GET /categories
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": "digital",
      "name": "数码产品",
      "icon": "http://localhost:8080/uploads/category/digital.png",
      "sort": 1,
      "createdAt": "2024-01-01T00:00:00"
    },
    {
      "id": "book",
      "name": "图书教材",
      "icon": "http://localhost:8080/uploads/category/book.png",
      "sort": 2,
      "createdAt": "2024-01-01T00:00:00"
    }
  ]
}
```

---

### 3.2 获取单个分类（公开）

```
GET /categories/{id}
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | String | 分类ID |

**成功响应：** 返回单个 `Category` 对象。

---

## 4. 购物车模块 `/cart`

> **所有接口均需登录** 🔒

### 4.1 查看购物车

```
GET /cart
Authorization: Bearer <token>
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "userId": 1,
      "productId": 2,
      "quantity": 1,
      "createTime": "2024-01-01T00:00:00"
    }
  ]
}
```

---

### 4.2 添加商品到购物车

```
POST /cart
Authorization: Bearer <token>
Content-Type: application/json
```

**请求体：**

```json
{
  "productId": 1,
  "quantity": 1
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | **是** | 商品ID |
| quantity | Integer | **是** | 数量 |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

### 4.3 更新购物车商品数量

```
PUT /cart/{productId}
Authorization: Bearer <token>
Content-Type: application/json
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

**请求体：**

```json
{
  "quantity": 2
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| quantity | Integer | **是** | 新数量 |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

### 4.4 移除购物车商品

```
DELETE /cart/{productId}
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

### 4.5 清空购物车

```
DELETE /cart
Authorization: Bearer <token>
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

## 5. 订单模块 `/orders`

> **所有接口均需登录** 🔒
>
> **订单状态流转：** `pending`（待付款）→ `shipped`（待收货）→ `completed`（已完成）/ `cancelled`（已取消）

### 5.1 获取订单列表

```
GET /orders
Authorization: Bearer <token>
```

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| status | String | 否 | `all` | 状态筛选：`all`、`pending`、`shipped`、`completed`、`cancelled` |
| page | int | 否 | `1` | 页码 |
| pageSize | int | 否 | `10` | 每页数量 |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": "ORD20240101000001",
        "productId": 1,
        "productName": "iPhone 15 Pro",
        "productImage": "http://localhost:8080/uploads/product/xxx.png",
        "price": 5999.00,
        "quantity": 1,
        "status": "pending",
        "buyerId": 1,
        "sellerId": 2,
        "address": "XX大学X号宿舍楼XXX",
        "reviewed": false,
        "createTime": "2024-01-01T00:00:00",
        "updateTime": "2024-01-01T00:00:00"
      }
    ],
    "total": 50,
    "page": 1,
    "pageSize": 10
  }
}
```

---

### 5.2 获取订单详情

```
GET /orders/{id}
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | String | 订单ID |

**成功响应：** 返回单个 `Order` 对象。

---

### 5.3 创建订单

```
POST /orders
Authorization: Bearer <token>
Content-Type: application/json
```

**请求体：**

```json
{
  "productId": 1,
  "quantity": 1,
  "address": "XX大学X号宿舍楼XXX"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| productId | Long | **是** | 商品ID |
| quantity | Integer | **是** | 购买数量 |
| address | String | 否 | 收货地址 |

**成功响应：** 返回创建的 `Order` 对象。

---

### 5.4 支付订单

```
POST /orders/{id}/pay
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | String | 订单ID |

> 将订单状态从 `pending` 更新为 `shipped`。

**成功响应：** 返回更新后的 `Order` 对象。

---

### 5.5 确认收货

```
POST /orders/{id}/confirm
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | String | 订单ID |

> 将订单状态从 `shipped` 更新为 `completed`。

**成功响应：** 返回更新后的 `Order` 对象。

---

### 5.6 取消订单

```
POST /orders/{id}/cancel
Authorization: Bearer <token>
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| id | String | 订单ID |

> 仅 `pending` 状态的订单可取消。

**成功响应：** 返回更新后的 `Order` 对象。

---

## 6. 评价模块 `/products/{productId}/reviews`

### 6.1 获取商品评价列表（公开）

```
GET /products/{productId}/reviews
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

| 查询参数 | 类型 | 必填 | 默认值 | 说明 |
|----------|------|------|--------|------|
| page | int | 否 | `1` | 页码 |
| pageSize | int | 否 | `10` | 每页数量 |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": 1,
        "productId": 1,
        "userId": 2,
        "rating": 5,
        "content": "商品很好，卖家态度也不错",
        "time": "2024-01-01T00:00:00"
      }
    ],
    "total": 20,
    "page": 1,
    "pageSize": 10
  }
}
```

---

### 6.2 发表评价 🔒

```
POST /products/{productId}/reviews
Authorization: Bearer <token>
Content-Type: application/json
```

| 路径参数 | 类型 | 说明 |
|----------|------|------|
| productId | Long | 商品ID |

**请求体：**

```json
{
  "rating": 5,
  "content": "商品很好，卖家态度也不错"
}
```

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| rating | Integer | **是** | 评分（1-5） |
| content | String | 否 | 评价内容 |

> 每个用户对同一商品只能评价一次。

**成功响应：** 返回创建的 `Review` 对象。

---

## 7. 文件上传模块 `/upload`

### 7.1 上传图片

```
POST /upload/image
Content-Type: multipart/form-data
```

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | **是** | 图片文件（jpg/jpeg/png/webp/gif，≤5MB） |
| type | String | 否 | 类型：`product`（商品图，默认）/ `avatar`（头像） |

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "url": "http://localhost:8080/uploads/product/1781169217669-xxx.png",
    "type": "product"
  }
}
```

**错误响应：**

```json
{
  "code": 500,
  "message": "仅支持 jpg、png、webp、gif 格式",
  "data": null
}
```

**限制说明：**

- 支持格式：jpg、jpeg、png、webp、gif
- 文件大小限制：5MB
- 文件必须为图片类型（Content-Type 以 `image/` 开头）

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

**分页响应格式：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [],
    "total": 100,
    "page": 1,
    "pageSize": 10
  }
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| list | Array | 数据列表 |
| total | long | 总记录数 |
| page | int | 当前页码 |
| pageSize | int | 每页数量 |

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

其余接口均需在请求头中携带有效的 JWT Token。

---

## 数据模型

### User（用户）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 用户ID |
| username | String | 用户名 |
| password | String | 密码（BCrypt加密） |
| name | String | 昵称 |
| avatar | String | 头像URL |
| phone | String | 手机号 |
| email | String | 邮箱 |
| address | String | 地址 |
| rating | Double | 卖家评分 |
| soldCount | Integer | 已售数量 |
| createdAt | LocalDateTime | 创建时间 |
| updatedAt | LocalDateTime | 更新时间 |

### Product（商品）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 商品ID |
| name | String | 商品名称 |
| price | BigDecimal | 售价 |
| originalPrice | BigDecimal | 原价 |
| category | String | 分类ID |
| image | String | 封面图URL |
| images | String | 图片URL数组（JSON字符串） |
| description | String | 商品描述 |
| condition | String | 成色 |
| publishTime | LocalDateTime | 发布时间 |
| isHot | Boolean | 是否热门 |
| isNew | Boolean | 是否新品 |
| stock | Integer | 库存 |
| status | String | 状态 |
| sellerId | Long | 卖家ID |
| viewCount | Integer | 浏览量 |
| createdAt | LocalDateTime | 创建时间 |
| updatedAt | LocalDateTime | 更新时间 |
| seller | User | 关联卖家信息 |

### Order（订单）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | 订单ID |
| productId | Long | 商品ID |
| productName | String | 商品名称 |
| productImage | String | 商品图片 |
| price | BigDecimal | 成交价格 |
| quantity | Integer | 数量 |
| status | String | 状态：pending/shipped/completed/cancelled |
| buyerId | Long | 买家ID |
| sellerId | Long | 卖家ID |
| address | String | 收货地址 |
| reviewed | Boolean | 是否已评价 |
| createTime | LocalDateTime | 创建时间 |
| updateTime | LocalDateTime | 更新时间 |

### CartItem（购物车项）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 购物车项ID |
| userId | Long | 用户ID |
| productId | Long | 商品ID |
| quantity | Integer | 数量 |
| createTime | LocalDateTime | 创建时间 |

### Review（评价）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 评价ID |
| productId | Long | 商品ID |
| userId | Long | 用户ID |
| rating | Integer | 评分（1-5） |
| content | String | 评价内容 |
| time | LocalDateTime | 评价时间 |

### Category（分类）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | 分类ID |
| name | String | 分类名称 |
| icon | String | 图标URL |
| sort | Integer | 排序权重 |
| createdAt | LocalDateTime | 创建时间 |

### Favorite（收藏）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 收藏ID |
| userId | Long | 用户ID |
| productId | Long | 商品ID |
| createTime | LocalDateTime | 收藏时间 |
