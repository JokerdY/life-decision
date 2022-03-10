/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : life

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 10/03/2022 15:53:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for option_information
-- ----------------------------
DROP TABLE IF EXISTS `option_information`;
CREATE TABLE `option_information`  (
                                       `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                       `questionnaire_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '问卷id',
                                       `question_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '问题id',
                                       `option_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '选项值',
                                       `fill_enabled` int(1) DEFAULT NULL COMMENT '是否支持填空，如：其他__  0：不支持 1：支持',
                                       `option_sort` int(255) DEFAULT NULL,
                                       `associated_jump_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联跳转id -1标识End',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       INDEX `option_information_idx_questionnaire_question_id`(`questionnaire_id`, `question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_answer
-- ----------------------------
DROP TABLE IF EXISTS `question_answer`;
CREATE TABLE `question_answer`  (
                                    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                    `questionnaire_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                    `question_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                    `create_date` datetime(0) DEFAULT CURRENT_TIMESTAMP,
                                    `option_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '问题选项id',
                                    `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '填空或备注信息',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_answer
-- ----------------------------
INSERT INTO `question_answer` VALUES ('1', '1', '1', '1', '2022-03-10 13:31:39', '1', NULL);
INSERT INTO `question_answer` VALUES ('2', '1', '1', '2', '2022-03-10 13:31:48', '3', NULL);
INSERT INTO `question_answer` VALUES ('3', '2', '1', '1', '2022-03-10 13:33:18', '2', NULL);

-- ----------------------------
-- Table structure for question_information
-- ----------------------------
DROP TABLE IF EXISTS `question_information`;
CREATE TABLE `question_information`  (
                                         `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                         `questionnaire_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                         `question_num` double DEFAULT NULL COMMENT '问题序号 如1 1.1',
                                         `question_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '问题名称{}表示填空的位置，由前端进行渲染',
                                         `big_label_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '大标签名',
                                         `question_type` int(255) DEFAULT NULL COMMENT '问题类型：1-填空 2-单选 3-多选 4-时间计数器 5-天数计数器',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         INDEX `question_information_idx_questionnaire_id`(`questionnaire_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for questionnaire_information
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire_information`;
CREATE TABLE `questionnaire_information`  (
                                              `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                              `questionnaire_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                              `create_date` datetime(0) DEFAULT CURRENT_TIMESTAMP,
                                              `update_date` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
                                              `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '1-逻辑删除 0-未删除',
                                              `questionnaire_type` int(255) DEFAULT NULL COMMENT '问卷类型：1-健康 2-饮食 3-生活',
                                              PRIMARY KEY (`id`) USING BTREE,
                                              INDEX `questionnaire_information_idx_type`(`questionnaire_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questionnaire_information
-- ----------------------------
INSERT INTO `questionnaire_information` VALUES ('1', '问卷1', '2022-03-10 13:30:49', '2022-03-10 13:30:49', 0, 1);

-- ----------------------------
-- Table structure for questionnaire_submit_information
-- ----------------------------
DROP TABLE IF EXISTS `questionnaire_submit_information`;
CREATE TABLE `questionnaire_submit_information`  (
                                                     `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                                     `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                                     `questionnaire_id` int(11) DEFAULT NULL,
                                                     `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP,
                                                     PRIMARY KEY (`id`) USING BTREE,
                                                     INDEX `idx`(`user_id`, `questionnaire_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questionnaire_submit_information
-- ----------------------------
INSERT INTO `questionnaire_submit_information` VALUES ('1', '1', 1, '2022-03-10 14:16:13');
INSERT INTO `questionnaire_submit_information` VALUES ('2', '2', 1, '2022-03-10 14:16:18');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
                             `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `column_value` int(11) DEFAULT NULL COMMENT '字典值',
                             `convert_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对应标签名',
                             `sort_id` int(11) DEFAULT NULL COMMENT '排序',
                             `column_name` varchar(129) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段名',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'questionnaire_information', 1, '生活问卷', 1, 'questionnaire_type');

-- ----------------------------
-- Table structure for user_information
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
                                     `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                     `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                     `telphone_num` int(11) NOT NULL,
                                     `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                     `admin_enable` int(1) DEFAULT 1 COMMENT '0:admin 1:user',
                                     PRIMARY KEY (`id`, `telphone_num`) USING BTREE,
                                     UNIQUE INDEX `user_idx_telphone_num`(`telphone_num`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES ('1', 'admin', 1, '1', 1);
INSERT INTO `user_information` VALUES ('2', 'dd', 2, '2', 1);

SET FOREIGN_KEY_CHECKS = 1;
