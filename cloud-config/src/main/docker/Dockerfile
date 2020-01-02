# 运行环境
FROM jdk:8
# 创建 app 目录
RUN mkdir -p /home/project/config
WORKDIR /home/project/config
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="config" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY ./cloud-config.jar /home/project/config
# 执行命令
CMD java -jar cloud-config.jar