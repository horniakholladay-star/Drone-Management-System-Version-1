# 无人机信息管理系统 (Drone Management System)

基于 Spring Boot 2.2.x + Apache Shiro + MyBatis + Thymeleaf + SQLite 构建的无人机信息管理 Web 应用。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.2.13 | Web 框架 |
| Apache Shiro | 1.7.1 | 安全认证与授权 |
| MyBatis | 2.1.4 | ORM 持久层 |
| Thymeleaf | - | 服务端模板引擎 |
| Druid | 1.2.20 | 数据库连接池与监控 |
| SQLite | 3.36.0 | 默认数据库（零配置） |
| MySQL | 8.0 | 可选生产数据库 |
| Bootstrap | 3.3.7 | 前端 UI 框架 |

## 快速启动

### 环境要求
- JDK 8+
- Maven 3.6+

### 默认配置（SQLite）
```bash
cd drone-management
mvn clean package -DskipTests
java -jar target/drone-management-1.0.0.jar
```
访问: http://localhost:8080

### 使用 MySQL
```bash
java -jar target/drone-management-1.0.0.jar --spring.profiles.active=mysql
```

### 默认账号
- 用户名: `admin`
- 密码: `admin123`

## 项目结构

```
drone-management/
├── src/main/java/com/drone/management/
│   ├── common/          # 通用响应封装 (Result, PageResult)
│   ├── config/          # Shiro, Druid, MyBatis, WebMvc, Tomcat 配置
│   ├── controller/      # 控制器 (DroneController, IndexController)
│   ├── dao/             # 数据访问层 (MyBatis)
│   ├── entity/          # 实体类 (Drone)
│   ├── exception/       # 全局异常处理
│   ├── interceptor/     # 请求日志拦截器
│   ├── runner/          # 数据库初始化
│   └── service/         # 业务逻辑层
├── src/main/resources/
│   ├── mybatis/mapper/  # MyBatis XML 映射
│   ├── templates/       # Thymeleaf 页面模板
│   └── static/          # 静态资源 (CSS, JS)
└── pom.xml
```

## 功能特性

- ✅ 无人机信息 CRUD（新增、编辑、删除、查询）
- ✅ 分页查询与多条件搜索（名称、型号、状态）
- ✅ Shiro 安全认证（表单登录）
- ✅ SQLite 零配置启动，支持切换 MySQL
- ✅ Druid 连接池监控 (`/druid`)
- ✅ 全局异常处理 + 400/403/404/500 错误页面
- ✅ UTF-8 编码强制（`CharacterEncodingFilter` + 表单 `accept-charset`）
- ✅ Session Cookie 追踪（无 `;jsessionid=` URL 重写）
- ✅ Bootstrap 3 响应式 UI

## Druid 监控

访问: http://localhost:8080/druid
- 用户名: `admin`
- 密码: `admin123`

## License

MIT
