# spring-cloud 基础架构（v1.1）

## 环境要求
- jdk 1.8
- maven 3.3.9
- git 2.21.0
- docker latest （容器）
- mysql 8.0 （docker pull mysql:8.0）
- redis 5.0.7 （docker pull redis:5.0.7）
- rabbit management （docker pull rabbitmq:management）
- spring boot 2.1.6.RELEASE
- spring cloud Greenwich.RELEASE

## 总体架构
- spring cloud + spring boot
- 持久层: mybatis-plus
- 授权: spring security + oauth + jwt
- 网关: zuul + ratelimit 
- 服务之间通信与负载: feign + hystrix
- 消息总线: rabbitmq + spring cloud bus
- nosql: redis

## 服务
- cloud-center （总服务）
- cloud-config （配置文件中心）
- cloud-auth （授权服务）
- cloud-common（公共服务）
- cloud-gate （网关）
- cloud-modules （业务模块）