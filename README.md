# AIDatasetsPlatform

## 概览

AIDatasetsPlatform是一个基于Spring Boot的综合应用平台，旨在为人工智能（AI）研究和开发收集、管理和使用数据集。它为用户提供了一个强大的平台，用于上传、存储和访问从文本、图像到音频和视频等多种数据集。该平台的目标是通过提供丰富的数据仓库，便于快速开发和测试AI模型。

## 特性

- **数据集管理**：用户可以上传、下载和管理数据集。每个数据集都通过包括名称和描述的元数据进行描述。
- **用户认证**：平台支持用户注册和认证，确保安全访问系统。
- **文件存储**：使用Apache Commons IO进行高效的文件处理和存储操作。
- **安全性**：利用Spring Boot安全性为API端点提供安全访问，支持基本认证和基于角色的访问控制。
- **数据库支持**：集成MySQL用于持久存储用户信息和数据集信息。

## 技术栈

- **后端**：Spring Boot、Spring Data JPA、Spring Security
- **数据库**：MySQL
- **前端**：使用Thymeleaf模板渲染UI组件
- **构建工具**：Maven

## 快速开始

### 前提条件

- JDK 17或更高版本
- MySQL服务器（5.7或更高版本）
- Maven

### 设置

1. **克隆仓库：**

```sh
git clone https://github.com/uluckystar/AIDatasetsPlatform.git
cd AIDatasetsPlatform
```

2. **配置MySQL数据库：**

创建一个名为`adp`的数据库，并更新`src/main/resources/application.properties`文件中的MySQL用户和密码。

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/adp?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
```

3. **构建项目：**

```sh
mvn clean install
```

4. **运行应用程序：**

```sh
java -jar target/AIDatasetsPlatform-0.0.1-SNAPSHOT.jar
```

服务器将在`http://localhost:8088`上启动。

## 使用方法

- 访问`http://localhost:8088/login`进入登录页面。
- 登录后，用户可以通过导航至`/file-manager`端点来上传数据集。
- 可以从数据集列表页面下载数据集。

## 贡献

欢迎贡献。请fork仓库并提交任何增强功能的pull请求。

## 许可证

[MIT 许可证](LICENSE)

---

请根据您项目的实际设置和要求调整URL、数据库配置和任何特定指导。