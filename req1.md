项目开发需求具体如下：

1 技术栈

（1）系统环境

&#x20;     Java EE 8

&#x20;     Servlet 3.0

&#x20;     Apache Maven 3

（2）主框架

&#x20;     Spring Boot 2.2.x

&#x20;     Spring Framework 5.2.x

&#x20;     Apache Shiro 1.7

（3）持久层

&#x20;     Apache MyBatis 3.5.x

&#x20;     Hibernate Validation 6.0.x

&#x20;     Alibaba Druid 1.2.x

（4）视图层

&#x20;     Bootstrap 3.3.7

&#x20;     Thymeleaf 3.0.x

2 支持SQLite，MySQL数据库，并可以自由切换，初期版本使用SQLite；

3 业务功能：

&#x20;  无人机信息管理，包括无人机信息的录入，查询，删除和修改，无人机的属性由AI自动生成；

4 服务层，数据操作层设计上接口和实现分别放在不同包；

5 拦截器：建立拦截器单独的包，将各类拦截器放到该包，执行时打印请求信息；

6 根据上面要求，还有遗漏的请补充。

