# 运行环境
FROM jdk:8
# 创建 app 目录
RUN mkdir -p /home/project/center
WORKDIR /home/project/center
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="center" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY ./cloud-center.jar /home/project/center
# 执行命令
CMD java -jar cloud-center.jar