# 运行环境
FROM openjdk:8-jre-alpine
# 创建 app 目录
RUN mkdir -p /home/project/auth
WORKDIR /home/project/auth
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="auth" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY ./cloud-auth.jar /home/project/auth
# 执行命令
CMD java -jar cloud-auth.jar