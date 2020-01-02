# 运行环境
FROM registry.gz.cvte.cn/library/node:10.15.3
# 创建 app 目录
RUN mkdir -p /home/project/front
WORKDIR /home/project/front
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="front" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY . /home/project/front
RUN npm install
EXPOSE 3000
CMD [ "npm", "run dev" ]