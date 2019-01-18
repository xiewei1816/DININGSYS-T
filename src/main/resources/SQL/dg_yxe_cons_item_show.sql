/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 192.168.2.48:3306
Source Database       : diningsys_clear

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-29 09:12:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dg_yxe_cons_item_show`
-- ----------------------------
DROP TABLE IF EXISTS `dg_yxe_cons_item_show`;
CREATE TABLE `dg_yxe_cons_item_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cons_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_yxe_cons_item_show
-- ----------------------------
INSERT INTO `dg_yxe_cons_item_show` VALUES ('5', '18', '0');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('6', '18', '100');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('7', '18', '104');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('8', '18', '933');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('9', '18', '105');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('10', '18', '934');
INSERT INTO `dg_yxe_cons_item_show` VALUES ('11', '18', '936');
