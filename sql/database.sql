-- 校园二手交易平台 数据库初始化脚本
-- 数据库名: campus_market

CREATE DATABASE IF NOT EXISTS campus_market DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_market;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名/学号',
  `password` VARCHAR(255) NOT NULL COMMENT '密码(加密存储)',
  `name` VARCHAR(50) NOT NULL COMMENT '昵称',
  `avatar` VARCHAR(500) DEFAULT '' COMMENT '头像URL',
  `phone` VARCHAR(20) DEFAULT '' COMMENT '手机号',
  `email` VARCHAR(100) DEFAULT '' COMMENT '邮箱',
  `address` VARCHAR(255) DEFAULT '' COMMENT '默认收货地址',
  `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
  `sold_count` INT DEFAULT 0 COMMENT '已售数量',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入用户数据
INSERT INTO `user` (`username`, `password`, `name`, `avatar`, `phone`, `email`, `address`, `rating`, `sold_count`) VALUES
('admin', '$2a$10$VzaASfnoiizUIip7wagM6OWekdJWqR.qbnuwRuZI1VnIyIhf.ddN6', '家豪', 'http://localhost:8080/uploads/avatar/1781168491436-5d08975128cf4501aa551c79f0966265.png', '13800138001', 'jiahao@admin.com', '清北大学4号宿舍楼101室', 4.85, 15),
('20210002', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '李四', 'https://api.dicebear.com/7.x/avataaars/svg?seed=li', '13800138002', 'lisi@school.edu', '北京理工大学2号宿舍楼305', 4.92, 28),
('20210003', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '王五', 'https://api.dicebear.com/7.x/avataaars/svg?seed=wang', '13800138003', 'wangwu@school.edu', '北京理工大学3号宿舍楼108', 4.78, 12),
('20210004', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '赵六', 'https://api.dicebear.com/7.x/avataaars/svg?seed=zhao', '13800138004', 'zhaoliu@school.edu', '北京理工大学4号宿舍楼402', 4.65, 8),
('20210005', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '钱七', 'https://api.dicebear.com/7.x/avataaars/svg?seed=qian', '13800138005', 'qianqi@school.edu', '北京理工大学5号宿舍楼501', 4.95, 42),
('20210006', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '孙八', 'https://api.dicebear.com/7.x/avataaars/svg?seed=sun', '13800138006', 'sunba@school.edu', '北京理工大学6号宿舍楼618', 4.88, 19),
('20210007', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '周九', 'https://api.dicebear.com/7.x/avataaars/svg?seed=zhou', '13800138007', 'zhoujiu@school.edu', '北京理工大学7号宿舍楼712', 4.72, 6),
('20210008', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '吴十', 'https://api.dicebear.com/7.x/avataaars/svg?seed=wu', '13800138008', 'wushi@school.edu', '北京理工大学8号宿舍楼820', 4.81, 23),
('20210009', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '郑十一', 'https://api.dicebear.com/7.x/avataaars/svg?seed=zheng', '13800138009', 'zhengshiyi@school.edu', '北京理工大学9号宿舍楼901', 4.90, 31),
('20210010', '$2a$10$xJwVxKhC8GjLvT1KjYH.Oe1Y6Z0XqvQZ9X5K3mN2pR5sH8vU7wXy', '冯十二', 'https://api.dicebear.com/7.x/avataaars/svg?seed=feng', '13800138010', 'fengshier@school.edu', '北京理工大学10号宿舍楼1001', 4.68, 11);

-- ----------------------------
-- 2. 分类表
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` VARCHAR(50) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `icon` VARCHAR(50) DEFAULT '' COMMENT 'Element Plus图标名',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

INSERT INTO `category` (`id`, `name`, `icon`, `sort`) VALUES
('digital', '数码', 'Monitor', 1),
('books', '书籍', 'Reading', 2),
('clothing', '服饰', 'Shop', 3),
('daily', '生活用品', 'ShoppingBag', 4);

-- ----------------------------
-- 3. 商品表
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL COMMENT '商品名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT '售价',
  `original_price` DECIMAL(10,2) DEFAULT 0 COMMENT '原价',
  `category` VARCHAR(50) NOT NULL COMMENT '分类ID',
  `image` VARCHAR(500) NOT NULL COMMENT '封面图',
  `images` TEXT COMMENT '详情图(JSON数组)',
  `description` TEXT COMMENT '商品描述',
  `condition` VARCHAR(20) NOT NULL COMMENT '成色',
  `publish_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `is_hot` TINYINT(1) DEFAULT 0 COMMENT '是否热门',
  `is_new` TINYINT(1) DEFAULT 0 COMMENT '是否最新',
  `stock` INT DEFAULT 1 COMMENT '库存',
  `status` VARCHAR(20) DEFAULT 'active' COMMENT '状态:active在售/inactive已下架/sold已售出',
  `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`category`) REFERENCES `category`(`id`),
  FOREIGN KEY (`seller_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 插入商品数据
INSERT INTO `product` (`name`, `price`, `original_price`, `category`, `image`, `images`, `description`, `condition`, `publish_time`, `is_hot`, `is_new`, `stock`, `status`, `seller_id`, `view_count`) VALUES
-- 数码类
('iPhone 14 Pro Max 256G 暗紫色', 5999.00, 8999.00, 'digital', 'https://picsum.photos/400/400?random=1', '["https://picsum.photos/800/800?random=11","https://picsum.photos/800/800?random=12"]', '99新无划痕，电池健康度98%，原装充电线，全套包装盒都在。因为换了新机所以出掉。', '99新', '2024-03-15 10:30:00', 1, 0, 1, 'active', 1, 1256),
('小米13 Pro 12+256G 陶瓷白', 3299.00, 4999.00, 'digital', 'https://picsum.photos/400/400?random=2', '["https://picsum.photos/800/800?random=13","https://picsum.photos/800/800?random=14"]', '95新，轻微使用痕迹，屏幕贴膜保护，无维修记录。', '95新', '2024-03-18 14:20:00', 1, 1, 1, 'active', 2, 892),
('华为Mate 60 Pro 512G 雅川青', 6499.00, 7999.00, 'digital', 'https://picsum.photos/400/400?random=3', '["https://picsum.photos/800/800?random=15"]', '全新未拆封未激活，支持华为官方保修。国行正品。', '全新', '2024-03-20 09:00:00', 1, 1, 2, 'active', 3, 2134),
('AirPods Pro 2代 USB-C', 1399.00, 1899.00, 'digital', 'https://picsum.photos/400/400?random=4', '["https://picsum.photos/800/800?random=16"]', '9成新，使用三个月，功能正常，配件齐全。', '95新', '2024-03-12 16:45:00', 0, 0, 1, 'active', 4, 567),
('MacBook Air M2 13寸 8+256G', 5899.00, 7999.00, 'digital', 'https://picsum.photos/400/400?random=5', '["https://picsum.photos/800/800?random=17","https://picsum.photos/800/800?random=18"]', '98新，学生自用，电池循环次数仅23次，无任何磕碰划痕。', '98新', '2024-03-19 11:30:00', 1, 0, 1, 'active', 5, 1567),
('iPad Pro 11寸 2022款 128G', 4599.00, 6199.00, 'digital', 'https://picsum.photos/400/400?random=6', '["https://picsum.photos/800/800?random=19"]', '99新，带Apple Pencil二代笔和妙控键盘。', '99新', '2024-03-17 15:20:00', 0, 1, 1, 'active', 1, 723),
('索尼WH-1000XM4 头戴式耳机', 1299.00, 2199.00, 'digital', 'https://picsum.photos/400/400?random=7', '["https://picsum.photos/800/800?random=20"]', '95新，降噪效果极佳，包耳式设计，续航30小时。', '95新', '2024-03-14 13:10:00', 0, 0, 1, 'active', 6, 432),
('Switch OLED 日版', 1899.00, 2599.00, 'digital', 'https://picsum.photos/400/400?random=8', '["https://picsum.photos/800/800?random=21","https://picsum.photos/800/800?random=22"]', '9成新，主机+原配底座+充电器+游戏卡2张（塞尔达+马里奥）。', '95新', '2024-03-16 10:00:00', 1, 0, 1, 'active', 2, 1089),
('佳能EOS R50 微单相机', 4599.00, 5999.00, 'digital', 'https://picsum.photos/400/400?random=9', '["https://picsum.photos/800/800?random=23"]', '全新未拆封，国行正品，可开正规发票。', '全新', '2024-03-21 08:30:00', 0, 1, 1, 'active', 7, 678),
('罗技G502 Hero 游戏鼠标', 199.00, 399.00, 'digital', 'https://picsum.photos/400/400?random=10', '["https://picsum.photos/800/800?random=24"]', '8成新，用了一学期，功能全部正常，可自定义按键。', '良好', '2024-03-10 17:20:00', 0, 0, 1, 'active', 8, 234),

-- 书籍类
('高等数学第七版 上册+下册', 35.00, 79.00, 'books', 'https://picsum.photos/400/400?random=30', '["https://picsum.photos/800/800?random=31"]', '教材七成新，少量笔记，不影响阅读。第一章有轻微折痕。', '良好', '2024-03-15 09:00:00', 1, 0, 3, 'active', 1, 567),
('线性代数同济第六版', 22.00, 45.00, 'books', 'https://picsum.photos/400/400?random=31', '["https://picsum.photos/800/800?random=32"]', '9成新，几乎没有笔记，字迹清晰。', '95新', '2024-03-18 10:30:00', 0, 1, 2, 'active', 3, 345),
('概率论与数理统计浙大第四版', 25.00, 49.00, 'books', 'https://picsum.photos/400/400?random=32', '["https://picsum.photos/800/800?random=33"]', '8成新，有少量课后题标记。重点章节有荧光笔标注。', '良好', '2024-03-12 14:00:00', 0, 0, 1, 'active', 4, 289),
('数据结构C语言版 严蔚敏', 28.00, 55.00, 'books', 'https://picsum.photos/400/400?random=33', '["https://picsum.photos/800/800?random=34"]', '9成新，光盘还在。代码和数据结构图都用铅笔标注过。', '95新', '2024-03-19 11:00:00', 1, 0, 1, 'active', 5, 456),
('算法导论第三版', 89.00, 139.00, 'books', 'https://picsum.photos/400/400?random=34', '["https://picsum.photos/800/800?random=35"]', '9成新，经典算法书籍，研究生阶段购买，现已用不到。', '95新', '2024-03-20 15:30:00', 0, 1, 1, 'active', 6, 234),
('考研数学复习全书 数学一', 55.00, 119.00, 'books', 'https://picsum.photos/400/400?random=35', '["https://picsum.photos/800/800?random=36"]', '9成新，因跨专业考研不需要了。几乎没做过。', '99新', '2024-03-16 09:30:00', 0, 0, 1, 'active', 7, 178),
('英语六级真题卷 2019-2023', 18.00, 49.00, 'books', 'https://picsum.photos/400/400?random=36', '["https://picsum.photos/800/800?random=37"]', '9成新，只做了两套，后面懒得做了。答案解析都在。', '99新', '2024-03-14 16:00:00', 0, 0, 1, 'active', 8, 123),
('微观经济学曼昆第九版', 42.00, 89.00, 'books', 'https://picsum.photos/400/400?random=37', '["https://picsum.photos/800/800?random=38"]', '8成新，经济学专业教材，有少量划线。', '良好', '2024-03-17 13:45:00', 0, 1, 1, 'active', 9, 234),
('Python编程从入门到实践', 38.00, 89.00, 'books', 'https://picsum.photos/400/400?random=38', '["https://picsum.photos/800/800?random=39"]', '9成新，适合零基础学习Python，书中代码已跑通。', '95新', '2024-03-21 10:00:00', 1, 0, 1, 'active', 2, 567),
('Gone with the Wind 英文原版', 45.00, 120.00, 'books', 'https://picsum.photos/400/400?random=39', '["https://picsum.photos/800/800?random=40"]', '8成新，进口英文原版书，纸张质量很好。', '良好', '2024-03-13 11:20:00', 0, 0, 1, 'active', 10, 89),

-- 服饰类
('优衣库男士纯棉T恤 XL码 黑色', 29.00, 79.00, 'clothing', 'https://picsum.photos/400/400?random=50', '["https://picsum.photos/800/800?random=51"]', '9成新，买了没怎么穿过，吊牌还在。尺码不合适转让。', '99新', '2024-03-15 14:30:00', 0, 0, 2, 'active', 1, 234),
('阿迪达斯三叶草运动裤 M码', 89.00, 299.00, 'clothing', 'https://picsum.photos/400/400?random=51', '["https://picsum.photos/800/800?random=52"]', '95新，体育课穿过几次，面料透气舒适。', '95新', '2024-03-18 09:15:00', 0, 1, 1, 'active', 2, 167),
('耐克Air Max 270 跑步鞋 42码', 359.00, 899.00, 'clothing', 'https://picsum.photos/400/400?random=52', '["https://picsum.photos/800/800?random=53"]', '8成新，跑了一个学期的体育课，鞋底略有磨损。', '良好', '2024-03-12 16:00:00', 0, 0, 1, 'active', 3, 345),
('ZARA女士开衫 M码 米白色', 79.00, 299.00, 'clothing', 'https://picsum.photos/400/400?random=53', '["https://picsum.photos/800/800?random=54"]', '9成新，春夏款开衫，只穿过两次。', '99新', '2024-03-19 11:45:00', 0, 1, 1, 'active', 4, 189),
('哥伦比亚户外冲锋衣 XL码 黑色', 299.00, 899.00, 'clothing', 'https://picsum.photos/400/400?random=54', '["https://picsum.photos/800/800?random=55"]', '95新，户外社团解散，装备闲置。防水透气三合一。', '95新', '2024-03-16 14:20:00', 0, 0, 1, 'active', 5, 234),
('李宁羽毛球拍 超轻拍', 129.00, 399.00, 'clothing', 'https://picsum.photos/400/400?random=55', '["https://picsum.photos/800/800?random=56"]', '9成新，会打羽毛球的朋友送的，Wilson专业级球拍。', '99新', '2024-03-20 10:30:00', 0, 1, 1, 'active', 6, 123),
('斯凯奇熊猫鞋 38码', 199.00, 599.00, 'clothing', 'https://picsum.photos/400/400?random=56', '["https://picsum.photos/800/800?random=57"]', '8成新，春秋季穿，款式经典百搭。', '良好', '2024-03-14 15:00:00', 0, 0, 1, 'active', 7, 278),
('Champion冠军卫衣 L码 藏青色', 159.00, 499.00, 'clothing', 'https://picsum.photos/400/400?random=57', '["https://picsum.photos/800/800?random=58"]', '9成新，校园代购入手，穿了两次不合适。', '99新', '2024-03-17 12:00:00', 0, 1, 1, 'active', 8, 345),
('北面1996羽绒服 黑色 M码', 699.00, 1899.00, 'clothing', 'https://picsum.photos/400/400?random=58', '["https://picsum.photos/800/800?random=59"]', '95新，冬天考研复习时买的，现在上岸了不需要了。鹅绒填充。', '95新', '2024-03-21 09:00:00', 1, 0, 1, 'active', 9, 456),
('彪马Puma双肩包 黑色', 69.00, 199.00, 'clothing', 'https://picsum.photos/400/400?random=59', '["https://picsum.photos/800/800?random=60"]', '9成新，电脑双肩包，适合15寸笔记本，多隔层设计。', '99新', '2024-03-13 10:15:00', 0, 0, 1, 'active', 10, 189),

-- 生活用品类
('小米台灯1S 智能护眼灯', 79.00, 169.00, 'daily', 'https://picsum.photos/400/400?random=70', '["https://picsum.photos/800/800?random=71"]', '9成新，宿舍用护眼台灯，支持米家APP控制，色温可调。', '99新', '2024-03-15 11:30:00', 0, 0, 1, 'active', 1, 234),
('美的电热水壶 1.7L', 59.00, 139.00, 'daily', 'https://picsum.photos/400/400?random=71', '["https://picsum.photos/800/800?random=72"]', '95新，毕业后出，宿舍必备，烧水快，自动断电。', '95新', '2024-03-18 14:00:00', 0, 1, 1, 'active', 2, 167),
('床上电脑桌 折叠书桌', 45.00, 129.00, 'daily', 'https://picsum.photos/400/400?random=72', '["https://picsum.photos/800/800?random=73"]', '8成新，床上用折叠桌，稳当不晃，升降可调。', '良好', '2024-03-12 09:30:00', 0, 0, 1, 'active', 3, 123),
('小米手环8 NFC版', 169.00, 299.00, 'daily', 'https://picsum.photos/400/400?random=73', '["https://picsum.photos/800/800?random=74"]', '9成新，睡眠监测、心率监测、门禁刷卡一应俱全。', '99新', '2024-03-19 16:20:00', 1, 0, 1, 'active', 4, 456),
('九阳破壁机L12-Ymini', 199.00, 499.00, 'daily', 'https://picsum.photos/400/400?random=74', '["https://picsum.photos/800/800?random=75"]', '9成新，宿舍做豆浆、果汁很方便，清洗也简单。', '99新', '2024-03-16 10:45:00', 0, 1, 1, 'active', 5, 234),
('倍思氮化镓65W充电器', 89.00, 199.00, 'daily', 'https://picsum.photos/400/400?random=75', '["https://picsum.photos/800/800?random=76"]', '95新，小巧便携，笔记本手机平板都能充。', '95新', '2024-03-20 13:00:00', 0, 0, 1, 'active', 6, 178),
('德尔玛加湿器 4.5L', 69.00, 159.00, 'daily', 'https://picsum.photos/400/400?random=76', '["https://picsum.photos/800/800?random=77"]', '9成新，静音设计，大容量水箱，适合宿舍使用。', '99新', '2024-03-14 11:15:00', 0, 0, 1, 'active', 7, 145),
('得力书签笔套装', 12.00, 35.00, 'daily', 'https://picsum.photos/400/400?random=77', '["https://picsum.photos/800/800?random=78"]', '全新未拆封，文具套装，金属材质，精致耐用。', '全新', '2024-03-17 15:30:00', 0, 1, 5, 'active', 8, 89),
('宿舍床上蚊帐 蒙古包式', 35.00, 89.00, 'daily', 'https://picsum.photos/400/400?random=78', '["https://picsum.photos/800/800?random=79"]', '8成新，毕业离校出，安装方便，防蚊效果好。', '良好', '2024-03-13 14:00:00', 0, 0, 1, 'active', 9, 67),
('小米移动电源3 20000mAh', 79.00, 189.00, 'daily', 'https://picsum.photos/400/400?random=79', '["https://picsum.photos/800/800?random=80"]', '95新，18W快充，可以给手机充电多次。', '95新', '2024-03-21 11:30:00', 0, 1, 1, 'active', 10, 234),

-- 更多商品以达到50条
('Apple Watch S9 45mm GPS版', 2899.00, 3999.00, 'digital', 'https://picsum.photos/400/400?random=80', '["https://picsum.photos/800/800?random=81"]', '99新，激活一个月，官方保修到2025年。', '99新', '2024-03-20 10:00:00', 1, 0, 1, 'active', 1, 789),
('机械革命游戏本 RTX4060', 5899.00, 7999.00, 'digital', 'https://picsum.photos/400/400?random=81', '["https://picsum.photos/800/800?random=82"]', '95新，毕业出，游戏性能很强，跑了3年实验室项目。', '95新', '2024-03-19 14:30:00', 1, 0, 1, 'active', 2, 567),
('离散数学第七版', 28.00, 59.00, 'books', 'https://picsum.photos/400/400?random=82', '["https://picsum.photos/800/800?random=83"]', '8成新，有少量习题解答痕迹。', '良好', '2024-03-18 09:00:00', 0, 0, 1, 'active', 3, 156),
('NIKE Jordan卫衣 XL码 红色', 179.00, 599.00, 'clothing', 'https://picsum.photos/400/400?random=83', '["https://picsum.photos/800/800?random=84"]', '9成新，校园篮球赛买的，只穿过两次。', '99新', '2024-03-17 16:00:00', 0, 1, 1, 'active', 4, 234),
('小熊迷你电饭煲 1.2L', 69.00, 169.00, 'daily', 'https://picsum.photos/400/400?random=84', '["https://picsum.photos/800/800?random=85"]', '9成新，适合1-2人使用，煮饭煲汤都能行。', '99新', '2024-03-16 11:30:00', 0, 0, 1, 'active', 5, 123);

-- ----------------------------
-- 4. 订单表
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` VARCHAR(50) PRIMARY KEY,
  `product_id` BIGINT NOT NULL,
  `product_name` VARCHAR(200) NOT NULL,
  `product_image` VARCHAR(500) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `quantity` INT DEFAULT 1,
  `status` VARCHAR(20) DEFAULT 'pending' COMMENT 'pending待付款/shipped待发货/completed已完成/cancelled已取消',
  `buyer_id` BIGINT NOT NULL COMMENT '买家ID',
  `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
  `address` VARCHAR(255) NOT NULL COMMENT '收货地址',
  `reviewed` TINYINT(1) DEFAULT 0 COMMENT '是否已评价',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
  FOREIGN KEY (`buyer_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`seller_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 插入订单数据
INSERT INTO `order` (`id`, `product_id`, `product_name`, `product_image`, `price`, `quantity`, `status`, `buyer_id`, `seller_id`, `address`, `reviewed`, `create_time`) VALUES
('ORD20240315001', 1, 'iPhone 14 Pro Max 256G 暗紫色', 'https://picsum.photos/400/400?random=1', 5999.00, 1, 'completed', 3, 1, '北京理工大学3号宿舍楼108', 1, '2024-03-15 14:30:00'),
('ORD20240316001', 5, 'MacBook Air M2 13寸 8+256G', 'https://picsum.photos/400/400?random=5', 5899.00, 1, 'completed', 4, 5, '北京理工大学4号宿舍楼402', 1, '2024-03-16 10:20:00'),
('ORD20240317001', 31, '高等数学第七版 上册+下册', 'https://picsum.photos/400/400?random=30', 35.00, 1, 'completed', 6, 1, '北京理工大学6号宿舍楼618', 1, '2024-03-17 09:15:00'),
('ORD20240318001', 12, '小米13 Pro 12+256G 陶瓷白', 'https://picsum.photos/400/400?random=2', 3299.00, 1, 'shipped', 7, 2, '北京理工大学7号宿舍楼712', 0, '2024-03-18 16:45:00'),
('ORD20240319001', 21, 'AirPods Pro 2代 USB-C', 'https://picsum.photos/400/400?random=4', 1399.00, 1, 'shipped', 8, 4, '北京理工大学8号宿舍楼820', 0, '2024-03-19 11:30:00'),
('ORD20240320001', 8, 'Switch OLED 日版', 'https://picsum.photos/400/400?random=8', 1899.00, 1, 'pending', 9, 2, '北京理工大学9号宿舍楼901', 0, '2024-03-20 14:00:00'),
('ORD20240321001', 15, 'iPad Pro 11寸 2022款 128G', 'https://picsum.photos/400/400?random=6', 4599.00, 1, 'pending', 10, 1, '北京理工大学10号宿舍楼1001', 0, '2024-03-21 09:30:00'),
('ORD20240321002', 35, '线性代数同济第六版', 'https://picsum.photos/400/400?random=31', 22.00, 1, 'shipped', 1, 3, '北京理工大学1号宿舍楼201', 0, '2024-03-21 10:15:00'),
('ORD20240321003', 40, 'Python编程从入门到实践', 'https://picsum.photos/400/400?random=38', 38.00, 1, 'completed', 2, 2, '北京理工大学2号宿舍楼305', 1, '2024-03-21 11:00:00'),
('ORD20240321004', 45, '彪马Puma双肩包 黑色', 'https://picsum.photos/400/400?random=59', 69.00, 1, 'completed', 5, 10, '北京理工大学5号宿舍楼501', 1, '2024-03-21 14:30:00');

-- ----------------------------
-- 5. 评价表
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `rating` TINYINT NOT NULL COMMENT '评分1-5',
  `content` TEXT COMMENT '评价内容',
  `time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价表';

-- 插入评价数据
INSERT INTO `review` (`product_id`, `user_id`, `rating`, `content`, `time`) VALUES
(1, 3, 5, '手机收到啦！成色很好，跟描述一致，电池也很耐用。卖家发货很快，推荐！', '2024-03-16 10:00:00'),
(5, 4, 5, 'MacBook太香了，性能强劲，重量也很轻。学长人很好，还送了我一些软件资源。', '2024-03-17 15:30:00'),
(31, 6, 4, '课本很新，价格便宜！不过有一章有重点标记，不影响使用。', '2024-03-18 09:45:00'),
(12, 3, 5, '性价比超高的小米旗舰，拍照效果很棒！交易过程顺利愉快。', '2024-03-19 11:20:00'),
(21, 4, 5, '降噪效果真的很不错，戴上耳机整个世界都安静了。', '2024-03-20 16:00:00'),
(40, 2, 5, '书收到了，印刷清晰，代码都能跑通。很适合新手入门！', '2024-03-22 10:30:00'),
(45, 5, 5, '双肩包质量很好，空间大分区多，通勤很方便。卖家包邮很划算！', '2024-03-22 17:00:00'),
(8, 9, 4, '游戏机没问题，塞尔达太好玩了！就是游戏卡只有两张，意犹未尽。', '2024-03-21 20:00:00'),
(15, 10, 5, '平板配键盘简直生产力爆棚，考研复习效率翻倍！', '2024-03-22 11:00:00'),
(35, 1, 5, '线性代数满绩选手的经验：这本书框架清晰，例题经典，考研必备！', '2024-03-22 14:00:00');

-- ----------------------------
-- 6. 收藏表
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 插入收藏数据
INSERT INTO `favorite` (`user_id`, `product_id`, `create_time`) VALUES
(1, 3, '2024-03-20 10:00:00'),
(1, 9, '2024-03-20 10:05:00'),
(1, 25, '2024-03-21 11:00:00'),
(2, 1, '2024-03-16 14:00:00'),
(2, 8, '2024-03-18 09:30:00'),
(2, 22, '2024-03-19 16:00:00'),
(3, 5, '2024-03-17 11:00:00'),
(3, 12, '2024-03-18 15:30:00'),
(4, 15, '2024-03-21 09:00:00'),
(4, 32, '2024-03-21 09:15:00'),
(5, 40, '2024-03-19 14:00:00'),
(5, 45, '2024-03-20 10:30:00'),
(6, 8, '2024-03-16 16:00:00'),
(6, 21, '2024-03-17 10:00:00'),
(7, 35, '2024-03-21 08:30:00'),
(8, 12, '2024-03-19 11:30:00'),
(8, 25, '2024-03-20 15:00:00'),
(9, 5, '2024-03-18 13:00:00'),
(9, 40, '2024-03-20 09:00:00'),
(10, 1, '2024-03-15 16:00:00'),
(10, 3, '2024-03-21 14:30:00');

-- ----------------------------
-- 7. 购物车表
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT DEFAULT 1,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 插入购物车数据
INSERT INTO `cart` (`user_id`, `product_id`, `quantity`, `create_time`) VALUES
(1, 8, 1, '2024-03-19 10:00:00'),
(1, 22, 1, '2024-03-20 14:30:00'),
(2, 15, 1, '2024-03-18 11:00:00'),
(2, 32, 1, '2024-03-19 16:00:00'),
(3, 5, 1, '2024-03-17 09:30:00'),
(4, 40, 1, '2024-03-21 08:00:00'),
(5, 9, 1, '2024-03-20 15:30:00'),
(6, 21, 1, '2024-03-16 14:00:00'),
(7, 45, 1, '2024-03-21 10:00:00'),
(8, 12, 1, '2024-03-19 13:00:00');

-- ----------------------------
-- 8. 轮播图表
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `image` VARCHAR(500) NOT NULL COMMENT '轮播图URL',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `subtitle` VARCHAR(200) DEFAULT '' COMMENT '副标题',
  `link` VARCHAR(500) DEFAULT '' COMMENT '跳转链接',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) DEFAULT 1 COMMENT '状态:1启用0禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

INSERT INTO `banner` (`image`, `title`, `subtitle`, `link`, `sort`, `status`) VALUES
('https://picsum.photos/1200/400?random=101', '热门推荐', '精选校园好物，限时特惠', '/products?sort=hot', 1, 1),
('https://picsum.photos/1200/400?random=102', '最新发布', '发现新鲜闲置，抢先一步', '/products?sort=new', 2, 1),
('https://picsum.photos/1200/400?random=103', '书籍专区', '教材教辅低价淘', '/products?category=books', 3, 1),
('https://picsum.photos/1200/400?random=104', '数码专场', '手机电脑平板，应有尽有', '/products?category=digital', 4, 1);

-- ----------------------------
-- 9. 收货地址表
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `consignee` VARCHAR(50) NOT NULL COMMENT '收货人',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `province` VARCHAR(50) DEFAULT '' COMMENT '省份',
  `city` VARCHAR(50) DEFAULT '' COMMENT '城市',
  `district` VARCHAR(50) DEFAULT '' COMMENT '区县',
  `detail` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `is_default` TINYINT(1) DEFAULT 0 COMMENT '是否默认',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

INSERT INTO `address` (`user_id`, `consignee`, `phone`, `province`, `city`, `district`, `detail`, `is_default`) VALUES
(1, '张三', '13800138001', '北京市', '海淀区', '学院路街道', '北京理工大学1号宿舍楼201', 1),
(2, '李四', '13800138002', '北京市', '海淀区', '学院路街道', '北京理工大学2号宿舍楼305', 1),
(3, '王五', '13800138003', '北京市', '海淀区', '学院路街道', '北京理工大学3号宿舍楼108', 1),
(4, '赵六', '13800138004', '北京市', '海淀区', '学院路街道', '北京理工大学4号宿舍楼402', 1),
(5, '钱七', '13800138005', '北京市', '海淀区', '学院路街道', '北京理工大学5号宿舍楼501', 1);

-- ----------------------------
-- 初始化完成
-- ----------------------------
SELECT 'Database initialized successfully!' AS message;
