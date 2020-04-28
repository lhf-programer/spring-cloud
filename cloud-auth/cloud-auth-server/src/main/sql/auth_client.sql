/*
 Navicat Premium Data Transfer

 Source Server         : lvhaifeng-test
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.0.117:3306
 Source Schema         : spring_cloud

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 15/04/2020 16:37:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户端 id',
  `secret` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户端 密钥',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户端 名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_client
-- ----------------------------
INSERT INTO `auth_client` VALUES ('1d2adc3949ba59abbe56e057f20f883e', 'cloud-admin', '123456', 'cloud-admin', '用户', 'haifeng.lv', '2019-12-13 00:00:00', 'haifeng.lv', '2019-12-13 00:00:00');
INSERT INTO `auth_client` VALUES ('1d3adc3949ba59abbe56e057f20f883e', 'cloud-gate', '123456', 'cloud-gate', '路由', 'haifeng.lv', '2019-12-13 00:00:00', 'haifeng.lv', '2019-12-13 00:00:00');
INSERT INTO `auth_client` VALUES ('1d4adc3949ba59abbe56e057f20f883e', 'cloud-auth', '123456', 'cloud-auth', '授权', 'haifeng.lv', '2019-12-13 00:00:00', 'haifeng.lv', '2019-12-13 00:00:00');

-- ----------------------------
-- Table structure for auth_client_service
-- ----------------------------
DROP TABLE IF EXISTS `auth_client_service`;
CREATE TABLE `auth_client_service`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `service_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '服务 id',
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户机 id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_client_service
-- ----------------------------
INSERT INTO `auth_client_service` VALUES ('2d3adc3949ba59abbe56e057f20f883e', '1d3adc3949ba59abbe56e057f20f883e', '1d4adc3949ba59abbe56e057f20f883e', 'cloud-gate->cloud-auth', 'haifeng.lv', '2019-12-21 14:51:26', 'haifeng.lv', '2019-12-21 14:51:31');
INSERT INTO `auth_client_service` VALUES ('3d3adc3949ba59abbe56e057f20f883e', '1d4adc3949ba59abbe56e057f20f883e', '1d3adc3949ba59abbe56e057f20f883e', 'cloud-auth->cloud-gate', 'haifeng.lv', '2019-12-21 14:51:26', 'haifeng.lv', '2019-12-21 14:51:31');
INSERT INTO `auth_client_service` VALUES ('4d3adc3949ba59abbe56e057f20f883e', '1d4adc3949ba59abbe56e057f20f883e', '1d2adc3949ba59abbe56e057f20f883e', 'cloud-auth->cloud-admin', 'haifeng.lv', '2019-12-21 14:51:26', 'haifeng.lv', '2019-12-21 14:51:31');
INSERT INTO `auth_client_service` VALUES ('5d3adc3949ba59abbe56e057f20f883e', '1d2adc3949ba59abbe56e057f20f883e', '1d3adc3949ba59abbe56e057f20f883e', 'cloud-admin->cloud-gate', 'haifeng.lv', '2019-12-21 14:51:26', 'haifeng.lv', '2019-12-21 14:51:31');
INSERT INTO `auth_client_service` VALUES ('5d3adc3949ba59abbe56e057f20f984e', '1d2adc3949ba59abbe56e057f20f883e', '1d4adc3949ba59abbe56e057f20f883e', 'cloud-admin->cloud-auth', 'haifeng.lv', '2019-12-21 14:51:26', 'haifeng.lv', '2019-12-21 14:51:31');

-- ----------------------------
-- Table structure for gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '映射路劲',
  `service_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '映射服务',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '映射外连接',
  `retryable` tinyint(2) NULL DEFAULT NULL COMMENT '是否重试',
  `enabled` tinyint(2) NULL DEFAULT NULL COMMENT '是否启用',
  `strip_prefix` tinyint(2) NULL DEFAULT NULL COMMENT '是否忽略前缀',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_route
-- ----------------------------
INSERT INTO `gateway_route` VALUES ('1d0adc3949ba59abbe56e057f20f883e', '/auth/**', 'cloud-auth', 'http://localhost:9777/', 0, 1, 1, 'haifeng.lv', '2019-12-13 00:00:00', 'haifeng.lv', '2019-12-13 00:00:00');
INSERT INTO `gateway_route` VALUES ('1d1adc3949ba59abbe56e057f20f883e', '/admin/**', 'cloud-admin', 'http://localhost:8762/', 0, 1, 1, 'haifeng.lv', '2019-12-13 00:00:00', 'haifeng.lv', '2019-12-13 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
