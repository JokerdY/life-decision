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

 Date: 13/03/2022 15:52:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer_proposal
-- ----------------------------
DROP TABLE IF EXISTS `answer_proposal`;
CREATE TABLE `answer_proposal`  (
                                    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `questionnaire_type` int(255) NOT NULL COMMENT '问卷类型：1-基本情况 2-体力活动 3-食物频率 4-情绪体验',
                                    `question_sort` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题序号id',
                                    `option_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题选项id',
                                    `proposal_id` int(255) NOT NULL COMMENT '具体建议存于sys_dict',
                                    PRIMARY KEY (`questionnaire_type`, `question_sort`, `option_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer_proposal
-- ----------------------------
INSERT INTO `answer_proposal` VALUES ('1', 2, '2', '1', 1);
INSERT INTO `answer_proposal` VALUES ('2', 2, '2', '2', 2);

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
                                       `associated_jump_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联跳转questionid -1标识End',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       INDEX `option_information_idx_questionnaire_question_id`(`questionnaire_id`, `question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of option_information
-- ----------------------------
INSERT INTO `option_information` VALUES ('1', '1', '1', '＜2000元/月', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('10', '1', '3', '无', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('100', '2', '37', '无中度体力活动体育锻炼', 0, 2, '39');
INSERT INTO `option_information` VALUES ('101', '2', '38', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('102', '2', '39', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('103', '2', '40', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('104', '2', '41', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('105', '2', '42', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('106', '3', '43', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('107', '3', '43', '不食用', 0, 2, '46');
INSERT INTO `option_information` VALUES ('108', '3', '44', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('109', '3', '44', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('11', '1', '3', '疾病史', 1, 2, NULL);
INSERT INTO `option_information` VALUES ('110', '3', '44', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('111', '3', '44', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('115', '3', '45', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('116', '3', '46', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('117', '3', '46', '不食用', 0, 2, '49');
INSERT INTO `option_information` VALUES ('118', '3', '47', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('119', '3', '47', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('12', '1', '3', '手术史', 1, 3, NULL);
INSERT INTO `option_information` VALUES ('120', '3', '47', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('121', '3', '47', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('122', '3', '48', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('123', '3', '49', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('124', '3', '49', '不食用', 0, 1, '52');
INSERT INTO `option_information` VALUES ('125', '3', '50', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('126', '3', '50', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('127', '3', '50', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('128', '3', '50', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('129', '3', '51', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('13', '1', '4', '无 ', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('130', '3', '52', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('131', '3', '52', '不食用', 0, 1, '55');
INSERT INTO `option_information` VALUES ('132', '3', '53', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('133', '3', '53', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('134', '3', '53', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('135', '3', '53', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('136', '3', '54', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('137', '3', '55', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('138', '3', '55', '不食用', 0, 1, '58');
INSERT INTO `option_information` VALUES ('139', '3', '56', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('14', '1', '4', '高血压', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('140', '3', '56', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('141', '3', '56', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('142', '3', '56', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('143', '3', '57', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('144', '3', '58', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('145', '3', '58', '不食用', 0, 1, '61');
INSERT INTO `option_information` VALUES ('146', '3', '59', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('147', '3', '59', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('148', '3', '59', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('149', '3', '59', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('15', '1', '4', '糖尿病', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('150', '3', '60', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('151', '3', '61', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('152', '3', '61', '不食用', 0, 1, '64');
INSERT INTO `option_information` VALUES ('153', '3', '62', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('154', '3', '62', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('155', '3', '62', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('156', '3', '62', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('157', '3', '63', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('158', '3', '64', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('159', '3', '64', '不食用', 0, 1, '67');
INSERT INTO `option_information` VALUES ('16', '1', '4', '冠心病', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('160', '3', '65', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('161', '3', '65', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('162', '3', '65', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('163', '3', '65', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('164', '3', '66', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('165', '3', '67', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('166', '3', '67', '不食用', 0, 1, '70');
INSERT INTO `option_information` VALUES ('167', '3', '68', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('168', '3', '68', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('169', '3', '68', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('17', '1', '4', '慢性阻塞性肺疾病', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('170', '3', '68', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('171', '3', '69', '{}毫升', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('172', '3', '70', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('173', '3', '70', '不食用', 0, 1, '73');
INSERT INTO `option_information` VALUES ('174', '3', '71', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('175', '3', '71', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('176', '3', '71', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('177', '3', '71', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('178', '3', '72', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('179', '3', '73', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('18', '1', '4', '恶性肿瘤', 0, 6, NULL);
INSERT INTO `option_information` VALUES ('180', '3', '73', '不食用', 0, 1, '76');
INSERT INTO `option_information` VALUES ('181', '3', '74', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('182', '3', '74', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('183', '3', '74', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('184', '3', '74', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('185', '3', '75', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('186', '3', '76', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('187', '3', '76', '不食用', 0, 1, '79');
INSERT INTO `option_information` VALUES ('188', '3', '77', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('189', '3', '77', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('19', '1', '4', '脑卒中', 0, 7, NULL);
INSERT INTO `option_information` VALUES ('190', '3', '77', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('191', '3', '77', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('192', '3', '78', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('193', '3', '79', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('194', '3', '79', '不食用', 0, 1, '82');
INSERT INTO `option_information` VALUES ('195', '3', '80', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('196', '3', '80', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('197', '3', '80', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('198', '3', '80', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('199', '3', '81', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('2', '1', '1', '≥2000且＜5000元/月', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('20', '1', '4', '严重精神障碍', 0, 8, NULL);
INSERT INTO `option_information` VALUES ('200', '3', '82', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('201', '3', '82', '不食用', 0, 1, '85');
INSERT INTO `option_information` VALUES ('202', '3', '83', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('203', '3', '83', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('204', '3', '83', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('205', '3', '83', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('206', '3', '84', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('207', '3', '85', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('208', '3', '85', '不食用', 0, 1, '88');
INSERT INTO `option_information` VALUES ('209', '3', '86', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('21', '1', '4', '结核病', 0, 9, NULL);
INSERT INTO `option_information` VALUES ('210', '3', '86', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('211', '3', '86', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('212', '3', '86', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('213', '3', '87', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('214', '3', '88', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('215', '3', '88', '不食用', 0, 1, '91');
INSERT INTO `option_information` VALUES ('216', '3', '89', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('217', '3', '89', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('218', '3', '89', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('219', '3', '89', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('22', '1', '4', '肝炎', 0, 10, NULL);
INSERT INTO `option_information` VALUES ('220', '3', '90', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('221', '3', '91', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('222', '3', '91', '不食用', 0, 1, '94');
INSERT INTO `option_information` VALUES ('223', '3', '92', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('224', '3', '92', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('225', '3', '92', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('226', '3', '92', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('227', '3', '93', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('228', '3', '94', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('229', '3', '94', '不食用', 0, 1, '97');
INSERT INTO `option_information` VALUES ('23', '1', '4', '先天畸形', 0, 11, NULL);
INSERT INTO `option_information` VALUES ('230', '3', '95', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('231', '3', '95', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('232', '3', '95', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('233', '3', '95', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('234', '3', '96', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('235', '3', '97', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('236', '3', '97', '不食用', 0, 1, '100');
INSERT INTO `option_information` VALUES ('237', '3', '98', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('238', '3', '98', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('239', '3', '98', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('24', '1', '4', '其他', 1, 12, NULL);
INSERT INTO `option_information` VALUES ('240', '3', '98', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('241', '3', '99', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('242', '3', '100', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('243', '3', '100', '不食用', 0, 1, '103');
INSERT INTO `option_information` VALUES ('244', '3', '101', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('245', '3', '101', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('246', '3', '101', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('247', '3', '101', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('248', '3', '102', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('249', '3', '103', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('25', '1', '5', '无症状', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('250', '3', '103', '不食用', 0, 1, '106');
INSERT INTO `option_information` VALUES ('251', '3', '104', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('252', '3', '104', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('253', '3', '104', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('254', '3', '104', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('255', '3', '105', '{}克', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('256', '3', '106', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('257', '3', '106', '不食用', 0, 1, '109');
INSERT INTO `option_information` VALUES ('258', '3', '107', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('259', '3', '107', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('26', '1', '5', '头痛', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('260', '3', '107', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('261', '3', '107', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('262', '3', '108', '{}毫升', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('263', '3', '109', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('264', '3', '109', '不食用', 0, 1, '112');
INSERT INTO `option_information` VALUES ('265', '3', '110', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('266', '3', '110', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('267', '3', '110', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('268', '3', '110', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('269', '3', '111', '{}毫升', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('27', '1', '5', '头晕', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('270', '3', '112', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('271', '3', '112', '不食用', 0, 1, '115');
INSERT INTO `option_information` VALUES ('272', '3', '113', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('273', '3', '113', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('274', '3', '113', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('275', '3', '113', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('276', '3', '114', '{}两', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('277', '3', '115', '食用', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('278', '3', '115', '不食用', 0, 1, '-1');
INSERT INTO `option_information` VALUES ('279', '3', '116', '每天{}次', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('28', '1', '5', '心悸', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('280', '3', '116', '每周{}次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('281', '3', '116', '每月{}次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('282', '3', '116', '每年{}次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('283', '3', '117', '{}两', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('284', '4', '118', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('285', '4', '118', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('286', '4', '118', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('287', '4', '118', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('288', '4', '118', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('289', '4', '119', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('29', '1', '5', '胸闷', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('290', '4', '119', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('291', '4', '119', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('292', '4', '119', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('293', '4', '119', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('294', '4', '120', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('295', '4', '120', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('296', '4', '120', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('297', '4', '120', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('298', '4', '120', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('299', '4', '121', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('3', '1', '1', '≥5000且＜8000元/月', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('30', '1', '5', '胸痛', 0, 6, NULL);
INSERT INTO `option_information` VALUES ('300', '4', '121', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('301', '4', '121', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('302', '4', '121', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('303', '4', '121', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('304', '4', '122', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('305', '4', '122', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('306', '4', '122', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('307', '4', '122', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('308', '4', '122', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('309', '4', '123', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('31', '1', '5', '眼花', 0, 7, NULL);
INSERT INTO `option_information` VALUES ('310', '4', '123', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('311', '4', '123', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('312', '4', '123', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('313', '4', '123', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('314', '4', '124', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('315', '4', '124', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('316', '4', '124', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('317', '4', '124', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('318', '4', '124', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('319', '4', '125', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('32', '1', '5', '耳鸣', 0, 8, NULL);
INSERT INTO `option_information` VALUES ('320', '4', '125', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('321', '4', '125', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('322', '4', '125', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('323', '4', '125', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('324', '4', '126', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('325', '4', '126', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('326', '4', '126', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('327', '4', '126', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('328', '4', '126', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('329', '4', '127', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('33', '1', '5', '其他', 1, 9, NULL);
INSERT INTO `option_information` VALUES ('330', '4', '127', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('331', '4', '127', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('332', '4', '127', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('333', '4', '127', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('334', '4', '128', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('335', '4', '128', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('336', '4', '128', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('337', '4', '128', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('338', '4', '128', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('339', '4', '129', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('34', '1', '6', '荤素均衡', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('340', '4', '129', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('341', '4', '129', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('342', '4', '129', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('343', '4', '129', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('344', '4', '130', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('345', '4', '130', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('346', '4', '130', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('347', '4', '130', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('348', '4', '130', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('349', '4', '131', '从来没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('35', '1', '6', '荤食为主', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('350', '4', '131', '几乎没有', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('351', '4', '131', '有时', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('352', '4', '131', '经常', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('353', '4', '131', '通常', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('354', '4', '132', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('355', '4', '132', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('356', '4', '132', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('357', '4', '132', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('358', '4', '133', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('359', '4', '133', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('36', '1', '6', '素食为主', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('360', '4', '133', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('361', '4', '133', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('362', '4', '134', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('363', '4', '134', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('364', '4', '134', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('365', '4', '134', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('366', '4', '135', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('367', '4', '135', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('368', '4', '135', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('369', '4', '135', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('37', '1', '6', '嗜盐', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('370', '4', '136', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('371', '4', '136', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('372', '4', '136', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('373', '4', '136', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('374', '4', '137', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('375', '4', '137', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('376', '4', '137', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('377', '4', '137', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('378', '4', '138', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('379', '4', '138', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('38', '1', '6', '嗜油', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('380', '4', '138', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('381', '4', '138', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('382', '4', '139', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('383', '4', '139', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('384', '4', '139', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('385', '4', '139', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('386', '4', '140', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('387', '4', '140', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('388', '4', '140', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('389', '4', '140', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('39', '1', '6', '嗜糖', 0, 6, NULL);
INSERT INTO `option_information` VALUES ('390', '4', '141', '很少或者根本没有', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('391', '4', '141', '不太多', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('392', '4', '141', '有时或者说有一半的时间', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('393', '4', '141', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('394', '4', '141', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('395', '4', '141', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('396', '4', '141', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('397', '4', '141', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('398', '4', '142', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('399', '4', '142', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('4', '1', '1', '≥8000且＜10000元/月 ', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('40', '1', '7', '从不或几乎不参加', 0, 1, '9');
INSERT INTO `option_information` VALUES ('400', '4', '142', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('401', '4', '142', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('402', '4', '143', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('403', '4', '143', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('404', '4', '143', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('405', '4', '143', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('406', '4', '144', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('407', '4', '144', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('408', '4', '144', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('409', '4', '144', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('41', '1', '7', '每月1~3次', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('410', '4', '145', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('411', '4', '145', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('412', '4', '145', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('413', '4', '145', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('414', '4', '146', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('415', '4', '146', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('416', '4', '146', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('417', '4', '146', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('418', '4', '147', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('419', '4', '147', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('42', '1', '7', '每周1~2次', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('420', '4', '147', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('421', '4', '147', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('422', '4', '148', '完全不会', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('423', '4', '148', '有几天', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('424', '4', '148', '一半以上的天数', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('425', '4', '148', '大多数的时间', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('43', '1', '7', '每周3~5次', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('44', '1', '7', '每天或几乎每天', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('45', '1', '8', '散步', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('46', '1', '8', '跑步', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('47', '1', '8', '游泳', 0, 3, NULL);
INSERT INTO `option_information` VALUES ('48', '1', '8', '篮、足、排球', 0, 4, NULL);
INSERT INTO `option_information` VALUES ('49', '1', '8', '乒乓球、羽毛球、台球等小球', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('5', '1', '1', '＞10000元/月', 0, 5, NULL);
INSERT INTO `option_information` VALUES ('50', '1', '8', '广场舞、健身操', 0, 6, NULL);
INSERT INTO `option_information` VALUES ('51', '1', '8', '武术、太极拳', 0, 7, NULL);
INSERT INTO `option_information` VALUES ('52', '1', '8', '自行车', 0, 8, NULL);
INSERT INTO `option_information` VALUES ('53', '1', '8', '登山', 0, 9, NULL);
INSERT INTO `option_information` VALUES ('54', '1', '8', '力量训练（自重训练、器械训练）', 0, 10, NULL);
INSERT INTO `option_information` VALUES ('55', '1', '8', '其他', 1, 11, NULL);
INSERT INTO `option_information` VALUES ('56', '1', '9', '吸烟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('57', '1', '9', '已戒烟', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('58', '1', '9', '从不吸烟', 0, 3, '13');
INSERT INTO `option_information` VALUES ('59', '1', '10', '{}支（平均）', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('6', '1', '2', '无', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('60', '1', '11', '{}岁', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('61', '1', '12', '{}年', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('62', '1', '13', '饮酒', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('63', '1', '13', '已戒酒', 0, 2, NULL);
INSERT INTO `option_information` VALUES ('64', '1', '13', '从不饮酒', 0, 3, '-1');
INSERT INTO `option_information` VALUES ('65', '1', '14', '{}岁', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('66', '1', '15', '{}岁', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('67', '2', '16', '是', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('68', '2', '16', '否', 0, 2, '23');
INSERT INTO `option_information` VALUES ('69', '2', '17', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('7', '1', '2', '食品类', 1, 2, NULL);
INSERT INTO `option_information` VALUES ('70', '2', '17', '无工作相关的重体力活动', 0, 2, '19');
INSERT INTO `option_information` VALUES ('71', '2', '18', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('72', '2', '19', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('73', '2', '19', '无工作相关的中度体力活动', 0, 2, '21');
INSERT INTO `option_information` VALUES ('74', '2', '20', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('75', '2', '21', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('76', '2', '21', '无工作相关的步行', 0, 2, '23');
INSERT INTO `option_information` VALUES ('77', '2', '22', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('78', '2', '23', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('79', '2', '23', '未乘车外出', 0, 2, '25');
INSERT INTO `option_information` VALUES ('8', '1', '2', '药物类', 1, 3, NULL);
INSERT INTO `option_information` VALUES ('80', '2', '24', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('81', '2', '25', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('82', '2', '25', '未骑自行车外出', 0, 2, '27');
INSERT INTO `option_information` VALUES ('83', '2', '26', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('84', '2', '27', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('85', '2', '27', '未步行外出', 0, 2, '29');
INSERT INTO `option_information` VALUES ('86', '2', '28', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('87', '2', '29', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('88', '2', '29', '无工作之余的重体力活动', 0, 2, '31');
INSERT INTO `option_information` VALUES ('89', '2', '30', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('9', '1', '2', '其他', 1, 4, NULL);
INSERT INTO `option_information` VALUES ('90', '2', '31', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('91', '2', '31', '无工作之余的中度体力活动', 0, 2, '33');
INSERT INTO `option_information` VALUES ('92', '2', '32', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('93', '2', '33', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('94', '2', '33', '未外出散步', 0, 2, '35');
INSERT INTO `option_information` VALUES ('95', '2', '34', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('96', '2', '35', '每周{}天', 1, 1, NULL);
INSERT INTO `option_information` VALUES ('97', '2', '35', '无重体力活动体育锻炼', 0, 2, '37');
INSERT INTO `option_information` VALUES ('98', '2', '36', '每天{}分钟', 0, 1, NULL);
INSERT INTO `option_information` VALUES ('99', '2', '37', '每周{}天', 1, 1, NULL);

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
-- Table structure for question_information
-- ----------------------------
DROP TABLE IF EXISTS `question_information`;
CREATE TABLE `question_information`  (
                                         `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                         `questionnaire_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                         `question_sort` int(11) DEFAULT NULL COMMENT 'sort',
                                         `question_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '问题名称{}表示填空的位置，由前端进行渲染',
                                         `big_label_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '大标签名',
                                         `question_type` int(255) DEFAULT NULL COMMENT '问题类型：1-填空 2-单选 3-多选 4-时间计数器，单位分钟 5-天数计数器 6-数字填空',
                                         PRIMARY KEY (`id`, `big_label_id`) USING BTREE,
                                         INDEX `question_information_idx_questionnaire_id`(`questionnaire_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_information
-- ----------------------------
INSERT INTO `question_information` VALUES ('1', '1', 1, '家庭人均月收入', '1', 2);
INSERT INTO `question_information` VALUES ('10', '1', 10, '【吸烟情况】日吸烟量', '1', 6);
INSERT INTO `question_information` VALUES ('100', '3', 58, '在过去的1年内，您有没有食用菌菇类（香菇、草菇、平菇等）', '8', 2);
INSERT INTO `question_information` VALUES ('101', '3', 59, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('102', '3', 60, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('103', '3', 61, '在过去的1年内，您有没有食用水果（苹果、梨、香蕉等）', '8', 2);
INSERT INTO `question_information` VALUES ('104', '3', 62, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('105', '3', 63, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('106', '3', 64, '在过去的1年内，您有没有食用饮用甜饮料（可乐、雪碧、冰红茶、运动饮料等）', '8', 2);
INSERT INTO `question_information` VALUES ('107', '3', 65, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('108', '3', 66, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('109', '3', 67, '在过去的1年内，您有没有食用有啤酒', '8', 2);
INSERT INTO `question_information` VALUES ('11', '1', 11, '【吸烟情况】开始吸烟年龄', '1', 6);
INSERT INTO `question_information` VALUES ('110', '3', 68, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('111', '3', 69, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('112', '3', 70, '在过去的1年内，您有没有食用食用黄酒', '8', 2);
INSERT INTO `question_information` VALUES ('113', '3', 71, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('114', '3', 72, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('115', '3', 73, '在过去的1年内，您有没有食用白酒', '8', 2);
INSERT INTO `question_information` VALUES ('116', '3', 74, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('117', '3', 75, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('118', '4', 1, '当意想不到的事情发生时，感到烦躁', '9', 2);
INSERT INTO `question_information` VALUES ('119', '4', 2, '感到自己无法控制生活中重要的事情', '9', 2);
INSERT INTO `question_information` VALUES ('12', '1', 12, '【吸烟情况】戒烟已持续多少年', '1', 6);
INSERT INTO `question_information` VALUES ('120', '4', 3, '感到紧张和压力', '9', 2);
INSERT INTO `question_information` VALUES ('121', '4', 4, '成功地解决了令人烦恼的生活琐事', '9', 2);
INSERT INTO `question_information` VALUES ('122', '4', 5, '觉得自己正在有效地处理生活中发生的重大变化', '9', 2);
INSERT INTO `question_information` VALUES ('123', '4', 6, '觉得自己有信心能够处理个人问题', '9', 2);
INSERT INTO `question_information` VALUES ('124', '4', 7, '觉得事情正在和你希望的一样发展', '9', 2);
INSERT INTO `question_information` VALUES ('125', '4', 8, '觉得自己不能处理所有必须做的事情', '9', 2);
INSERT INTO `question_information` VALUES ('126', '4', 9, '觉得自己能控制生活中的一些恼怒情绪', '9', 2);
INSERT INTO `question_information` VALUES ('127', '4', 10, '觉得自己能安排一切', '9', 2);
INSERT INTO `question_information` VALUES ('128', '4', 11, '由于无法掌控发生的事情，感到生气了', '9', 2);
INSERT INTO `question_information` VALUES ('129', '4', 12, '认为自己必须完成某件事情了', '9', 2);
INSERT INTO `question_information` VALUES ('13', '1', 13, '【饮酒情况】是否饮酒', '1', 2);
INSERT INTO `question_information` VALUES ('130', '4', 13, '觉得自己能控制时间安排的方式', '9', 2);
INSERT INTO `question_information` VALUES ('131', '4', 14, '觉得困难积累得太大而无法克服', '9', 2);
INSERT INTO `question_information` VALUES ('132', '4', 1, '我因一些小事而烦恼', '10', 2);
INSERT INTO `question_information` VALUES ('133', '4', 2, '我在做事时很难集中精力', '10', 2);
INSERT INTO `question_information` VALUES ('134', '4', 3, '我感到情绪低落', '10', 2);
INSERT INTO `question_information` VALUES ('135', '4', 4, '我觉得做任何事都很费劲', '10', 2);
INSERT INTO `question_information` VALUES ('136', '4', 5, '我对未来充满希望', '10', 2);
INSERT INTO `question_information` VALUES ('137', '4', 6, '我感到害怕', '10', 2);
INSERT INTO `question_information` VALUES ('138', '4', 7, '我的睡眠不好', '10', 2);
INSERT INTO `question_information` VALUES ('139', '4', 8, '我很愉快', '10', 2);
INSERT INTO `question_information` VALUES ('14', '1', 14, '【饮酒情况】开始饮酒年龄', '1', 6);
INSERT INTO `question_information` VALUES ('140', '4', 9, '我感到孤独', '10', 2);
INSERT INTO `question_information` VALUES ('141', '4', 10, '我觉得我无法继续我的生活', '10', 2);
INSERT INTO `question_information` VALUES ('142', '4', 1, '我经常感到不安、担心或急切', '11', 2);
INSERT INTO `question_information` VALUES ('143', '4', 2, '我总是不能停止或无法控制担心', '11', 2);
INSERT INTO `question_information` VALUES ('144', '4', 3, '我对各种各样的事情担忧过多', '11', 2);
INSERT INTO `question_information` VALUES ('145', '4', 4, '我很紧张，无法放松', '11', 2);
INSERT INTO `question_information` VALUES ('146', '4', 5, '我非常焦躁，以至无法静坐', '11', 2);
INSERT INTO `question_information` VALUES ('147', '4', 6, '我最近变得很易怒或躁动', '11', 2);
INSERT INTO `question_information` VALUES ('148', '4', 7, '我总是担忧会有不祥的事情发生', '11', 2);
INSERT INTO `question_information` VALUES ('15', '1', 15, '【饮酒情况】戒酒年龄', '1', 6);
INSERT INTO `question_information` VALUES ('16', '2', 1, '您目前是否外出工作? ', '2', 2);
INSERT INTO `question_information` VALUES ('17', '2', 2, '在过去7天内，您在工作中有几天参加了重体力活动(如搬运重物、挖掘、爬楼梯等)且持续时间超过10分钟?(注意不包括工作以外的活动)', '2', 2);
INSERT INTO `question_information` VALUES ('18', '2', 3, '在工作中，每天花多长时间进行重体力活动', '2', 6);
INSERT INTO `question_information` VALUES ('19', '2', 4, '在过去7天内，您在工作中有几天参加了中度体力活动(如提拎小型物品等)且持续时间超过10分钟?(注意不包括工作以外的活动) ', '2', 2);
INSERT INTO `question_information` VALUES ('2', '1', 2, '过敏史', '1', 3);
INSERT INTO `question_information` VALUES ('20', '2', 5, '在工作中，每天花多长时间进行中度体力活动?', '2', 6);
INSERT INTO `question_information` VALUES ('21', '2', 6, '在过去7天内，您有几天工作中步行时间持续超过10分钟? (注意不包括上下班路上的步行时间) ', '2', 2);
INSERT INTO `question_information` VALUES ('22', '2', 7, '在工作中，每天花多长时间步行?', '2', 6);
INSERT INTO `question_information` VALUES ('23', '2', 8, '在过去7天内，您有几天乘车外出? ', '3', 2);
INSERT INTO `question_information` VALUES ('24', '2', 9, '每天乘车花多长时间?  ', '3', 6);
INSERT INTO `question_information` VALUES ('25', '2', 10, '在过去7天内，您有几天骑自行车外出，且持续时间超过10 分钟?', '3', 2);
INSERT INTO `question_information` VALUES ('26', '2', 11, '每天骑自行车花多长时间?', '3', 6);
INSERT INTO `question_information` VALUES ('27', '2', 12, '在过去7天内，您有几天步行外出，且持续时间超过10分钟?', '3', 2);
INSERT INTO `question_information` VALUES ('28', '2', 13, '每天步行花多长时间', '3', 6);
INSERT INTO `question_information` VALUES ('29', '2', 14, '在过去7天内，您有几天参与了重体力家务劳动(如搬运重物、砍柴、扫雪、拖地板等)且持续时间超过10分钟。', '4', 2);
INSERT INTO `question_information` VALUES ('3', '1', 3, '既往史（即疾病史或手术史）', '1', 3);
INSERT INTO `question_information` VALUES ('30', '2', 15, '每天花多长时间进行重体力家务劳动?', '4', 6);
INSERT INTO `question_information` VALUES ('31', '2', 16, '在过去7天内，您有几天参与了中度体力家务劳动(如提拎小型物品、扫地、擦窗户、整理房间、做饭、洗衣服等)且持续时间超过10分钟', '4', 2);
INSERT INTO `question_information` VALUES ('32', '2', 17, '每天花多长时间进行中度体力家务劳动?', '4', 6);
INSERT INTO `question_information` VALUES ('33', '2', 18, '在过去7天内，您有几天外出散步，且持续时间超过10分钟? (不包括您己描述过的步行时间)', '5', 2);
INSERT INTO `question_information` VALUES ('34', '2', 19, '每天花在散步中的时间是多少? ', '5', 6);
INSERT INTO `question_information` VALUES ('35', '2', 20, '在过去7天内，您有几天参加了重体力活动的体育锻炼(如有氧健身、跑步、快速骑车、游泳及足球、篮球类活动等)且持续时间超过10分钟?', '5', 2);
INSERT INTO `question_information` VALUES ('36', '2', 21, '每天花多长时间进行重体力活动体育锻炼? ', '5', 6);
INSERT INTO `question_information` VALUES ('37', '2', 22, '在过去7天内，您有几天参加了中度体力活动的体育锻炼(如快速行走、跳交谊舞、打保龄球、 乒乓球，羽毛球等)且持续时间超过10分钟?', '5', 2);
INSERT INTO `question_information` VALUES ('38', '2', 23, '每天花多长时间进行中度体力活动体育锻炼?', '5', 6);
INSERT INTO `question_information` VALUES ('39', '2', 24, '在过去7天内，您工作日每天花在坐姿状态中的时间是多少?', '6', 6);
INSERT INTO `question_information` VALUES ('4', '1', 4, '有无家族史（可单选可多选）', '1', 3);
INSERT INTO `question_information` VALUES ('40', '2', 25, '在过去7天内，您周末或休息日每天花在坐姿状态中的时间是多少?', '6', 6);
INSERT INTO `question_information` VALUES ('41', '2', 26, '在过去7天内，您在工作日每天花在睡眠(包括午睡)中的时间是多少?', '7', 6);
INSERT INTO `question_information` VALUES ('42', '2', 27, '在过去7天内，您在周末或休息日每天花在睡眠中的时间是多少? ', '7', 6);
INSERT INTO `question_information` VALUES ('43', '3', 1, '在过去的1年内，您有没有食用米饭', '8', 2);
INSERT INTO `question_information` VALUES ('44', '3', 2, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('45', '3', 3, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('46', '3', 4, '在过去的1年内，您有没有食用粥、稀饭或泡饭', '8', 2);
INSERT INTO `question_information` VALUES ('47', '3', 5, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('48', '3', 6, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('49', '3', 7, '在过去的1年内，您有没有食用面粉类食物（馒头、面包、面条、饼等）', '8', 2);
INSERT INTO `question_information` VALUES ('5', '1', 5, '有无以下的症状（可单选可多选）', '1', 3);
INSERT INTO `question_information` VALUES ('50', '3', 8, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('51', '3', 9, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('52', '3', 10, '在过去的1年内，您有没有食用甜食、点心、蛋糕', '8', 2);
INSERT INTO `question_information` VALUES ('53', '3', 11, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('54', '3', 12, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('55', '3', 13, '在过去的1年内，您有没有食用油炸食物（油条、油饼等）', '8', 2);
INSERT INTO `question_information` VALUES ('56', '3', 14, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('57', '3', 15, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('58', '3', 16, '在过去的1年内，您有没有食用有馅类食物（包子、馄饨、饺子等）', '8', 2);
INSERT INTO `question_information` VALUES ('59', '3', 17, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('6', '1', 6, '饮食习惯（可单选可多选）', '1', 3);
INSERT INTO `question_information` VALUES ('60', '3', 18, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('61', '3', 19, '在过去的1年内，您有没有食用食用粗杂粮（包括糙米、小米、玉米、薏米、燕麦、红小豆、绿豆等）', '8', 2);
INSERT INTO `question_information` VALUES ('62', '3', 20, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('63', '3', 21, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('64', '3', 22, '在过去的1年内，您有没有食用薯类（包括红薯、土豆、芋头、山药、魔芋等）', '8', 2);
INSERT INTO `question_information` VALUES ('65', '3', 23, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('66', '3', 24, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('67', '3', 25, '在过去的1年内，您有没有食用牛奶、酸奶或奶粉', '8', 2);
INSERT INTO `question_information` VALUES ('68', '3', 26, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('69', '3', 27, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('7', '1', 7, '【体育锻炼】在过去1年里，您一般多久参加1次体育锻炼', '1', 2);
INSERT INTO `question_information` VALUES ('70', '3', 28, '在过去的1年内，您有没有食用鸡蛋或鸭蛋', '8', 2);
INSERT INTO `question_information` VALUES ('71', '3', 29, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('72', '3', 30, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('73', '3', 31, '在过去的1年内，您有没有食用红肉类菜肴（猪、牛、羊肉等）', '8', 2);
INSERT INTO `question_information` VALUES ('74', '3', 32, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('75', '3', 33, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('76', '3', 34, '在过去的1年内，您有没有食用家禽类菜肴（鸡、鸭、鹅肉等）', '8', 2);
INSERT INTO `question_information` VALUES ('77', '3', 35, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('78', '3', 36, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('79', '3', 37, '在过去的1年内，您有没有食用加工肉制品（香肠、熏肉、午餐肉、火腿等）', '8', 2);
INSERT INTO `question_information` VALUES ('8', '1', 8, '【体育锻炼】您常用的锻炼方式（可单选可多选）', '1', 3);
INSERT INTO `question_information` VALUES ('80', '3', 38, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('81', '3', 39, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('82', '3', 40, '在过去的1年内，您有没有食用有河鲜类菜肴（青鱼、鲈鱼、鲢鱼、河虾等）', '8', 2);
INSERT INTO `question_information` VALUES ('83', '3', 41, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('84', '3', 42, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('85', '3', 43, '在过去的1年内，您有没有食用食用海鲜类菜肴（带鱼、鲳鱼、黄鱼、海虾等）', '8', 2);
INSERT INTO `question_information` VALUES ('86', '3', 44, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('87', '3', 45, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('88', '3', 46, '在过去的1年内，您有没有食用豆制品菜肴（豆腐、香干、素鸡等）', '8', 2);
INSERT INTO `question_information` VALUES ('89', '3', 47, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('9', '1', 9, '【吸烟情况】是否吸烟', '1', 2);
INSERT INTO `question_information` VALUES ('90', '3', 48, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('91', '3', 49, '在过去的1年内，您有没有食用坚果类（西瓜子、葵花子、核桃、花生、开心果等）', '8', 2);
INSERT INTO `question_information` VALUES ('92', '3', 50, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('93', '3', 51, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('94', '3', 52, '在过去的1年内，您有没有食用深色蔬菜类（青菜、菠菜、空心菜、番茄、青椒、胡萝卜等）', '8', 2);
INSERT INTO `question_information` VALUES ('95', '3', 53, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('96', '3', 54, '平均每次食用量', '8', 1);
INSERT INTO `question_information` VALUES ('97', '3', 55, '在过去的1年内，您有没有食用浅色蔬菜类（白菜、萝卜、黄瓜等）', '8', 2);
INSERT INTO `question_information` VALUES ('98', '3', 56, '食用的次数', '8', 2);
INSERT INTO `question_information` VALUES ('99', '3', 57, '平均每次食用量', '8', 1);

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
                                              `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                              PRIMARY KEY (`id`) USING BTREE,
                                              INDEX `questionnaire_information_idx_type`(`questionnaire_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questionnaire_information
-- ----------------------------
INSERT INTO `questionnaire_information` VALUES ('1', '指导语：下面是关于您一般情况的了解，请您根据实际情况，真实地回答下列问题，不要遗漏。请在符合自己的选项上打“√”；填空题的回答，请写在下划线上。谢谢合作!', '2022-03-10 13:30:49', '2022-03-13 14:41:07', 0, 1, '预计2~3分钟完成');
INSERT INTO `questionnaire_information` VALUES ('2', '指导语：请回顾一下过去7天您的体力活动情况，包括日常工作、日常生活、日常交通、运动锻炼以及休闲娱乐的情况。重体力活动是指那些使您呼吸心跳明显加快的活动，中度体力活动是指那些使您呼吸心跳略微加快的活动。请在符合自己的选项上打“√”；填空题的回答，请写在下划线上。谢谢合作!', '2022-03-13 13:29:46', '2022-03-13 14:41:11', 0, 2, '预计3~4分钟完成');
INSERT INTO `questionnaire_information` VALUES ('3', '指导语：请回顾一下过去12个月里，你是否吃过以下食物，并评估这些食物的平均食用量和次数，并填写问卷，谢谢合作!', '2022-03-13 14:41:28', '2022-03-13 15:23:14', 0, 3, '预计5~-6分钟完成');
INSERT INTO `questionnaire_information` VALUES ('4', '指导语：请根据您近一个月的感受（而不仅仅是今天的感受），选择 1个最能反映您感受的答案，在选项上打 “√”。', '2022-03-13 15:23:10', '2022-03-13 15:23:10', 0, 4, '预计3~4分钟完成');

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
INSERT INTO `sys_dict` VALUES ('1', 'questionnaire_information', 1, '基本情况问卷', 1, 'questionnaire_type');
INSERT INTO `sys_dict` VALUES ('10', 'user_information', 2, '大学本科', 3, 'educational_level');
INSERT INTO `sys_dict` VALUES ('11', 'user_information', 3, '大学专科和专科学校', 4, 'educational_level');
INSERT INTO `sys_dict` VALUES ('12', 'user_information', 4, '中等专业学校', 5, 'educational_level');
INSERT INTO `sys_dict` VALUES ('13', 'user_information', 5, '技工学校', 6, 'educational_level');
INSERT INTO `sys_dict` VALUES ('14', 'user_information', 6, '高中', 7, 'educational_level');
INSERT INTO `sys_dict` VALUES ('15', 'user_information', 7, '初中', 8, 'educational_level');
INSERT INTO `sys_dict` VALUES ('16', 'user_information', 8, '小学', 9, 'educational_level');
INSERT INTO `sys_dict` VALUES ('17', 'user_information', 9, '文盲或半文盲', 10, 'educational_level');
INSERT INTO `sys_dict` VALUES ('18', 'user_information', 10, '不详', 11, 'educational_level');
INSERT INTO `sys_dict` VALUES ('19', 'user_information', 1, '国家机关、党群组织、企业、事业单位负责人', 1, 'occupation');
INSERT INTO `sys_dict` VALUES ('2', 'questionnaire_information', 2, '体力活动测评', 2, 'questionnaire_type');
INSERT INTO `sys_dict` VALUES ('20', 'user_information', 2, '专业技术人员', 2, 'occupation');
INSERT INTO `sys_dict` VALUES ('21', 'user_information', 3, '办事人员和有关人员', 3, 'occupation');
INSERT INTO `sys_dict` VALUES ('22', 'user_information', 4, '商业、服务业人员', 4, 'occupation');
INSERT INTO `sys_dict` VALUES ('23', 'user_information', 5, '农、林、牧、渔、水利业生产人员', 5, 'occupation');
INSERT INTO `sys_dict` VALUES ('24', 'user_information', 6, '生产、运输设备操作人员及有关人员', 6, 'occupation');
INSERT INTO `sys_dict` VALUES ('25', 'user_information', 7, '军人', 7, 'occupation');
INSERT INTO `sys_dict` VALUES ('26', 'user_information', 8, '不便分类的其他从业人员', 8, 'occupation');
INSERT INTO `sys_dict` VALUES ('27', 'user_information', 9, '无职业', 9, 'occupation');
INSERT INTO `sys_dict` VALUES ('28', 'question_information', 1, '一、一般情况', 1, 'big_label');
INSERT INTO `sys_dict` VALUES ('29', 'question_information', 2, '一、日常工作', 2, 'big_label');
INSERT INTO `sys_dict` VALUES ('3', 'questionnaire_information', 3, '食物频率测评', 3, 'questionnaire_type');
INSERT INTO `sys_dict` VALUES ('30', 'question_information', 3, '二、日常交通', 3, 'big_label');
INSERT INTO `sys_dict` VALUES ('31', 'question_information', 4, '三、日常生活', 4, 'big_label');
INSERT INTO `sys_dict` VALUES ('32', 'question_information', 5, '四、运动锻炼及休闲娱乐', 5, 'big_label');
INSERT INTO `sys_dict` VALUES ('33', 'question_information', 6, '五、坐姿时间', 6, 'big_label');
INSERT INTO `sys_dict` VALUES ('34', 'question_information', 7, '六、睡眠时间', 7, 'big_label');
INSERT INTO `sys_dict` VALUES ('35', 'question_information', 8, '一、一般食物', 8, 'big_label');
INSERT INTO `sys_dict` VALUES ('36', 'question_information', 9, '一、', 9, 'big_label');
INSERT INTO `sys_dict` VALUES ('37', 'question_information', 10, '二、', 10, 'big_label');
INSERT INTO `sys_dict` VALUES ('38', 'question_infotmation', 11, '三、', 11, 'big_label');
INSERT INTO `sys_dict` VALUES ('4', 'questionnaire_information', 4, '情绪体验', 4, 'questionnaire_type');
INSERT INTO `sys_dict` VALUES ('5', 'user_information', 0, '女', 2, 'sex');
INSERT INTO `sys_dict` VALUES ('6', 'user_information', 1, '男', 1, 'sex');
INSERT INTO `sys_dict` VALUES ('7', 'user_information', 1, '管理员', 1, 'identity');
INSERT INTO `sys_dict` VALUES ('8', 'user_information', 0, '普通用户', 2, 'identity');
INSERT INTO `sys_dict` VALUES ('9', 'user_information', 1, '研究生', 1, 'educational_level');

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
                                     `sex` int(1) DEFAULT NULL COMMENT '0:female 1:male',
                                     `date_of_birth` date DEFAULT NULL COMMENT '出生日期',
                                     `weight` decimal(6, 2) DEFAULT NULL,
                                     `height` decimal(6, 2) DEFAULT NULL,
                                     `educational_level` int(6) DEFAULT NULL COMMENT '文化程度',
                                     `occupation` int(6) DEFAULT NULL COMMENT '职业',
                                     PRIMARY KEY (`id`, `telphone_num`) USING BTREE,
                                     UNIQUE INDEX `user_idx_telphone_num`(`telphone_num`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES ('1', 'admin', 1, '1', 1, 1, '2022-03-10', 80.00, 180.00, 1, 4);
INSERT INTO `user_information` VALUES ('2', 'dd', 2, '2', 1, 0, '2020-06-12', 60.00, 168.00, 2, 3);

SET FOREIGN_KEY_CHECKS = 1;
