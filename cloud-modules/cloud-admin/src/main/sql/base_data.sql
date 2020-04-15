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

 Date: 15/04/2020 16:37:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for button
-- ----------------------------
DROP TABLE IF EXISTS `button`;
CREATE TABLE `button`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '按钮名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '按钮路径',
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属菜单id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '按钮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of button
-- ----------------------------
INSERT INTO `button` VALUES ('0425ae4b720b74d22d0b77323e79ef55', '菜单删除', '/admin/menu/remove', 'f17c142ee8990fe78479129d79c05753', '菜单删除', 'admin', '2020-01-17 16:10:48', 'admin', '2020-01-17 16:10:48');
INSERT INTO `button` VALUES ('3267a84f58a966832a94ee98737efa9f', '角色权限', '/admin/role/permit', 'd078ba5ba13987ad00819218d5ad7a32', '角色权限', 'admin', '2020-01-17 16:12:50', 'admin', '2020-01-17 16:12:50');
INSERT INTO `button` VALUES ('392631943cc993dc2dc72805682a3570', '角色修改', '/admin/role/edit', 'd078ba5ba13987ad00819218d5ad7a32', '角色修改', 'admin', '2020-01-17 16:07:37', 'admin', '2020-01-17 16:07:37');
INSERT INTO `button` VALUES ('3a479d9b3780d14e3384df55e13589a6', '用户删除', '/admin/user/remove', 'd078ba5ba13987ad00819218d5ad7a32', '用户删除', 'admin', '2020-01-17 16:07:00', 'admin', '2020-01-17 16:07:00');
INSERT INTO `button` VALUES ('4f0a036b6918f82fecb1245bab40f64f', '按钮修改', '/admin/button/edit', 'ed9ccac628fcc0cf66ce232ba6ae4d84', '按钮修改', 'admin', '2020-01-17 16:11:31', 'admin', '2020-01-17 16:11:31');
INSERT INTO `button` VALUES ('68eb6d6110716f1285eeb13643c49e06', '菜单修改', '/admin/menu/edit', 'f17c142ee8990fe78479129d79c05753', '菜单添加', 'admin', '2020-01-17 16:10:25', 'admin', '2020-01-17 16:10:25');
INSERT INTO `button` VALUES ('7e509beed4a9c69c8d981e11291c7af1', '按钮删除', '/admin/button/remove', 'ed9ccac628fcc0cf66ce232ba6ae4d84', '按钮删除', 'admin', '2020-01-17 16:12:29', 'admin', '2020-01-17 16:12:29');
INSERT INTO `button` VALUES ('919a80611371fb845f83e98db222d923', '用户添加', '/admin/user/add', 'd9a0a17d33c77e46ed8b3c97a74d7ce7', '用户添加', 'admin', '2020-01-17 16:06:12', 'admin', '2020-01-17 16:06:12');
INSERT INTO `button` VALUES ('9967776fb6ee98075d326587409f36bd', '用户修改', '/admin/user/edit', 'd9a0a17d33c77e46ed8b3c97a74d7ce7', '用户修改', 'admin', '2020-01-17 16:06:44', 'admin', '2020-01-17 16:06:44');
INSERT INTO `button` VALUES ('a9bf680581d85cf4461240c970b9d74a', '菜单添加', '/admin/menu/add', 'f17c142ee8990fe78479129d79c05753', '菜单添加', 'admin', '2020-01-17 16:10:05', 'admin', '2020-01-17 16:10:05');
INSERT INTO `button` VALUES ('b169cb40150b6cd2b56029c8ef6adb45', '按钮添加', '/admin/button/add', 'ed9ccac628fcc0cf66ce232ba6ae4d84', '按钮添加', 'admin', '2020-01-17 16:11:20', 'admin', '2020-01-17 16:11:20');
INSERT INTO `button` VALUES ('b7ae369c10e75b9478c998e3dab77d48', '角色添加', '/admin/role/add', 'd078ba5ba13987ad00819218d5ad7a32', '角色添加', 'admin', '2020-01-17 16:08:52', 'admin', '2020-01-17 16:08:52');
INSERT INTO `button` VALUES ('cc4f2c24ef17542c4cbf3d94c56375fb', '用户修改密码', '/admin/user/editPassword', 'd9a0a17d33c77e46ed8b3c97a74d7ce7', '用户修改密码', 'admin', '2020-01-17 16:13:10', 'admin', '2020-01-17 16:13:10');
INSERT INTO `button` VALUES ('e2e6ccbaef39137f6e7a2bf0649bf035', '角色删除', '/admin/role/remove', 'd078ba5ba13987ad00819218d5ad7a32', '角色删除', 'admin', '2020-01-17 16:07:15', 'admin', '2020-01-17 16:07:15');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单路径',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '父菜单id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('39643eaf3cc6e1fe8c751d206d6db3e2', '服务监控', '/system/service', '7828455cebc6c4596d097f6d2c7125a4', '服务监控', 'admin', '2020-01-17 16:18:03', 'admin', '2020-01-17 16:18:03');
INSERT INTO `menu` VALUES ('7828455cebc6c4596d097f6d2c7125a4', '系统监控菜单', '/system', '0', '系统监控菜单', 'admin', '2020-01-17 16:17:21', 'admin', '2020-01-17 16:17:21');
INSERT INTO `menu` VALUES ('d078ba5ba13987ad00819218d5ad7a32', '角色管理', '/admin/role', 'e4b55a8817d4dfb6c3bfc2ccc59d999e', '角色管理', 'admin', '2020-01-17 16:04:52', 'admin', '2020-01-17 16:04:52');
INSERT INTO `menu` VALUES ('d9a0a17d33c77e46ed8b3c97a74d7ce7', '用户管理', '/admin/user', 'e4b55a8817d4dfb6c3bfc2ccc59d999e', '用户管理', 'admin', '2020-01-17 16:04:34', 'admin', '2020-01-17 16:04:34');
INSERT INTO `menu` VALUES ('e4b55a8817d4dfb6c3bfc2ccc59d999e', '系统管理菜单', '/admin', '0', '系统管理菜单', 'admin', '2020-01-17 16:03:18', 'admin', '2020-01-17 16:03:18');
INSERT INTO `menu` VALUES ('ed9ccac628fcc0cf66ce232ba6ae4d84', '按钮管理', '/admin/button', 'e4b55a8817d4dfb6c3bfc2ccc59d999e', '按钮管理', 'admin', '2020-01-17 16:05:13', 'admin', '2020-01-17 16:05:13');
INSERT INTO `menu` VALUES ('f17c142ee8990fe78479129d79c05753', '菜单管理', '/admin/menu', 'e4b55a8817d4dfb6c3bfc2ccc59d999e', '菜单管理', 'admin', '2020-01-17 16:05:33', 'admin', '2020-01-17 16:05:33');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('05e8fe0e1b23bb05c7e0f0b3450cb4bc', 'USER', 'USER', 'admin', '2020-01-17 16:18:37', 'admin', '2020-01-17 16:18:37');
INSERT INTO `role` VALUES ('e10adc3949ba59abbe56e057f20f883y', 'ADMIN', 'ADMIN', 'haifeng.lv', '2019-12-13 14:10:17', 'admin', '2020-01-11 15:44:52');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `type` tinyint(2) NOT NULL COMMENT '资源类型 0 菜单 1 按钮',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('146a1691971f2f8a67e79a4b28748929', 'e10adc3949ba59abbe56e057f20f883y', 'f17c142ee8990fe78479129d79c05753', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('2a4a02d699345dc985e3fdbeb42a5d3c', 'e10adc3949ba59abbe56e057f20f883y', '919a80611371fb845f83e98db222d923', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('37afd0369fff69625562ea9700ddfd64', 'e10adc3949ba59abbe56e057f20f883y', '4f0a036b6918f82fecb1245bab40f64f', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('445c53ec6e8e2a9fa1da33b620a303a6', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', 'e2e6ccbaef39137f6e7a2bf0649bf035', 1, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('4473ce936e2386db0123e91bf8f6feb5', 'e10adc3949ba59abbe56e057f20f883y', '3267a84f58a966832a94ee98737efa9f', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('459068b02110ac4ded1f95241cefaa23', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', '3267a84f58a966832a94ee98737efa9f', 1, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('47a863428518a7e230724ec9d50dd671', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', '3a479d9b3780d14e3384df55e13589a6', 1, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('4a9ef5e2754906aab00d21736522ee87', 'e10adc3949ba59abbe56e057f20f883y', 'ed9ccac628fcc0cf66ce232ba6ae4d84', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('588fda11f47bb244356d4db9fd3c9bb0', 'e10adc3949ba59abbe56e057f20f883y', 'd9a0a17d33c77e46ed8b3c97a74d7ce7', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('5d1f5fe42fefa31c5658aaf31c21e792', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', 'b7ae369c10e75b9478c998e3dab77d48', 1, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('5dbaf72661984a0472294270be0c9dfc', 'e10adc3949ba59abbe56e057f20f883y', 'd078ba5ba13987ad00819218d5ad7a32', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('5e520136b5e5a1eca2a148b381e38db3', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', 'd078ba5ba13987ad00819218d5ad7a32', 0, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('5f8a5a71a50f2d5c5c55e11342d8dc32', 'e10adc3949ba59abbe56e057f20f883y', '9967776fb6ee98075d326587409f36bd', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('6444ef2fdef9e062cce4e5cb4ef7c692', 'e10adc3949ba59abbe56e057f20f883y', '3a479d9b3780d14e3384df55e13589a6', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('7b30662a47b91be85e2c0092954098a8', 'e10adc3949ba59abbe56e057f20f883y', 'a9bf680581d85cf4461240c970b9d74a', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('995ded2870ab7efebe06c0b3a9e331d1', 'e10adc3949ba59abbe56e057f20f883y', 'e4b55a8817d4dfb6c3bfc2ccc59d999e', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('a2078d10f3faef3a55ecb9f03f6d2a65', 'e10adc3949ba59abbe56e057f20f883y', '7e509beed4a9c69c8d981e11291c7af1', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('a45997d200cca0ac476ce77230b9942f', 'e10adc3949ba59abbe56e057f20f883y', 'b169cb40150b6cd2b56029c8ef6adb45', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('aa72b707cce11fc10cc38d6324b1bab7', 'e10adc3949ba59abbe56e057f20f883y', '392631943cc993dc2dc72805682a3570', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('bf5f89b2020f3ac3ba28e81cd2e8bb1d', 'e10adc3949ba59abbe56e057f20f883y', '68eb6d6110716f1285eeb13643c49e06', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('dadd6c5077b5b30b173f01fec6a9485c', 'e10adc3949ba59abbe56e057f20f883y', 'e2e6ccbaef39137f6e7a2bf0649bf035', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('e4c1909e2dcdde6bf4c2a1d567851602', 'e10adc3949ba59abbe56e057f20f883y', '7828455cebc6c4596d097f6d2c7125a4', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('e5dad94e4be79b90c0f0f6b00e87db2d', 'e10adc3949ba59abbe56e057f20f883y', 'cc4f2c24ef17542c4cbf3d94c56375fb', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('eec45c2eb1fa619d55c25b106102d722', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', '392631943cc993dc2dc72805682a3570', 1, NULL, 'admin', '2020-01-17 16:19:24', 'admin', '2020-01-17 16:19:24');
INSERT INTO `role_resource` VALUES ('efb86215cd560f66ac397342ebcf99a8', 'e10adc3949ba59abbe56e057f20f883y', '0425ae4b720b74d22d0b77323e79ef55', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('f27bc227e58f0bb694360014dc4dfd6f', 'e10adc3949ba59abbe56e057f20f883y', 'b7ae369c10e75b9478c998e3dab77d48', 1, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');
INSERT INTO `role_resource` VALUES ('ff92febe51f2e3a3844d3b4150580034', 'e10adc3949ba59abbe56e057f20f883y', '39643eaf3cc6e1fe8c751d206d6db3e2', 0, NULL, 'admin', '2020-01-17 16:18:11', 'admin', '2020-01-17 16:18:11');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `realName` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '真实名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7414104c1ae82e3ae99d64eb2d5389a2', 'user', 'LRicO8QH5+rEGPi6aRA4FSGyAQO9dFC8Qj+MMMqGtwI=', '用户', '用户', 'admin', '2020-01-17 16:18:59', 'admin', '2020-01-17 16:18:59');
INSERT INTO `user` VALUES ('e20adc3949ba59abbe56e057f20f883e', 'admin', 'jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=', '超级管理员', '超级管理员', 'haifeng.lv', '2019-12-13 14:10:17', 'admin', '2020-01-17 16:19:15');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `crt_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime(0) NOT NULL COMMENT '创建时间',
  `upd_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `upd_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('73fd43fa48ef1bb2c0113d9765f99f41', '7414104c1ae82e3ae99d64eb2d5389a2', '05e8fe0e1b23bb05c7e0f0b3450cb4bc', NULL, 'admin', '2020-01-17 16:18:59', 'admin', '2020-01-17 16:18:59');
INSERT INTO `user_role` VALUES ('f648ead4cbe84e840108db7d787040d9', 'e20adc3949ba59abbe56e057f20f883e', 'e10adc3949ba59abbe56e057f20f883y', NULL, 'admin', '2020-01-17 16:19:15', 'admin', '2020-01-17 16:19:15');

SET FOREIGN_KEY_CHECKS = 1;
