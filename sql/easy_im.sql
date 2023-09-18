/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : easy_im

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 13/09/2023 17:05:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for easy_im_dialog
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_dialog`;
CREATE TABLE `easy_im_dialog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `dialog_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '对话ID',
  `sender_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '发送者ID',
  `receiver_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '接收者ID',
  `dialog_type` int(4) NOT NULL COMMENT '对话类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '对话表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_im_friend
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_friend`;
CREATE TABLE `easy_im_friend`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `friend_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '好友用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `EASYIM_FRIEND_USERFRIENDID`(`user_id`, `friend_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '好友表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_im_group
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_group`;
CREATE TABLE `easy_im_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `group_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '群组ID（群组账户：随机生成）',
  `group_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '群组名称',
  `group_avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '群组头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `EASYIM_GROUP_GROUPID`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '群组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_im_member
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_member`;
CREATE TABLE `easy_im_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `group_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '群组ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `EASYIM_GROUPMEMBER_USERGROUPID`(`user_id`, `group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_im_record
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_record`;
CREATE TABLE `easy_im_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `dialog_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '对话ID',
  `sender_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '发送者ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '消息内容',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '消息时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '聊天记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for easy_im_user
-- ----------------------------
DROP TABLE IF EXISTS `easy_im_user`;
CREATE TABLE `easy_im_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID（用户账号）',
  `user_nickname` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `EASYIM_USERID`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
