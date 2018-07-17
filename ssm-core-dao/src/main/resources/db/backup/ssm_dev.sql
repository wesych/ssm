/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : ssm_dev

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 17/07/2018 17:18:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checksum` int(11) NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `installed_on` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `flyway_schema_history_s_idx`(`success`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
INSERT INTO `flyway_schema_history` VALUES (1, '1', 'Initializer', 'SQL', 'V1__Initializer.sql', 925808720, 'root', '2018-06-29 17:23:40', 1834, 1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` date NOT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'CREATE', '2017-12-22');
INSERT INTO `permission` VALUES (2, 'MODIFY', '2017-12-22');
INSERT INTO `permission` VALUES (3, 'DELETE', '2017-12-22');
INSERT INTO `permission` VALUES (4, 'QUERY', '2017-12-22');
INSERT INTO `permission` VALUES (5, 'COMMENT', '2017-12-22');
INSERT INTO `permission` VALUES (6, 'PLAY', '2017-12-22');
INSERT INTO `permission` VALUES (7, 'COLLECT', '2017-12-22');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` date NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN', '2017-12-12');
INSERT INTO `role` VALUES (2, 'GUEST', '2017-12-15');
INSERT INTO `role` VALUES (3, 'USER', '2017-12-18');
INSERT INTO `role` VALUES (4, 'VIP', '2017-12-22');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);
INSERT INTO `role_permission` VALUES (6, 1, 6);
INSERT INTO `role_permission` VALUES (7, 1, 7);
INSERT INTO `role_permission` VALUES (8, 2, 4);
INSERT INTO `role_permission` VALUES (9, 2, 6);
INSERT INTO `role_permission` VALUES (10, 3, 1);
INSERT INTO `role_permission` VALUES (11, 3, 4);
INSERT INTO `role_permission` VALUES (12, 3, 5);
INSERT INTO `role_permission` VALUES (13, 3, 6);
INSERT INTO `role_permission` VALUES (14, 3, 7);
INSERT INTO `role_permission` VALUES (15, 4, 1);
INSERT INTO `role_permission` VALUES (16, 4, 4);
INSERT INTO `role_permission` VALUES (17, 4, 5);
INSERT INTO `role_permission` VALUES (18, 4, 6);
INSERT INTO `role_permission` VALUES (19, 4, 7);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(1) NULL DEFAULT 1,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birth` date NULL DEFAULT NULL,
  `join_time` date NOT NULL,
  `locked` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '18205185818', 'df2e0b09589b05f28d5b635abfae4c99a050b475', 'zhangwei4815@live.com', 1, '南京', '1989-09-12', '2017-09-08', 0);
INSERT INTO `user` VALUES (2, 'test', 'test', '12345678999', '53da3bb1a30960fa0fb8416d0527e4f5e5683904', 'test@126.com', 0, '上海', '1991-07-24', '2017-10-04', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 3);
INSERT INTO `user_role` VALUES (3, 2, 4);

SET FOREIGN_KEY_CHECKS = 1;
