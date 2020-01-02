# 运行环境
FROM jdk:8
# 创建 app 目录
RUN mkdir -p /home/project/admin
WORKDIR /home/project/admin
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="admin" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY ./cloud-admin.jar /home/project/admin
# 执行命令
CMD java -jar cloud-admin.jar