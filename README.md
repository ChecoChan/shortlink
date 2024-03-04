# 简介

短链接（Short Link）是指将一个原始的长 URL（Uniform Resource Locator）通过特定的算法或服务转化为一个更短、易于记忆的
URL。短链接通常只包含几个字符，而原始的长 URL 可能会非常长。

短链接的原理非常简单，通过一个原始链接生成个相对短的链接，然后通过访问短链接跳转到原始链接。

如果更细节一些的话，那就是：

1. **生成唯一标识符**：当用户输入或提交一个长 URL 时，短链接服务会生成一个唯一的标识符或者短码。
2. **将标识符与长 URL 关联**：短链接服务将这个唯一标识符与用户提供的长 URL 关联起来，并将其保存在数据库或者其他持久化存储中。
3. **创建短链接**：将生成的唯一标识符加上短链接服务的域名（例如：http://nurl.ink ）作为前缀，构成一个短链接。
4. **重定向**：当用户访问该短链接时，短链接服务接收到请求后会根据唯一标识符查找关联的长 URL，然后将用户重定向到这个长 URL。
5. **跟踪统计**：一些短链接服务还会提供访问统计和分析功能，记录访问量、来源、地理位置等信息。

# 如何使用

## 克隆项目

```shell
git clone https://github.com/ChecoChan/shortlink.git
```
检查项目 SDK 的版本是否为 JDK 17，如果不是请选择电脑上的 JDK 版本。运行以下命令，测试是否具备运行环境

```shell
mvn clean install
```

## 安装中间件及数据库初始化

### Redis Latest

```shell
docker run -p 6379:6379 --name redis  -d redis redis-server --requirepass "123456"
```

### Nacos 2.1.2

```shell
docker run \
-d -p 8848:8848 \
-p 9848:9848 \
--name nacos2 \
-e MODE=standalone \
-e TIME_ZONE='Asia/Shanghai' \
nacos/nacos-server:v2.1.2
```

### MySQL 5.7.36
- Windows、Linux 以及 Mac M1 以下电脑
    ```shell
    docker run --name mysql \
    -p 3306:3306 \
    -e MYSQL_ROOT_HOST='%' \
    -e MYSQL_ROOT_PASSWORD=root \
    -d mysql:5.7.36
    ```
- Mac M1 及以上电脑
    ```shell
    docker run --name mysql \
    --platform=linux/amd64 \
    -p 3306:3306 \
    -e MYSQL_ROOT_HOST='%' \
    -e MYSQL_ROOT_PASSWORD=root \
    -d amd64/mysql:5.7.36
    ```

### 数据库初始化
- MySQL 数据库中创建新的 DB，名称为 link
- 创建好数据库后，进入 link 数据库中，导入项目中下述 SQL 语句
  ```
  resources/database/link.sql
  resources/database/link-data.sql
  ```

## 启动后端项目
### SpringBoot 模式
启动 Aggregation Service 和 GateWay Service 即可。
### SpringCloud 模式
配置中默认是 SpringBoot 单体模式，如果是以分布式方式启动，需要修改后管服务和短链接服务的 application.yaml 配置文件中的属性。
将 `spring.profiles.active: aggregation` 改为 `spring.profiles.active: dev`。然后依次启动各个微服务即可。

## 启动前端项目
- 安装 Node.js
- 进入 shortlink/console-vue 目录执行通过终端工具依次执行下述命令
  ```shell
  npm install
  npm dev
  ```
默认用户名和密码：admin/admin123456
  
