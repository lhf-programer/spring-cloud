DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS button;
DROP TABLE IF EXISTS role_resource;

# 用户
CREATE TABLE user (
id VARCHAR(32) NOT NULL,
username varchar(64) NOT NULL COMMENT '登录名',
password varchar(64) NOT NULL COMMENT '密码',
realName varchar(64) NOT NULL COMMENT '真实名称',
description varchar(255) COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '用户';

# 角色
CREATE TABLE role (
id VARCHAR(32) NOT NULL,
name varchar(255) NOT NULL COMMENT '角色名称',
description varchar(255) COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '角色';

# 用户角色
CREATE TABLE user_role (
id VARCHAR(32) NOT NULL,
user_id VARCHAR(32) NOT NULL COMMENT '用户id',
role_id VARCHAR(32) NOT NULL COMMENT '角色id',
description varchar(255) COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '用户角色';

# 菜单
CREATE TABLE menu (
id VARCHAR(32) NOT NULL,
name varchar(255) NOT NULL COMMENT '菜单名称',
url varchar(255) NOT NULL COMMENT '菜单路径',
parent_id varchar(32) NOT NULL COMMENT '父菜单id',
description varchar(255) NULL COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '菜单';

# 按钮
CREATE TABLE button (
id VARCHAR(32) NOT NULL,
name varchar(255) NOT NULL COMMENT '按钮名称',
url varchar(255) NOT NULL COMMENT '按钮路径',
menu_id varchar(32) NOT NULL COMMENT '所属菜单id',
description varchar(255) NULL COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '按钮';

# 角色资源
CREATE TABLE role_resource (
id VARCHAR(32) NOT NULL,
role_id VARCHAR(32) NOT NULL COMMENT '角色id',
resource_id VARCHAR(32) NOT NULL COMMENT '资源id',
type TINYINT(2) NOT NULL COMMENT '资源类型 0 菜单 1 按钮',
description varchar(255) COMMENT '描述',
crt_user varchar(64) COMMENT '创建人',
crt_time datetime NOT NULL COMMENT '创建时间',
upd_user varchar(64) COMMENT '最后更新人',
upd_time datetime NOT NULL COMMENT '最后更新时间',
PRIMARY KEY (id) 
) COMMENT '角色资源';


# 测试数据
INSERT INTO user (id, username, password, realName, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f883e','user','jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=','user','user','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17'); 
INSERT INTO user (id, username , password, realName, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e20adc3949ba59abbe56e057f20f883e','admin','jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=','admin','admin','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17'); 
INSERT INTO role (id, name, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f883i','USER','USER','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17');
INSERT INTO role (id, name, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f883y','ADMIN','ADMIN','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17');
INSERT INTO user_role (id, user_id, role_id, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f883q','e10adc3949ba59abbe56e057f20f883e', 'e10adc3949ba59abbe56e057f20f883i','','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17');
INSERT INTO user_role (id, user_id, role_id, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f884e','e20adc3949ba59abbe56e057f20f883e', 'e10adc3949ba59abbe56e057f20f883i','','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17');
INSERT INTO user_role (id, user_id, role_id, description, crt_user, crt_time, upd_user, upd_time) VALUES ('e10adc3949ba59abbe56e057f20f885e','e20adc3949ba59abbe56e057f20f883e', 'e10adc3949ba59abbe56e057f20f883y','','haifeng.lv','2019-12-13 14:10:17','haifeng.lv','2019-12-13 14:10:17');