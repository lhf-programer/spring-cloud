DROP TABLE IF EXISTS gateway_route;
DROP TABLE IF EXISTS auth_client;
DROP TABLE IF EXISTS auth_client_service;

# 路由
CREATE TABLE gateway_route (
	id VARCHAR(32) NOT NULL,
	path varchar(64) COMMENT '映射路劲',
	service_id varchar(64) COMMENT '映射服务',
	url varchar(255) COMMENT '映射外连接',
	retryable TINYINT(2) COMMENT '是否重试',
	enabled TINYINT(2) COMMENT '是否启用',
	strip_prefix TINYINT(2) COMMENT '是否忽略前缀',
	crt_user varchar(64) COMMENT '创建人',
	crt_time datetime NOT NULL COMMENT '创建时间',
	upd_user varchar(64) COMMENT '最后更新人',
	upd_time datetime NOT NULL COMMENT '最后更新时间',
	PRIMARY KEY (id) 
);

# 认证客户端
CREATE TABLE auth_client (
	id VARCHAR(32) NOT NULL,
	code VARCHAR(64) NOT NULL COMMENT '客户端 id',
	secret VARCHAR(64) NOT NULL COMMENT '客户端 密钥',
	name VARCHAR(64) NOT NULL COMMENT '客户端 名称',
	description VARCHAR(255) NOT NULL COMMENT '描述',
	crt_user varchar(64) COMMENT '创建人',
	crt_time datetime NOT NULL COMMENT '创建时间',
	upd_user varchar(64) COMMENT '最后更新人',
	upd_time datetime NOT NULL COMMENT '最后更新时间',
	PRIMARY KEY (id) 
);

# 认证客户端服务
CREATE TABLE auth_client_service (
	id VARCHAR(32) NOT NULL,
	service_id varchar(32) COMMENT '服务 id',
	client_id varchar(32) COMMENT '客户机 id',
	description VARCHAR(255) NOT NULL COMMENT '描述',
	crt_user varchar(64) COMMENT '创建人',
	crt_time datetime NOT NULL COMMENT '创建时间',
	upd_user varchar(64) COMMENT '最后更新人',
	upd_time datetime NOT NULL COMMENT '最后更新时间',
	PRIMARY KEY (id) 
);