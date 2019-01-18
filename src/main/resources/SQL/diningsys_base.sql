/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 192.168.2.48:3306
Source Database       : diningsys_base

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-30 09:22:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bg_item_custom_money_name`
-- ----------------------------
DROP TABLE IF EXISTS `bg_item_custom_money_name`;
CREATE TABLE `bg_item_custom_money_name` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `custom_money_name` varchar(20) DEFAULT NULL COMMENT '自定义金额名称',
  `item_code` int(11) DEFAULT NULL COMMENT '自定义编码index',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bg_item_custom_money_name
-- ----------------------------

-- ----------------------------
-- Table structure for `desk_business_setting`
-- ----------------------------
DROP TABLE IF EXISTS `desk_business_setting`;
CREATE TABLE `desk_business_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seatServ` varchar(2000) DEFAULT NULL,
  `billServ` varchar(2000) DEFAULT NULL,
  `printerSetting` varchar(2000) DEFAULT NULL,
  `serialRul` varchar(2000) DEFAULT NULL,
  `loungeSetting` varchar(2000) DEFAULT NULL,
  `uuid_key` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of desk_business_setting
-- ----------------------------
INSERT INTO `desk_business_setting` VALUES ('10', '{\"isCanModifyServerMan\":\"\",\"isManualInputOrderNum\":\"\",\"defaultWaiter\":\"waiter\",\"isUnsubscribeReasonNeed\":\"\",\"isUseGiftOrderReason\":\"\",\"isUseGiftOrderReasonNeed\":\"\",\"isRetainRoomReserve\":\"\",\"isNewOrderNeedForegift\":\"\",\"isReserveForegiftToDeposit\":\"\",\"isAutoShowBusinessInfo\":\"\",\"isAutoInsertDeskLabelToSettlementRemarks\":\"\",\"staffCardAuthorizType\":\"IC卡\",\"isAllowManualInputAuthorizNumber\":\"\",\"isGetPosNumberByIp\":\"\",\"isOnlyAllowModifyIntradayData\":\"\",\"isChangeSeatOrServerCorrespond\":\"\",\"isNormalMealMustOptionsNumber\":\"\"}', '{\"isForegiftInSettlement\":\"\",\"isNoLimitDiscountScheme\":\"\",\"isPrintVoucherToGetInvoice\":\"\",\"isNoForceInputInvoiceNumberAndMoney\":\"\",\"isAllowNegativeNumberInQuotaDiscount\":\"\",\"isQuotaDiscountAndNoSmallChangeApportionToItems\":\"\",\"isLimitMemCardConsumeArea\":\"\",\"isQuotaDiscountToItemsAsPossible\":\"\",\"isUploadConsumeDetailWhenUnpaid\":\"\",\"isNoServiceChargeNoSettlement\":\"\",\"isDiscountCardNoUsePromotionItems\":\"\",\"isServiceChargeNoDiscount\":\"\",\"isOnlyOffWork\":\"\",\"isSendSmsOffWork\":\"\",\"resaveSmsNumber\":\"\",\"noSmallChangeWay\":\"3\",\"noSmallChangePlace\":\"1\"}', '{}', '{\"dbsJournalBusiness\":{\"isNeedPrefix\":\"1\",\"prefix\":\"YY\",\"isNeedOrganCode\":\"\",\"isNeedDateString\":\"1\",\"dateFormat\":\"0\",\"isOrderBeginWithOne\":\"1\"},\"dbsJournalSettlement\":{\"isNeedPrefix\":\"1\",\"prefix\":\"JS\",\"isNeedOrganCode\":\"\",\"isNeedDateString\":\"1\",\"dateFormat\":\"0\",\"isOrderBeginWithOne\":\"1\"},\"dbsJournalOffwork\":{\"isNeedPrefix\":\"1\",\"prefix\":\"JB\",\"isNeedOrganCode\":\"\",\"isNeedDateString\":\"1\",\"dateFormat\":\"0\",\"isOrderBeginWithOne\":\"1\"}}', '{}', '95e96a41-ca1c-44a5-a9d2-2f0805df43a6');

-- ----------------------------
-- Table structure for `desk_business_setting_serialrul`
-- ----------------------------
DROP TABLE IF EXISTS `desk_business_setting_serialrul`;
CREATE TABLE `desk_business_setting_serialrul` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maxNum` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `flowType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of desk_business_setting_serialrul
-- ----------------------------
INSERT INTO `desk_business_setting_serialrul` VALUES ('1', '1', '2018-01-16 09:50:11', 'YY');
INSERT INTO `desk_business_setting_serialrul` VALUES ('2', '1', '2018-01-16 09:50:06', 'JS');
INSERT INTO `desk_business_setting_serialrul` VALUES ('3', '3', '2017-12-05 16:32:56', 'JB');
INSERT INTO `desk_business_setting_serialrul` VALUES ('4', '2', '2018-01-16 09:51:51', 'FW');
INSERT INTO `desk_business_setting_serialrul` VALUES ('5', '3', '2017-12-06 09:57:23', 'YD');
INSERT INTO `desk_business_setting_serialrul` VALUES ('6', '12508', '2018-01-16 09:50:11', 'TDHM');

-- ----------------------------
-- Table structure for `dg_allow_number`
-- ----------------------------
DROP TABLE IF EXISTS `dg_allow_number`;
CREATE TABLE `dg_allow_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客位容纳人数表',
  `name` varchar(11) DEFAULT NULL COMMENT '名字',
  `min_allow_number` int(11) DEFAULT NULL COMMENT '最小容纳人数',
  `max_allow_number` int(11) DEFAULT NULL COMMENT '最大容纳人数',
  `uuid_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_allow_number
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_area_limit_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_area_limit_item`;
CREATE TABLE `dg_area_limit_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  `item_id` int(11) DEFAULT NULL COMMENT '限售品项id',
  `item_code` varchar(30) DEFAULT NULL COMMENT '限售品项代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_area_limit_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_area_manager`
-- ----------------------------
DROP TABLE IF EXISTS `dg_area_manager`;
CREATE TABLE `dg_area_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sj_discount` int(11) DEFAULT NULL COMMENT '时价品项允许打折',
  `cx_discount` int(11) DEFAULT NULL COMMENT '促销品项允许打折',
  `bj_discount` int(11) DEFAULT NULL COMMENT '变价品项允许打折',
  `bff_discount` int(11) DEFAULT NULL COMMENT '包房费允许打折',
  `fwf_discount` int(11) DEFAULT NULL COMMENT '服务费允许打折',
  `zdxf_discount` int(11) DEFAULT NULL COMMENT '最低消费允许打折',
  `bff_free` int(11) DEFAULT NULL COMMENT '包房费免费时长',
  `bff_time_free` int(11) DEFAULT NULL COMMENT '包房费多长时间内免费',
  `bff_surplus_remind` int(11) DEFAULT NULL COMMENT '包房剩余时间提醒',
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_area_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_call_service`
-- ----------------------------
DROP TABLE IF EXISTS `dg_call_service`;
CREATE TABLE `dg_call_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seat_id` int(11) DEFAULT NULL COMMENT '客位id',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `content` varchar(1000) DEFAULT NULL COMMENT '呼叫服务内容',
  `state` int(11) DEFAULT '1' COMMENT '初始化状态',
  `type` int(11) DEFAULT NULL COMMENT '类型 1为呼叫服务  2表示易小二结算成功  10蜀国大师会员积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_call_service
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_card`
-- ----------------------------
DROP TABLE IF EXISTS `dg_card`;
CREATE TABLE `dg_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cardnum` varchar(255) NOT NULL,
  `consumerid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_card
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_cash_pledge`
-- ----------------------------
DROP TABLE IF EXISTS `dg_cash_pledge`;
CREATE TABLE `dg_cash_pledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '押金记录表',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '开单编号',
  `cp_type` varchar(36) DEFAULT NULL COMMENT '押金类型 - 0-登记押金；1-退押金',
  `cp_currency` varchar(11) DEFAULT NULL COMMENT '币种',
  `cp_money` double DEFAULT NULL COMMENT '金额',
  `conver_money` double DEFAULT NULL COMMENT '换算金额',
  `reg_time` datetime DEFAULT NULL COMMENT '登记时间',
  `ref_info` varchar(225) DEFAULT NULL COMMENT '参考信息',
  `remark` varchar(225) DEFAULT NULL COMMENT '备注',
  `print_number` int(11) DEFAULT '0' COMMENT '打印次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_cash_pledge
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_commons_variable`
-- ----------------------------
DROP TABLE IF EXISTS `dg_commons_variable`;
CREATE TABLE `dg_commons_variable` (
  `cv_code` varchar(100) DEFAULT NULL COMMENT '标志代码',
  `cv_value` varchar(100) DEFAULT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公共设置';

-- ----------------------------
-- Records of dg_commons_variable
-- ----------------------------
INSERT INTO `dg_commons_variable` VALUES ('current_open_flush', '5');
INSERT INTO `dg_commons_variable` VALUES ('current_open_water_flush', '10');

-- ----------------------------
-- Table structure for `dg_consumer_seat`
-- ----------------------------
DROP TABLE IF EXISTS `dg_consumer_seat`;
CREATE TABLE `dg_consumer_seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allow_number_id` int(11) DEFAULT NULL COMMENT '客位容纳人数标准ID',
  `number` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `all_name` varchar(500) DEFAULT NULL,
  `seat_type` int(11) NOT NULL,
  `allow_number` int(11) NOT NULL DEFAULT '0' COMMENT '容纳人数',
  `default_state` int(11) NOT NULL DEFAULT '0',
  `cons_area` int(11) NOT NULL,
  `mnemonic` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `explains` text,
  `disabled_seat` int(11) NOT NULL DEFAULT '0',
  `inset_retention_room` int(11) NOT NULL DEFAULT '0',
  `immediate_settlement` int(11) NOT NULL DEFAULT '0',
  `not_statistics_overtaiwan` int(11) NOT NULL DEFAULT '0',
  `empty_guest` int(11) NOT NULL DEFAULT '0',
  `customer_retention` int(11) NOT NULL DEFAULT '0' COMMENT '留客（用于到客等候排队）（0否 1是）',
  `create_user` varchar(200) NOT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0',
  `seat_state` int(11) DEFAULT '1' COMMENT '客位状态。1空闲、2占用、3清扫、4预定、5埋单',
  `vip_identify` int(11) DEFAULT NULL COMMENT 'VIP客房，0非，1是',
  `seat_label` varchar(200) DEFAULT NULL COMMENT '客座标签',
  `last_open_time` datetime DEFAULT NULL COMMENT '客座最近的开台时间',
  `uuid_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_consumer_seat
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_consumption_area`
-- ----------------------------
DROP TABLE IF EXISTS `dg_consumption_area`;
CREATE TABLE `dg_consumption_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `mnemonic` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `explains` text,
  `create_user` varchar(200) NOT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0',
  `uuid_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_consumption_area
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_desk_business_setting_leftmenu`
-- ----------------------------
DROP TABLE IF EXISTS `dg_desk_business_setting_leftmenu`;
CREATE TABLE `dg_desk_business_setting_leftmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `child_count` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `murl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_desk_business_setting_leftmenu
-- ----------------------------
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('1', '前台营业设置', '6', null, '', '');
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('8', '客位服务', '0', '1', '', '/DININGSYS/deskbusinesssetting/toSeetServ');
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('9', '账单服务', '0', '1', '', '/DININGSYS/ProDiscountPan/allowDiscountPlan');
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('10', '打印服务', '0', '1', '', '/DININGSYS/ProImportant/index');
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('11', '单据流水号生成规则', '0', '1', '', '/DININGSYS/ProImportant/index');
INSERT INTO `dg_desk_business_setting_leftmenu` VALUES ('12', '雅座设置', '0', '1', '', '/DININGSYS/ProImportant/index');

-- ----------------------------
-- Table structure for `dg_emp_area_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `dg_emp_area_statistics`;
CREATE TABLE `dg_emp_area_statistics` (
  `emp_code` varchar(20) DEFAULT NULL COMMENT '员工code',
  `area_code` varchar(20) DEFAULT NULL COMMENT '区域code'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工查看消费区域统计信息权限';

-- ----------------------------
-- Records of dg_emp_area_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_flavor`
-- ----------------------------
DROP TABLE IF EXISTS `dg_flavor`;
CREATE TABLE `dg_flavor` (
  `id` int(11) NOT NULL,
  `number` varchar(40) DEFAULT NULL,
  `zjf` varchar(40) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `px` int(11) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  `createtime` varchar(20) DEFAULT NULL,
  `isonly` varchar(10) DEFAULT '0',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_flavor
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_for_meal_time_price`
-- ----------------------------
DROP TABLE IF EXISTS `dg_for_meal_time_price`;
CREATE TABLE `dg_for_meal_time_price` (
  `id` int(11) NOT NULL COMMENT '主键',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_for_meal_time_price
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_for_meal_time_price_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_for_meal_time_price_s`;
CREATE TABLE `dg_for_meal_time_price_s` (
  `id` int(11) NOT NULL,
  `meal_time_id` int(11) DEFAULT NULL COMMENT '市别id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `meal_time_price_id` int(11) DEFAULT NULL COMMENT '市别价格id',
  `price` double DEFAULT NULL COMMENT '品项市别价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_for_meal_time_price_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_front_desk_oper_qx`
-- ----------------------------
DROP TABLE IF EXISTS `dg_front_desk_oper_qx`;
CREATE TABLE `dg_front_desk_oper_qx` (
  `code` varchar(30) NOT NULL COMMENT '权限编码',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `val` int(11) DEFAULT '0' COMMENT '权限值(0/1)',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_front_desk_oper_qx
-- ----------------------------
INSERT INTO `dg_front_desk_oper_qx` VALUES ('bjfp_qx', '补开发票', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('btpd_qx', '吧台盘点', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ckdqyyqk_qx', '查看当前营业情况', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('cxmd_qx', '撤销埋单', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('cxzz_qx', '撤销转账', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('cz_qx', '拆帐', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('deyh_qx', '定额优惠权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('dfpkssbdsjk_qx', '导发票库时删本地数据库', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('dpzt_qx', '单品转台', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('emp_area_sta_qx', '启用员工查看消费区域统计信息权限', '0');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('fhygzjs_qx', '非会员挂账结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('fkxz_qx', '付款修正权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('fwjs_qx', '返位结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('gbff_qx', '改包房费', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('gfwf_qx', '改服务费', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ggrs_qx', '更改人数', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ghcmp_qx', '更换触摸屏Pos', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ghkw_qx', '更换客位', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ghkz_qx', '更换客座', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('hygzjs_qx', '会员挂单结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('hykjs_qx', '会员卡结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jb_qx', '结班', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jdbcd_qx', '加单不厨打', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jdsyxslwfs_qx', '加单时,允许数量为负数', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jdxglspx_qx', '加单修改临时品项', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jrtd_qx', '加入团队', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jsewmzfhx_qx', '结算二维码支付核销', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jsrs_qx', '减少人数', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('jsscewmzf_qx', '结算删除二维码支付', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('js_qx', '解锁', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ktssdzxydd_qx', '开台时,锁定执行预订单', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('lxyztgq_qx', '离线验证团购券', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('mbff_qx', '免包房费', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('mfwf_qx', '免服务费', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('mzdxf_qx', '免最低消费', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('pcceqx_yhtc_qx', '配餐超额权限(宴会套餐)', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('pd_qx', '跑单权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('pxbj_qx', '品项变价', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('pxgqxl_qx', '品项沽清/限量', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qhfpk_qx', '切换发票库', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qjs_qx', '券结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qt1js_qx', '其他1结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qt2js_qx', '其他2结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qxhc_qx', '取消划菜', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('qyhykdz_qx', '启用会员卡打折', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('sp_qx', '锁屏', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('tctd_qx', '退出团队', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('tcxt_qx', '退出系统', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('tdbcd_qx', '退单不厨打', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('td_qx', '退单权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('tlspx_qx', '退临时品项', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xgdpyh_qx', '修改单品优惠', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xglbyh_qx', '修改类别优惠', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xgsl_qx', '修改数量', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xgtyshdyhbl_qx', '修改特约商户的优惠比例', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xgyhtc_qx', '修改宴会套餐权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xgzqje_qx', '修改赠券金额', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('xykjs_qx', '信用卡结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ycqtzkdhhm_qx', '隐藏前台咨客电话号码', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('yhcpxdtd_qx', '已划菜品项的退单', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('yjdj_qx', '押金登记', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('yjzdxg_qx', '已结账单修改', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('yq_qx', '宴请权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('ysq_qx', '预授权权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('yxjd_qx', '允许加单', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zj_qx', '赠券', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zpjs_qx', '支票结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zslspx_qx', '赠送临时品项', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zs_qx', '赠送权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zwzjs_qx', '转外账结算权限', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zxyd_qx', '执行预定', '1');
INSERT INTO `dg_front_desk_oper_qx` VALUES ('zz_qx', '转账权限', '1');

-- ----------------------------
-- Table structure for `dg_gate_item_allow_custom`
-- ----------------------------
DROP TABLE IF EXISTS `dg_gate_item_allow_custom`;
CREATE TABLE `dg_gate_item_allow_custom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `small_gate_id` int(11) DEFAULT NULL COMMENT '小类id',
  `allow_custom` int(11) DEFAULT NULL COMMENT '是否允许自定义金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_gate_item_allow_custom
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_gift_form`
-- ----------------------------
DROP TABLE IF EXISTS `dg_gift_form`;
CREATE TABLE `dg_gift_form` (
  `id` int(11) DEFAULT NULL,
  `gfcode` varchar(10) NOT NULL COMMENT '编码',
  `gfname` varchar(20) DEFAULT NULL COMMENT '名称',
  `gfreason` int(11) DEFAULT NULL COMMENT '赠单原因',
  `create_time` datetime DEFAULT NULL,
  `gfdescription` varchar(100) DEFAULT NULL COMMENT '说明',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赠单原因';

-- ----------------------------
-- Records of dg_gift_form
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_gift_form_reason`
-- ----------------------------
DROP TABLE IF EXISTS `dg_gift_form_reason`;
CREATE TABLE `dg_gift_form_reason` (
  `id` int(11) DEFAULT NULL,
  `gfcode` varchar(10) NOT NULL COMMENT '编码',
  `gfrtype` varchar(20) DEFAULT NULL COMMENT '赠单原因类型',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赠单原因表';

-- ----------------------------
-- Records of dg_gift_form_reason
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_goods_consign`
-- ----------------------------
DROP TABLE IF EXISTS `dg_goods_consign`;
CREATE TABLE `dg_goods_consign` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '吧台管理-物品寄存ID',
  `client_name` varchar(12) DEFAULT NULL COMMENT '客户名称',
  `client_phone` varchar(13) DEFAULT NULL COMMENT '客户电话',
  `client_seat` varchar(18) DEFAULT NULL COMMENT '客位',
  `goods_type` int(11) DEFAULT NULL COMMENT '物品寄存种类  对应 dg_goods_type表id',
  `goods_code` varchar(12) DEFAULT NULL COMMENT '物品编号',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '物品名称',
  `goods_number` int(11) DEFAULT NULL COMMENT '数量',
  `goods_specification` varchar(24) DEFAULT NULL COMMENT '规格',
  `goods_color` varchar(8) DEFAULT NULL COMMENT '颜色',
  `goods_expiration_date` date DEFAULT NULL COMMENT '保质截止日期',
  `goods_explain` varchar(64) DEFAULT NULL COMMENT '说明',
  `gc_flag` varchar(8) DEFAULT NULL COMMENT '寄存状态 1-寄存；2-取出；3-处理',
  `gc_pos` varchar(16) DEFAULT NULL COMMENT '寄存操作POS',
  `gc_operator` varchar(12) DEFAULT NULL COMMENT '寄存操作员',
  `gc_start_time` datetime DEFAULT NULL COMMENT '寄存时间',
  `gc_end_time` datetime DEFAULT NULL COMMENT '寄存截止时间',
  `gc_address` varchar(36) DEFAULT NULL COMMENT '寄存位置',
  `qz_time` datetime DEFAULT NULL COMMENT '取走时间',
  `qz_pos` varchar(16) DEFAULT NULL COMMENT '取走操作POS',
  `qz_operator` varchar(12) DEFAULT NULL COMMENT '取走操作员',
  `cl_way` varchar(12) DEFAULT NULL COMMENT '处理方式',
  `cl_pos` varchar(16) DEFAULT NULL COMMENT '处理操作POS',
  `cl_operator` varchar(12) DEFAULT NULL COMMENT '处理操作员',
  `cl_explain` varchar(64) DEFAULT NULL COMMENT '处理说明',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否删除 0默认-未删 1-已删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_goods_consign
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `dg_goods_type`;
CREATE TABLE `dg_goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '寄存物品种类表',
  `gt_name` varchar(12) DEFAULT NULL COMMENT '物品种类名称',
  `is_remind` varchar(2) DEFAULT NULL COMMENT '结算时是否提醒 0-不提醒，1-提醒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_important_acitivity_discount_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_important_acitivity_discount_s`;
CREATE TABLE `dg_important_acitivity_discount_s` (
  `id` int(11) NOT NULL,
  `gate_id` int(11) DEFAULT NULL COMMENT '小类id',
  `discount` int(11) DEFAULT NULL COMMENT '对应打折比例',
  `p_id` int(11) DEFAULT NULL COMMENT '活动id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_important_acitivity_discount_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_important_activity_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_important_activity_discount`;
CREATE TABLE `dg_important_activity_discount` (
  `id` int(11) NOT NULL,
  `organ_id` int(11) DEFAULT NULL COMMENT '组织机构id',
  `name` varchar(40) DEFAULT NULL COMMENT '活动名称',
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `dustbin` int(11) DEFAULT NULL COMMENT '是否进入垃圾箱',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用',
  `discount` int(11) DEFAULT NULL COMMENT '统一折扣比例',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_important_activity_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_depamaterial`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_depamaterial`;
CREATE TABLE `dg_inve_depamaterial` (
  `id` varchar(36) NOT NULL,
  `mateType` varchar(20) DEFAULT NULL,
  `wareID` varchar(36) DEFAULT NULL,
  `inveId` varchar(36) DEFAULT NULL,
  `serialNumber` varchar(64) DEFAULT NULL,
  `depaId` varchar(36) DEFAULT NULL,
  `sinceNumber` varchar(32) DEFAULT NULL,
  `dateTime` varchar(64) DEFAULT NULL,
  `itemId` varchar(36) DEFAULT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `origPrice` decimal(18,2) DEFAULT NULL,
  `presPrice` decimal(18,2) DEFAULT NULL,
  `number` decimal(18,2) DEFAULT NULL,
  `sumAmount` decimal(18,2) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_depamaterial
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_discpoint`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_discpoint`;
CREATE TABLE `dg_inve_discpoint` (
  `id` varchar(36) NOT NULL,
  `wareID` varchar(36) DEFAULT NULL,
  `inveId` varchar(36) DEFAULT NULL,
  `serialNumber` varchar(64) DEFAULT NULL,
  `sinceNumber` varchar(32) DEFAULT NULL,
  `dateTime` varchar(64) DEFAULT NULL,
  `itemId` varchar(36) DEFAULT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `origPrice` decimal(18,2) DEFAULT NULL,
  `presPrice` decimal(18,2) DEFAULT NULL,
  `number` decimal(18,2) DEFAULT NULL,
  `diffNum` decimal(18,2) DEFAULT NULL,
  `diffAmount` decimal(18,2) DEFAULT NULL,
  `sumAmount` decimal(18,2) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_discpoint
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_inventory`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_inventory`;
CREATE TABLE `dg_inve_inventory` (
  `id` varchar(36) NOT NULL,
  `wareID` varchar(36) NOT NULL,
  `serialNumber` varchar(64) DEFAULT NULL,
  `itemId` varchar(36) NOT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) NOT NULL,
  `number` decimal(18,2) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_items`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_items`;
CREATE TABLE `dg_inve_items` (
  `id` varchar(36) NOT NULL,
  `itemNo` varchar(36) DEFAULT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `price` decimal(18,0) DEFAULT NULL,
  `itemTypeId` varchar(36) DEFAULT NULL,
  `state` varchar(9) DEFAULT NULL,
  `minStorage` int(9) DEFAULT NULL,
  `maxStorage` int(9) DEFAULT NULL,
  `inCode` varchar(10) DEFAULT NULL,
  `spellCode` varchar(16) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  `outWareId` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_items
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_itemtype`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_itemtype`;
CREATE TABLE `dg_inve_itemtype` (
  `id` varchar(36) NOT NULL,
  `itemTypeNo` varchar(36) DEFAULT NULL,
  `itemTypeName` varchar(32) NOT NULL,
  `state` varchar(9) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_itemtype
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_procure`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_procure`;
CREATE TABLE `dg_inve_procure` (
  `id` varchar(36) NOT NULL,
  `procType` varchar(20) DEFAULT NULL,
  `inveId` varchar(36) DEFAULT NULL,
  `wareID` varchar(36) DEFAULT NULL,
  `serialNumber` varchar(64) DEFAULT NULL,
  `supplierId` varchar(36) DEFAULT NULL,
  `sinceNumber` varchar(32) DEFAULT NULL,
  `dateTime` varchar(64) DEFAULT NULL,
  `itemId` varchar(36) DEFAULT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `origPrice` decimal(18,2) DEFAULT NULL,
  `presPrice` decimal(18,2) DEFAULT NULL,
  `number` decimal(18,2) DEFAULT NULL,
  `sumAmount` decimal(18,2) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_procure
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_supplier`;
CREATE TABLE `dg_inve_supplier` (
  `id` varchar(36) NOT NULL,
  `superNo` varchar(9) DEFAULT NULL,
  `supName` varchar(32) NOT NULL,
  `contactName` varchar(20) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `state` varchar(9) NOT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_transfers`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_transfers`;
CREATE TABLE `dg_inve_transfers` (
  `id` varchar(36) NOT NULL,
  `outWareId` varchar(36) DEFAULT NULL,
  `inWareId` varchar(36) DEFAULT NULL,
  `serialNumber` varchar(64) DEFAULT NULL,
  `sinceNumber` varchar(32) DEFAULT NULL,
  `dateTime` varchar(64) DEFAULT NULL,
  `itemId` varchar(36) DEFAULT NULL,
  `itemName` varchar(32) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `origPrice` decimal(18,2) DEFAULT NULL,
  `presPrice` decimal(18,2) DEFAULT NULL,
  `number` decimal(18,2) DEFAULT NULL,
  `sumAmount` decimal(18,2) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_transfers
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_inve_warehouse`
-- ----------------------------
DROP TABLE IF EXISTS `dg_inve_warehouse`;
CREATE TABLE `dg_inve_warehouse` (
  `id` varchar(36) NOT NULL,
  `wareNo` varchar(9) DEFAULT NULL,
  `wareName` varchar(32) NOT NULL,
  `manageName` varchar(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `state` varchar(9) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_inve_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_current_price`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_current_price`;
CREATE TABLE `dg_item_current_price` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项代码',
  `current_price` double DEFAULT NULL COMMENT '时价',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_current_price
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_custom_money`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_custom_money`;
CREATE TABLE `dg_item_custom_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项代码',
  `custom_money_id` int(11) DEFAULT NULL COMMENT '允许自定义金额id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_custom_money
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_disable`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_disable`;
CREATE TABLE `dg_item_disable` (
  `id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项代码',
  `type` int(11) DEFAULT NULL COMMENT '类型(全部/普通/零时)',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_disable
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_discount`;
CREATE TABLE `dg_item_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` varchar(20) DEFAULT NULL COMMENT '菜品编码',
  `discount` int(11) DEFAULT NULL COMMENT '是否打折/0不打折/1打折',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_discount_programme`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_discount_programme`;
CREATE TABLE `dg_item_discount_programme` (
  `id` int(11) NOT NULL,
  `recyclebin` int(11) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL COMMENT '方案编号',
  `name` varchar(20) DEFAULT NULL COMMENT '方案名称',
  `type` varchar(2) DEFAULT NULL COMMENT '方案类型',
  `allow_f_dis` int(11) DEFAULT NULL COMMENT '是否允许前台打折',
  `disable` int(11) DEFAULT NULL COMMENT '是否停用',
  `date_limit` int(11) DEFAULT NULL COMMENT '是否开启日期限制',
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `discount` int(11) DEFAULT NULL COMMENT '统一折扣比例',
  `use_store_type` int(11) DEFAULT NULL,
  `use_store` varchar(1000) DEFAULT NULL,
  `time` datetime DEFAULT NULL COMMENT '修改事件',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_discount_programme
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_discount_programme_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_discount_programme_s`;
CREATE TABLE `dg_item_discount_programme_s` (
  `id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL COMMENT '对应方案id',
  `item_id` int(11) NOT NULL COMMENT '品项id',
  `discount` int(11) NOT NULL COMMENT '折扣比例',
  `disable` int(11) NOT NULL COMMENT '是否停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_discount_programme_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file`;
CREATE TABLE `dg_item_file` (
  `id` int(11) NOT NULL,
  `num` varchar(50) NOT NULL COMMENT '编码',
  `zjf` varchar(20) DEFAULT NULL COMMENT '助记符',
  `name` varchar(30) NOT NULL COMMENT '名字',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位',
  `ppdl_id` int(11) DEFAULT NULL COMMENT '品项大类ID',
  `ppxl_id` int(11) DEFAULT NULL COMMENT '品项小类ID',
  `xxlx_id` varchar(10) DEFAULT NULL COMMENT '销售类型',
  `pxld` varchar(10) DEFAULT NULL COMMENT '品项辣度',
  `jbiao` varchar(10) DEFAULT NULL COMMENT '角标',
  `dyfz` varchar(10) DEFAULT NULL COMMENT '打印分组',
  `yybf` int(11) DEFAULT NULL COMMENT '允许半分',
  `zxddsl` double DEFAULT NULL COMMENT '最小点单数量',
  `standard_price` double DEFAULT NULL COMMENT '标准价格',
  `cost_price` double DEFAULT NULL COMMENT '成本价格',
  `ywmc` varchar(100) DEFAULT NULL COMMENT '英文名称',
  `qtmc` varchar(100) DEFAULT NULL COMMENT '其他名称',
  `gg` varchar(100) DEFAULT NULL COMMENT '规格',
  `sptm` varchar(100) DEFAULT NULL COMMENT '商品条码',
  `sm` varchar(100) DEFAULT NULL COMMENT '说明',
  `bzzzsc` int(11) DEFAULT NULL COMMENT '标准制作时长',
  `cs` varchar(10) DEFAULT NULL COMMENT '厨师',
  `tssjd_one` int(11) DEFAULT NULL COMMENT '特殊时间段1',
  `tssjd_two` int(11) DEFAULT NULL COMMENT '特殊时间段2',
  `is_tc` int(11) DEFAULT NULL COMMENT '是否是套餐',
  `tcywhpxxl` int(11) DEFAULT NULL COMMENT '套餐已维护可选品项或小类',
  `gjjefssl` int(11) DEFAULT NULL COMMENT '根据金额反算数量 ',
  `jsqtsxgsl` int(11) DEFAULT NULL COMMENT '结算前提示修改数量',
  `cyzdxf` int(11) DEFAULT NULL COMMENT '参与最低消费',
  `rfid_card` varchar(100) DEFAULT NULL COMMENT 'RFID卡编号',
  `lspx` int(11) DEFAULT NULL COMMENT '临时品项 ',
  `yxdz` int(11) DEFAULT NULL COMMENT '允许打折',
  `zyzfids` varchar(200) DEFAULT NULL COMMENT '专用做法ID',
  `ggzyzfids` varchar(200) DEFAULT NULL COMMENT '公共做法里选择的专用做法',
  `pxdt` varchar(500) DEFAULT NULL COMMENT '品项大图',
  `pxxt` varchar(500) DEFAULT NULL COMMENT '品项小图',
  `yyxgids` varchar(200) DEFAULT NULL COMMENT '营养效果ID',
  `pxxtsm` varchar(200) DEFAULT NULL COMMENT '保健、食疗、美容效果',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `pxdt_file` varchar(200) DEFAULT NULL,
  `pxxt_file` varchar(200) DEFAULT NULL,
  `delFlag` int(11) DEFAULT '0',
  `yxe` int(11) DEFAULT '1' COMMENT '是否在易小二上显示',
  `uuid_key` varchar(200) NOT NULL,
  `pxxt_file_ol` varchar(500) DEFAULT NULL,
  `pxdt_file_ol` varchar(500) DEFAULT NULL,
  `sfwm` int(11) DEFAULT NULL,
  `show_ct` int(11) DEFAULT NULL,
  `color_gate` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid_key`),
  KEY `index_dif_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品项表';

-- ----------------------------
-- Records of dg_item_file
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_color`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_color`;
CREATE TABLE `dg_item_file_color` (
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `id` varchar(100) NOT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_file_color
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_package`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_package`;
CREATE TABLE `dg_item_file_package` (
  `id` int(11) NOT NULL,
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `package_type` int(11) DEFAULT NULL COMMENT '套餐类型',
  `package_banquet` int(11) DEFAULT NULL COMMENT '宴会套餐数量跟套餐明细数量有关 ',
  `package_sum` int(11) DEFAULT NULL COMMENT '套餐必选品项数量',
  `package_amount_sum` int(11) DEFAULT NULL COMMENT '套餐品项的各个数量总和',
  `package_standardPrice_sum` double DEFAULT NULL COMMENT '标准价格和',
  `package_standardPrice_sum_num` double DEFAULT NULL,
  `item_file_addPrice` double DEFAULT NULL COMMENT '品项加价',
  `canupdate_item_num` int(11) DEFAULT NULL COMMENT '允许修改可选品项数量 ',
  `min_num` int(11) DEFAULT NULL COMMENT '可选品项最小总数量',
  `max_num` int(11) DEFAULT NULL COMMENT '可选品项最大总数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品项套餐总览表';

-- ----------------------------
-- Records of dg_item_file_package
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_package_bx`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_package_bx`;
CREATE TABLE `dg_item_file_package_bx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL COMMENT '套餐ID',
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_amount` int(11) DEFAULT NULL COMMENT '品项数量',
  `item_price` double DEFAULT NULL COMMENT '品项价格',
  `item_amount_price` double DEFAULT NULL COMMENT '数量x标准价格',
  `item_addPrice` double DEFAULT NULL COMMENT '品项加价',
  `addPrice_num` int(11) DEFAULT NULL COMMENT '按数量加价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐必选品项表';

-- ----------------------------
-- Records of dg_item_file_package_bx
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_package_kx`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_package_kx`;
CREATE TABLE `dg_item_file_package_kx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL COMMENT '套餐ID',
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_amount` int(11) DEFAULT NULL COMMENT '品项数量和',
  `item_addPrice` double DEFAULT NULL COMMENT '品项加价',
  `addPrice_num` int(11) DEFAULT NULL COMMENT '按数量加价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐可选品项';

-- ----------------------------
-- Records of dg_item_file_package_kx
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_package_slxd`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_package_slxd`;
CREATE TABLE `dg_item_file_package_slxd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL COMMENT '套餐ID',
  `item_file_type_id` int(11) DEFAULT NULL COMMENT '品项类别ID',
  `max_num` int(11) DEFAULT NULL COMMENT '最大限定数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 可选品项大类数量限定';

-- ----------------------------
-- Records of dg_item_file_package_slxd
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_package_th`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_package_th`;
CREATE TABLE `dg_item_file_package_th` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL COMMENT '套餐ID',
  `bx_item_file_id` int(11) DEFAULT NULL COMMENT '必选品项的ID',
  `item_file_id` int(11) DEFAULT NULL COMMENT '替换的品项的ID',
  `item_amout` int(11) DEFAULT NULL COMMENT '数量',
  `item_replacePrice` double DEFAULT NULL COMMENT '替换加价',
  `addPrice_num` int(11) DEFAULT NULL COMMENT '按数量加价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='必选品项的替换品项';

-- ----------------------------
-- Records of dg_item_file_package_th
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_rank`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_rank`;
CREATE TABLE `dg_item_file_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemFileId` int(11) DEFAULT NULL COMMENT '品项（品项小类）ID',
  `itemFileType` int(11) DEFAULT NULL COMMENT '排序类型  1 = 品项排序； 2 = 品项小类排序',
  `rank` int(11) DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=290 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dg_item_file_rank
-- ----------------------------
INSERT INTO `dg_item_file_rank` VALUES ('1', '243', '1', '2');
INSERT INTO `dg_item_file_rank` VALUES ('2', '227', '1', '3');
INSERT INTO `dg_item_file_rank` VALUES ('3', '320', '1', '1');
INSERT INTO `dg_item_file_rank` VALUES ('4', '472', '1', '4');
INSERT INTO `dg_item_file_rank` VALUES ('5', '309', '1', '5');
INSERT INTO `dg_item_file_rank` VALUES ('6', '344', '1', '6');
INSERT INTO `dg_item_file_rank` VALUES ('7', '300', '1', '7');
INSERT INTO `dg_item_file_rank` VALUES ('8', '480', '1', '8');
INSERT INTO `dg_item_file_rank` VALUES ('9', '237', '1', '9');
INSERT INTO `dg_item_file_rank` VALUES ('10', '336', '1', '10');
INSERT INTO `dg_item_file_rank` VALUES ('11', '262', '1', '11');
INSERT INTO `dg_item_file_rank` VALUES ('12', '314', '1', '12');
INSERT INTO `dg_item_file_rank` VALUES ('13', '433', '1', '13');
INSERT INTO `dg_item_file_rank` VALUES ('14', '479', '1', '14');
INSERT INTO `dg_item_file_rank` VALUES ('15', '304', '1', '15');
INSERT INTO `dg_item_file_rank` VALUES ('16', '311', '1', '16');
INSERT INTO `dg_item_file_rank` VALUES ('17', '402', '1', '17');
INSERT INTO `dg_item_file_rank` VALUES ('18', '312', '1', '18');
INSERT INTO `dg_item_file_rank` VALUES ('19', '347', '1', '19');
INSERT INTO `dg_item_file_rank` VALUES ('20', '294', '1', '20');
INSERT INTO `dg_item_file_rank` VALUES ('21', '285', '1', '21');
INSERT INTO `dg_item_file_rank` VALUES ('22', '409', '1', '22');
INSERT INTO `dg_item_file_rank` VALUES ('23', '381', '1', '23');
INSERT INTO `dg_item_file_rank` VALUES ('24', '240', '1', '24');
INSERT INTO `dg_item_file_rank` VALUES ('25', '298', '1', '25');
INSERT INTO `dg_item_file_rank` VALUES ('26', '212', '1', '26');
INSERT INTO `dg_item_file_rank` VALUES ('27', '424', '1', '27');
INSERT INTO `dg_item_file_rank` VALUES ('28', '484', '1', '28');
INSERT INTO `dg_item_file_rank` VALUES ('29', '313', '1', '29');
INSERT INTO `dg_item_file_rank` VALUES ('30', '355', '1', '30');
INSERT INTO `dg_item_file_rank` VALUES ('31', '228', '1', '31');
INSERT INTO `dg_item_file_rank` VALUES ('32', '457', '1', '32');
INSERT INTO `dg_item_file_rank` VALUES ('33', '251', '1', '33');
INSERT INTO `dg_item_file_rank` VALUES ('34', '327', '1', '34');
INSERT INTO `dg_item_file_rank` VALUES ('35', '306', '1', '35');
INSERT INTO `dg_item_file_rank` VALUES ('36', '323', '1', '36');
INSERT INTO `dg_item_file_rank` VALUES ('37', '350', '1', '37');
INSERT INTO `dg_item_file_rank` VALUES ('38', '241', '1', '38');
INSERT INTO `dg_item_file_rank` VALUES ('39', '215', '1', '39');
INSERT INTO `dg_item_file_rank` VALUES ('40', '308', '1', '40');
INSERT INTO `dg_item_file_rank` VALUES ('41', '410', '1', '41');
INSERT INTO `dg_item_file_rank` VALUES ('42', '412', '1', '42');
INSERT INTO `dg_item_file_rank` VALUES ('43', '341', '1', '43');
INSERT INTO `dg_item_file_rank` VALUES ('44', '288', '1', '44');
INSERT INTO `dg_item_file_rank` VALUES ('45', '449', '1', '45');
INSERT INTO `dg_item_file_rank` VALUES ('46', '453', '1', '46');
INSERT INTO `dg_item_file_rank` VALUES ('47', '210', '1', '47');
INSERT INTO `dg_item_file_rank` VALUES ('48', '401', '1', '48');
INSERT INTO `dg_item_file_rank` VALUES ('49', '244', '1', '49');
INSERT INTO `dg_item_file_rank` VALUES ('50', '485', '1', '50');
INSERT INTO `dg_item_file_rank` VALUES ('51', '418', '1', '51');
INSERT INTO `dg_item_file_rank` VALUES ('52', '267', '1', '52');
INSERT INTO `dg_item_file_rank` VALUES ('53', '326', '1', '53');
INSERT INTO `dg_item_file_rank` VALUES ('54', '230', '1', '54');
INSERT INTO `dg_item_file_rank` VALUES ('55', '234', '1', '55');
INSERT INTO `dg_item_file_rank` VALUES ('56', '331', '1', '56');
INSERT INTO `dg_item_file_rank` VALUES ('57', '349', '1', '57');
INSERT INTO `dg_item_file_rank` VALUES ('58', '239', '1', '58');
INSERT INTO `dg_item_file_rank` VALUES ('59', '264', '1', '59');
INSERT INTO `dg_item_file_rank` VALUES ('60', '357', '1', '60');
INSERT INTO `dg_item_file_rank` VALUES ('61', '469', '1', '61');
INSERT INTO `dg_item_file_rank` VALUES ('62', '405', '1', '62');
INSERT INTO `dg_item_file_rank` VALUES ('63', '371', '1', '63');
INSERT INTO `dg_item_file_rank` VALUES ('64', '335', '1', '64');
INSERT INTO `dg_item_file_rank` VALUES ('65', '413', '1', '65');
INSERT INTO `dg_item_file_rank` VALUES ('66', '221', '1', '66');
INSERT INTO `dg_item_file_rank` VALUES ('67', '268', '1', '67');
INSERT INTO `dg_item_file_rank` VALUES ('68', '471', '1', '68');
INSERT INTO `dg_item_file_rank` VALUES ('69', '481', '1', '69');
INSERT INTO `dg_item_file_rank` VALUES ('70', '468', '1', '70');
INSERT INTO `dg_item_file_rank` VALUES ('71', '482', '1', '71');
INSERT INTO `dg_item_file_rank` VALUES ('72', '421', '1', '72');
INSERT INTO `dg_item_file_rank` VALUES ('73', '266', '1', '73');
INSERT INTO `dg_item_file_rank` VALUES ('74', '206', '1', '74');
INSERT INTO `dg_item_file_rank` VALUES ('75', '222', '1', '75');
INSERT INTO `dg_item_file_rank` VALUES ('76', '265', '1', '76');
INSERT INTO `dg_item_file_rank` VALUES ('77', '248', '1', '77');
INSERT INTO `dg_item_file_rank` VALUES ('78', '205', '1', '78');
INSERT INTO `dg_item_file_rank` VALUES ('79', '226', '1', '79');
INSERT INTO `dg_item_file_rank` VALUES ('80', '463', '1', '80');
INSERT INTO `dg_item_file_rank` VALUES ('81', '217', '1', '81');
INSERT INTO `dg_item_file_rank` VALUES ('82', '478', '1', '82');
INSERT INTO `dg_item_file_rank` VALUES ('83', '467', '1', '83');
INSERT INTO `dg_item_file_rank` VALUES ('84', '353', '1', '84');
INSERT INTO `dg_item_file_rank` VALUES ('85', '235', '1', '85');
INSERT INTO `dg_item_file_rank` VALUES ('86', '466', '1', '86');
INSERT INTO `dg_item_file_rank` VALUES ('87', '292', '1', '87');
INSERT INTO `dg_item_file_rank` VALUES ('88', '299', '1', '88');
INSERT INTO `dg_item_file_rank` VALUES ('89', '211', '1', '89');
INSERT INTO `dg_item_file_rank` VALUES ('90', '416', '1', '90');
INSERT INTO `dg_item_file_rank` VALUES ('91', '330', '1', '91');
INSERT INTO `dg_item_file_rank` VALUES ('92', '282', '1', '92');
INSERT INTO `dg_item_file_rank` VALUES ('93', '246', '1', '93');
INSERT INTO `dg_item_file_rank` VALUES ('94', '207', '1', '94');
INSERT INTO `dg_item_file_rank` VALUES ('95', '295', '1', '95');
INSERT INTO `dg_item_file_rank` VALUES ('96', '276', '1', '96');
INSERT INTO `dg_item_file_rank` VALUES ('97', '213', '1', '97');
INSERT INTO `dg_item_file_rank` VALUES ('98', '270', '1', '98');
INSERT INTO `dg_item_file_rank` VALUES ('99', '451', '1', '99');
INSERT INTO `dg_item_file_rank` VALUES ('100', '229', '1', '100');
INSERT INTO `dg_item_file_rank` VALUES ('101', '317', '1', '101');
INSERT INTO `dg_item_file_rank` VALUES ('102', '435', '1', '102');
INSERT INTO `dg_item_file_rank` VALUES ('103', '477', '1', '103');
INSERT INTO `dg_item_file_rank` VALUES ('104', '430', '1', '104');
INSERT INTO `dg_item_file_rank` VALUES ('105', '293', '1', '105');
INSERT INTO `dg_item_file_rank` VALUES ('106', '260', '1', '106');
INSERT INTO `dg_item_file_rank` VALUES ('107', '283', '1', '107');
INSERT INTO `dg_item_file_rank` VALUES ('108', '378', '1', '108');
INSERT INTO `dg_item_file_rank` VALUES ('109', '352', '1', '109');
INSERT INTO `dg_item_file_rank` VALUES ('110', '443', '1', '110');
INSERT INTO `dg_item_file_rank` VALUES ('111', '461', '1', '111');
INSERT INTO `dg_item_file_rank` VALUES ('112', '369', '1', '112');
INSERT INTO `dg_item_file_rank` VALUES ('113', '488', '1', '113');
INSERT INTO `dg_item_file_rank` VALUES ('114', '303', '1', '114');
INSERT INTO `dg_item_file_rank` VALUES ('115', '261', '1', '115');
INSERT INTO `dg_item_file_rank` VALUES ('116', '257', '1', '116');
INSERT INTO `dg_item_file_rank` VALUES ('117', '447', '1', '117');
INSERT INTO `dg_item_file_rank` VALUES ('118', '275', '1', '118');
INSERT INTO `dg_item_file_rank` VALUES ('119', '301', '1', '119');
INSERT INTO `dg_item_file_rank` VALUES ('120', '247', '1', '120');
INSERT INTO `dg_item_file_rank` VALUES ('121', '256', '1', '121');
INSERT INTO `dg_item_file_rank` VALUES ('122', '316', '1', '122');
INSERT INTO `dg_item_file_rank` VALUES ('123', '427', '1', '123');
INSERT INTO `dg_item_file_rank` VALUES ('124', '332', '1', '124');
INSERT INTO `dg_item_file_rank` VALUES ('125', '476', '1', '125');
INSERT INTO `dg_item_file_rank` VALUES ('126', '359', '1', '126');
INSERT INTO `dg_item_file_rank` VALUES ('127', '269', '1', '127');
INSERT INTO `dg_item_file_rank` VALUES ('128', '291', '1', '128');
INSERT INTO `dg_item_file_rank` VALUES ('129', '231', '1', '129');
INSERT INTO `dg_item_file_rank` VALUES ('130', '236', '1', '130');
INSERT INTO `dg_item_file_rank` VALUES ('131', '259', '1', '131');
INSERT INTO `dg_item_file_rank` VALUES ('132', '337', '1', '132');
INSERT INTO `dg_item_file_rank` VALUES ('133', '302', '1', '133');
INSERT INTO `dg_item_file_rank` VALUES ('134', '286', '1', '134');
INSERT INTO `dg_item_file_rank` VALUES ('135', '473', '1', '135');
INSERT INTO `dg_item_file_rank` VALUES ('136', '204', '1', '136');
INSERT INTO `dg_item_file_rank` VALUES ('137', '426', '1', '137');
INSERT INTO `dg_item_file_rank` VALUES ('138', '318', '1', '138');
INSERT INTO `dg_item_file_rank` VALUES ('139', '351', '1', '139');
INSERT INTO `dg_item_file_rank` VALUES ('140', '438', '1', '140');
INSERT INTO `dg_item_file_rank` VALUES ('141', '290', '1', '141');
INSERT INTO `dg_item_file_rank` VALUES ('142', '233', '1', '142');
INSERT INTO `dg_item_file_rank` VALUES ('143', '339', '1', '143');
INSERT INTO `dg_item_file_rank` VALUES ('144', '356', '1', '144');
INSERT INTO `dg_item_file_rank` VALUES ('145', '460', '1', '145');
INSERT INTO `dg_item_file_rank` VALUES ('146', '253', '1', '146');
INSERT INTO `dg_item_file_rank` VALUES ('147', '437', '1', '147');
INSERT INTO `dg_item_file_rank` VALUES ('148', '406', '1', '148');
INSERT INTO `dg_item_file_rank` VALUES ('149', '346', '1', '149');
INSERT INTO `dg_item_file_rank` VALUES ('150', '274', '1', '150');
INSERT INTO `dg_item_file_rank` VALUES ('151', '209', '1', '151');
INSERT INTO `dg_item_file_rank` VALUES ('152', '440', '1', '152');
INSERT INTO `dg_item_file_rank` VALUES ('153', '373', '1', '153');
INSERT INTO `dg_item_file_rank` VALUES ('154', '364', '1', '154');
INSERT INTO `dg_item_file_rank` VALUES ('155', '278', '1', '155');
INSERT INTO `dg_item_file_rank` VALUES ('156', '361', '1', '156');
INSERT INTO `dg_item_file_rank` VALUES ('157', '342', '1', '157');
INSERT INTO `dg_item_file_rank` VALUES ('158', '281', '1', '158');
INSERT INTO `dg_item_file_rank` VALUES ('159', '232', '1', '159');
INSERT INTO `dg_item_file_rank` VALUES ('160', '403', '1', '160');
INSERT INTO `dg_item_file_rank` VALUES ('161', '273', '1', '161');
INSERT INTO `dg_item_file_rank` VALUES ('162', '258', '1', '162');
INSERT INTO `dg_item_file_rank` VALUES ('163', '338', '1', '163');
INSERT INTO `dg_item_file_rank` VALUES ('164', '365', '1', '164');
INSERT INTO `dg_item_file_rank` VALUES ('165', '208', '1', '165');
INSERT INTO `dg_item_file_rank` VALUES ('166', '407', '1', '166');
INSERT INTO `dg_item_file_rank` VALUES ('167', '462', '1', '167');
INSERT INTO `dg_item_file_rank` VALUES ('168', '379', '1', '168');
INSERT INTO `dg_item_file_rank` VALUES ('169', '225', '1', '169');
INSERT INTO `dg_item_file_rank` VALUES ('170', '254', '1', '170');
INSERT INTO `dg_item_file_rank` VALUES ('171', '280', '1', '171');
INSERT INTO `dg_item_file_rank` VALUES ('172', '216', '1', '172');
INSERT INTO `dg_item_file_rank` VALUES ('173', '315', '1', '173');
INSERT INTO `dg_item_file_rank` VALUES ('174', '319', '1', '174');
INSERT INTO `dg_item_file_rank` VALUES ('175', '363', '1', '175');
INSERT INTO `dg_item_file_rank` VALUES ('176', '220', '1', '176');
INSERT INTO `dg_item_file_rank` VALUES ('177', '445', '1', '177');
INSERT INTO `dg_item_file_rank` VALUES ('178', '348', '1', '178');
INSERT INTO `dg_item_file_rank` VALUES ('179', '380', '1', '179');
INSERT INTO `dg_item_file_rank` VALUES ('180', '487', '1', '180');
INSERT INTO `dg_item_file_rank` VALUES ('181', '297', '1', '181');
INSERT INTO `dg_item_file_rank` VALUES ('182', '324', '1', '182');
INSERT INTO `dg_item_file_rank` VALUES ('183', '334', '1', '183');
INSERT INTO `dg_item_file_rank` VALUES ('184', '411', '1', '184');
INSERT INTO `dg_item_file_rank` VALUES ('185', '417', '1', '185');
INSERT INTO `dg_item_file_rank` VALUES ('186', '271', '1', '186');
INSERT INTO `dg_item_file_rank` VALUES ('187', '345', '1', '187');
INSERT INTO `dg_item_file_rank` VALUES ('188', '360', '1', '188');
INSERT INTO `dg_item_file_rank` VALUES ('189', '284', '1', '189');
INSERT INTO `dg_item_file_rank` VALUES ('190', '358', '1', '190');
INSERT INTO `dg_item_file_rank` VALUES ('191', '377', '1', '191');
INSERT INTO `dg_item_file_rank` VALUES ('192', '441', '1', '192');
INSERT INTO `dg_item_file_rank` VALUES ('193', '376', '1', '193');
INSERT INTO `dg_item_file_rank` VALUES ('194', '422', '1', '194');
INSERT INTO `dg_item_file_rank` VALUES ('195', '464', '1', '195');
INSERT INTO `dg_item_file_rank` VALUES ('196', '250', '1', '196');
INSERT INTO `dg_item_file_rank` VALUES ('197', '289', '1', '197');
INSERT INTO `dg_item_file_rank` VALUES ('198', '272', '1', '198');
INSERT INTO `dg_item_file_rank` VALUES ('199', '420', '1', '199');
INSERT INTO `dg_item_file_rank` VALUES ('200', '375', '1', '200');
INSERT INTO `dg_item_file_rank` VALUES ('201', '442', '1', '201');
INSERT INTO `dg_item_file_rank` VALUES ('202', '245', '1', '202');
INSERT INTO `dg_item_file_rank` VALUES ('203', '454', '1', '203');
INSERT INTO `dg_item_file_rank` VALUES ('204', '448', '1', '204');
INSERT INTO `dg_item_file_rank` VALUES ('205', '218', '1', '205');
INSERT INTO `dg_item_file_rank` VALUES ('206', '366', '1', '206');
INSERT INTO `dg_item_file_rank` VALUES ('207', '432', '1', '207');
INSERT INTO `dg_item_file_rank` VALUES ('208', '415', '1', '208');
INSERT INTO `dg_item_file_rank` VALUES ('209', '255', '1', '209');
INSERT INTO `dg_item_file_rank` VALUES ('210', '328', '1', '210');
INSERT INTO `dg_item_file_rank` VALUES ('211', '279', '1', '211');
INSERT INTO `dg_item_file_rank` VALUES ('212', '224', '1', '212');
INSERT INTO `dg_item_file_rank` VALUES ('213', '459', '1', '213');
INSERT INTO `dg_item_file_rank` VALUES ('214', '340', '1', '214');
INSERT INTO `dg_item_file_rank` VALUES ('215', '489', '1', '215');
INSERT INTO `dg_item_file_rank` VALUES ('216', '277', '1', '216');
INSERT INTO `dg_item_file_rank` VALUES ('217', '329', '1', '217');
INSERT INTO `dg_item_file_rank` VALUES ('218', '465', '1', '218');
INSERT INTO `dg_item_file_rank` VALUES ('219', '354', '1', '219');
INSERT INTO `dg_item_file_rank` VALUES ('220', '305', '1', '220');
INSERT INTO `dg_item_file_rank` VALUES ('221', '223', '1', '221');
INSERT INTO `dg_item_file_rank` VALUES ('222', '408', '1', '222');
INSERT INTO `dg_item_file_rank` VALUES ('223', '219', '1', '223');
INSERT INTO `dg_item_file_rank` VALUES ('224', '455', '1', '224');
INSERT INTO `dg_item_file_rank` VALUES ('225', '362', '1', '225');
INSERT INTO `dg_item_file_rank` VALUES ('226', '214', '1', '226');
INSERT INTO `dg_item_file_rank` VALUES ('227', '439', '1', '227');
INSERT INTO `dg_item_file_rank` VALUES ('228', '249', '1', '228');
INSERT INTO `dg_item_file_rank` VALUES ('229', '322', '1', '229');
INSERT INTO `dg_item_file_rank` VALUES ('230', '423', '1', '230');
INSERT INTO `dg_item_file_rank` VALUES ('231', '374', '1', '231');
INSERT INTO `dg_item_file_rank` VALUES ('232', '368', '1', '232');
INSERT INTO `dg_item_file_rank` VALUES ('233', '333', '1', '233');
INSERT INTO `dg_item_file_rank` VALUES ('234', '321', '1', '234');
INSERT INTO `dg_item_file_rank` VALUES ('235', '263', '1', '235');
INSERT INTO `dg_item_file_rank` VALUES ('236', '404', '1', '236');
INSERT INTO `dg_item_file_rank` VALUES ('237', '367', '1', '237');
INSERT INTO `dg_item_file_rank` VALUES ('238', '325', '1', '238');
INSERT INTO `dg_item_file_rank` VALUES ('239', '425', '1', '239');
INSERT INTO `dg_item_file_rank` VALUES ('240', '452', '1', '240');
INSERT INTO `dg_item_file_rank` VALUES ('241', '446', '1', '241');
INSERT INTO `dg_item_file_rank` VALUES ('242', '370', '1', '242');
INSERT INTO `dg_item_file_rank` VALUES ('243', '444', '1', '243');
INSERT INTO `dg_item_file_rank` VALUES ('244', '456', '1', '244');
INSERT INTO `dg_item_file_rank` VALUES ('245', '242', '1', '245');
INSERT INTO `dg_item_file_rank` VALUES ('246', '287', '1', '246');
INSERT INTO `dg_item_file_rank` VALUES ('247', '431', '1', '247');
INSERT INTO `dg_item_file_rank` VALUES ('248', '474', '1', '248');
INSERT INTO `dg_item_file_rank` VALUES ('249', '296', '1', '249');
INSERT INTO `dg_item_file_rank` VALUES ('250', '307', '1', '250');
INSERT INTO `dg_item_file_rank` VALUES ('251', '310', '1', '251');
INSERT INTO `dg_item_file_rank` VALUES ('252', '436', '1', '252');
INSERT INTO `dg_item_file_rank` VALUES ('253', '252', '1', '253');
INSERT INTO `dg_item_file_rank` VALUES ('254', '414', '1', '254');
INSERT INTO `dg_item_file_rank` VALUES ('255', '419', '1', '255');
INSERT INTO `dg_item_file_rank` VALUES ('256', '486', '1', '256');
INSERT INTO `dg_item_file_rank` VALUES ('257', '470', '1', '257');
INSERT INTO `dg_item_file_rank` VALUES ('258', '434', '1', '258');
INSERT INTO `dg_item_file_rank` VALUES ('259', '238', '1', '259');
INSERT INTO `dg_item_file_rank` VALUES ('260', '343', '1', '260');
INSERT INTO `dg_item_file_rank` VALUES ('261', '458', '1', '261');
INSERT INTO `dg_item_file_rank` VALUES ('262', '450', '1', '262');
INSERT INTO `dg_item_file_rank` VALUES ('263', '372', '1', '263');
INSERT INTO `dg_item_file_rank` VALUES ('264', '46', '2', '1');
INSERT INTO `dg_item_file_rank` VALUES ('265', '51', '2', '2');
INSERT INTO `dg_item_file_rank` VALUES ('266', '52', '2', '3');
INSERT INTO `dg_item_file_rank` VALUES ('267', '38', '2', '4');
INSERT INTO `dg_item_file_rank` VALUES ('268', '50', '2', '5');
INSERT INTO `dg_item_file_rank` VALUES ('269', '41', '2', '6');
INSERT INTO `dg_item_file_rank` VALUES ('270', '60', '2', '7');
INSERT INTO `dg_item_file_rank` VALUES ('271', '39', '2', '8');
INSERT INTO `dg_item_file_rank` VALUES ('272', '37', '2', '9');
INSERT INTO `dg_item_file_rank` VALUES ('273', '35', '2', '10');
INSERT INTO `dg_item_file_rank` VALUES ('274', '40', '2', '11');
INSERT INTO `dg_item_file_rank` VALUES ('275', '57', '2', '12');
INSERT INTO `dg_item_file_rank` VALUES ('276', '42', '2', '13');
INSERT INTO `dg_item_file_rank` VALUES ('277', '58', '2', '14');
INSERT INTO `dg_item_file_rank` VALUES ('278', '56', '2', '15');
INSERT INTO `dg_item_file_rank` VALUES ('279', '48', '2', '16');
INSERT INTO `dg_item_file_rank` VALUES ('280', '53', '2', '17');
INSERT INTO `dg_item_file_rank` VALUES ('281', '43', '2', '18');
INSERT INTO `dg_item_file_rank` VALUES ('282', '36', '2', '19');
INSERT INTO `dg_item_file_rank` VALUES ('283', '49', '2', '20');
INSERT INTO `dg_item_file_rank` VALUES ('284', '47', '2', '21');
INSERT INTO `dg_item_file_rank` VALUES ('285', '44', '2', '22');
INSERT INTO `dg_item_file_rank` VALUES ('286', '55', '2', '23');
INSERT INTO `dg_item_file_rank` VALUES ('287', '45', '2', '24');
INSERT INTO `dg_item_file_rank` VALUES ('288', '54', '2', '25');
INSERT INTO `dg_item_file_rank` VALUES ('289', '59', '2', '26');

-- ----------------------------
-- Table structure for `dg_item_file_type`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_type`;
CREATE TABLE `dg_item_file_type` (
  `id` int(11) NOT NULL,
  `num` varchar(40) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `zjf` varchar(20) DEFAULT NULL COMMENT '助记符',
  `pp` varchar(20) DEFAULT NULL COMMENT '所属品项大类',
  `description` varchar(200) DEFAULT NULL COMMENT '说明',
  `qyzzfflbxd` int(11) DEFAULT NULL COMMENT '启用制作方法类别限定 ',
  `qyzzfflbids` varchar(200) DEFAULT NULL COMMENT '启用制作方法类别限定ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `default_sczt` int(11) DEFAULT NULL COMMENT '默认上菜状态',
  `operator` int(11) DEFAULT NULL COMMENT '操作人',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标识',
  `coupon_code` varchar(10) DEFAULT NULL COMMENT '优惠券关联相关code',
  `yxe` int(11) DEFAULT '1' COMMENT '是否在易小二上显示',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`),
  KEY `index_dift_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品项类别';

-- ----------------------------
-- Records of dg_item_file_type
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_file_zccf`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_file_zccf`;
CREATE TABLE `dg_item_file_zccf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `inve_item_id` varchar(255) DEFAULT NULL COMMENT '组成成分id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `counter` double DEFAULT NULL COMMENT '组成成分分量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_file_zccf
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_for_place`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_for_place`;
CREATE TABLE `dg_item_for_place` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` int(11) DEFAULT NULL COMMENT '区域id',
  `area_col_name` varchar(20) DEFAULT NULL COMMENT '区域对应表字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_for_place
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_for_place_price`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_for_place_price`;
CREATE TABLE `dg_item_for_place_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `place_id` int(11) DEFAULT NULL COMMENT '区域id',
  `price` double DEFAULT NULL COMMENT '当前值',
  `line_id` int(11) DEFAULT NULL COMMENT '行id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_for_place_price
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_for_week`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_for_week`;
CREATE TABLE `dg_item_for_week` (
  `id` int(11) NOT NULL COMMENT '自增主键',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `item_id` int(11) DEFAULT NULL COMMENT '品项编码',
  `x1` double DEFAULT NULL,
  `x2` double DEFAULT NULL,
  `x3` double DEFAULT NULL,
  `x4` double DEFAULT NULL,
  `x5` double DEFAULT NULL,
  `x6` double DEFAULT NULL,
  `x7` double DEFAULT NULL,
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_for_week
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_from_cook`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_from_cook`;
CREATE TABLE `dg_item_from_cook` (
  `id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `cook_id` int(11) DEFAULT NULL COMMENT '厨师号',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_from_cook
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_gift_plan`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_gift_plan`;
CREATE TABLE `dg_item_gift_plan` (
  `id` int(11) DEFAULT NULL,
  `recycle` int(11) DEFAULT NULL COMMENT '回收站',
  `item_id` int(11) DEFAULT NULL COMMENT '品项编码',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `start_time` varchar(20) DEFAULT NULL COMMENT '每天开始时间',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用',
  `end_time` varchar(20) DEFAULT NULL COMMENT '每天结束时间',
  `week1` int(11) DEFAULT NULL COMMENT '星期一',
  `week2` int(11) DEFAULT NULL COMMENT '星期二',
  `week3` int(11) DEFAULT NULL COMMENT '星期三',
  `week4` int(11) DEFAULT NULL COMMENT '星期四',
  `week5` int(11) DEFAULT NULL COMMENT '星期五',
  `week6` int(11) DEFAULT NULL COMMENT '星期六',
  `week7` int(11) DEFAULT NULL COMMENT '星期日',
  `explains` varchar(1000) DEFAULT NULL COMMENT '说明',
  `gift_amount` int(11) DEFAULT NULL COMMENT '每次赠送数量',
  `gift_frequency_limit` int(11) DEFAULT NULL COMMENT '赠送次数限定',
  `name` varchar(30) DEFAULT NULL COMMENT '方案名称',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_gift_plan
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_gift_plan_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_gift_plan_s`;
CREATE TABLE `dg_item_gift_plan_s` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `gift_acount` int(11) DEFAULT NULL COMMENT '赠送数量',
  `enable` int(11) DEFAULT NULL COMMENT '启用',
  `parent_id` int(11) DEFAULT NULL COMMENT '父表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_gift_plan_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_member_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_member_discount`;
CREATE TABLE `dg_item_member_discount` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '会员方案名称',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用(0/1)',
  `explains` varchar(1000) DEFAULT NULL COMMENT '说明',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `day_start_time` varchar(20) DEFAULT NULL COMMENT '日开始时间',
  `day_end_time` varchar(20) DEFAULT NULL COMMENT '日结束时间',
  `week` varchar(255) DEFAULT NULL COMMENT '每周启用日子',
  `use_all_shop` int(11) DEFAULT NULL COMMENT '0/1 0为指定店1为所有店',
  `affiliation` varchar(1000) DEFAULT NULL COMMENT '所属机构(1/2/3/4)模式',
  `publish` int(11) DEFAULT NULL COMMENT '是否发布',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改日期',
  `recycle_bin` int(11) DEFAULT NULL COMMENT '是否进入回收站',
  `level_id` varchar(100) DEFAULT NULL COMMENT '会员等级id',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_member_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_member_discount_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_member_discount_s`;
CREATE TABLE `dg_item_member_discount_s` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用',
  `price` double DEFAULT NULL COMMENT '方案价格',
  `consistent` int(11) DEFAULT NULL COMMENT '价格是否一致',
  `p_id` int(11) DEFAULT NULL COMMENT '父表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_member_discount_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_outof_stock`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_outof_stock`;
CREATE TABLE `dg_item_outof_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `m_type` int(11) DEFAULT NULL COMMENT '类别(不指定/按照当前市别/按照营业日)',
  `m_bis` int(11) DEFAULT NULL COMMENT '当前市别',
  `date` date DEFAULT NULL COMMENT '日期',
  `uuid_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_outof_stock
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_price_ladder`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_price_ladder`;
CREATE TABLE `dg_item_price_ladder` (
  `id` int(11) NOT NULL,
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `ladder_1` double DEFAULT NULL COMMENT '阶梯1价',
  `ladder_2` double DEFAULT NULL COMMENT '阶梯2价',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_price_ladder
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_price_priority`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_price_priority`;
CREATE TABLE `dg_item_price_priority` (
  `id` int(11) NOT NULL,
  `code` varchar(20) DEFAULT NULL COMMENT '价格优先级编码',
  `name` varchar(20) DEFAULT NULL COMMENT '价格优先级名称',
  `n_index` int(11) DEFAULT NULL COMMENT '价格优先级顺序',
  `m_index` int(11) DEFAULT NULL COMMENT '默认顺序',
  `enable` int(11) DEFAULT NULL COMMENT '是否使用',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_price_priority
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_pro_dep`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_pro_dep`;
CREATE TABLE `dg_item_pro_dep` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_pro_dep
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_pro_dep_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_pro_dep_s`;
CREATE TABLE `dg_item_pro_dep_s` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `place_id` int(11) DEFAULT NULL COMMENT '区域id',
  `pro_dep_id` int(11) DEFAULT NULL COMMENT '主表id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `dep_id` int(11) DEFAULT NULL COMMENT '出品部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_pro_dep_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_sale_limit`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_sale_limit`;
CREATE TABLE `dg_item_sale_limit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `today` date DEFAULT NULL COMMENT '限量id',
  `type` int(11) DEFAULT NULL COMMENT '限量类型(是否减去今日预定数量)',
  `uuid_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_sale_limit
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_sale_limit_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_sale_limit_s`;
CREATE TABLE `dg_item_sale_limit_s` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项代码',
  `sale_count` decimal(18,2) DEFAULT NULL COMMENT '今日可销售数量',
  `reservation_count` decimal(18,2) DEFAULT NULL COMMENT '今日预定数量',
  `front_sale_count` decimal(18,2) DEFAULT NULL COMMENT '今日前台可销售数量',
  `limit_id` int(11) DEFAULT NULL COMMENT '限量id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_sale_limit_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_show_rank`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_show_rank`;
CREATE TABLE `dg_item_show_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '品项显示排行表ID',
  `px_id` int(11) DEFAULT NULL COMMENT '品项基础表ID',
  `is_show` varchar(2) DEFAULT '0' COMMENT '是否显示，默认‘0’=显示',
  `is_rank` varchar(2) DEFAULT '0' COMMENT '是否排行，默认‘0’=排行',
  `pxlx` varchar(2) DEFAULT NULL COMMENT '品项类型 “0”=品项 “1”=品项小类 ',
  `rank` int(11) DEFAULT NULL COMMENT '之间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `uuid_key` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_show_rank
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_time_limit`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_time_limit`;
CREATE TABLE `dg_item_time_limit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `sale_limit` double DEFAULT NULL COMMENT '限购数量',
  `surplus_limit` double DEFAULT NULL COMMENT '剩余数量',
  `start_time` varchar(100) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(100) DEFAULT NULL COMMENT '结束时间',
  `price` double DEFAULT NULL COMMENT '限量抢购价格',
  `p_id` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_time_limit
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_time_limit_p`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_time_limit_p`;
CREATE TABLE `dg_item_time_limit_p` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_time` varchar(255) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_time_limit_p
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_time_limit_pic`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_time_limit_pic`;
CREATE TABLE `dg_item_time_limit_pic` (
  `p_id` int(11) NOT NULL COMMENT '父id',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_names` varchar(255) NOT NULL COMMENT '图片名称',
  `type` int(11) NOT NULL COMMENT '1 横向广告图片  2纵向',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_time_limit_pic
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_time_set`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_time_set`;
CREATE TABLE `dg_item_time_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '品项显示时间设置',
  `refresh_hz` varchar(8) DEFAULT NULL COMMENT '刷新频率',
  `start_date` date DEFAULT NULL COMMENT '品项统计起始日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_time_set
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_item_type_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_item_type_discount`;
CREATE TABLE `dg_item_type_discount` (
  `id` int(11) NOT NULL,
  `dg_num` varchar(20) DEFAULT NULL COMMENT '小类num',
  `dg_id` int(11) NOT NULL COMMENT '小类id',
  `discount` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_item_type_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_newest_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_newest_item`;
CREATE TABLE `dg_newest_item` (
  `id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_newest_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_non_member`
-- ----------------------------
DROP TABLE IF EXISTS `dg_non_member`;
CREATE TABLE `dg_non_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `mnemonic` varchar(100) DEFAULT NULL,
  `accountType` int(11) NOT NULL DEFAULT '1',
  `empId` int(11) NOT NULL,
  `balance` decimal(10,3) NOT NULL DEFAULT '0.000',
  `repaymentPeriod` int(11) NOT NULL DEFAULT '0',
  `creditLimit` decimal(10,3) NOT NULL DEFAULT '0.000',
  `contacts` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `explains` text,
  `isDisable` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `createUserid` int(11) NOT NULL,
  `delFlag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_non_member
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_non_member_credit`
-- ----------------------------
DROP TABLE IF EXISTS `dg_non_member_credit`;
CREATE TABLE `dg_non_member_credit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` int(11) NOT NULL,
  `documentNo` varchar(200) NOT NULL,
  `documentType` int(11) NOT NULL,
  `aymentAmount` decimal(10,3) NOT NULL DEFAULT '0.000',
  `creditAmount` decimal(10,3) NOT NULL DEFAULT '0.000',
  `paidAmount` decimal(10,3) NOT NULL DEFAULT '0.000',
  `discountAmount` decimal(10,3) NOT NULL DEFAULT '0.000',
  `paymentTime` datetime NOT NULL,
  `operatorId` varchar(200) NOT NULL,
  `settlementFlowNum` varchar(200) DEFAULT NULL,
  `explains` text,
  `createTime` datetime NOT NULL,
  `createUserid` int(11) NOT NULL,
  `delFlag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_non_member_credit
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_nutrition`
-- ----------------------------
DROP TABLE IF EXISTS `dg_nutrition`;
CREATE TABLE `dg_nutrition` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL COMMENT '编码',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `bzsrl` double DEFAULT NULL COMMENT '标准摄入量',
  `unit` int(11) DEFAULT NULL COMMENT '单位',
  `zjf` varchar(20) DEFAULT NULL COMMENT '助记符',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营养表';

-- ----------------------------
-- Records of dg_nutrition
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_offset`
-- ----------------------------
DROP TABLE IF EXISTS `dg_offset`;
CREATE TABLE `dg_offset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `time` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '加单还是减单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_offset
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_class_water`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_class_water`;
CREATE TABLE `dg_open_class_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_user` varchar(20) DEFAULT NULL,
  `login_user_name` varchar(30) DEFAULT NULL COMMENT '登录系统时的名称',
  `login_pos` int(11) DEFAULT NULL COMMENT '登录POS的code',
  `petty_cash` double DEFAULT NULL COMMENT '备用金',
  `login_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL COMMENT '结班时间',
  `open_class_state` int(11) DEFAULT '0' COMMENT '默认为0，为结班。1表示已结班',
  `open_class_water` varchar(100) DEFAULT NULL COMMENT '结班流水号',
  `item_count_sum` int(11) DEFAULT NULL COMMENT '品项数量和',
  `item_amounts_receivable` decimal(18,2) DEFAULT NULL COMMENT '所有品项应收金额',
  `item_discount` decimal(18,2) DEFAULT NULL COMMENT '品项折扣和',
  `discount_mount` decimal(18,2) DEFAULT NULL COMMENT '品项折后金额',
  `last_open_water_amount` int(11) DEFAULT NULL COMMENT '前班未结台数',
  `last_open_deposit` decimal(18,2) DEFAULT NULL COMMENT '前班未结押金',
  `current_wj_amount` int(11) DEFAULT NULL COMMENT '本班未结台数',
  `current_wj_deposit` decimal(18,2) DEFAULT NULL COMMENT '本班未退押金',
  `bbkdrs` int(11) DEFAULT NULL,
  `bbkdzs` int(11) DEFAULT NULL,
  `bbwjje` decimal(18,2) DEFAULT NULL,
  `bbyjrs` int(11) DEFAULT NULL,
  `bbzdje` decimal(18,2) DEFAULT NULL,
  `bbtcje` decimal(18,2) DEFAULT NULL,
  `xfje` decimal(18,2) DEFAULT NULL,
  `fwf` decimal(18,2) DEFAULT NULL,
  `bff` decimal(18,2) DEFAULT NULL,
  `zdxfbq` decimal(18,2) DEFAULT NULL,
  `yhje` decimal(18,2) DEFAULT NULL,
  `ysxj` decimal(18,2) DEFAULT NULL,
  `mlje` decimal(18,2) DEFAULT NULL,
  `deyh` decimal(18,2) DEFAULT NULL,
  `yshj` decimal(18,2) DEFAULT NULL,
  `czje` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结班流水信息';

-- ----------------------------
-- Records of dg_open_class_water
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_discount`;
CREATE TABLE `dg_open_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proportion` double(11,0) DEFAULT NULL COMMENT '优惠的信息',
  `authorized_person` varchar(100) DEFAULT NULL COMMENT '授权人',
  `authorized_person_name` varchar(20) DEFAULT NULL COMMENT '预授权人名称',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `type` int(11) DEFAULT NULL COMMENT '1比例优惠 2全单优惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='结算流水优惠信息';

-- ----------------------------
-- Records of dg_open_discount
-- ----------------------------
INSERT INTO `dg_open_discount` VALUES ('4', '80', '001', '张三', '126', '2');
INSERT INTO `dg_open_discount` VALUES ('5', '80', '001', '张三', '129', '1');
INSERT INTO `dg_open_discount` VALUES ('6', '80', '001', '张三', '130', '2');

-- ----------------------------
-- Table structure for `dg_open_water`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water`;
CREATE TABLE `dg_open_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_water_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water_2017_12`;
CREATE TABLE `dg_open_water_2017_12` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_water_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water_2018_01`;
CREATE TABLE `dg_open_water_2018_01` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_water_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water_2018_02`;
CREATE TABLE `dg_open_water_2018_02` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_water_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water_2018_03`;
CREATE TABLE `dg_open_water_2018_03` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_open_water_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_open_water_2018_04`;
CREATE TABLE `dg_open_water_2018_04` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业流水ID',
  `ow_num` varchar(50) DEFAULT NULL COMMENT '营业流水号',
  `seat_id` int(11) DEFAULT NULL COMMENT '客桌ID',
  `people_count` int(11) DEFAULT NULL COMMENT '人数',
  `item_costs_sum` double DEFAULT NULL COMMENT '品项价格和',
  `discount_costs` double DEFAULT NULL COMMENT '优惠',
  `service_charge` double DEFAULT NULL COMMENT '服务费',
  `private_room_cost` double DEFAULT NULL COMMENT '包房费',
  `minimum_consumption` double DEFAULT NULL COMMENT '最低消费',
  `minimum_charge_complete` double DEFAULT NULL COMMENT '最低消费补齐',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `clearing_water_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `first_time` datetime DEFAULT NULL COMMENT '开台时间',
  `open_bis` varchar(20) DEFAULT NULL COMMENT '开台市别',
  `seat_amount` int(11) DEFAULT NULL COMMENT '该营业流水下有几张需要付款的客位，默认为1，转账+1',
  `ow_state` int(11) DEFAULT '1' COMMENT '营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、\n5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账',
  `seat_label` varchar(100) DEFAULT NULL COMMENT '客座标签',
  `open_pos` int(11) DEFAULT NULL COMMENT '开单POS',
  `waiter` int(11) DEFAULT NULL COMMENT '服务员',
  `marketing_staff` int(11) DEFAULT NULL COMMENT '营销员',
  `cr_id` varchar(100) DEFAULT NULL COMMENT '会员',
  `documents` varchar(100) DEFAULT NULL COMMENT '单据',
  `ow_notes` varchar(500) DEFAULT NULL COMMENT '整单备注',
  `closed_time` datetime DEFAULT NULL,
  `is_team` int(11) DEFAULT NULL COMMENT '开单默认为0，代表不是团队。1为团队',
  `team_members` varchar(500) DEFAULT NULL COMMENT '团队号码，开单就有团队号码，但不一定是团队开单',
  `team_main_seat` int(11) DEFAULT NULL COMMENT '是否为团队主客位',
  `transfer_target` varchar(100) DEFAULT NULL COMMENT '该营业流水转账的营业流水号',
  `join_team_notes` varchar(200) DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的备注（转账、加入团队）',
  `join_team_time` datetime DEFAULT NULL COMMENT '当该营业流水转账或者加入团队时的时间',
  `split_open_water` int(11) DEFAULT '0' COMMENT '是否为拆账流水，默认为0非拆账流水，1为拆账流水',
  `split_open_water_num` varchar(100) DEFAULT NULL COMMENT '拆账流水是来自哪个营业流水',
  `split_time` datetime DEFAULT NULL COMMENT '拆账时间',
  `yjd_print_num` int(11) DEFAULT '0' COMMENT '预接单打印次数',
  `kyd_print_num` int(11) DEFAULT '0' COMMENT '客用单打印次数',
  `handingsbill_time` datetime DEFAULT NULL COMMENT '该营业流水挂S账的时间',
  `open_operator` varchar(20) DEFAULT NULL COMMENT '开单的操作人员',
  `private_room_type` int(11) DEFAULT '0' COMMENT '包房费类型(不存在为0,存在为包房费方案id)',
  `is_increasing_item` int(11) DEFAULT NULL COMMENT '是否存在自增品项',
  `modify_service_charge` double DEFAULT NULL COMMENT '修改之后的服务费',
  `free_servce_charge` int(11) DEFAULT NULL COMMENT '是否免服务费',
  `free_min_spend` int(11) DEFAULT NULL COMMENT '是否免最低消费',
  `free_private_room` int(11) DEFAULT NULL COMMENT '免包房费',
  `modify_private_room` double DEFAULT NULL COMMENT '修改之后的包房费',
  `final_item_cost_sum` decimal(18,2) DEFAULT NULL COMMENT '买单时最终的品项消费',
  `yd_id` int(11) DEFAULT NULL COMMENT '预定id',
  PRIMARY KEY (`id`),
  KEY `index_ownum` (`ow_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水';

-- ----------------------------
-- Records of dg_open_water_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway`;
CREATE TABLE `dg_ow_clearingway` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway_2017_12`;
CREATE TABLE `dg_ow_clearingway_2017_12` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway_2018_01`;
CREATE TABLE `dg_ow_clearingway_2018_01` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway_2018_02`;
CREATE TABLE `dg_ow_clearingway_2018_02` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway_2018_03`;
CREATE TABLE `dg_ow_clearingway_2018_03` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_clearingway_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_clearingway_2018_04`;
CREATE TABLE `dg_ow_clearingway_2018_04` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `settlement_amount` double DEFAULT NULL COMMENT '结算金额',
  `conversion_amount` decimal(18,2) DEFAULT NULL COMMENT '换算金额',
  `notes` varchar(100) DEFAULT NULL COMMENT '备注',
  `cw_code` varchar(50) DEFAULT NULL COMMENT '结算方式code',
  `non_zero_amount` double DEFAULT NULL COMMENT '不找零金额',
  `foreign_pay` double DEFAULT NULL COMMENT '外币支付',
  `cw_time` datetime DEFAULT NULL COMMENT '结算时间',
  `cons_id` varchar(255) DEFAULT NULL COMMENT '会员支付Id',
  KEY `doc_cwid_index` (`cw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算方式';

-- ----------------------------
-- Records of dg_ow_clearingway_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details`;
CREATE TABLE `dg_ow_consumer_details` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details_2017_12`;
CREATE TABLE `dg_ow_consumer_details_2017_12` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details_2018_01`;
CREATE TABLE `dg_ow_consumer_details_2018_01` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details_2018_02`;
CREATE TABLE `dg_ow_consumer_details_2018_02` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details_2018_03`;
CREATE TABLE `dg_ow_consumer_details_2018_03` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_consumer_details_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_consumer_details_2018_04`;
CREATE TABLE `dg_ow_consumer_details_2018_04` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项ID',
  `item_file_number` double(11,2) DEFAULT NULL COMMENT '数量',
  `item_file_zs` double DEFAULT NULL COMMENT '只数',
  `production_costs` double DEFAULT NULL COMMENT '制作费用',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `subtotal` double DEFAULT NULL COMMENT '小计',
  `ow_id` int(11) DEFAULT NULL COMMENT '服务单号ID',
  `guest` varchar(3) DEFAULT NULL COMMENT '该品项消费的具体客座',
  `notes` varchar(5) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单  5/减少人数  6/增加人数 7/更换客位生存包房费品项',
  `serving_type` int(11) DEFAULT NULL COMMENT '上菜状态',
  `serving_type_global` int(11) DEFAULT NULL COMMENT '上菜状态是否为全局',
  `expectations_serving_time` datetime DEFAULT NULL COMMENT '期望上菜时间',
  `serving_case` int(11) DEFAULT NULL COMMENT '上菜情况',
  `is_tc` int(11) DEFAULT '0' COMMENT '是否是套餐',
  `parent_id` int(11) DEFAULT NULL COMMENT '父品项id',
  `item_final_price` double DEFAULT NULL COMMENT '品项加单时的最后价格',
  `back_ow_id` int(11) DEFAULT NULL COMMENT '退款时对应加单的服务id/方便计算制作费用',
  `reminder_number` int(11) DEFAULT '0' COMMENT '催单次数',
  `qc_zt` int(11) DEFAULT '0' COMMENT '是否已起菜',
  `qc_fs` int(11) DEFAULT NULL COMMENT '起菜方式 1/起菜 2/拖菜  3/停菜',
  `qc_zhsj` datetime DEFAULT NULL COMMENT '起菜最后时间',
  `qc_sl` double(11,0) DEFAULT '0' COMMENT '起菜数量',
  `variable_price` int(11) DEFAULT NULL COMMENT '是否变价，值0|1',
  `initial_price` double DEFAULT NULL COMMENT '初始价格',
  `zs_item_final_price` double DEFAULT '0' COMMENT '赠送计算当时单价',
  `zs_production_costs` double DEFAULT '0' COMMENT '赠送当时折扣和',
  `zs_subtotal` double DEFAULT '0' COMMENT '赠送和小计',
  `is_price_cal` int(11) DEFAULT '0' COMMENT '品项价格是否已经计算过',
  `item_pay_money` double DEFAULT NULL COMMENT '品项结算买单/埋单时的价格',
  `discount_money` decimal(18,2) DEFAULT NULL COMMENT '优惠金额',
  `settlement_status` int(11) DEFAULT '0' COMMENT '结算状态，默认为0，已经结算为1',
  `add_by_yxe` int(11) DEFAULT NULL,
  KEY `index_docd_owid_itemfileid` (`ow_id`) USING BTREE,
  KEY `index_docd_itemFileid` (`item_file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水消费明细';

-- ----------------------------
-- Records of dg_ow_consumer_details_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_customer_info`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_customer_info`;
CREATE TABLE `dg_ow_customer_info` (
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `age_range` varchar(20) DEFAULT NULL COMMENT '年龄范围',
  `man_count` int(11) DEFAULT NULL COMMENT '男人数',
  `female_count` int(11) DEFAULT NULL COMMENT '女人数',
  `forgen_count` int(11) DEFAULT NULL COMMENT '外宾人数',
  `reg_time` datetime DEFAULT NULL COMMENT '登记时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营业流水顾客信息';

-- ----------------------------
-- Records of dg_ow_customer_info
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_details_other`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_details_other`;
CREATE TABLE `dg_ow_details_other` (
  `sf_id` int(11) DEFAULT NULL COMMENT '服务单ID',
  `item_id` int(11) DEFAULT NULL COMMENT '具体品项ID',
  `otype` int(11) DEFAULT NULL COMMENT '1、口味。2要求。3制作方法',
  `ocode` varchar(20) DEFAULT '0' COMMENT 'code。临时数据为0',
  `oname` varchar(50) DEFAULT NULL COMMENT '具体的名称',
  `ocosts` double DEFAULT NULL COMMENT '当为制作方法时，前台修改的制作费用',
  `zzff_sf` int(11) DEFAULT NULL COMMENT '若是自制方法(制作方法是否收费 0/1)',
  `zzff_sf_type` int(11) DEFAULT NULL COMMENT '若是自制方法(收费类型  1/2  1按品项  2按品项数量)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品项的其他信息、口味、要求、制作方法';

-- ----------------------------
-- Records of dg_ow_details_other
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_discount`;
CREATE TABLE `dg_ow_discount` (
  `discount_info` varchar(500) DEFAULT NULL COMMENT '优惠的信息',
  `authorized_person` varchar(100) DEFAULT NULL COMMENT '授权人',
  `authorized_person_name` varchar(20) DEFAULT NULL COMMENT '预授权人名称',
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水优惠信息';

-- ----------------------------
-- Records of dg_ow_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_lockinfo`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_lockinfo`;
CREATE TABLE `dg_ow_lockinfo` (
  `owNum` varchar(100) DEFAULT NULL COMMENT '锁定操作的营业流水号',
  `ow_state` int(11) DEFAULT NULL COMMENT '锁定的状态 3  8 9',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定的时间',
  `lock_operator` varchar(10) DEFAULT NULL COMMENT '操作人',
  `lock_pos` int(11) DEFAULT NULL COMMENT '锁定的操作POS',
  `lock_notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_ow_lockinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_modify_seat`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_modify_seat`;
CREATE TABLE `dg_ow_modify_seat` (
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水',
  `org_seat_id` int(11) DEFAULT NULL COMMENT '更换前桌位id',
  `now_seat_id` int(11) DEFAULT NULL COMMENT '现在客位id',
  `org_fwf` double DEFAULT NULL COMMENT '原服务费',
  `org_bff` double DEFAULT NULL COMMENT '原包房费(按客位人数/固定金额收取不存在为0  只有包房收费方案存在原来包房费)',
  `is_ggbff` int(11) DEFAULT NULL COMMENT '是否更改包房费方案(只有按包房费方案存在此项)',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL COMMENT '更换桌位当前时间',
  `is_jsbffpx` int(11) DEFAULT NULL COMMENT '是否加入包房费品项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_ow_modify_seat
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt`;
CREATE TABLE `dg_ow_receipt` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt_2017_12`;
CREATE TABLE `dg_ow_receipt_2017_12` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt_2018_01`;
CREATE TABLE `dg_ow_receipt_2018_01` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt_2018_02`;
CREATE TABLE `dg_ow_receipt_2018_02` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt_2018_03`;
CREATE TABLE `dg_ow_receipt_2018_03` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_receipt_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_receipt_2018_04`;
CREATE TABLE `dg_ow_receipt_2018_04` (
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水ID',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_count` int(11) DEFAULT NULL COMMENT '发票张数',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_num` varchar(100) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算流水发票信息';

-- ----------------------------
-- Records of dg_ow_receipt_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form`;
CREATE TABLE `dg_ow_service_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form_2017_12`;
CREATE TABLE `dg_ow_service_form_2017_12` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form_2018_01`;
CREATE TABLE `dg_ow_service_form_2018_01` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form_2018_02`;
CREATE TABLE `dg_ow_service_form_2018_02` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form_2018_03`;
CREATE TABLE `dg_ow_service_form_2018_03` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_service_form_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_service_form_2018_04`;
CREATE TABLE `dg_ow_service_form_2018_04` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_num` varchar(50) DEFAULT NULL COMMENT '服务流水号',
  `ow_id` int(11) DEFAULT NULL COMMENT '营业流水ID',
  `waiter_id` int(11) DEFAULT NULL COMMENT '服务员ID',
  `service_time` datetime DEFAULT NULL COMMENT '服务时间',
  `service_roundtrip` varchar(10) DEFAULT NULL COMMENT '服务的客位',
  `service_type` int(11) DEFAULT NULL COMMENT '1/开单客位自增品项 2/加单  3/赠单  4/退单 5/减少人数  6/增加人数  7/更换客位生存包房费品项 8：单品转台',
  `zdbz` varchar(255) DEFAULT NULL COMMENT '整单备注',
  PRIMARY KEY (`id`),
  KEY `index_dosf_owid` (`ow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务单信息';

-- ----------------------------
-- Records of dg_ow_service_form_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_ow_sgds_kj`
-- ----------------------------
DROP TABLE IF EXISTS `dg_ow_sgds_kj`;
CREATE TABLE `dg_ow_sgds_kj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '蜀国大师优惠卷优惠类型 1定额优惠 2折扣优惠',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `coupon_id` varchar(255) DEFAULT NULL COMMENT '卡劵id',
  `cw_id` int(11) DEFAULT NULL COMMENT '结算流水id',
  `denomination` double DEFAULT NULL COMMENT '面额，类型为折扣券时，80 等价于 8 折',
  `purpose` int(11) DEFAULT NULL COMMENT '卡券用途，1：仅限抵扣酒水消费，2：仅限抵扣锅底消费，3：仅限抵扣菜品消',
  `yh_money` double DEFAULT NULL COMMENT '优惠金额',
  `time` varchar(255) DEFAULT NULL COMMENT '消费时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_ow_sgds_kj
-- ----------------------------
INSERT INTO `dg_ow_sgds_kj` VALUES ('1', '2', '100005', '26', '113', '70', '2', null, '2017-11-10 13:38:19');
INSERT INTO `dg_ow_sgds_kj` VALUES ('2', '1', '100005', '31', '114', '30', '3', null, '2017-11-10 13:45:32');
INSERT INTO `dg_ow_sgds_kj` VALUES ('3', '2', '100005', '30', '115', '88', '2', '8.16', '2017-11-10 13:55:45');
INSERT INTO `dg_ow_sgds_kj` VALUES ('4', '1', '100005', '36', '118', '68', '3', '68', '2017-11-10 15:08:55');

-- ----------------------------
-- Table structure for `dg_pay_merchants`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pay_merchants`;
CREATE TABLE `dg_pay_merchants` (
  `id` varchar(36) NOT NULL,
  `nickName` varchar(50) DEFAULT NULL,
  `orgId` varchar(36) DEFAULT NULL,
  `remark` varchar(250) DEFAULT NULL,
  `wxAppId` varchar(120) DEFAULT NULL,
  `wxAppSecret` varchar(120) DEFAULT NULL,
  `wxApiSecretKey` varchar(120) DEFAULT NULL,
  `wxMchId` varchar(64) DEFAULT NULL,
  `zfbPid` varchar(64) DEFAULT NULL,
  `zfbAppid` varchar(64) DEFAULT NULL,
  `zfbPrivateKey` varchar(2048) DEFAULT NULL,
  `zfbPublicKey` varchar(500) DEFAULT NULL,
  `zfbAlipayPublicKey` varchar(500) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_pay_merchants
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_pay_water`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pay_water`;
CREATE TABLE `dg_pay_water` (
  `id` varchar(36) NOT NULL,
  `outTradeNo` varchar(64) DEFAULT NULL,
  `threeTradeNo` varchar(64) DEFAULT NULL,
  `payMoney` decimal(18,2) DEFAULT NULL,
  `tradeDate` varchar(64) DEFAULT NULL,
  `payType` varchar(9) DEFAULT NULL,
  `payState` varchar(9) DEFAULT NULL,
  `payPeopleInfo` varchar(120) DEFAULT NULL,
  `bankCard` varchar(64) DEFAULT NULL,
  `bankCardType` varchar(20) DEFAULT NULL,
  `merchId` varchar(36) DEFAULT NULL,
  `orderNo` varchar(100) DEFAULT NULL,
  `orgId` varchar(64) DEFAULT NULL,
  `operUser` varchar(64) DEFAULT NULL,
  `createDate` varchar(64) DEFAULT NULL,
  `remark` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_pay_water
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_place_price`
-- ----------------------------
DROP TABLE IF EXISTS `dg_place_price`;
CREATE TABLE `dg_place_price` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_place_price
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_place_price_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_place_price_s`;
CREATE TABLE `dg_place_price_s` (
  `id` int(11) NOT NULL,
  `place_id` int(11) DEFAULT NULL COMMENT '区域id',
  `place_price_id` int(11) DEFAULT NULL COMMENT '区域价格对应id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `price` double DEFAULT NULL COMMENT '区域品项价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_place_price_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_pos`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pos`;
CREATE TABLE `dg_pos` (
  `id` int(11) NOT NULL,
  `number` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `ip_area` varchar(200) NOT NULL,
  `consumer_areas` text NOT NULL,
  `mnemonic` varchar(200) DEFAULT NULL,
  `organization` varchar(500) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(100) DEFAULT NULL,
  `del_flag` varchar(2) NOT NULL DEFAULT '0',
  `uuid_key` varchar(200) NOT NULL,
  `canJb` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_pos
-- ----------------------------
INSERT INTO `dg_pos` VALUES ('9999999', 'yxe_pos', '易小二pos', '000.000.000.000', '29', 'yxe_pos', '1', '2017-02-10 08:57:06', null, '0', '1803bcd8-2bff-4651-813a-0a106b448d65', '1');

-- ----------------------------
-- Table structure for `dg_pre_orderbill`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pre_orderbill`;
CREATE TABLE `dg_pre_orderbill` (
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `item_file_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_file_count` int(11) DEFAULT NULL COMMENT '预点数量',
  `ow_num` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预点单号(每次点单出一条)',
  `water_id` int(11) DEFAULT NULL COMMENT '营业流水号',
  `first_num` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '条码(每条流水只有一个）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dg_pre_orderbill
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_pre_orderbill_color`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pre_orderbill_color`;
CREATE TABLE `dg_pre_orderbill_color` (
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ow_num` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预点单号',
  `color_item_id` int(11) DEFAULT NULL COMMENT '颜色关联品项id',
  `water_id` int(11) DEFAULT NULL COMMENT '营业流水号',
  `color_item_count` int(11) DEFAULT NULL COMMENT '颜色对应点单数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dg_pre_orderbill_color
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_print_data`
-- ----------------------------
DROP TABLE IF EXISTS `dg_print_data`;
CREATE TABLE `dg_print_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `print_type` varchar(100) DEFAULT NULL COMMENT '打印类型 001/002...',
  `unique_identif` varchar(100) DEFAULT NULL COMMENT '唯一标识(时间戳)',
  `content` text COMMENT '打印发送json字符串',
  `success` int(11) DEFAULT '0' COMMENT '是否成功/成功为1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_print_data
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_print_manager`
-- ----------------------------
DROP TABLE IF EXISTS `dg_print_manager`;
CREATE TABLE `dg_print_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '打印机名称',
  `num` varchar(100) DEFAULT NULL COMMENT '打印机编码',
  `type` int(11) DEFAULT NULL COMMENT '1/2  按品项按品项小类',
  `disable` int(11) DEFAULT '0' COMMENT '是否停用',
  `trash` int(11) DEFAULT '0' COMMENT '是否进入回收站',
  `area_ids` varchar(255) DEFAULT NULL COMMENT '消费区域id组合  1,2,3',
  `q_o_z` int(11) DEFAULT '1' COMMENT '切打或整打',
  `zt` int(11) DEFAULT '0' COMMENT '是否打印转台单',
  `wm` int(11) DEFAULT '1' COMMENT '是否打印外卖',
  `ct` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_print_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_print_manager_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_print_manager_s`;
CREATE TABLE `dg_print_manager_s` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL COMMENT '父表id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id或品项小类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_print_manager_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_product_phase_leftmenu`
-- ----------------------------
DROP TABLE IF EXISTS `dg_product_phase_leftmenu`;
CREATE TABLE `dg_product_phase_leftmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `child_count` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `murl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_product_phase_leftmenu
-- ----------------------------
INSERT INTO `dg_product_phase_leftmenu` VALUES ('1', '品项管理', '6', null, null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('2', '品项打折', '4', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('3', '品项价格', '6', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('4', '品项限量', '3', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('5', '公告信息', '2', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('6', '其它', '4', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('7', '促销方案', '1', '1', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('8', '允许打折品项', '0', '2', null, '/DININGSYS/ProDiscount/allowDiscount');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('9', '品项打折方案', '0', '2', null, '/DININGSYS/ProDiscountPan/allowDiscountPlan');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('10', '重要活动打折', '0', '2', null, '/DININGSYS/ProImportant/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('12', '品项会员方案', '0', '2', null, '/DININGSYS/DgMemberDiscount/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('13', '一菜多价', '3', '3', null, null);
INSERT INTO `dg_product_phase_leftmenu` VALUES ('14', '促销品项', '0', '3', null, '/DININGSYS/DgPromotion/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('15', '时价品项', '0', '3', null, '/DININGSYS/DgCurrent/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('16', '阶梯价格', '0', '3', null, '/DININGSYS/ProLadder/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('17', '价格优先级', '0', '3', null, '/DININGSYS/ProPricePriority/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('18', '品项沽清', '0', '4', null, '/DININGSYS/ProItemOutofStock/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('19', '销售限量', '0', '4', null, '/DININGSYS/DgItemSaleLimit/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('20', '品项停用', '0', '4', null, '/DININGSYS/DgItemDisable/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('21', '推荐菜品', '0', '5', null, '/DININGSYS/RecommedItem/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('22', '最新菜品', '0', '5', null, '/DININGSYS/NewestItem/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('23', '品项出品部门', '0', '6', null, '/DININGSYS/DgItemProDepartment/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('24', '特殊品项', '0', '6', null, '/DININGSYS/DgSpecialItem/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('25', '品项出品厨师', '0', '6', null, '/DININGSYS/DgItemFromCook/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('26', '赠送品项促销方案', '0', '7', null, '/DININGSYS/DgItemGiftPlan/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('27', '按市别', '0', '13', null, '/DININGSYS/mealTime/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('28', '按消费区域', '0', '13', null, '/DININGSYS/placeTime/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('29', '按星期', '0', '13', null, '/DININGSYS/weekTime/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('30', '自定义金额', '0', '6', null, '/DININGSYS/DgCustomMoney/index');
INSERT INTO `dg_product_phase_leftmenu` VALUES ('31', '限时抢购', '0', '5', null, '/DININGSYS/DgItemTimeLimit/index');

-- ----------------------------
-- Table structure for `dg_promotion_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_promotion_item`;
CREATE TABLE `dg_promotion_item` (
  `id` int(11) NOT NULL COMMENT '促销品项id',
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(20) DEFAULT NULL COMMENT '品项编码',
  `max_count` int(11) DEFAULT NULL COMMENT '每客位最大点菜数量',
  `pro_price` double DEFAULT NULL COMMENT '促销价格',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_promotion_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_pro_methods`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pro_methods`;
CREATE TABLE `dg_pro_methods` (
  `id` int(11) NOT NULL,
  `pmcode` varchar(10) NOT NULL COMMENT '编号',
  `pmname` varchar(20) DEFAULT NULL COMMENT '名称',
  `pmmnemonic` varchar(20) DEFAULT NULL COMMENT '助记符',
  `pmtid` int(11) DEFAULT NULL COMMENT '制作方法类别',
  `orderno` int(11) DEFAULT NULL COMMENT '顺序号',
  `dept` int(11) DEFAULT NULL COMMENT '所属部门',
  `collect_pro_money` int(11) DEFAULT NULL COMMENT '收取制作费用',
  `pro_money` double(10,2) DEFAULT NULL COMMENT '制作费用',
  `collect_pro_moneybynum` int(11) DEFAULT '0' COMMENT '按品项数量收取制作费用',
  `can_update` int(11) DEFAULT NULL COMMENT '前台销售时可以修改制作费用 ',
  `description` varchar(200) DEFAULT NULL COMMENT '说明',
  `organid` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delFlag` int(11) DEFAULT NULL COMMENT '删除标识',
  `is_special_methods` int(11) DEFAULT NULL COMMENT '区分是否是品项添加时添加的制作方法',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='制作方法';

-- ----------------------------
-- Records of dg_pro_methods
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_pro_methods_type`
-- ----------------------------
DROP TABLE IF EXISTS `dg_pro_methods_type`;
CREATE TABLE `dg_pro_methods_type` (
  `id` int(11) NOT NULL,
  `pmtname` varchar(20) NOT NULL COMMENT '名称',
  `pmtorder` int(11) DEFAULT NULL COMMENT '顺序号',
  `organid` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标识',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_pro_methods_type
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_public_code0`
-- ----------------------------
DROP TABLE IF EXISTS `dg_public_code0`;
CREATE TABLE `dg_public_code0` (
  `id` int(11) DEFAULT NULL,
  `cCode` varchar(3) DEFAULT NULL COMMENT '代码',
  `cName` varchar(24) DEFAULT NULL COMMENT '名称',
  `cKeyWD` varchar(24) DEFAULT NULL COMMENT '速记码',
  `cParent` varchar(24) DEFAULT NULL COMMENT '上级代码',
  `cOrder` int(11) DEFAULT NULL COMMENT '排序值',
  `iSystem` varchar(2) DEFAULT NULL COMMENT '是否系统公共代码 ： “Y”-是，”N“或”null“不是',
  `iDelFlg` varchar(2) DEFAULT '0' COMMENT '是否删除 默认“0”-未删除 “1”-已删除',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_public_code0
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `dg_receipt`;
CREATE TABLE `dg_receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receipt_amount` int(11) DEFAULT NULL COMMENT '发票金额',
  `receipt_denomination` int(11) DEFAULT NULL COMMENT '发票面额',
  `receipt_num` varchar(20) DEFAULT NULL COMMENT '发票号码',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发票信息表';

-- ----------------------------
-- Records of dg_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water`;
CREATE TABLE `dg_reception_clearing_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water_2017_12`;
CREATE TABLE `dg_reception_clearing_water_2017_12` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water_2018_01`;
CREATE TABLE `dg_reception_clearing_water_2018_01` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water_2018_02`;
CREATE TABLE `dg_reception_clearing_water_2018_02` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water_2018_03`;
CREATE TABLE `dg_reception_clearing_water_2018_03` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reception_clearing_water_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reception_clearing_water_2018_04`;
CREATE TABLE `dg_reception_clearing_water_2018_04` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_num` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算流水号',
  `consumption_amount` double DEFAULT NULL COMMENT '消费金额',
  `zero_amount` double DEFAULT NULL COMMENT '抹零金额',
  `fixed_discount` double DEFAULT NULL COMMENT '定额优惠',
  `amounts_receivable` double DEFAULT NULL COMMENT '应收金额',
  `paid_amount` double DEFAULT NULL COMMENT '实收金额',
  `change_amount` double DEFAULT NULL COMMENT '找零金额',
  `clearing_time` datetime DEFAULT NULL COMMENT '结算时间',
  `clearing_bis` int(11) DEFAULT NULL COMMENT '结算市别',
  `clearing_operator` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算操作人员',
  `clearing_pos` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算POS',
  `print_cont` int(11) DEFAULT NULL COMMENT '打印次数',
  `invoicing` int(11) DEFAULT NULL COMMENT '开具发票',
  `zero_settlement` int(11) DEFAULT NULL COMMENT '零结算',
  `retro_documents` int(11) DEFAULT NULL COMMENT '补录单据',
  `statement_label` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结账单标注',
  `coupon_amount` double DEFAULT NULL COMMENT '赠券金额',
  `clearing_notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算备注',
  `clearing_member` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算会员',
  `clearing_state` int(11) DEFAULT NULL COMMENT '结算状态1：未结（埋单）、2：已结、3是否存在返结算、4该结算流水已经续单，表示这条结算流水已经作废、5该营业流水为埋单作废',
  `shift_state` int(11) DEFAULT NULL,
  `jb_id` int(11) DEFAULT NULL COMMENT '接班流水关联',
  `general_proportions` double DEFAULT NULL COMMENT '常规比例',
  `single_proportions` double DEFAULT NULL COMMENT '全单比例',
  `pay_type` int(11) DEFAULT NULL COMMENT '品项打折（1）、重要活动（2）以及会员支付（3）',
  `before_discounts_amount` double(11,2) DEFAULT NULL,
  `total_discount_amount` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算流水表';

-- ----------------------------
-- Records of dg_reception_clearing_water_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_recommend_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_recommend_item`;
CREATE TABLE `dg_recommend_item` (
  `id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_recommend_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reserve_manager`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reserve_manager`;
CREATE TABLE `dg_reserve_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cr_id` varchar(255) DEFAULT NULL COMMENT '会员id',
  `seat_id` int(11) DEFAULT NULL COMMENT '客位id',
  `number` int(11) DEFAULT NULL COMMENT '人数',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `yd_time` datetime DEFAULT NULL COMMENT '预定时间',
  `yd_num` varchar(255) DEFAULT NULL COMMENT '预定单号',
  `state` int(11) DEFAULT NULL COMMENT '预定状态 (0初始化状态,1已成,-1废弃)',
  `yd_resoure` varchar(255) DEFAULT '0' COMMENT '预定来源',
  `xszf` int(11) DEFAULT '0' COMMENT '是否是线上支付',
  `xszf_money` double DEFAULT NULL COMMENT '线上支付金额',
  `xszf_type` int(11) DEFAULT NULL COMMENT '线上支付类型(1支付宝,2微信..)',
  `time` datetime DEFAULT NULL COMMENT '下单时间',
  `name` varchar(155) DEFAULT NULL COMMENT '预定人姓名',
  `sex` int(11) DEFAULT NULL COMMENT '预定人性别',
  `w_o_w` int(11) DEFAULT '0',
  `b_s_d` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_reserve_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_reserve_seatids`
-- ----------------------------
DROP TABLE IF EXISTS `dg_reserve_seatids`;
CREATE TABLE `dg_reserve_seatids` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seat_id` int(11) NOT NULL COMMENT '客位id',
  `reserve_id` int(11) NOT NULL COMMENT '预约id',
  `state` int(11) DEFAULT NULL COMMENT '状态 1/-1 null 为初始化状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_reserve_seatids
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_charging_scheme`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_charging_scheme`;
CREATE TABLE `dg_seat_charging_scheme` (
  `id` int(11) NOT NULL,
  `code` varchar(30) DEFAULT NULL COMMENT '方案编码',
  `name` varchar(30) DEFAULT NULL COMMENT '方案名称',
  `type` int(11) DEFAULT NULL COMMENT '方案类型',
  `type_min_val` int(11) DEFAULT NULL COMMENT '不足多少分钟，按多少分钟补齐',
  `type_hour_val` int(11) DEFAULT NULL COMMENT '尾时段不足多少分钟不计费',
  `amount_up_lim` double DEFAULT NULL COMMENT '收费金额上限',
  `t_long_low_lim` int(11) DEFAULT NULL COMMENT '收费时长下限',
  `qy_ssdsf` int(11) DEFAULT NULL COMMENT '启用首时段收费',
  `ssdsf_min` int(11) DEFAULT NULL COMMENT '多少分钟',
  `ssdsf_money` double DEFAULT NULL COMMENT '多少钱',
  `trash` int(11) DEFAULT NULL COMMENT '回收站',
  `time` datetime DEFAULT NULL COMMENT '最新修改时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_seat_charging_scheme
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_charging_scheme_s`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_charging_scheme_s`;
CREATE TABLE `dg_seat_charging_scheme_s` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL COMMENT '父id',
  `t_long` int(11) DEFAULT NULL COMMENT '时长，多少分钟',
  `money` double DEFAULT NULL COMMENT '收费金额',
  `sd` int(11) DEFAULT NULL COMMENT '时段对应数据字典id',
  `discount` int(11) DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_seat_charging_scheme_s
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_doorinfo`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_doorinfo`;
CREATE TABLE `dg_seat_doorinfo` (
  `mac` varchar(100) DEFAULT NULL,
  `seatId` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_seat_doorinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_item`;
CREATE TABLE `dg_seat_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL COMMENT '品项数量',
  `item_code` varchar(30) DEFAULT NULL COMMENT '品项code',
  `count` double DEFAULT NULL COMMENT '开单数量',
  `use_open_counter` int(11) DEFAULT NULL COMMENT '是否按照开单人数',
  `seat_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_seat_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_manager`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_manager`;
CREATE TABLE `dg_seat_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zdxf` int(11) DEFAULT NULL COMMENT '是否收取最低消费',
  `zdxf_type` int(11) DEFAULT NULL COMMENT '最低消费类型(1/按每客位 2/每客位人数)',
  `zdxf_money` decimal(18,2) DEFAULT NULL COMMENT '最低消费金额',
  `fwf` int(11) DEFAULT NULL COMMENT '是否收取服务费',
  `fwf_type` int(11) DEFAULT NULL COMMENT '服务费类型',
  `fwf_gd` decimal(18,2) DEFAULT NULL COMMENT '服务费固定金额',
  `fwf_xf_ratio` int(11) DEFAULT NULL COMMENT '服务费消费比例',
  `fwf_con_free` decimal(18,2) DEFAULT NULL COMMENT '服务费消费满多少免费',
  `fwf_sx` decimal(18,2) DEFAULT NULL COMMENT '服务费上限',
  `fwf_people` int(11) DEFAULT NULL COMMENT '服务费客位人数',
  `qssc` int(11) DEFAULT NULL COMMENT '清扫时长',
  `qssc_tx` int(11) DEFAULT NULL COMMENT '启动清扫时长语音提醒功能',
  `bff` int(11) DEFAULT NULL COMMENT '是否收取包房费',
  `bff_gd` decimal(18,2) DEFAULT NULL COMMENT '包房费固定金额收取',
  `bff_people` int(11) DEFAULT NULL COMMENT '包房费客位人数收取',
  `bff_gd_pro` int(11) DEFAULT NULL COMMENT '固定包房费收费方案对应id(具体数据子表关联)',
  `bff_week_pro` int(11) DEFAULT NULL COMMENT '一周内设置不同的包房费方案',
  `bff_item_id` int(11) DEFAULT NULL COMMENT '包房费品项id',
  `bff_con_free` decimal(18,2) DEFAULT NULL COMMENT '包房费消费满多少不收取',
  `bff_timing` int(11) DEFAULT NULL COMMENT '是否启动包房计时功能',
  `seat_id` int(11) DEFAULT NULL COMMENT '客位id',
  `bff_week_pro_d` varchar(1000) DEFAULT NULL COMMENT '包房收费一周内设置不同方案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_seat_manager
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_seat_reservation`
-- ----------------------------
DROP TABLE IF EXISTS `dg_seat_reservation`;
CREATE TABLE `dg_seat_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seat_id` int(11) DEFAULT NULL COMMENT '客座ID',
  `reservation_time` datetime DEFAULT NULL COMMENT '预定时间',
  `implementation_time` datetime DEFAULT NULL COMMENT '预定的执行时间',
  `actual_time` datetime DEFAULT NULL COMMENT '实际上台时间',
  `book_people` varchar(20) DEFAULT NULL COMMENT '预定人名称',
  `reserve_telephone` varchar(20) DEFAULT NULL COMMENT '预留的电话号码',
  `reserve_way` int(11) DEFAULT NULL COMMENT '预定的方式1店内预定、2电话预定、3网上预定',
  `reservation_costs` double DEFAULT NULL COMMENT '预定的费用',
  `reserve_people_count` int(11) DEFAULT NULL COMMENT '预定人数',
  `reserve_state` int(11) DEFAULT NULL COMMENT '状态',
  `reserve_bis` int(11) DEFAULT NULL COMMENT '预定市别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客座预定信息';

-- ----------------------------
-- Records of dg_seat_reservation
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_service_class`
-- ----------------------------
DROP TABLE IF EXISTS `dg_service_class`;
CREATE TABLE `dg_service_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eatTimeId` int(11) NOT NULL,
  `seatId` int(11) NOT NULL,
  `waiterId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUserid` varchar(200) NOT NULL,
  `delFlag` int(11) NOT NULL DEFAULT '0',
  `uuid_key` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_service_class
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_serving`
-- ----------------------------
DROP TABLE IF EXISTS `dg_serving`;
CREATE TABLE `dg_serving` (
  `id` int(11) NOT NULL,
  `scode` varchar(10) NOT NULL COMMENT '编码',
  `sname` varchar(20) DEFAULT NULL COMMENT '名称',
  `mnemonic` varchar(20) DEFAULT NULL COMMENT '助记符',
  `sorg` int(11) DEFAULT NULL COMMENT '所属组织结构',
  `create_time` datetime DEFAULT NULL,
  `delFlag` int(11) DEFAULT NULL,
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上菜状态';

-- ----------------------------
-- Records of dg_serving
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_settlement_way`
-- ----------------------------
DROP TABLE IF EXISTS `dg_settlement_way`;
CREATE TABLE `dg_settlement_way` (
  `id` int(11) NOT NULL,
  `number` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) NOT NULL,
  `exchangeRate` decimal(10,3) NOT NULL DEFAULT '0.000',
  `shortcutKey` varchar(20) DEFAULT NULL,
  `rollFaceValue` decimal(10,3) NOT NULL DEFAULT '0.000',
  `createTime` datetime DEFAULT NULL,
  `explains` text,
  `actualIncomeRatio` decimal(10,3) NOT NULL DEFAULT '0.000',
  `notActualIncomeRatio` decimal(10,3) NOT NULL DEFAULT '0.000',
  `isDisabled` int(11) NOT NULL DEFAULT '0',
  `isCommon` int(11) NOT NULL DEFAULT '0',
  `isKeepSystem` int(11) NOT NULL DEFAULT '0',
  `isMustInformation` int(11) NOT NULL DEFAULT '0',
  `isAloneUse` int(11) NOT NULL DEFAULT '0',
  `isCurrency` int(11) NOT NULL DEFAULT '0',
  `isAllowChange` int(11) NOT NULL DEFAULT '0',
  `delFlag` int(11) NOT NULL DEFAULT '0',
  `createUserid` varchar(100) DEFAULT NULL,
  `uuid_key` varchar(200) NOT NULL,
  `dklx` int(1) DEFAULT NULL COMMENT '券抵扣类型,1：固定金额，2：百分比金额',
  `qyxdksz` int(1) DEFAULT NULL COMMENT '券允许抵扣设置，1：无限制，2：大类，3：小类，4：具体品项',
  `itemIds` text,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_settlement_way
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_settlement_way_rank`
-- ----------------------------
DROP TABLE IF EXISTS `dg_settlement_way_rank`;
CREATE TABLE `dg_settlement_way_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '结算方式排序管理表',
  `settlementWayId` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dg_settlement_way_rank
-- ----------------------------
INSERT INTO `dg_settlement_way_rank` VALUES ('1', '31', '10');
INSERT INTO `dg_settlement_way_rank` VALUES ('2', '36', '8');
INSERT INTO `dg_settlement_way_rank` VALUES ('3', '13', '1');
INSERT INTO `dg_settlement_way_rank` VALUES ('4', '14', '3');
INSERT INTO `dg_settlement_way_rank` VALUES ('5', '16', '6');
INSERT INTO `dg_settlement_way_rank` VALUES ('6', '17', '7');
INSERT INTO `dg_settlement_way_rank` VALUES ('7', '18', '5');
INSERT INTO `dg_settlement_way_rank` VALUES ('8', '19', '9');
INSERT INTO `dg_settlement_way_rank` VALUES ('9', '21', '4');
INSERT INTO `dg_settlement_way_rank` VALUES ('10', '22', '2');
INSERT INTO `dg_settlement_way_rank` VALUES ('11', '30', '11');
INSERT INTO `dg_settlement_way_rank` VALUES ('12', '35', '12');
INSERT INTO `dg_settlement_way_rank` VALUES ('13', '37', '13');
INSERT INTO `dg_settlement_way_rank` VALUES ('14', '29', '14');
INSERT INTO `dg_settlement_way_rank` VALUES ('15', '33', '15');
INSERT INTO `dg_settlement_way_rank` VALUES ('16', '40', '16');
INSERT INTO `dg_settlement_way_rank` VALUES ('17', '34', '17');
INSERT INTO `dg_settlement_way_rank` VALUES ('18', '32', '18');
INSERT INTO `dg_settlement_way_rank` VALUES ('19', '28', '19');
INSERT INTO `dg_settlement_way_rank` VALUES ('20', '39', '20');
INSERT INTO `dg_settlement_way_rank` VALUES ('21', '67', '1');
INSERT INTO `dg_settlement_way_rank` VALUES ('22', '59', '2');
INSERT INTO `dg_settlement_way_rank` VALUES ('23', '62', '3');
INSERT INTO `dg_settlement_way_rank` VALUES ('24', '64', '4');
INSERT INTO `dg_settlement_way_rank` VALUES ('25', '61', '5');
INSERT INTO `dg_settlement_way_rank` VALUES ('26', '60', '6');
INSERT INTO `dg_settlement_way_rank` VALUES ('27', '65', '7');
INSERT INTO `dg_settlement_way_rank` VALUES ('28', '63', '8');

-- ----------------------------
-- Table structure for `dg_settlement_way_type`
-- ----------------------------
DROP TABLE IF EXISTS `dg_settlement_way_type`;
CREATE TABLE `dg_settlement_way_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `hasRollFace` int(11) NOT NULL DEFAULT '0',
  `hasActualIncomeRatio` int(11) NOT NULL DEFAULT '0',
  `hasNotActualIncomeRatio` int(11) NOT NULL DEFAULT '0',
  `hasCurrency` int(11) NOT NULL DEFAULT '0',
  `hasAllowChange` int(11) NOT NULL DEFAULT '0',
  `explains` text,
  `delFlag` int(11) DEFAULT NULL,
  `createUserid` varchar(100) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_settlement_way_type
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_specially_business`
-- ----------------------------
DROP TABLE IF EXISTS `dg_specially_business`;
CREATE TABLE `dg_specially_business` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `businessId` varchar(200) NOT NULL,
  `discountRate` decimal(10,3) NOT NULL DEFAULT '0.000',
  `isEditRate` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `createUserid` int(11) NOT NULL,
  `delFlag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_specially_business
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_special_item`
-- ----------------------------
DROP TABLE IF EXISTS `dg_special_item`;
CREATE TABLE `dg_special_item` (
  `id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL COMMENT '品项id',
  `item_code` varchar(30) DEFAULT NULL COMMENT '品项编码',
  `type` int(11) DEFAULT NULL COMMENT '类型(1代表服务费品项/2代表最低消费品项)',
  `min_consume_type` int(11) DEFAULT NULL COMMENT '如果为type,对应最低消费补齐方式',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_special_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_takeout_byonline`
-- ----------------------------
DROP TABLE IF EXISTS `dg_takeout_byonline`;
CREATE TABLE `dg_takeout_byonline` (
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '注释',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `createTime` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `finishTime` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '完成时间',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `contactUser` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人',
  `contactTel` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `contactAddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系地址',
  `orderDetails` text COLLATE utf8_unicode_ci COMMENT '订单详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dg_takeout_byonline
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_url_setting`
-- ----------------------------
DROP TABLE IF EXISTS `dg_url_setting`;
CREATE TABLE `dg_url_setting` (
  `code` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_url_setting
-- ----------------------------
INSERT INTO `dg_url_setting` VALUES ('mysql.bin', 'D:/mysql-5.6.24-winx64/bin/', 'mysql安装地址bin目录', '1');
INSERT INTO `dg_url_setting` VALUES ('member.url', '', '会员地址', '2');
INSERT INTO `dg_url_setting` VALUES ('synmsUrl', 'http://192.168.2.147:8080/yqsh-ms1', 'synmsUrl', '3');
INSERT INTO `dg_url_setting` VALUES ('encDog', '3be47813-73f6-4e1b-ba80-e8b6a2445029', '会员分店KEY(易小二平台)', '4');
INSERT INTO `dg_url_setting` VALUES ('print.url', 'http://127.0.0.1:8081/json', '打印机地址', '5');
INSERT INTO `dg_url_setting` VALUES ('yxe.url', 'http://114.115.147.30:8085', '易小二支付接口', '6');
INSERT INTO `dg_url_setting` VALUES ('yxe.applyId', '', 'yxe.applyId(江阴)', '7');
INSERT INTO `dg_url_setting` VALUES ('yxe.sha', '', 'yxe.sha', '8');
INSERT INTO `dg_url_setting` VALUES ('yxe.des', '', 'yxe.des', '9');
INSERT INTO `dg_url_setting` VALUES ('queuingSYS_URL', 'http://192.168.2.69:8080/icatering1', '排号系统', '10');
INSERT INTO `dg_url_setting` VALUES ('queuingSYS_ID', 'bd5c20b6-5736-40f4-b43f-7bdc78dfbb2b', '排号系统所用的店铺ID', '11');
INSERT INTO `dg_url_setting` VALUES ('queuingSYS_key', '!@#$%http://www.yqsh.com&&123456789^*', '排号系统所用的KEY', '12');
INSERT INTO `dg_url_setting` VALUES ('DOWN_DATA_SHOPKEY', '', '数据下载所需的KEY', '13');
INSERT INTO `dg_url_setting` VALUES ('DOWN_DATA_URL', 'http://pt.yqsh.com/cateringChain', '数据下载的地址', '14');
INSERT INTO `dg_url_setting` VALUES ('memberZDKey', '', '会员总店key', '15');
INSERT INTO `dg_url_setting` VALUES ('ONLINE_DOMAIN', '192.168.2.55:8080', '获取线上二维码域名', '16');
INSERT INTO `dg_url_setting` VALUES ('USE_MEMBER', '0', '是否启用会员', '17');
INSERT INTO `dg_url_setting` VALUES ('UPDATE_SET_STATE', '0', '排号更新桌子信息', '18');
INSERT INTO `dg_url_setting` VALUES ('IS_WM', '0', '外卖开关', '19');
INSERT INTO `dg_url_setting` VALUES ('ONLINE_ORDER', '1', '线上点餐开关', '20');
INSERT INTO `dg_url_setting` VALUES ('yxe_qy', '0', '是否启动易小二区域菜品显示控制', '21');
INSERT INTO `dg_url_setting` VALUES ('yxe_qy_c', '8,9,10', '易小二品项显示控制区域(id组合，逗号分隔)', '22');
INSERT INTO `dg_url_setting` VALUES ('yxe_qy_o', '36,37', '易小二不受控制区域显示品项小类(一般是锅底)', '23');
INSERT INTO `dg_url_setting` VALUES ('SGDS_KJ_URL', 'https://mastershu.mashakeji.com/index/Api', '蜀国大师卡劵核销url', '24');
INSERT INTO `dg_url_setting` VALUES ('SGDS_KJ_JS', '108,109.110,111,102', '酒水小类id组合', '25');
INSERT INTO `dg_url_setting` VALUES ('SGDS_KJ_GD', '104,105', '锅底小类组合', '26');
INSERT INTO `dg_url_setting` VALUES ('SGDS_KJ_CP', '101,106,107,112,113,114,115,116,117,118,119,120,121,124,125', '菜品打折小类组合', '27');
INSERT INTO `dg_url_setting` VALUES ('SGDS_JB_DEPITS', '009', null, '29');
INSERT INTO `dg_url_setting` VALUES ('member.comId', '', '会员分店Key(会员平台)', '30');
INSERT INTO `dg_url_setting` VALUES ('member.applyId', '', '会员applyId', '31');
INSERT INTO `dg_url_setting` VALUES ('member.sha', '', '会员sha', '32');
INSERT INTO `dg_url_setting` VALUES ('member.des', '', '会员des', '33');
INSERT INTO `dg_url_setting` VALUES ('IS_OFFSET', '0', '是否冲减', '34');
INSERT INTO `dg_url_setting` VALUES ('store_url', 'http://localhost:8084/store', '冲减地址', '35');

-- ----------------------------
-- Table structure for `dg_week_discount`
-- ----------------------------
DROP TABLE IF EXISTS `dg_week_discount`;
CREATE TABLE `dg_week_discount` (
  `id` int(11) NOT NULL COMMENT '自增主键',
  `name` varchar(255) NOT NULL COMMENT '名字',
  `p_id` int(11) NOT NULL DEFAULT '0' COMMENT '方案id',
  `uuid_key` varchar(100) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_week_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `dg_yxe_cons_item_show`
-- ----------------------------
DROP TABLE IF EXISTS `dg_yxe_cons_item_show`;
CREATE TABLE `dg_yxe_cons_item_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cons_id` int(11) DEFAULT NULL,
  `item_id` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dg_yxe_cons_item_show
-- ----------------------------

-- ----------------------------
-- Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `jobid` varchar(40) NOT NULL DEFAULT '',
  `createtime` timestamp NULL DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `jobname` varchar(255) DEFAULT NULL,
  `jobgroup` varchar(255) DEFAULT NULL,
  `jobstatus` varchar(255) DEFAULT NULL,
  `cronexpression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `beanclass` varchar(255) DEFAULT NULL,
  `isconcurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `springid` varchar(255) DEFAULT NULL,
  `methodname` varchar(255) NOT NULL,
  `delflag` int(11) DEFAULT '0',
  PRIMARY KEY (`jobid`),
  UNIQUE KEY `name_group` (`jobname`,`jobgroup`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('13ca5dd6-bf6c-11e7-9414-00259030e14f', '2017-11-02 09:20:13', '2017-11-03 10:04:59', '轮询客位信息到缓存', 'loop', '0', '0/8 * * * * ?', '轮询客位信息到缓存', 'com.yqsh.diningsys.web.interceptors.YxeTaskBean', '0', null, 'Loop', '0');
INSERT INTO `schedule_job` VALUES ('5e17fca9-badb-11e7-9414-00259030e14f', '2014-04-25 16:37:12', '2017-11-03 10:05:00', '验证日期', 'checkTime', '0', '0 0/2 *  * * ?', '验证日期', 'com.yqsh.diningsys.web.interceptors.GqTaskBean', '0', null, 'checkTime', '0');
INSERT INTO `schedule_job` VALUES ('5e180287-badb-11e7-9414-00259030e14f', '2014-04-25 16:52:17', '2017-11-03 09:56:46', '打印轮循', 'printLoop', '0', '0/5 * *  * * ?', '打印轮循,是否存在数据,存在发送数据', 'com.yqsh.diningsys.web.interceptors.GqTaskBean', '0', null, 'printLoop', '0');
INSERT INTO `schedule_job` VALUES ('5e180636-badb-11e7-9414-00259030e14f', '2016-01-05 10:16:35', '2017-11-03 09:51:04', '打印轮循', 'yd', '0', '0 */1 *  * * ?', '打印轮循,是否存在数据,存在发送数据', 'com.yqsh.diningsys.web.interceptors.GqTaskBean', '0', null, 'yd', '0');
INSERT INTO `schedule_job` VALUES ('5e1809b6-badb-11e7-9414-00259030e14f', '2016-01-05 10:23:19', '2017-11-03 10:05:04', '清扫状态修改及轮循平台座位状态', 'clearingStateReset', '0', '0 0/1 *  * * ?', '清扫状态修改及轮循平台座位状态', 'com.yqsh.diningsys.web.interceptors.GqTaskBean', '0', null, 'clearingStateReset', '0');
INSERT INTO `schedule_job` VALUES ('d49814f1-badf-11e7-9414-00259030e14f', '2017-10-27 14:26:21', '2017-11-03 10:19:31', '定时上传分店中的营业流水数据', 'autoDataUpload', '0', '0 * 0/2 * * ?', '定时上传分店中的营业流水数据', 'com.yqsh.diningsys.web.util.BusinessDataUpload', '0', null, 'autoDataUpload', '0');

-- ----------------------------
-- Table structure for `sys_authorization_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorization_log`;
CREATE TABLE `sys_authorization_log` (
  `id` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `auth_time` datetime DEFAULT NULL COMMENT '授权时间',
  `auth_code` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '授权码',
  `auth_op_user` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作用户',
  `auth_user` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '授权用户',
  `auth_remarks` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作备注信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='授权码日志';

-- ----------------------------
-- Records of sys_authorization_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_autoseq`
-- ----------------------------
DROP TABLE IF EXISTS `sys_autoseq`;
CREATE TABLE `sys_autoseq` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `rownum` int(11) DEFAULT NULL,
  `currentnum` int(11) DEFAULT NULL,
  `hasparent` int(11) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `head` varchar(255) DEFAULT NULL,
  `hashead` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_autoseq
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_backup_database`
-- ----------------------------
DROP TABLE IF EXISTS `sys_backup_database`;
CREATE TABLE `sys_backup_database` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_number` varchar(30) DEFAULT NULL,
  `product_name` varchar(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `backup_file_name` varchar(30) DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `backup_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_backup_database
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2017_08`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2017_08`;
CREATE TABLE `sys_business_log_2017_08` (
  `uuid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `oper` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COLLATE utf8_unicode_ci COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_business_log_2017_08
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2017_09`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2017_09`;
CREATE TABLE `sys_business_log_2017_09` (
  `uuid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `oper` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COLLATE utf8_unicode_ci COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_business_log_2017_09
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2017_10`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2017_10`;
CREATE TABLE `sys_business_log_2017_10` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2017_10
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2017_11`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2017_11`;
CREATE TABLE `sys_business_log_2017_11` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2017_11
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2017_12`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2017_12`;
CREATE TABLE `sys_business_log_2017_12` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2017_12
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_01`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_01`;
CREATE TABLE `sys_business_log_2018_01` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_01
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_02`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_02`;
CREATE TABLE `sys_business_log_2018_02` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_02
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_03`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_03`;
CREATE TABLE `sys_business_log_2018_03` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_03
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_04`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_04`;
CREATE TABLE `sys_business_log_2018_04` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_04
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_05`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_05`;
CREATE TABLE `sys_business_log_2018_05` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_05
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_business_log_2018_06`
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_log_2018_06`;
CREATE TABLE `sys_business_log_2018_06` (
  `uuid` varchar(255) NOT NULL,
  `oper` varchar(255) DEFAULT NULL COMMENT '操作人(姓名)',
  `pos` varchar(255) DEFAULT NULL COMMENT '操作pos',
  `time` datetime DEFAULT NULL COMMENT '操作时间',
  `content` text COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)',
  `ow_num` varchar(255) DEFAULT NULL COMMENT '营业流水号',
  `seat_name` varchar(255) DEFAULT NULL COMMENT '客位名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_business_log_2018_06
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dataupload_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dataupload_log`;
CREATE TABLE `sys_dataupload_log` (
  `id` varchar(200) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:正常结算流水相关数据，2：返位结算的相关数据，3：废弃流水相关数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_dataupload_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_fwjs_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_fwjs_log`;
CREATE TABLE `sys_fwjs_log` (
  `cw_id` int(11) DEFAULT NULL,
  `op_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='返位结算log表';

-- ----------------------------
-- Records of sys_fwjs_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `wind` varchar(20) DEFAULT NULL,
  `content` text,
  `oper_id` int(11) NOT NULL,
  `open_water` varchar(30) DEFAULT NULL,
  `settlement_water` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `menu_name` varchar(50) NOT NULL,
  `menu_url` varchar(200) DEFAULT NULL,
  `menu_code` varchar(50) DEFAULT NULL,
  `menu_state` int(11) DEFAULT NULL,
  `menu_order` int(11) DEFAULT NULL,
  `menu_icon` varchar(200) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL COMMENT '1默认，2按钮操作',
  `menu_level` int(11) DEFAULT NULL COMMENT '0：根节点、1菜单类型（前台/后台）、2一级模块、3二级模块\n4、三级模块、5具体的操作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22559 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '全部基本模块', null, null, '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '后台管理', null, null, '1', '1', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('3', '2', '档案管理', 'javascript:void(0)', 'archive_management', '1', '1', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('4', '3', '公共代码', 'DININGSYS/archive/dpc/index', 'public_code', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('5', '4', '修改', null, 'public_code:update', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('6', '4', '预览', null, 'public_code:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('7', '4', '刷新', null, 'public_code:flush', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('8', '4', '导出', null, 'public_code:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('9', '3', '员工管理', 'DININGSYS/archive/emp/index', 'emp_index', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('10', '9', '删除', null, 'emp_index:del', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('11', '9', '新建', null, 'emp_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('12', '9', '导出', null, 'emp_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('13', '9', '刷新', null, 'emp_index:flush', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('14', '9', '预览', null, 'emp_index:preview', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('15', '9', '回收站', null, 'emp_index:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('16', '9', '修改', null, 'emp_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('17', '3', '上菜状态', 'DININGSYS/archive/serving/index', 'serving', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('18', '17', '保存', null, 'serving:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('19', '17', '打印', null, 'serving:print', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('20', '17', '修改', null, 'serving:update', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('21', '17', '回收站', null, 'serving:recycle', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('22', '17', '查询', null, 'serving:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('23', '17', '预览', null, 'serving:preview', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('24', '17', '新建', null, 'serving:create', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('25', '17', '导出', null, 'serving:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('26', '17', '删除', null, 'serving:del', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('27', '3', '赠单原因', 'DININGSYS/archive/giftForm/index', 'gift_form', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('28', '27', '保存', null, 'gift_form:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('29', '27', '打印', null, 'gift_form:print', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('30', '27', '修改', null, 'gift_form:update', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('31', '27', '回收站', null, 'gift_form:recycle', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('32', '27', '查询', null, 'gift_form:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('33', '27', '预览', null, 'gift_form:preview', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('34', '27', '新建', null, 'gift_form:create', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('35', '27', '导出', null, 'gift_form:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('36', '27', '删除', null, 'gift_form:del', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('37', '27', '刷新', null, 'gift_form:flush', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('38', '3', '部门管理', 'DININGSYS/archive/dep/index', 'dep_index', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('39', '38', '修改', null, 'dep_index:update', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('40', '38', '回收站', null, 'dep_index:recycle', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('41', '38', '预览', null, 'dep_index:preview', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('42', '38', '新建', null, 'dep_index:create', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('43', '38', '导出', null, 'dep_index:export', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('44', '38', '删除', null, 'dep_index:del', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('45', '38', '刷新', null, 'dep_index:flush', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('46', '3', '组织机构', 'DININGSYS/archive/org/index', 'org_index', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('47', '46', '修改', null, 'org_index:update', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('48', '46', '预览', null, 'org_index:previrew', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('49', '46', '刷新', null, 'org_index:flush', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('50', '46', '导出', null, 'org_index:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('51', '3', '营业市别', 'DININGSYS/archive/bis/index', 'bis_index', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('52', '51', '保存', null, 'bis_index:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('53', '51', '打印', null, 'bis_index:print', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('54', '51', '修改', null, 'bis_index:update', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('55', '51', '回收站', null, 'bis_index:recycle', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('56', '51', '查询', null, 'bis_index:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('57', '51', '预览', null, 'bis_index:preview', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('58', '51', '新建', null, 'bis_index:create', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('59', '51', '导出', null, 'bis_index:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('60', '51', '删除', null, 'bis_index:del', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('61', '51', '刷新', null, 'bis_index:flush', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('62', '51', '插入', null, 'bis_index:insert', '1', '11', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('63', '3', '品项档案', 'DININGSYS/archive/itemFile/index', 'item_file', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('64', '63', '查询', null, 'item_file:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('65', '63', '编号控制', null, 'item_file:numberControl', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('66', '63', '预览', null, 'item_file:preview', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('67', '63', '新建', null, 'item_file:create', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('68', '63', '导出', null, 'item_file:export', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('69', '63', '删除', null, 'item_file:del', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('70', '63', '保存', null, 'item_file:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('71', '63', '套餐', null, 'item_file:package', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('72', '63', '打印', null, 'item_file:print', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('73', '63', '价目表', null, 'item_file:priceList', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('74', '63', '维护基本营养成分', null, 'item_file:baseNutrition', '1', '11', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('75', '63', '修改', null, 'item_file:update', '1', '12', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('76', '63', '回收站', null, 'item_file:recycle', '1', '13', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('77', '3', '退菜原因', 'DININGSYS/archive/rfc/index', 'rfc_index', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('80', '77', '预览', null, 'rfc_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('81', '77', '新建', null, 'rfc_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('82', '77', '导出', null, 'rfc_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('83', '77', '删除', null, 'rfc_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('84', '77', '保存', null, 'rfc_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('85', '77', '打印', null, 'rfc_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('86', '77', '修改', null, 'rfc_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('87', '77', '回收站', null, 'rfc_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('88', '77', '查询', null, 'rfc_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('89', '3', '整单备注', 'DININGSYS/archive/zdbz/index', 'zdbz_index', '1', '10', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('90', '89', '预览', null, 'zdbz_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('91', '89', '新建', null, 'zdbz_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('92', '89', '导出', null, 'zdbz_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('93', '89', '删除', null, 'zdbz_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('94', '89', '保存', null, 'zdbz_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('95', '89', '打印', null, 'zdbz_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('96', '89', '修改', null, 'zdbz_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('97', '89', '回收站', null, 'zdbz_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('98', '89', '查询', null, 'zdbz_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('99', '3', '费用项目', 'DININGSYS/archive/fyxm/index', 'fyxm_index', '1', '11', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('100', '99', '预览', null, 'fyxm_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('101', '99', '新建', null, 'fyxm_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('102', '99', '导出', null, 'fyxm_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('103', '99', '删除', null, 'fyxm_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('104', '99', '保存', null, 'fyxm_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('105', '99', '打印', null, 'fyxm_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('106', '99', '修改', null, 'fyxm_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('107', '99', '回收站', null, 'fyxm_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('108', '99', '查询', null, 'fyxm_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('109', '99', '费用类型', null, 'fyxm_index:feeType', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('110', '3', '消费区域和客位', 'DININGSYS/consumerSeat/index', 'consumer_seat_index', '1', '12', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('111', '110', '预览', null, 'consumer_seat_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('112', '110', '新建', null, 'consumer_seat_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('113', '110', '导出', null, 'consumer_seat_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('114', '110', '删除', null, 'consumer_seat_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('115', '110', '保存', null, 'consumer_seat_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('116', '110', '打印', null, 'consumer_seat_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('117', '110', '修改', null, 'consumer_seat_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('118', '110', '回收站', null, 'consumer_seat_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('119', '110', '查询', null, 'consumer_seat_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('120', '110', '维护客位类型', null, 'consumer_seat_index:whSeatType', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('121', '110', '批量创建客位', null, 'consumer_seat_index:multiCreateSeat', '1', '11', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('122', '3', 'POS档案', 'DININGSYS/pos/index', 'pos_index', '1', '13', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('123', '122', '吧台盘点方式', null, 'pos_index:barInventory', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('124', '122', '预览', null, 'pos_index:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('125', '122', '新建', null, 'pos_index:create', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('126', '122', '导出', null, 'pos_index:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('127', '122', '删除', null, 'pos_index:del', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('128', '122', '保存', null, 'pos_index:save', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('129', '122', '打印', null, 'pos_index:print', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('130', '122', '修改', null, 'pos_index:update', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('131', '122', '回收站', null, 'pos_index:recycle', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('132', '122', '查询', null, 'pos_index:query', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('135', '3', '结算方式', 'DININGSYS/settlementWay/index', 'settlementWay_index', '1', '14', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('136', '135', '预览', null, 'settlementWay_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('137', '135', '新建', null, 'settlementWay_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('138', '135', '导出', null, 'settlementWay_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('139', '135', '删除', null, 'settlementWay_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('140', '135', '保存', null, 'settlementWay_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('141', '135', '打印', null, 'settlementWay_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('142', '135', '修改', null, 'settlementWay_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('143', '135', '回收站', null, 'settlementWay_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('144', '135', '查询', null, 'settlementWay_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('146', '3', '制作方法', 'DININGSYS/archive/proMethods/index', 'pro_methods_index', '1', '15', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('147', '146', '预览', null, 'pro_methods_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('148', '146', '新建', null, 'pro_methods_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('149', '146', '导出', null, 'pro_methods_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('150', '146', '删除', null, 'pro_methods_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('151', '146', '保存', null, 'pro_methods_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('152', '146', '打印', null, 'pro_methods_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('153', '146', '修改', null, 'pro_methods_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('154', '146', '回收站', null, 'pro_methods_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('155', '146', '查询', null, 'pro_methods_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('156', '3', '口味要求', 'DININGSYS/archive/flavor/index', 'flavor_index', '1', '16', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('157', '156', '预览', null, 'flavor_index:preview', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('158', '156', '新建', null, 'flavor_index:create', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('159', '156', '导出', null, 'flavor_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('160', '156', '删除', null, 'flavor_index:del', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('161', '156', '保存', null, 'flavor_index:save', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('162', '156', '打印', null, 'flavor_index:print', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('163', '156', '修改', null, 'flavor_index:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('164', '156', '回收站', null, 'flavor_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('165', '156', '查询', null, 'flavor_index:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('166', '2', '营业管理', 'javascript:void(0)', null, '1', '2', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('167', '166', '品项管理', 'DININGSYS/productItemconfig/index', 'dg_product_phase', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('168', '167', '品项价格\\连锁品项促销方案', null, 'dg_product_phase_c1', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('169', '168', '新建', null, 'dg_product_phase_c1:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('170', '168', '修改', null, 'dg_product_phase_c1:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('171', '168', '删除', null, 'dg_product_phase_c1:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('172', '168', '打印', null, 'dg_product_phase_c1:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('173', '168', '预览', null, 'dg_product_phase_c1:preivew', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('174', '168', '回收站', null, 'dg_product_phase_c1:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('175', '168', '保存', null, 'dg_product_phase_c1:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('176', '168', '导出', null, 'dg_product_phase_c1:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('177', '168', '查询', null, 'dg_product_phase_c1:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('178', '168', '发布', null, 'dg_product_phase_c1:publish', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('179', '167', '品项价格\\品项连锁销售方案', null, 'dg_product_phase_c2', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('180', '179', '新建', null, 'dg_product_phase_c2:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('181', '179', '修改', null, 'dg_product_phase_c2:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('182', '179', '删除', null, 'dg_product_phase_c2:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('183', '179', '打印', null, 'dg_product_phase_c2:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('184', '179', '预览', null, 'dg_product_phase_c2:preview', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('185', '179', '回收站', null, 'dg_product_phase_c2:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('186', '179', '保存', null, 'dg_product_phase_c2:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('187', '179', '导出', null, 'dg_product_phase_c2:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('188', '179', '查询', null, 'dg_product_phase_c2:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('189', '179', '发布', null, 'dg_product_phase_c2:publish', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('190', '167', '品项价格\\一菜多价按市别', null, 'dg_product_phase_c3', '1', '3', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('198', '190', '保存', null, 'dg_product_phase_c3:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('200', '190', '查询', null, 'dg_product_phase_c3:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('202', '167', '品项价格\\连锁品项会员方案', null, 'dg_product_phase_c4', '1', '4', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('203', '202', '新建', null, 'dg_product_phase_c4:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('204', '202', '修改', null, 'dg_product_phase_c4:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('205', '202', '删除', null, 'dg_product_phase_c4:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('206', '202', '打印', null, 'dg_product_phase_c4:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('207', '202', '预览', null, 'dg_product_phase_c4:preview', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('208', '202', '回收站', null, 'dg_product_phase_c4:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('209', '202', '保存', null, 'dg_product_phase_c4:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('210', '202', '导出', null, 'dg_product_phase_c4:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('211', '202', '查询', null, 'dg_product_phase_c4:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('212', '202', '发布', null, 'dg_product_phase_c4:publish', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('213', '167', '品项价格\\一菜多价按消费区域', null, 'dg_product_phase_c5', '1', '5', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('214', '213', '保存', null, 'dg_product_phase_c5:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('215', '213', '查询', null, 'dg_product_phase_c5:query', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('216', '167', '品项价格\\一菜多价按星期', null, 'dg_product_phase_c6', '1', '6', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('217', '216', '保存', null, 'dg_product_phase_c6:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('218', '216', '查询', null, 'dg_product_phase_c6:query', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('219', '167', '品项价格\\促销品项', null, 'dg_product_phase_c7', '1', '7', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('220', '219', '打印', null, 'dg_product_phase_c7:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('221', '219', '预览', null, 'dg_product_phase_c7:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('222', '219', '保存', null, 'dg_product_phase_c7:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('223', '219', '导出', null, 'dg_product_phase_c7:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('224', '219', '查询', null, 'dg_product_phase_c7:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('225', '167', '品项价格\\时价品项', null, 'dg_product_phase_c8', '1', '8', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('226', '224', '打印', null, 'dg_product_phase_c8:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('227', '224', '预览', null, 'dg_product_phase_c8:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('228', '224', '保存', null, 'dg_product_phase_c8:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('229', '224', '导出', null, 'dg_product_phase_c8:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('230', '224', '查询', null, 'dg_product_phase_c8:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('231', '167', '品项价格\\价格优先级', null, 'dg_product_phase_c9', '1', '9', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('232', '231', '保存', null, 'dg_product_phase_c9:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('233', '167', '品项打折\\允许打折品项', null, 'dg_product_phase_c10', '1', '10', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('234', '233', '打印', null, 'dg_product_phase_c10:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('235', '233', '预览', null, 'dg_product_phase_c10:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('236', '233', '保存', null, 'dg_product_phase_c10:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('237', '233', '导出', null, 'dg_product_phase_c10:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('238', '233', '查询', null, 'dg_product_phase_c10:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('239', '167', '品项打折\\品项打折方案', null, 'dg_product_phase_c11', '1', '11', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('240', '239', '新建', null, 'dg_product_phase_c11:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('241', '239', '修改', null, 'dg_product_phase_c11:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('242', '239', '删除', null, 'dg_product_phase_c11:dle', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('243', '239', '打印', null, 'dg_product_phase_c11:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('244', '239', '预览', null, 'dg_product_phase_c11:preview', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('245', '239', '回收站', null, 'dg_product_phase_c11:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('246', '239', '保存', null, 'dg_product_phase_c11:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('247', '239', '导出', null, 'dg_product_phase_c11:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('248', '239', '查询', null, 'dg_product_phase_c11:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('250', '167', '品项打折\\重要活动打折', null, 'dg_product_phase_c12', '1', '12', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('251', '250', '打印', null, 'dg_product_phase_c12:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('252', '250', '预览', null, 'dg_product_phase_c12:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('253', '250', '保存', null, 'dg_product_phase_c12:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('254', '250', '导出', null, 'dg_product_phase_c12:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('255', '250', '查询', null, 'dg_product_phase_c12:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('256', '167', '品项限量\\品项沽清', null, 'dg_product_phase_c13', '1', '13', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('257', '256', '打印', null, 'dg_product_phase_c13:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('258', '256', '预览', null, 'dg_product_phase_c13:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('259', '256', '保存', null, 'dg_product_phase_c13:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('260', '256', '导出', null, 'dg_product_phase_c13:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('261', '256', '查询', null, 'dg_product_phase_c13:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('262', '167', '品项限量\\销售限量', null, 'dg_product_phase_c14', '1', '14', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('263', '262', '打印', null, 'dg_product_phase_c14:print', '1', '1', '', '2', '5');
INSERT INTO `sys_menu` VALUES ('264', '262', '预览', null, 'dg_product_phase_c14:preview', '1', '2', '', '2', '5');
INSERT INTO `sys_menu` VALUES ('265', '262', '保存', null, 'dg_product_phase_c14:save', '1', '3', '', '2', '5');
INSERT INTO `sys_menu` VALUES ('266', '262', '导出', null, 'dg_product_phase_c14:export', '1', '4', '', '2', '5');
INSERT INTO `sys_menu` VALUES ('267', '262', '查询', null, 'dg_product_phase_c14:query', '1', '5', '', '2', '5');
INSERT INTO `sys_menu` VALUES ('268', '167', '品项限量\\品项停用', null, 'dg_product_phase_c15', '1', '15', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('269', '268', '打印', null, 'dg_product_phase_c15:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('270', '268', '预览', null, 'dg_product_phase_c15:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('271', '268', '保存', null, 'dg_product_phase_c15:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('272', '268', '导出', null, 'dg_product_phase_c15:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('273', '268', '查询', null, 'dg_product_phase_c15:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('274', '167', '公告信息\\急推菜品', null, 'dg_product_phase_c16', '1', '16', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('275', '274', '打印', null, 'dg_product_phase_c16:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('276', '274', '预览', null, 'dg_product_phase_c16:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('277', '274', '保存', null, 'dg_product_phase_c16:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('278', '274', '导出', null, 'dg_product_phase_c16:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('279', '274', '查询', null, 'dg_product_phase_c16:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('280', '167', '公告信息\\最新菜品', null, 'dg_product_phase_c17', '1', '17', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('281', '280', '打印', null, 'dg_product_phase_c17:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('282', '280', '预览', null, 'dg_product_phase_c17:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('283', '280', '保存', null, 'dg_product_phase_c17:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('284', '280', '导出', null, 'dg_product_phase_c17:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('285', '280', '查询', null, 'dg_product_phase_c17:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('286', '167', '其他\\品项出品部门', null, 'dg_product_phase_c18', '1', '18', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('287', '286', '保存', null, 'dg_product_phase_c18:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('288', '286', '查询', null, 'dg_product_phase_c18:query', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('289', '288', '其他\\特殊品项', null, 'dg_product_phase_c19', '1', '19', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('290', '289', '保存', null, 'dg_product_phase_c19:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('291', '167', '其他\\自定义金额', null, 'dg_product_phase_c20', '1', '20', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('292', '291', '打印', null, 'dg_product_phase_c20:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('293', '291', '预览', null, 'dg_product_phase_c20:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('294', '291', '保存', null, 'dg_product_phase_c20:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('295', '291', '导出', null, 'dg_product_phase_c20:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('296', '291', '查询', null, 'dg_product_phase_c20:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('297', '167', '促销方案', null, 'dg_product_phase_c21', '1', '21', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('298', '297', '打印', null, 'dg_product_phase_c21:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('299', '297', '预览', null, 'dg_product_phase_c21:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('300', '297', '保存', null, 'dg_product_phase_c21:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('301', '297', '导出', null, 'dg_product_phase_c21:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('302', '297', '查询', null, 'dg_product_phase_c21:query', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('303', '297', '新建', null, 'dg_product_phase_c21:create', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('304', '297', '修改', null, 'dg_product_phase_c21:update', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('305', '297', '删除', null, 'dg_product_phase_c21:del', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('306', '297', '回收站', null, 'dg_product_phase_c21:recycle', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('307', '167', '品项打折\\品项阶梯打折', null, 'dg_product_phase_c22', '1', '22', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('308', '307', '打印', null, 'dg_product_phase_c22:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('309', '307', '预览', null, 'dg_product_phase_c22:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('310', '307', '刷新', null, 'dg_product_phase_c22:flush', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('311', '307', '保存', null, 'dg_product_phase_c22:save', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('312', '307', '导出', null, 'dg_product_phase_c22:export', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('313', '307', '查询', null, 'dg_product_phase_c22:query', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('314', '167', '其他\\品项出品厨师', null, 'dg_product_phase_c23', '1', '23', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('315', '314', '保存', null, 'dg_product_phase_c23:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('316', '314', '查询', null, 'dg_product_phase_c23:query', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('317', '167', '阶梯价格', null, 'dg_product_phase_c24', '1', '24', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('318', '317', '刷新', null, 'dg_product_phase_c24:flush', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('319', '317', '保存', null, 'dg_product_phase_c24:save', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('320', '317', '设置', null, 'dg_product_phase_c24:setting', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('321', '317', '查询', null, 'dg_product_phase_c24:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('322', '166', '费用登记', 'DININGSYS/businessMan/fydj/index', 'fydj_index', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('323', '322', '打印', null, 'fydj_index:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('324', '322', '预览', null, 'fydj_index:preview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('325', '322', '导出', null, 'fydj_index:export', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('326', '322', '查询', null, 'fydj_index:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('327', '322', '新建', null, 'fydj_index:create', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('328', '322', '修改', null, 'fydj_index:update', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('329', '322', '删除', null, 'fydj_index:del', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('330', '322', '回收站', null, 'fydj_index:recycle', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('331', '166', '消费品项显示设置', 'DININGSYS/businessMan/itemShowRank/index', 'cgds_index', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('332', '331', '保存', null, 'cgds_index:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('333', '166', '非会员挂账管理', 'DININGSYS/nonMember/index', 'deskBusiness_nonMember', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('334', '333', '新建', null, 'deskBusiness_nonMember:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('335', '333', '修改', null, 'deskBusiness_nonMember:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('336', '333', '删除', null, 'deskBusiness_nonMember:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('337', '333', '打印', null, 'deskBusiness_nonMember:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('338', '333', '预览', null, 'deskBusiness_nonMember:preivew', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('339', '333', '回收站', null, 'deskBusiness_nonMember:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('340', '333', '保存', null, 'deskBusiness_nonMember:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('341', '333', '导出', null, 'deskBusiness_nonMember:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('342', '333', '查询', null, 'deskBusiness_nonMember:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('343', '333', '付款', null, 'deskBusiness_nonMember:payment', '1', '10', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('344', '166', '特约商户管理', 'DININGSYS/speciallyBusiness/index', 'deskBusiness_speciallyBusiness', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('345', '344', '新建', null, 'deskBusiness_speciallyBusiness:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('346', '344', '修改', null, 'deskBusiness_speciallyBusiness:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('347', '344', '删除', null, 'deskBusiness_speciallyBusiness:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('348', '344', '打印', null, 'deskBusiness_speciallyBusiness:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('349', '344', '预览', null, 'deskBusiness_speciallyBusiness:preview', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('350', '344', '回收站', null, 'deskBusiness_speciallyBusiness:recycle', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('351', '344', '保存', null, 'deskBusiness_speciallyBusiness:save', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('352', '344', '导出', null, 'deskBusiness_speciallyBusiness:export', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('353', '344', '查询', null, 'deskBusiness_speciallyBusiness:query', '1', '9', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('354', '166', '服务员服务客位', 'DININGSYS/serviceClass/index', 'deskBusiness_serviceClass', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('355', '354', '新建', null, 'deskBusiness_serviceClass:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('356', '354', '另存为', null, 'deskBusiness_serviceClass:saveAs', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('357', '354', '打印', null, 'deskBusiness_serviceClass:print', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('358', '354', '预览', null, 'deskBusiness_serviceClass:preview', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('359', '354', '回收站', null, 'deskBusiness_serviceClass:recycle', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('360', '354', '保存', null, 'deskBusiness_serviceClass:save', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('361', '354', '导出', null, 'deskBusiness_serviceClass:export', '1', '7', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('362', '354', '批量创建', null, 'deskBusiness_serviceClass:batchCreate', '1', '8', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('363', '166', '消费区域与客位管理', 'DININGSYS/consumerSeatManager/index', 'consumer_seat_man_index', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('364', '363', '打印', null, 'consumer_seat_man_index:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('365', '363', '预览', null, 'consumer_seat_man_index:preiview', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('366', '363', '保存', null, 'consumer_seat_man_index:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('367', '363', '导出', null, 'consumer_seat_man_index:export', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('368', '363', '包房收费方案', null, 'consumer_seat_man_index:prmcp', '1', '5', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('369', '363', '收取服务费的类别', null, 'consumer_seat_man_index:chsbc', '1', '6', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('370', '166', '前台营业设置', 'DININGSYS/deskbusinesssetting/index', 'deskBusiness_setting', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('371', '2', '前台营业', 'javascript:void(0)', 'front_desk_business', '1', '3', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('372', '371', '当前营业情况', 'DININGSYS/deskBusiness/currentOpen/index', 'deskBusiness_currentOpen', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('373', '372', '查看客位信息', null, 'deskBusiness_currentOpen:seatInfo', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('374', '372', '查看账单信息', null, 'deskBusiness_currentOpen:checkInfo', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('375', '372', '查看结算方式信息', null, 'deskBusiness_currentOpen:clearingWay', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('376', '371', '已结账单', 'DININGSYS/deskBusiness/closedBills/index', 'deskBusiness_closedBills', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('377', '376', '补开发票', null, 'deskBusiness_closedBills:repairInvoice', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('378', '376', '打印账单', null, 'deskBusiness_closedBills:printCheck', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('379', '376', '付款修正', null, 'deskBusiness_closedBills:paymentC', '1', '3', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('380', '376', '导出', null, 'deskBusiness_closedBills:export', '1', '4', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('381', '376', '编辑结算备注', null, 'deskBusiness_closedBills:editRepairClearingNotes', '1', '5', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('382', '371', '未结账单', 'DININGSYS/deskBusiness/openBills/index', 'deskBusiness_openBills', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('383', '2', '权限管理', 'javascript:void(0)', 'sys_permission', '1', '4', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('384', '383', '操作权限', 'DININGSYS/permission/businessPermisson/index', 'business_permissions', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('385', '384', '系统使用权限', null, 'business_permissions_sys_per', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('386', '384', '业务操作权限', null, 'business_permissions_bus_per', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('387', '384', '员工查看消费区域统计信息权限', null, 'business_permissions_empAreaSta_per', '1', '3', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('388', '386', '保存', null, 'business_permissions_bus_per:save', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('389', '2', '系统管理', 'javascript:void(0)', 'sysconfig_management', '1', '5', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('390', '389', '修改密码', 'DININGSYS/sysconfig/updateIndex', 'updatepassword', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('391', '389', '系统日志', 'DININGSYS/sysconfig/log', 'sysconfig_index', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('392', '391', '删除', null, 'sysconfig_index:del', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('393', '389', '数据库备份', 'DININGSYS/sysbackup/index', 'sys_backup_database', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('394', '1', '前台营业', null, 'desk_open', '1', '2', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('395', '394', '客位服务', null, 'roundtripService', '1', '1', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('396', '395', '开单', null, 'billing', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('397', '396', '选择会员', null, 'billing:chooseMembers', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('398', '396', '选择客座', null, 'billing:chooseSeat', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('399', '395', '团队开单', null, 'team_billing', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('400', '395', '加单', null, 'single', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('401', '400', '新建临时品项', null, 'single:createTempItem', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('402', '400', '赠送', null, 'single:give', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('403', '400', '团队加单', null, 'single:teamSingle', '1', '3', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('404', '395', '退单', null, 'chargeback', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('405', '395', '赠单', null, 'gftForm', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('406', '395', '催单', null, 'reminder', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('407', '395', '起菜', null, 'cuisine', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('408', '395', '押金', null, 'deposit', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('409', '408', '登记押金', null, 'deposit:reg_deposit', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('410', '408', '退押金', null, 'deposit:refund_deposit', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('411', '395', '团队退单', null, 'team_chargeback', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('412', '394', '账单服务', null, 'billing_services', '1', '2', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('413', '412', '买单结算', null, 'pay_settlement', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('414', '412', '预授权', null, 'pre_authorization', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('415', '412', '转账', null, 'transfer', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('416', '412', '拆账', null, 'a_sham', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('417', '412', '关账', null, 'closing', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('418', '412', '结班', null, 'succession', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('419', '412', '续单', null, 'continued', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('420', '412', '拆分品项', null, 'split_items', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('421', '412', '挂S账', null, 'hung_s_accounts', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('422', '412', '查看S账', null, 'view_s_accounts', '1', '10', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('423', '422', '打印客用单', null, 'view_s_accounts:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('424', '422', '结算', null, 'view_s_accounts:settlement', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('425', '422', '撤销S账', null, 'view_s_accounts:cancel', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('426', '412', '虚拟账单', null, 'virtual_bill', '1', '11', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('427', '412', '撤销埋单', null, 'cancel_pay', '1', '12', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('428', '412', '预订单管理', null, 're_manafement', '1', '13', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('429', '412', '线上预点单', null, 'online_pre_order', '1', '14', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('430', '412', '团购卷验证', null, 'tgjyz', '1', '15', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('431', '394', '查询打印', null, 'query_print', '1', '3', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('432', '2', '库存管理', 'javascript:void(0)', 'inve_inventor_manage', '1', '6', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('433', '432', '供应商管理', 'DININGSYS/inve/dgSupplier/index', 'dgsupplier_index', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('434', '432', '仓库管理', 'DININGSYS/inve/warehouse/index', 'wafehouse_index', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('435', '432', '物品类型', 'DININGSYS/inve/itemType/index', 'dgItemType_index', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('436', '432', '物品管理', 'DININGSYS/inve/items/index', 'inve_items_incex', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('437', '432', '库存管理', 'DININGSYS/inve/inventory/index', 'inve_inventory_incex', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('438', '432', '采购管理', 'DININGSYS/inve/proc/index', 'inve_proc_index', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('439', '432', '部门物料', 'DININGSYS/inve/depaMate/index', 'inve_depaMate_index', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('440', '432', '调拨管理', 'DININGSYS/inve/tran/index', 'inve_tran_index', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('441', '432', '盘点管理', 'DININGSYS/inve/discPoint/index', 'inve_discPoint_index', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('442', '433', '新增', null, 'dgsupplier_index:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('443', '433', '修改', null, 'dgsupplier_index:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('444', '433', '删除', null, 'dgsupplier_index:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('445', '433', '查询', null, 'dgsupplier_index:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('446', '434', '新增', null, 'wafehouse_index:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('447', '434', '修改', null, 'wafehouse_index:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('448', '434', '删除', null, 'wafehouse_index:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('449', '434', '查询', null, 'wafehouse_index:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('450', '435', '新增', null, 'dgItemType_index:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('451', '435', '修改', null, 'dgItemType_index:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('452', '435', '删除', null, 'dgItemType_index:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('453', '435', '查询', null, 'dgItemType_index:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('454', '436', '新增', null, 'inve_items_incex:create', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('455', '436', '修改', null, 'inve_items_incex:update', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('456', '436', '删除', null, 'inve_items_incex:del', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('457', '436', '查询', null, 'inve_items_incex:query', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('458', '437', '查询', null, 'inve_inventory_incex:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('459', '437', '导出', null, 'inve_inventory_incex:export', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('460', '437', '打印', null, 'inve_inventory_incex:print', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('461', '438', '查询', null, 'inve_proc_index:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('462', '438', '采购入库', null, 'inve_proc_index:cgrk', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('463', '438', '采购退货', null, 'inve_proc_index:cgth', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('464', '439', '查询', null, 'inve_depaMate_index:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('465', '439', '部门领料', null, 'inve_depaMate_index:bmll', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('466', '439', '部门退料', null, 'inve_depaMate_index:bmtl', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('467', '440', '查询', null, 'inve_tran_index:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('468', '440', '调拨', null, 'inve_tran_index:db', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('469', '441', '查询', null, 'inve_discPoint_index:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('470', '441', '盘点', null, 'inve_discPoint_index:pd', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('471', '389', '到期提醒', 'DININGSYS/reminder/index', 'reminder', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('472', '431', '当前客单', null, 'query_print_currentBill', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('473', '431', '核对单据', null, 'query_print_checkBill', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('474', '473', '打印', null, 'query_print_checkBill:print', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('475', '473', '补打-打印服务', null, 'query_print_checkBill:printService', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('476', '431', '打印客用单', null, 'query_print_printGuest', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('477', '431', '打印美食健康表', null, 'query_print_printHealthy', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('478', '431', '团队信息', null, 'query_print_teamInfo', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('479', '478', '退出团队', null, 'query_print_teamInfo_quitTeam', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('480', '478', '撤销转账', null, 'query_print_teamInfo_cancelTransfer', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('481', '431', '已结账单', null, 'closeBill', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('482', '481', '付款修正', null, 'closeBill_paymentCorrection', '1', '1', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('483', '481', '返位结算', null, 'closeBill_returnSettlement', '1', '2', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('484', '481', '补开发票', null, 'closeBill_repairInvoice', '1', '3', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('485', '481', '打印账单', null, 'closeBill_printBill', '1', '4', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('486', '481', '补赠券', null, 'closeBill_supplementCoupons', '1', '5', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('487', '481', '编辑结算备注', null, 'closeBill_editClosingNotes', '1', '6', null, '1', '4');
INSERT INTO `sys_menu` VALUES ('488', '431', '外部转账', null, 'externalBilling', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('489', '431', '公告板', null, 'bulletinBoard', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('490', '431', '未结账单', null, 'openBills', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('491', '490', '打印客用单', null, 'openBills_printGuest', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('492', '431', '类别销售时间', null, 'openBills_categorySalesTime', '1', '10', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('493', '431', '翻台账单查询', null, 'openBills_doubleBillingQuery', '1', '11', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('494', '431', '转账流水查询', null, 'openBills_transferFlowQuery', '1', '12', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('495', '431', '未结品项查询', null, 'openBills_openBillsQuery', '1', '13', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('496', '394', '更换修改', null, 'repalce_modify', '1', '4', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('497', '496', '更换客位', null, 'repalce_modify_rmRoundtrip', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('498', '496', '更换客座', null, 'repalce_modify_rmGuestTable', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('499', '496', '更换会员', null, 'repalce_modify_rmMembers', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('500', '496', '客位人数', null, 'repalce_modify_rmPeopleCount', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('501', '496', '客位状态', null, 'repalce_modify_rmState', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('502', '496', '客位标记', null, 'repalce_modify_rmMark', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('503', '496', '换服务员/营销员', null, 'repalce_modify_rmWaiter', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('504', '496', '品项变价', null, 'repalce_modify_rmVariablePrice', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('505', '496', '修改数量', null, 'repalce_modify_rmEditNumber', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('506', '496', '品项赠送', null, 'repalce_modify_rmFree', '1', '10', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('507', '496', '单品转台', null, 'repalce_modify_rmTurntable', '1', '11', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('508', '496', '品项沽清', null, 'repalce_modify_rmSoldOut', '1', '12', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('509', '508', '查询', null, 'repalce_modify_rmSoldOut:query', '1', '1', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('510', '508', '导出', null, 'repalce_modify_rmSoldOut:export', '1', '2', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('511', '508', '保存', null, 'repalce_modify_rmSoldOut:save', '1', '3', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('512', '508', '打印', null, 'repalce_modify_rmSoldOut:print', '1', '4', null, '2', '5');
INSERT INTO `sys_menu` VALUES ('513', '496', '修改套餐', null, 'repalce_modify_moTc', '0', '14', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('514', '496', '加入团队', null, 'repalce_modify_joinTeam', '1', '15', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('515', '496', '手工锁定', null, 'repalce_modify_manualLocking', '1', '16', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('516', '496', '解锁', null, 'repalce_modify_unlock', '1', '17', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('517', '496', '撤销品项赠送', null, 'repalce_modify_cancelFree', '1', '18', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('518', '496', '临时套餐', null, 'repalce_modify_temporaryTc', '1', '19', null, '0', '3');
INSERT INTO `sys_menu` VALUES ('519', '496', '顾客信息登记', null, 'repalce_modify_regCustomInfo', '1', '20', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('520', '496', '品项限量', null, 'Item_limits', '1', '13', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('521', '496', '撤销埋单', null, 'cancel_pay', '1', '21', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('522', '394', '吧台管理', null, 'bar_management', '1', '5', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('523', '522', '物品寄存', null, 'bar_management:', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('524', '394', '会员管理', null, 'member_management', '1', '6', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('525', '524', '会员卡查询', null, 'member_management:cardQuery', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('526', '524', '会员信息', null, 'member_management:info', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('527', '394', '包房服务', null, 'room_service', '1', '7', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('528', '527', '修改包房方案', null, 'room_service:editProgramme', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('529', '527', '查包房费', null, 'room_service:queryProgramme', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('530', '527', '打印时间确认单', null, 'room_service:printTime', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('531', '394', '系统', null, 'system', '1', '8', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('532', '531', '修改密码', null, 'system:updatePassWord', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('533', '531', '屏幕保护', null, 'system:screensavers', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('534', '531', '退出系统', null, 'system:loginOut', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('535', '394', '帮助', null, 'sys_help', '1', '9', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('536', '535', '关于', null, 'sys_help:aboutUs', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('537', '2', '打印设置', null, 'print_manger', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('538', '537', '打印设置', 'DININGSYS/PrintManger/index', null, '1', '1', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('539', '2', '支付模块', null, 'pay_parent', '1', '8', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('540', '539', '商户管理', 'DININGSYS/pay/merch/index', 'pay_parent:merchQuery', '1', '1', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('541', '539', '支付流水', 'DININGSYS/pay/water/index', 'pay_parent:waterQuery', '1', '2', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('542', '3', 'mac管理', 'DININGSYS/dgSeatDoorMac/index', 'dgSeatDoorMac:mac', '1', '1', '', '1', '1');
INSERT INTO `sys_menu` VALUES ('543', '2', '报表分析', 'javascript:void(0)', 'statistics_report', '1', '9', '', '1', '2');
INSERT INTO `sys_menu` VALUES ('544', '543', '结算方式信息', 'DININGSYS/report/payway/index', 'report_payway', '1', '1', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('545', '543', '品项销售明细', 'DININGSYS/report/itemFileSell/index', 'statistics_report:itemFileSell', '1', '2', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('546', '543', '结账单查询', 'DININGSYS/report/statement/index', 'statistics_report:statement', '1', '3', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('547', '543', '就餐人数情况-时段', 'DININGSYS/report/NumberOfMeals/index', 'statistics_report:numberOfMeals', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('548', '543', '品项类别销售信息', 'DININGSYS/report/itemFileType/index', 'statistics_report:itemFileType', '1', '5', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('549', '395', '预定', null, 'roundtripService:Reservation', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('550', '389', 'url编辑', 'DININGSYS/urlset/index', 'urlsetting', '1', '4', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('551', '543', '日营业报表', 'DININGSYS/report/openDay/index', 'statistics_report:openDay', '1', '6', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('552', '543', '月客流量统计', 'DININGSYS//report/monthlyPassenger/index', '', '1', '10', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('553', '543', '授权码日志', 'report_pt/report/index_sysAuthCode.jspx', 'report_sysAuthCode', '1', '20', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('568', '531', '系统设置', null, 'system:systemSetting', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('569', '394', '触摸屏主要功能', null, 'touchPadFunction', '1', '9', null, '1', '2');
INSERT INTO `sys_menu` VALUES ('570', '569', '已结账单', null, 'touchPad:closedBill', '1', '1', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('571', '569', '未结账单', null, 'touchPad:billING', '1', '2', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('572', '569', '结班', null, 'touchPad:endClass', '1', '3', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('573', '569', '结账单重打', null, 'touchPad:closedBillRePrint', '1', '4', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('574', '569', '修改密码', null, 'touchPad:updatePwd', '1', '5', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('575', '569', '系统设置', null, 'touchPad:sysSetting', '1', '6', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('576', '569', '核对单据', null, 'touchPad:checkBill', '1', '7', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('577', '569', '打印时间确认单', null, 'touchPad:printTimeForm', '1', '8', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('578', '569', '加入团队', null, 'touchPad:joinTeam', '1', '9', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('579', '569', '换服务员/营销员', null, 'touchPad:changeWaiter', '1', '10', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('580', '569', '押金', null, 'touchPad:deposit', '1', '11', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('581', '569', '品项赠送', null, 'touchPad:itemFileFree', '1', '12', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('582', '569', '更换客位', null, 'touchPad:changeDesk', '1', '13', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('583', '569', '更改人数', null, 'touchPad:changePeopleCount', '1', '14', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('584', '569', '更换会员', null, 'touchPad:changeMember', '1', '15', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('585', '569', '撤销品项赠送', null, 'touchPad:cancelItemFree', '1', '16', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('586', '569', '手工锁定', null, 'touchPad:lock', '1', '17', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('587', '569', '拆分品项', null, 'touchPad:splitItem', '1', '18', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('588', '569', '关账', null, 'touchPad:closeBill', '1', '19', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('589', '569', '转账', null, 'touchPad:Transfer', '1', '20', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('590', '569', '拆账', null, 'touchPad:splitCheck', '1', '21', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('591', '569', '结算', null, 'touchPad:pay', '1', '22', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('592', '569', '点单', null, 'touchPad:dd', '1', '23', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('593', '569', '退菜', null, 'touchPad:tc', '1', '24', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('594', '569', '品项变价', null, 'touchPad:pxbj', '1', '25', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('595', '569', '修改数量', null, 'touchPad:xgsl', '1', '26', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('596', '569', '赠单', null, 'touchPad:zd', '1', '27', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('597', '569', '催菜', null, 'touchPad:cc', '1', '28', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('598', '569', '起菜', null, 'touchPad:qc', '1', '29', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('599', '569', '绑定会员', null, 'touchPad:bdhy', '1', '30', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('600', '569', '优惠', null, 'touchPad:yh', '1', '31', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('601', '569', '定额优惠', null, 'touchPad:deyh', '1', '32', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('602', '569', '押金处理', null, 'touchPad:yjcl', '1', '33', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('603', '569', '开发票', null, 'touchPad:kfp', '1', '34', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('604', '569', '打印客用单', null, 'touchPad:dykyd', '1', '35', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('605', '569', '埋单', null, 'touchPad:md', '1', '36', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('606', '569', '撤销埋单', null, 'touchPad:cxmd', '1', '37', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('607', '569', '快速结算', null, 'touchPad:ksjs', '1', '38', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('608', '569', '开台', null, 'touchPad:openBill', '1', '39', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('609', '569', '团队开台', null, 'touchPad:openTeamBill', '1', '40', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('610', '569', '解锁', null, 'touchPad:unlock', '1', '41', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('611', '569', '团队信息', null, 'touchPad:teamInfo', '1', '42', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('612', '569', '单品转台', null, 'touchPad:itemFIleTurnTable', '1', '43', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('613', '569', '确认结算', null, 'touchPad:sureSettlement', '1', '44', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('614', '569', ' 前置打印客用单', null, 'touchPad:lsdyyjd', '1', '45', null, '1', '3');
INSERT INTO `sys_menu` VALUES ('22554', '543', '营业统计时间分析', 'DININGSYS/report/businessStatistics/index', '', '1', '10', '', '1', '3');
INSERT INTO `sys_menu` VALUES ('22558', '543', '营业分析', 'DININGSYS/report/businessAnalysisReport/index', '', '1', '13', '', '1', '3');

-- ----------------------------
-- Table structure for `sys_per_business`
-- ----------------------------
DROP TABLE IF EXISTS `sys_per_business`;
CREATE TABLE `sys_per_business` (
  `id` int(11) DEFAULT NULL,
  `yq_qx` int(11) DEFAULT NULL COMMENT '宴请权限',
  `pd_px` int(11) DEFAULT NULL COMMENT '跑单权限',
  `xyk_qx` int(11) DEFAULT NULL COMMENT '信用卡权限',
  `zp_qx` int(11) DEFAULT NULL COMMENT '支票权限',
  `q_qx` int(11) DEFAULT NULL COMMENT '券的权限',
  `hygz_qx` int(11) DEFAULT NULL COMMENT '会员挂账',
  `fhygz_qx` int(11) DEFAULT NULL COMMENT '非会员挂账',
  `zwz_qx` int(11) DEFAULT NULL COMMENT '转外账',
  `qt_one_qx` int(11) DEFAULT NULL COMMENT '其他一权限,修改为微信支付权限',
  `qt_two_qx` int(11) DEFAULT NULL COMMENT '其他2权限,修改为支付宝支付权限',
  `wbjsfs` int(11) DEFAULT NULL COMMENT '外部结算方式',
  `hyk_qx` int(11) DEFAULT NULL COMMENT '会员卡权限',
  `scewmzf_qx` int(11) DEFAULT NULL COMMENT '删除二维码支付权限',
  `ewmzfhx_qx` int(11) DEFAULT NULL COMMENT '二维码支付核销',
  `ghkw_qx` int(11) DEFAULT NULL COMMENT '更换客位',
  `ggrs_qx` int(11) DEFAULT NULL COMMENT '更改人数',
  `jsrs_qx` int(11) DEFAULT NULL COMMENT '减少人数',
  `ghkz_qx` int(11) DEFAULT NULL COMMENT '更换客座',
  `xgthtc_qx` int(11) DEFAULT NULL COMMENT '修改宴会套餐',
  `xgsl_qx` int(11) DEFAULT NULL COMMENT '修改数量',
  `dpzt_qx` int(11) DEFAULT NULL COMMENT '单品转台',
  `qyhydz_qx` int(11) DEFAULT NULL COMMENT '启用会员卡打折权限',
  `zz_qx` int(11) DEFAULT NULL COMMENT '转账权限',
  `cxzz_qx` int(11) DEFAULT NULL COMMENT '撤销转账',
  `cz_qx` int(11) DEFAULT NULL COMMENT '拆账权限',
  `jrtd_qx` int(11) DEFAULT NULL COMMENT '加入团队权限',
  `tctd_qx` int(11) DEFAULT NULL COMMENT '退出团队权限',
  `cxmd_qx` int(11) DEFAULT NULL COMMENT '撤销埋单',
  `fwjs_qx` int(11) DEFAULT NULL COMMENT '反位结算',
  `fkxz_qx` int(11) DEFAULT NULL COMMENT '付款修正',
  `bkfp_qx` int(11) DEFAULT NULL COMMENT '补开发票',
  `xgyjzd_qx` int(11) DEFAULT NULL COMMENT '修改已结账单',
  `yxslwf_qx` int(11) DEFAULT NULL COMMENT '允许数量为负',
  `qxhc_qx` int(11) DEFAULT NULL COMMENT '取消划菜权限',
  `dfpkssbdsj_qx` int(11) DEFAULT NULL COMMENT '导发票库时删本地数据权限',
  `jdxglspx_qx` int(11) DEFAULT NULL COMMENT '加单修改临时品项权限',
  `btpd_qx` int(11) DEFAULT NULL COMMENT '吧台盘点',
  `js_qx` int(11) DEFAULT NULL COMMENT '解锁',
  `ysq_qx` int(11) DEFAULT NULL COMMENT '预授权',
  `ckdqyyqk_qx` int(11) DEFAULT NULL COMMENT '查看当前营业情况',
  `yjdj_qx` int(11) DEFAULT NULL COMMENT '押金等级',
  `pxxl_qx` int(11) DEFAULT NULL COMMENT '品项限量',
  `qhfpk_qx` int(11) DEFAULT NULL COMMENT '切换发票库',
  `ghcmppos_qx` int(11) DEFAULT NULL COMMENT '更换触摸屏POS',
  `jdbcd_qx` int(11) DEFAULT NULL COMMENT '加单不厨打',
  `tdbcd_qx` int(11) DEFAULT NULL COMMENT '退单不厨打',
  `tcxt_qx` int(11) DEFAULT NULL COMMENT '退出系统',
  `yxjd_qx` int(11) DEFAULT NULL COMMENT '允许加单',
  `jb_qx` int(11) DEFAULT NULL COMMENT '加班权限',
  `yhcpxdtd_qx` int(11) DEFAULT NULL COMMENT '以划菜品项的退单',
  `xsqtzkdhhm_qx` int(11) DEFAULT NULL COMMENT '显示前台咨客电话号码',
  `zxyd_qx` int(11) DEFAULT NULL COMMENT '执行预定',
  `sdzsydd_qx` int(11) DEFAULT NULL COMMENT '开台时，锁定执行预订单',
  `lxyztgq_qx` int(11) DEFAULT NULL COMMENT '离线验证团购券',
  `sp_qx` int(11) DEFAULT NULL COMMENT '锁屏',
  `zw_code` varchar(10) DEFAULT NULL COMMENT '关联的职务CODE',
  `td_qx` int(11) DEFAULT NULL COMMENT '退单不厨打',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_per_business
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_per_discount`
-- ----------------------------
DROP TABLE IF EXISTS `sys_per_discount`;
CREATE TABLE `sys_per_discount` (
  `id` int(11) DEFAULT NULL,
  `cgyhzdbl` int(11) DEFAULT NULL COMMENT '常规优惠最低比例',
  `qdblyhzdbl` int(11) DEFAULT NULL COMMENT '全单比例优惠最低比例',
  `deyh` int(11) DEFAULT NULL COMMENT '定额优惠',
  `mddeyh_one` int(11) DEFAULT NULL COMMENT '没单定额优惠限制在多少元以内',
  `mddeyh_two` int(11) DEFAULT NULL COMMENT '没单定额优惠限制在消费额的多少百分比',
  `pcceqx` int(11) DEFAULT NULL COMMENT '配餐超额权限',
  `mdpccexz_one` int(11) DEFAULT NULL COMMENT '每单配餐超额限制多少元',
  `mdpccexz_two` int(11) DEFAULT NULL COMMENT '每单配餐超额限制在菜品合计的多少百分比',
  `yqzged` int(11) DEFAULT NULL COMMENT '宴请最高额度',
  `yqzgednum` int(11) DEFAULT NULL COMMENT '宴请最高额度多少元',
  `mfwf` int(11) DEFAULT NULL COMMENT '免服务费',
  `xglbyh` int(11) DEFAULT NULL COMMENT '修改类别优惠',
  `mbff` int(11) DEFAULT NULL COMMENT '免包房费',
  `gbff` int(11) DEFAULT NULL COMMENT '改包房费',
  `xgdpyh` int(11) DEFAULT NULL COMMENT '修改单品优惠',
  `mzdxf` int(11) DEFAULT NULL COMMENT '免最低消费',
  `gfwf` int(11) DEFAULT NULL COMMENT '改服务费',
  `zq` int(11) DEFAULT NULL COMMENT '赠券',
  `xgzqje` int(11) DEFAULT NULL COMMENT '修改赠券金额',
  `zw_code` varchar(10) DEFAULT NULL COMMENT '关联的职务CODE',
  `deyh_type` int(11) DEFAULT NULL COMMENT '定额优惠类型',
  `pcce_type` int(11) DEFAULT NULL COMMENT '配餐超额类型',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_per_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_per_itemfile`
-- ----------------------------
DROP TABLE IF EXISTS `sys_per_itemfile`;
CREATE TABLE `sys_per_itemfile` (
  `item_file_id` int(11) DEFAULT NULL COMMENT '具体的品项ID',
  `zw_code` varchar(10) DEFAULT NULL COMMENT '关联的职务ID',
  `type` int(11) DEFAULT NULL COMMENT '该具体品项的类别，赠送、退单、变价。1、2、3',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_per_itemfile
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_per_itemfiletype`
-- ----------------------------
DROP TABLE IF EXISTS `sys_per_itemfiletype`;
CREATE TABLE `sys_per_itemfiletype` (
  `pxxl_id` int(11) DEFAULT NULL COMMENT '赠送指定品项小类ID',
  `zw_code` varchar(10) DEFAULT NULL COMMENT '关联的职务ID',
  `type` int(11) DEFAULT NULL COMMENT '该品项小类是赠送、退单或者是变价：1、2、3',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_per_itemfiletype
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_per_overview`
-- ----------------------------
DROP TABLE IF EXISTS `sys_per_overview`;
CREATE TABLE `sys_per_overview` (
  `id` int(11) NOT NULL,
  `zw_code` varchar(10) DEFAULT NULL COMMENT '职务CODE',
  `qxsfysz` int(11) DEFAULT NULL COMMENT '权限是否已设置',
  `state` int(11) DEFAULT '0' COMMENT '当前职务的业务操作权限状态',
  `free_type` int(11) DEFAULT NULL COMMENT '赠送表格数据类型1.2',
  `chargeback_type` int(11) DEFAULT NULL COMMENT '退单表格数据类型1.2',
  `variablePrice_type` int(11) DEFAULT NULL COMMENT '变价表格类型1.2',
  `free_max_price` varchar(10) DEFAULT NULL COMMENT '赠送的品项的最大单价',
  `free_total_money` varchar(10) DEFAULT NULL COMMENT '赠送的每日最大金额',
  `free_temporary` int(11) DEFAULT NULL COMMENT '赠送临时品项',
  `chargeback_temporary` int(11) DEFAULT NULL COMMENT '退单临时品项',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_per_overview
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_code` varchar(20) NOT NULL COMMENT '职务即角色,存放职务的code',
  `menu_id` int(11) NOT NULL,
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_setting`
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `setting_code` varchar(100) DEFAULT NULL,
  `setting_value` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
INSERT INTO `sys_setting` VALUES ('single', '0');
INSERT INTO `sys_setting` VALUES ('usenet', '0');
INSERT INTO `sys_setting` VALUES ('name', '一起生活餐饮平台');
INSERT INTO `sys_setting` VALUES ('updateSeat', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL,
  `password` varchar(200) NOT NULL,
  `state` varchar(50) DEFAULT NULL,
  `emp_code` varchar(18) DEFAULT NULL COMMENT '员工编号',
  `emp_name` varchar(100) DEFAULT NULL,
  `emp_organization` int(11) DEFAULT NULL COMMENT '所属机构',
  `emp_department` int(11) DEFAULT NULL COMMENT '所属部门',
  `emp_duties` varchar(11) DEFAULT NULL,
  `emp_dob` date DEFAULT NULL,
  `emp_cardno` varchar(18) DEFAULT NULL,
  `emp_sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `is_marry` varchar(255) DEFAULT NULL COMMENT '是否已婚',
  `emp_cardid` varchar(18) DEFAULT NULL COMMENT '员工卡ID',
  `sqh_no1` varchar(50) DEFAULT NULL,
  `sqh_no2` varchar(50) DEFAULT NULL,
  `is_orguser_flag` varchar(2) DEFAULT NULL COMMENT '集团用户标志',
  `is_manager_flag` varchar(2) DEFAULT NULL COMMENT '管理员标志',
  `is_start_flag` varchar(2) DEFAULT NULL COMMENT '启动标志',
  `natives` varchar(8) DEFAULT NULL COMMENT '籍贯',
  `place` varchar(200) DEFAULT NULL COMMENT '户籍地',
  `phone` varchar(16) DEFAULT NULL,
  `email` varchar(18) DEFAULT NULL,
  `height` varchar(5) DEFAULT NULL,
  `nation` varchar(12) DEFAULT NULL COMMENT '民族',
  `mobile` varchar(14) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `is_del` varchar(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'normal', 'yxe_water', '易小二', '1', '1', '009', '2017-02-01', '111111111111111111', '男', '已婚', '88888888', '88888888', '88888888', '0', '0', '0', '', '', '', '', '0', '', '', '', '', '0', '2017-02-10 09:24:38', '92599e4f-266c-11e7-9329-00259030e14f');

-- ----------------------------
-- Table structure for `t_b_bis`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_bis`;
CREATE TABLE `t_b_bis` (
  `id` int(11) DEFAULT NULL COMMENT '营业市别自增ID',
  `bis_name` varchar(18) DEFAULT NULL COMMENT '名称',
  `bis_time` varchar(12) DEFAULT NULL COMMENT '开始时间',
  `bis_organization` int(11) DEFAULT NULL COMMENT '所属机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_bis
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_dep`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_dep`;
CREATE TABLE `t_b_dep` (
  `id` int(11) NOT NULL COMMENT '部门管理自增ID',
  `dep_code` varchar(12) DEFAULT NULL COMMENT '部门代码',
  `dep_name` varchar(24) DEFAULT NULL COMMENT '部门名称',
  `dep_sjm` varchar(18) DEFAULT NULL COMMENT '速记码',
  `dep_department` int(11) DEFAULT NULL COMMENT '上级部门-部门编号',
  `use_type` varchar(12) DEFAULT NULL COMMENT '使用类型',
  `dep_organization` int(11) DEFAULT NULL COMMENT '所属机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_dep
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_fydj`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_fydj`;
CREATE TABLE `t_b_fydj` (
  `id` int(11) NOT NULL COMMENT '费用登记自增ID',
  `fydj_name` varchar(12) DEFAULT NULL COMMENT '费用名称',
  `fydj_type` varchar(12) DEFAULT NULL COMMENT '费用类型',
  `fydj_expend` double DEFAULT NULL COMMENT '支出费用',
  `fydj_earning` double DEFAULT NULL COMMENT '收入费用',
  `fydj_time` datetime DEFAULT NULL COMMENT '费用发生时间',
  `fydj_organization` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `fydj_abstract` varchar(128) DEFAULT NULL COMMENT '摘要',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间-查询条件',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间-查询条件',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_fydj
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_fylx`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_fylx`;
CREATE TABLE `t_b_fylx` (
  `id` int(11) NOT NULL COMMENT '费用类型自增ID',
  `fylx_num` varchar(12) DEFAULT NULL COMMENT '编号',
  `fylx_name` varchar(12) DEFAULT NULL COMMENT '名称',
  `fylx_organization` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_fylx
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_fyxm`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_fyxm`;
CREATE TABLE `t_b_fyxm` (
  `id` int(11) NOT NULL COMMENT '费用项目自增ID',
  `fyxm_num` varchar(12) DEFAULT NULL COMMENT '编号',
  `fyxm_name` varchar(12) DEFAULT NULL COMMENT '名称（费用项目）',
  `fyxm_type` int(11) DEFAULT NULL COMMENT '费用类型',
  `fyxm_organization` varchar(40) DEFAULT NULL COMMENT '所属机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_fyxm
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_org`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_org`;
CREATE TABLE `t_b_org` (
  `id` int(11) NOT NULL COMMENT '组织机构自增ID',
  `org_code` varchar(12) DEFAULT NULL COMMENT '机构代码',
  `org_name` varchar(24) DEFAULT NULL COMMENT '机构名称',
  `org_sjm` varchar(18) DEFAULT NULL COMMENT '速记码',
  `org_kdsj` date DEFAULT NULL COMMENT '开店时间',
  `org_jmgh` varchar(12) DEFAULT NULL COMMENT '加密狗号',
  `org_phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `org_glms` varchar(12) DEFAULT NULL COMMENT '管理模式',
  `org_area` varchar(12) DEFAULT NULL COMMENT '区域',
  `org_brand` varchar(12) DEFAULT NULL COMMENT '品牌',
  `franchisees` varchar(24) DEFAULT NULL COMMENT '加盟商',
  `max_customer` int(11) unsigned DEFAULT NULL COMMENT '最大容纳客数',
  `address` varchar(32) DEFAULT NULL COMMENT '地址',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `is_own_flag` varchar(2) DEFAULT NULL COMMENT '本店标志',
  `is_start_flag` varchar(2) DEFAULT NULL COMMENT '启用标志',
  `is_newstore_flag` varchar(2) DEFAULT NULL COMMENT '新店标志',
  `is_line_flag` varchar(2) DEFAULT NULL COMMENT '排队标志',
  `rdc` varchar(2) DEFAULT NULL COMMENT '所属RDC',
  `province` varchar(12) DEFAULT NULL COMMENT '省',
  `city` varchar(12) DEFAULT NULL COMMENT '市',
  `region` varchar(12) DEFAULT NULL COMMENT '区',
  `road` varchar(12) DEFAULT NULL COMMENT '路',
  `area` varchar(12) DEFAULT NULL COMMENT '面积',
  `rdc_distance` varchar(12) DEFAULT NULL COMMENT '距RDC距离',
  `diyFields1` varchar(1000) DEFAULT NULL COMMENT '自定义字段1',
  `diyFields2` varchar(1000) DEFAULT NULL COMMENT '自定义字段2',
  `diyFields3` varchar(1000) DEFAULT NULL COMMENT '自定义字段3',
  `diyFields4` varchar(1000) DEFAULT NULL COMMENT '自定义字段4',
  `diyFields5` varchar(1000) DEFAULT NULL COMMENT '自定义字段5',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `only_code` varchar(100) DEFAULT NULL COMMENT '唯一编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_org
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_rfc`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_rfc`;
CREATE TABLE `t_b_rfc` (
  `id` int(11) NOT NULL COMMENT '退菜原因自增ID',
  `rfc_code` varchar(12) DEFAULT NULL COMMENT '编号',
  `rfc_name` varchar(12) DEFAULT NULL COMMENT '名称',
  `rfc_type` int(11) DEFAULT NULL COMMENT '退菜原因类型',
  `rfc_zjf` varchar(18) DEFAULT NULL COMMENT '助记符',
  `rfc_explain` varchar(128) DEFAULT NULL COMMENT '说明',
  `is_material_loss` varchar(2) DEFAULT NULL COMMENT '原料损失',
  `is_tdsgqpx` varchar(2) DEFAULT NULL COMMENT '退单时沽清品项',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_rfc
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_rfct`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_rfct`;
CREATE TABLE `t_b_rfct` (
  `id` int(11) NOT NULL COMMENT '退菜原因类型ID',
  `rfct_code` varchar(12) DEFAULT NULL COMMENT '编号',
  `rfct_name` varchar(12) DEFAULT NULL COMMENT '名称',
  `rfct_organization` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `is_default_flag` varchar(2) DEFAULT NULL COMMENT '默认设置',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `uuid_key` varchar(40) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_rfct
-- ----------------------------

-- ----------------------------
-- Table structure for `t_b_zdbz`
-- ----------------------------
DROP TABLE IF EXISTS `t_b_zdbz`;
CREATE TABLE `t_b_zdbz` (
  `id` int(11) NOT NULL COMMENT '整单备注自增ID',
  `zdbz_num` varchar(12) DEFAULT NULL COMMENT '编号',
  `order_remark` varchar(128) DEFAULT NULL COMMENT '整单备注',
  `zdbz_organization` int(11) DEFAULT NULL COMMENT '所属机构',
  `is_del` varchar(2) DEFAULT '0' COMMENT '是否回收',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `uuid_key` varchar(200) NOT NULL,
  PRIMARY KEY (`uuid_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_b_zdbz
-- ----------------------------

-- ----------------------------
-- Procedure structure for `autoCreateLogTable`
-- ----------------------------
DROP PROCEDURE IF EXISTS `autoCreateLogTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `autoCreateLogTable`()
BEGIN
    SET @v_table_date = DATE_FORMAT(NOW(),'%Y_%m');
    SET @v_new_table_date = DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 MONTH),'%Y_%m');

    SET @v_tablename = CONCAT('sys_business_log_',@v_new_table_date);
    SET @v_new_tablename = CONCAT('sys_business_log_',@v_table_date);
    SET @v_tabel_str = '(uuid varchar(255) NOT NULL,
			oper varchar(255) DEFAULT NULL COMMENT ''操作人(姓名)'',
			pos varchar(255) DEFAULT NULL COMMENT ''操作pos'',
			time datetime DEFAULT NULL COMMENT ''操作时间'',
			content text COMMENT ''内容'',
			type int(11) DEFAULT NULL COMMENT ''类型(1/更换客位  2/加入团队 3/更改客位人数   4/修改品项数量   5/修改品项价格  6/品项赠送   7/撤销品项赠送  8/单品转台   9/转账  10拆帐  11/关账  12/ S帐   13撤销 S帐  14/结班)'',
			ow_num varchar(255) DEFAULT NULL COMMENT ''营业流水号'',
			seat_name varchar(255) DEFAULT NULL COMMENT ''客位名称'',
			PRIMARY KEY (uuid))';
    SET @v_sql = CONCAT('create table if not exists ',@v_tablename ,@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;

    SET @v_sql = CONCAT('create table if not exists ',@v_new_tablename ,@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `autoCreateOpenTable`
-- ----------------------------
DROP PROCEDURE IF EXISTS `autoCreateOpenTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `autoCreateOpenTable`()
BEGIN
    SET @v_table_date = fnGetPartitionDateForMonth() ;

    SET @v_tablename = CONCAT('dg_open_water_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(id INT(11) PRIMARY KEY NOT NULL COMMENT ''营业流水ID'',ow_num VARCHAR(50) COMMENT ''营业流水号'',seat_id INT(11) COMMENT ''客座ID'',people_count INT(11) COMMENT ''人数'',
                    item_costs_sum DOUBLE COMMENT ''品项价格和'',discount_costs DOUBLE COMMENT ''优惠'',service_charge DOUBLE COMMENT ''服务费'',private_room_cost DOUBLE COMMENT ''包房费'',
                    minimum_consumption DOUBLE COMMENT ''最低消费'',minimum_charge_complete DOUBLE COMMENT ''最低消费补齐'',subtotal DOUBLE COMMENT ''小计'',clearing_water_id INT(11) COMMENT ''结算流水ID'',
                    first_time DATETIME COMMENT ''开台时间'',ow_state INT(11) DEFAULT ''1'' COMMENT ''营业流水状态1:初始化状态、2已经结算'',seat_label VARCHAR(100) COMMENT ''客座标签'',
                    open_pos INT(11) COMMENT ''开单POS'',waiter INT(11) COMMENT ''服务员'',cr_id INT(11) COMMENT ''会员'',documents VARCHAR(100) COMMENT ''单据'',ow_notes VARCHAR(500) COMMENT ''整单备注'',closed_time DATETIME)';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_open_water created finished:',@v_sql));

    SET @v_tablename = CONCAT('dg_ow_clearingway_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(cw_id INT(11) COMMENT ''结算流水ID'',settlement_amount DOUBLE COMMENT ''结算金额'',conversion_amount INT(11) COMMENT ''换算金额'',notes VARCHAR(100) COMMENT ''备注'',
                    cw_code VARCHAR(10) COMMENT ''结算方式code'',non_zero_amount DOUBLE COMMENT ''不找零金额'',foreign_pay DOUBLE COMMENT ''外币支付'')';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_ow_clearingway created finished:',@v_sql));

    SET @v_tablename = CONCAT('dg_ow_consumer_details_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(item_file_id INT(11) COMMENT ''品项ID'',item_file_number INT(11) COMMENT ''数量'',item_file_zs DOUBLE COMMENT ''只数'',production_costs DOUBLE COMMENT ''制作费用'',
                    discount DOUBLE COMMENT ''折扣'',subtotal DOUBLE COMMENT ''小计'',ow_id INT(11) COMMENT ''营业流水ID'',guest VARCHAR(3) COMMENT ''该品项消费的具体客座'',notes VARCHAR(5))';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_ow_consumer_details created finished:',@v_sql));

    SET @v_tablename = CONCAT('dg_ow_discount_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(discount_id INT(11) COMMENT ''优惠ID'',pre_authorization INT(11) COMMENT ''预授权'',authorized_person INT(11) COMMENT ''授权人'',
                    authorized_person_name VARCHAR(20) COMMENT ''预授权人名称'',cw_id INT(11) COMMENT ''结算流水ID'')';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_ow_discount created finished:',@v_sql));

    SET @v_tablename = CONCAT('dg_ow_receipt_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(invoice_id INT(11) COMMENT ''发票ID'',invoice_count INT(11) COMMENT ''发票张数'',invoice_amount INT(11) COMMENT ''发票金额'',cw_id INT(11) COMMENT ''结算流水ID'')';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_ow_receipt created finished:',@v_sql));

    SET @v_tablename = CONCAT('dg_reception_clearing_water_',CAST(@v_table_date AS CHAR(6)));
    SET @v_tabel_str = '(id INT(11) PRIMARY KEY NOT NULL,cw_num VARCHAR(100) COMMENT ''结算流水号'',consumption_amount DOUBLE COMMENT ''消费金额'',
                     zero_amount DOUBLE COMMENT ''抹零金额'',fixed_discount DOUBLE COMMENT ''定额优惠'',amounts_receivable DOUBLE COMMENT ''应收金额'',
                     paid_amount DOUBLE COMMENT ''实收金额'',change_amount DOUBLE COMMENT ''找零金额'',clearing_time DATETIME COMMENT ''结算时间'',
                     clearing_bis INT(11) COMMENT ''结算市别'',clearing_operator VARCHAR(10) COMMENT ''结算操作人员'',clearing_pos VARCHAR(10) COMMENT ''结算POS'',
                     print_cont INT(11) COMMENT ''打印次数'',invoicing INT(11) COMMENT ''开具发票'',zero_settlement INT(11) COMMENT ''零结算'',retro_documents INT(11) COMMENT ''补录单据'',
                     statement_label VARCHAR(500) COMMENT ''结账单标注'',coupon_amount DOUBLE COMMENT ''赠券金额'',clearing_notes VARCHAR(500) COMMENT ''结算备注'',seat_id INT(11) COMMENT ''客位ID'',clearing_state INT(11) COMMENT ''结算状态1：未结、2：已结'')';
    SET @v_sql = CONCAT(CONCAT('CREATE TABLE ',@v_tablename),@v_tabel_str);
    SET @sql = @v_sql;
    PREPARE cmd FROM @sql;
    EXECUTE cmd;
    DEALLOCATE PREPARE cmd;
    INSERT INTO sys_auto_log(log_time,log_content) VALUES (NOW(),CONCAT('table dg_reception_clearing_water created finished:',@v_sql));
  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `copy_docd_bak`
-- ----------------------------
DROP PROCEDURE IF EXISTS `copy_docd_bak`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `copy_docd_bak`()
BEGIN
			DECLARE tempSql text;
			#插入菜品明细表
			set tempSql = concat('insert dg_ow_consumer_details_2017_07 select docd.* from dg_ow_consumer_details_201708 docd where  docd.ow_id in (select b.id from dg_ow_service_form_2017_07 b )');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入菜品明细表
			set tempSql = concat('insert dg_ow_consumer_details_2017_08 select docd.* from dg_ow_consumer_details_201708 docd where  docd.ow_id in (select b.id from dg_ow_service_form_2017_08 b )');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入菜品明细表
			set tempSql = concat('delete docd from dg_ow_consumer_details_201708 docd where  docd.ow_id in (select b.id from dg_ow_service_form_2017_07 b )');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#插入菜品明细表
			set tempSql = concat('delete docd from dg_ow_consumer_details_201708 docd where  docd.ow_id in (select b.id from dg_ow_service_form_2017_08 b )');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `deleteDataOutOf7`
-- ----------------------------
DROP PROCEDURE IF EXISTS `deleteDataOutOf7`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `deleteDataOutOf7`()
BEGIN
	  #Routine body goes here...
    declare month_name VARCHAR(255);
    declare dg_ow_consumer_details_table_create VARCHAR(255);
    declare dg_ow_service_form_table_create VARCHAR(255);
    declare dg_open_water_table_create VARCHAR(255);
    declare dg_ow_receipt_table_create VARCHAR(255);
    declare dg_ow_clearingway_table_create VARCHAR(255);
    declare dg_reception_clearing_water_table_create VARCHAR(255);


    declare dg_ow_consumer_details_table VARCHAR(255);
    declare dg_ow_service_form_table VARCHAR(255);
    declare dg_open_water_table VARCHAR(255);
    declare dg_ow_receipt_table VARCHAR(255);
    declare dg_ow_clearingway_table VARCHAR(255);
    declare dg_reception_clearing_water_table VARCHAR(255);

		
		set month_name = date_format(now(),'%Y%m');
	  set dg_ow_consumer_details_table_create = concat('create table if not exists ','dg_ow_consumer_details','_',month_name,' like ','dg_ow_consumer_details');
		set dg_ow_consumer_details_table = concat('dg_ow_consumer_details','_',month_name);
		set @v_sql=dg_ow_consumer_details_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;
		
		set @v_sql = concat ('insert ' ,concat('dg_ow_consumer_details','_',month_name), ' select a.* from dg_ow_consumer_details a 
				left join dg_ow_service_form b on a.ow_id = b.id  
				left join dg_open_water c on b.ow_id = c.id where 
				(TO_DAYS(now())- TO_DAYS(c.first_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql = 'delete a from dg_ow_consumer_details a left join dg_ow_service_form b on a.ow_id = b.id 
				left join dg_open_water c on b.ow_id = c.id where 
				(TO_DAYS(now())- TO_DAYS(c.first_time)) > 7 and a.ow_id = b.id';
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

	  set dg_ow_service_form_table_create = concat('create table if not exists ','dg_ow_service_form','_',month_name,' like ','dg_ow_service_form');
		set dg_ow_service_form_table = concat('dg_ow_service_form','_',month_name);
		set @v_sql=dg_ow_service_form_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql = concat('insert ',dg_ow_service_form_table, ' select a.* from dg_ow_service_form a 
				left join dg_open_water c on a.ow_id = c.id where 
				(TO_DAYS(now())- TO_DAYS(c.first_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;


		set @v_sql = concat('delete a from  dg_ow_service_form a ,dg_open_water b 
				 where 
				 (TO_DAYS(now())- TO_DAYS(b.first_time)) > 7 and a.ow_id = b.id');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;


	  set dg_open_water_table_create = concat('create table if not exists ','dg_open_water','_',month_name,' like ','dg_open_water');
		set dg_open_water_table = concat('dg_open_water','_',month_name);
		set @v_sql=dg_open_water_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql = concat('insert ',dg_open_water_table ,' select c.* from dg_open_water c  where (TO_DAYS(now())- TO_DAYS(c.first_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;
		
		set @v_sql = 'delete  from  dg_open_water  where  (TO_DAYS(now())- TO_DAYS(first_time)) > 7 ';
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set dg_ow_receipt_table_create = concat('create table if not exists ','dg_ow_receipt','_',month_name,' like ','dg_ow_receipt');
		set dg_ow_receipt_table = concat('dg_ow_receipt','_',month_name);
		set @v_sql=dg_ow_receipt_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
		COMMIT;

		set @v_sql = concat('insert ',dg_ow_receipt_table,' select a.* from dg_ow_receipt a 
				left join dg_reception_clearing_water c on a.cw_id = c.id where 
				(TO_DAYS(now())- TO_DAYS(c.clearing_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
		COMMIT;

		set @v_sql =  'delete a from  dg_ow_receipt a ,dg_reception_clearing_water b 
				 where 
				 (TO_DAYS(now())- TO_DAYS(b.clearing_time)) > 7  and a.cw_id = b.id';
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
		COMMIT;



		set dg_ow_clearingway_table_create = concat('create table if not exists ','dg_ow_clearingway','_',month_name,' like ','dg_ow_clearingway');
		set dg_ow_clearingway_table = concat('dg_ow_clearingway','_',month_name);
	  set @v_sql=dg_ow_clearingway_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
		COMMIT;

		set @v_sql = concat('insert ',dg_ow_clearingway_table,' select a.* from dg_ow_clearingway a 
				left join dg_reception_clearing_water c on a.cw_id = c.id where 
				(TO_DAYS(now())- TO_DAYS(c.clearing_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
		COMMIT;

		set @v_sql = 'delete a from  dg_ow_clearingway a ,dg_reception_clearing_water b 
				 where 
				 (TO_DAYS(now())- TO_DAYS(b.clearing_time)) > 7 and a.cw_id = b.id';	
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
		COMMIT;




		set dg_reception_clearing_water_table_create = concat('create table if not exists ','dg_reception_clearing_water','_',month_name,' like ','dg_reception_clearing_water');
		set dg_reception_clearing_water_table = concat('dg_reception_clearing_water','_',month_name);
	  set @v_sql=dg_reception_clearing_water_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
    COMMIT;

		set @v_sql = concat('insert ',dg_reception_clearing_water_table,' select c.* from dg_reception_clearing_water c  where 
				(TO_DAYS(now())- TO_DAYS(c.clearing_time)) > 7');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
    COMMIT;

		
		set @v_sql = 'delete  from  dg_reception_clearing_water  where  (TO_DAYS(now())- TO_DAYS(clearing_time)) > 7';
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
    COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `DG_CS_openClassReport`
-- ----------------------------
DROP PROCEDURE IF EXISTS `DG_CS_openClassReport`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `DG_CS_openClassReport`(userCode VARCHAR(10),loginPos VARCHAR(10))
BEGIN
    select login_time,petty_cash from dg_open_class_water where login_user = userCode and loginPos = loginPos and

                                                                open_class_state = 0;
  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `DG_CS_openWaterTransfer`
-- ----------------------------
DROP PROCEDURE IF EXISTS `DG_CS_openWaterTransfer`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DG_CS_openWaterTransfer`(`transferOwNum` VARCHAR(100), `targetOwNum` VARCHAR(100), `opFlag` INT(11))
BEGIN

    declare transferTeamMembers VARCHAR(100); -- 待转账的流水的团队号码

    declare targetTeamMembers VARCHAR(100); -- 转账目标营业流水的团队号码

    declare targetTeamMembersMainSeat VARCHAR(100); -- 转账目标的团队主客位

    declare otherFirstTeamOwNum VARCHAR(100); -- 如果待转账的营业流水为团队，剩下的团队成员的第一个的营业流水

    declare otherFirstTeamMainSeat VARCHAR(100);-- 如果待转账的营业流水为团队，剩下的团队成员的第一个客位

    declare transferSeat INT; -- 待转账营业流水的座位号

    declare transferOtherOwNum VARCHAR(100); -- 如果待转账流水转完以后，该客位没有其他营业流水，则把该客位的状态修改为空闲，如果有，则不操作

    declare seatAmount INT; -- 转账目标的营业流水下面的该客位的初始化客座个数，开单为1，转账+1

    declare targetTeamFlag INT;-- 转账目标的营业流水是否为团队流水

    declare errno int;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION

    BEGIN

      ROLLBACK ;

      set errno = 1;

    END;

    start TRANSACTION ;

    set errno = 0;

    select team_members into targetTeamMembers from dg_open_water where ow_num = targetOwNum;

    select team_members into transferTeamMembers from dg_open_water where ow_num = transferOwNum;

    select team_main_seat into targetTeamMembersMainSeat from dg_open_water where ow_num = targetOwNum;

    select seat_id into transferSeat from dg_open_water where ow_num = transferOwNum;

    select seat_amount into seatAmount from dg_open_water where ow_num = targetOwNum;

    select is_team into targetTeamFlag from dg_open_water where ow_num = targetOwNum;

    if opFlag = 0 THEN -- 待转账的营业流水非团队

      update dg_open_water set team_members = targetTeamMembers,ow_state = 4,transfer_target = targetOwNum,team_main_seat = targetTeamMembersMainSeat,join_team_notes = '转账',join_team_time=now()

      where ow_num =transferOwNum;

    ELSE if opFlag = 1 THEN -- 待转账的营业流水为团队

      select ow_num into otherFirstTeamOwNum from dg_open_water dow where

        dow.ow_num != transferOwNum and

        dow.team_members = (select team_members from dg_open_water where ow_num = transferOwNum) limit 0,1;

      if otherFirstTeamOwNum is not NULL THEN

        select seat_id into otherFirstTeamMainSeat from dg_open_water where ow_num = otherFirstTeamOwNum;

        update dg_open_water set team_main_seat = otherFirstTeamMainSeat where team_members = transferTeamMembers;

      END IF;

      update dg_open_water set team_members = targetTeamMembers,ow_state = 4,transfer_target = targetOwNum,team_main_seat = targetTeamMembersMainSeat,join_team_notes = '转账',join_team_time=now()

      where ow_num =transferOwNum;

    END IF ;

    END IF ;

    select ow_num into transferOtherOwNum from dg_open_water where seat_id = transferSeat and ow_state in (1,3) limit 0,1;

    if transferOtherOwNum is NULL THEN -- 修改客位状态为空闲

          update dg_consumer_seat set seat_state = 1,last_open_time = NULL where id = transferSeat;

    END IF;

    update dg_open_water set seat_amount = seatAmount+1 where ow_num = targetOwNum; -- 修改转账目标下该客位的客座个数

    if targetTeamFlag = 0 THEN -- 如果转账目标的营业流水非团队，先修改该营业流水为团队

      update dg_open_water set is_team = 1 where ow_num = targetOwNum;

    END IF;-- 修改转账的营业流水为转账目标的营业流水的团队下的一份

      update dg_open_water set is_team = 1,team_members = targetTeamMembers,team_main_seat = targetTeamMembersMainSeat where ow_num = transferOwNum;

    COMMIT ;

    select errno;

  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `DG_CS_test`
-- ----------------------------
DROP PROCEDURE IF EXISTS `DG_CS_test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DG_CS_test`(IN finalPrice VARCHAR(4000), IN discountMoney VARCHAR(4000), IN owId VARCHAR(4000),
                            IN itemFileId VARCHAR(4000), IN cou INT)
BEGIN
DECLARE i INT;
declare fp varchar(100);
declare dm varchar(100);
DECLARE oi varchar(100);
declare ifi varchar(100);

declare fpd DOUBLE;
declare dmd decimal(18,2);
DECLARE oid int;
declare ifid int;

set i =1 ;
while i < cou DO
	select func_get_split_string(finalPrice,',',i) into fp;
	select func_get_split_string(discountMoney,',',i) into dm;
	select func_get_split_string(owId,',',i) into oi;
	select func_get_split_string(itemFileId,',',i) into ifi;

	 set fpd = cast(fp as DECIMAL(18,2));
	set dmd = cast(dm as decimal(18,2));
 set oid = CONVERT(oi , SIGNED);
 set ifid = CONVERT(ifi , SIGNED);

		 update dg_ow_consumer_details set item_pay_money = fpd,discount_money = dmd,settlement_status = 1
		 where ow_id = oid and item_file_id = ifid;
	set i = i+1;
end WHILE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `monthBakFun_copy`
-- ----------------------------
DROP PROCEDURE IF EXISTS `monthBakFun_copy`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `monthBakFun_copy`()
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE drcw_id int;
	DECLARE table_end_now VARCHAR(20);
	DECLARE table_end_old VARCHAR(20);
	DECLARE now_date VARCHAR(20);
	DECLARE tempSql text;
#	DECLARE drcw_cur CURSOR FOR SELECT id,clearing_time from dg_reception_clearing_water;
#	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
#	OPEN drcw_cur;
#	REPEAT
#		FETCH drcw_cur INTO drcw_id, drcw_time;
#		if drcw_time is not null THEN
#			set table_end = left(drcw_time,7);
#			set table_end  = replace(table_end,'-','');
			set now_date = DATE_FORMAT(NOW(),'%Y%m%d');
			set table_end_now = DATE_FORMAT(NOW(),'%Y_%m');
			set table_end_old = DATE_FORMAT(date_sub(now(),INTERVAL 1 MONTH),'%Y_%m');
		
			#建立中间表
			#结算流水表
			set tempSql = concat('create table if not exists ','dg_reception_clearing_water_',table_end_now,' like ','dg_reception_clearing_water');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#建立中间表
			#结算流水表
			set tempSql = concat('create table if not exists ','dg_reception_clearing_water_',table_end_old,' like ','dg_reception_clearing_water');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#营业流水表
			set tempSql = concat('create table if not exists ','dg_open_water_',table_end_now,' like ','dg_open_water');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#营业流水表
			set tempSql = concat('create table if not exists ','dg_open_water_',table_end_old,' like ','dg_open_water');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#发票表
			set tempSql = concat('create table if not exists ','dg_ow_receipt_',table_end_now,' like ','dg_ow_receipt');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#发票表
			set tempSql = concat('create table if not exists ','dg_ow_receipt_',table_end_old,' like ','dg_ow_receipt');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#支付类型表
			set tempSql = concat('create table if not exists ','dg_ow_clearingway_',table_end_now,' like ','dg_ow_clearingway');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#支付类型表
			set tempSql = concat('create table if not exists ','dg_ow_clearingway_',table_end_old,' like ','dg_ow_clearingway');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#服务流水表
			set tempSql = concat('create table if not exists ','dg_ow_service_form_',table_end_now,' like ','dg_ow_service_form');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#服务流水表
			set tempSql = concat('create table if not exists ','dg_ow_service_form_',table_end_old,' like ','dg_ow_service_form');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#菜品明细表
			set tempSql = concat('create table if not exists ','dg_ow_consumer_details_',table_end_now,' like ','dg_ow_consumer_details');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
			#菜品明细表
			set tempSql = concat('create table if not exists ','dg_ow_consumer_details_',table_end_old,' like ','dg_ow_consumer_details');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;

			
			#删除发票流水
			set tempSql = concat('delete dor from dg_ow_receipt_',table_end_now,
			' dor left join dg_reception_clearing_water_',table_end_now,' drcw on dor.cw_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#删除发票流水
			set tempSql = concat('delete dor from dg_ow_receipt_',table_end_old,
			' dor left join dg_reception_clearing_water_',table_end_old,' drcw on dor.cw_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;
					

			
			#加入发票流水
			set tempSql = concat('insert dg_ow_receipt_',table_end_now,' select dor.* from dg_ow_receipt dor left join ',
			'dg_reception_clearing_water drcw on dor.cw_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');

			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			set tempSql = concat('insert dg_ow_receipt_',table_end_old,' select dor.* from dg_ow_receipt dor left join '
			'dg_reception_clearing_water drcw on dor.cw_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			
			#删除结算方式表
			set tempSql = concat('delete dor from dg_ow_clearingway_',table_end_now,
			' dor left join dg_reception_clearing_water_',table_end_now,' drcw on dor.cw_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#删除结算方式表
			set tempSql = concat('delete dor from dg_ow_clearingway_',table_end_old,
			' dor left join dg_reception_clearing_water_',table_end_old,' drcw on dor.cw_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入结算方式表
			set tempSql = concat('insert dg_ow_clearingway_',table_end_now,' select dor.* from  dg_ow_clearingway dor left join 
			dg_reception_clearing_water drcw on dor.cw_id = drcw.id where  date_to_ym_format(drcw.clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入结算方式表
			set tempSql = concat('insert dg_ow_clearingway_',table_end_old,' select dor.* from  dg_ow_clearingway dor left join 
			dg_reception_clearing_water drcw on dor.cw_id = drcw.id where  date_to_ym_format(drcw.clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#删除菜品明细表
			set tempSql = concat('delete docd from dg_ow_consumer_details_',table_end_now,' docd left join dg_ow_service_form_',table_end_now,' b on docd.ow_id = b.id 
					left join dg_open_water_',table_end_now,' c on b.ow_id = c.id 
					left join dg_reception_clearing_water_',table_end_now,' drcw on c.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#删除菜品明细表
			set tempSql = concat('delete docd from dg_ow_consumer_details_',table_end_old,' docd left join dg_ow_service_form_',table_end_old,' b on docd.ow_id = b.id 
					left join dg_open_water_',table_end_old,' c on b.ow_id = c.id 
					left join dg_reception_clearing_water_',table_end_old,' drcw on c.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入菜品明细表
			set tempSql = concat('insert dg_ow_consumer_details_',table_end_now,' select docd.* from dg_ow_consumer_details docd left join dg_ow_service_form b on docd.ow_id = b.id 
					left join dg_open_water c on b.ow_id = c.id 
					left join dg_reception_clearing_water drcw on c.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入菜品明细表
			set tempSql = concat('insert dg_ow_consumer_details_',table_end_old,' select docd.* from dg_ow_consumer_details docd left join dg_ow_service_form b on docd.ow_id = b.id 
					left join dg_open_water c on b.ow_id = c.id 
					left join dg_reception_clearing_water drcw on c.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#删除服务明细表
			set tempSql = concat('delete docd from dg_ow_service_form_',table_end_now,' docd left join dg_open_water_',table_end_now,' c on docd.ow_id = c.id 
					left join dg_reception_clearing_water_',table_end_now,' drcw on c.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;
			

			#删除服务明细表
			set tempSql = concat('delete docd from dg_ow_service_form_',table_end_old,' docd left join dg_open_water_',table_end_old,' c on docd.ow_id = c.id 
					left join dg_reception_clearing_water_',table_end_old,' drcw on c.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;



			#插入服务明细表
			set tempSql = concat('insert  dg_ow_service_form_',table_end_now,' select docd.* from dg_ow_service_form docd left join dg_open_water  c on docd.ow_id = c.id 
					left join dg_reception_clearing_water drcw on c.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#插入服务明细表
			set tempSql = concat('insert  dg_ow_service_form_',table_end_old,' select docd.* from dg_ow_service_form docd left join dg_open_water  c on docd.ow_id = c.id 
					left join dg_reception_clearing_water drcw on c.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


		#删除营业流水表
			set tempSql = concat('delete doc from dg_open_water_',table_end_now,' doc
					left join dg_reception_clearing_water_',table_end_now,' drcw on doc.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;

			#删除营业流水表
			set tempSql = concat('delete doc from dg_open_water_',table_end_old,' doc
					left join dg_reception_clearing_water_',table_end_old,' drcw on doc.clearing_water_id = drcw.id where drcw.id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入营业流水表
			set tempSql = concat('insert  dg_open_water_',table_end_now,' select dow.* from  dg_open_water  dow 
					left join dg_reception_clearing_water drcw on dow.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#插入营业流水表
			set tempSql = concat('insert  dg_open_water_',table_end_old,' select dow.* from  dg_open_water  dow 
					left join dg_reception_clearing_water drcw on dow.clearing_water_id = drcw.id where date_to_ym_format(drcw.clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd; 
			COMMIT;


			#删除结算流水
			set tempSql = concat('delete from dg_reception_clearing_water_',table_end_now,' where id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;

			#删除结算流水
			set tempSql = concat('delete from dg_reception_clearing_water_',table_end_old,' where id in (SELECT id from dg_reception_clearing_water)');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;


			#插入结算流水
			set tempSql = concat('insert dg_reception_clearing_water_',table_end_now,' select * from dg_reception_clearing_water  where date_to_ym_format(clearing_time) = "',table_end_now,'" and (TO_DAYS(',now_date,')- TO_DAYS(clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;


			#插入结算流水
			set tempSql = concat('insert dg_reception_clearing_water_',table_end_old,' select * from dg_reception_clearing_water  where date_to_ym_format(clearing_time) = "',table_end_old,'" and (TO_DAYS(',now_date,')- TO_DAYS(clearing_time)) > 2');
			set @v_sql=tempSql;
			prepare cmd from @v_sql;
			execute cmd;
			deallocate prepare cmd;  
			COMMIT;
	

	
	############################删除原表操作

		set @v_sql = CONCAT('delete a from dg_ow_consumer_details a left join dg_ow_service_form b on a.ow_id = b.id 
				left join dg_open_water c on b.ow_id = c.id 
				left join dg_reception_clearing_water drcw on c.clearing_water_id = drcw.id where 
				(TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql = concat('delete a from dg_ow_service_form a left join dg_open_water b on a.ow_id = b.id
				 left join dg_reception_clearing_water drcw on b.clearing_water_id = drcw.id  where 
				 (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;
		
		set @v_sql = CONCAT('delete d from dg_open_water d left join dg_reception_clearing_water drcw on d.clearing_water_id = drcw.id  where  (TO_DAYS(',now_date,')- TO_DAYS(drcw.clearing_time)) > 2 ');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql =  CONCAT('delete a from  dg_ow_receipt a ,dg_reception_clearing_water b 
				 where 
				 (TO_DAYS(',now_date,')- TO_DAYS(b.clearing_time)) > 2  and a.cw_id = b.id');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
		COMMIT;

		set @v_sql = CONCAT('delete a from  dg_ow_clearingway a ,dg_reception_clearing_water b 
				 where 
				 (TO_DAYS(',now_date,')- TO_DAYS(b.clearing_time)) > 2 and a.cw_id = b.id');	
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
		COMMIT;

		set @v_sql = CONCAT('delete  from  dg_reception_clearing_water  where  (TO_DAYS(',now_date,')- TO_DAYS(clearing_time)) > 2');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
    COMMIT;

		set @v_sql = CONCAT('delete  from  dg_ow_lockinfo  where  (TO_DAYS(',now_date,')- TO_DAYS(lock_time)) > 2');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;
    COMMIT;
#		end if;
#  UNTIL done END REPEAT;
#	CLOSE drcw_cur;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `test4`
-- ----------------------------
DROP PROCEDURE IF EXISTS `test4`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test4`()
BEGIN
	  #Routine body goes here...
    declare month_name VARCHAR(255);
    declare dg_ow_consumer_details_table_create VARCHAR(255);
    declare dg_ow_service_form_table_create VARCHAR(255);
    declare dg_open_water_table_create VARCHAR(255);
    declare dg_ow_receipt_table_create VARCHAR(255);
    declare dg_ow_clearingway_table_create VARCHAR(255);
    declare dg_reception_clearing_water_table_create VARCHAR(255);


    declare dg_ow_consumer_details_table VARCHAR(255);
    declare dg_ow_service_form_table VARCHAR(255);
    declare dg_open_water_table VARCHAR(255);
    declare dg_ow_receipt_table VARCHAR(255);
    declare dg_ow_clearingway_table VARCHAR(255);
    declare dg_reception_clearing_water_table VARCHAR(255);

		
		set month_name = date_format(now(),'%Y%m');
	  set dg_ow_consumer_details_table_create = concat('create table if not exists ','dg_ow_consumer_details','_',month_name,' like ','dg_ow_consumer_details');
		set dg_ow_consumer_details_table = concat('dg_ow_consumer_details','_',month_name);
		set @v_sql=dg_ow_consumer_details_table_create;
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;
		
		set @v_sql = concat ('insert ' ,concat('dg_ow_consumer_details','_',month_name), ' select a.* from dg_ow_consumer_details a 
				left join dg_ow_service_form b on a.ow_id = b.id  
				left join dg_open_water c on b.ow_id = c.id 
				left join dg_reception_clearing_water drcw on   c.clearing_water_id = drcw.id 
				where 
				(TO_DAYS(now())- TO_DAYS(drcw.clearing_time)) > 2');
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

		set @v_sql = 'delete a from dg_ow_consumer_details a left join dg_ow_service_form b on a.ow_id = b.id 
				left join dg_open_water c on b.ow_id = c.id 
				left join dg_reception_clearing_water drcw on   c.clearing_water_id = drcw.id
				where 
				(TO_DAYS(now())- TO_DAYS(drcw.clearing_time)) > 2';
		prepare cmd from @v_sql;
		execute cmd;
		deallocate prepare cmd;  
    COMMIT;

	 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `date_to_ym_format`
-- ----------------------------
DROP FUNCTION IF EXISTS `date_to_ym_format`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `date_to_ym_format`(`time` datetime) RETURNS text CHARSET utf8
BEGIN
	#Routine body goes here...

	RETURN date_format(time,'%Y_%m');
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getAutoOwNum`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getAutoOwNum`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `DG_FU_getAutoOwNum`(`owType` varchar(20),`isBeginWithOne` varchar(20),`headStr` varchar(20)) RETURNS text CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN
	DECLARE ret text DEFAULT '';
	DECLARE OwNum char(100);
	IF isBeginWithOne = '1' THEN
		select (case when date(dbs.createtime)=date(curdate()) then dbs.maxNum
				else 0 end) into OwNum from desk_business_setting_serialrul dbs where dbs.flowType = CONVERT(owType USING utf8) COLLATE utf8_general_ci;
	ELSE 
		select maxNum into OwNum from desk_business_setting_serialrul where flowType = CONVERT(owType USING utf8) COLLATE utf8_general_ci;
	END IF;
	set OwNum = CONVERT(OwNum,SIGNED) + 1;
	UPDATE desk_business_setting_serialrul SET maxNum = OwNum,createTime = NOW() where flowType = CONVERT(owType USING utf8) COLLATE utf8_general_ci;
	WHILE  LENGTH(OwNum) < 6 DO
		set OwNum = CONCAT('0',OwNum);
	END WHILE;
	set OwNum = CONCAT(headStr,OwNum);
	return OwNum;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getItemFileBigTypeName`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getItemFileBigTypeName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getItemFileBigTypeName`(`pxdl` INT(11)) RETURNS varchar(200) CHARSET utf8
BEGIN
declare itemFileBigTypeName VARCHAR(100);
    if `pxdl`  is NULL THEN
      return(NULL);
    ELSE
			select name into itemFileBigTypeName from dg_item_file_type where id = pxdl;
      return(itemFileBigTypeName);
    END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getItemSmallTypeName`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getItemSmallTypeName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getItemSmallTypeName`(`pxxl` INT(11)) RETURNS varchar(200) CHARSET utf8
BEGIN
	declare itemFileSmallTypeName VARCHAR(100);
    if `pxxl`  is NULL THEN
      return(NULL);
    ELSE
			select name into itemFileSmallTypeName from dg_item_file_type where id = pxxl;
      return(itemFileSmallTypeName);
    END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getMinOpenTimeByOpenWaters`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getMinOpenTimeByOpenWaters`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getMinOpenTimeByOpenWaters`(openWaters varchar(2000)) RETURNS datetime
BEGIN
	declare minOpenTime datetime;
    if openWaters is null then
		return (null);
	else
		select min(first_time) into minOpenTime from dg_open_water where find_in_set(ow_num,openWaters);
        return (minOpenTime);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getNumberOfMealsByOpenWaters`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getNumberOfMealsByOpenWaters`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getNumberOfMealsByOpenWaters`(openWaters varchar(2000)) RETURNS int(11)
BEGIN
	declare numberOfMeals int;
    if openWaters is null then
		return (null);
	else 
		select sum(people_count) into numberOfMeals from dg_open_water where find_in_set(ow_num,openWaters);
        return (numberOfMeals);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getOpenWatersByCw`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getOpenWatersByCw`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getOpenWatersByCw`(cw varchar(100)) RETURNS varchar(2000) CHARSET utf8
BEGIN
	-- 根据结算流水号码获取到以逗号隔开的营业流水号码
    declare openWaters VARCHAR(2000);
    declare clearingWaterId int;
    if cw is null then
      return (null);
    else
      select id into clearingWaterId from dg_reception_clearing_water where cw_num = cw;
      select group_concat(ow_num) into openWaters from dg_open_water where clearing_water_id =  clearingWaterId;
      return(openWaters);
    end if;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getOpenWaterTransferInfo`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getOpenWaterTransferInfo`;
DELIMITER ;;
CREATE DEFINER=`skip-grants user`@`skip-grants host` FUNCTION `DG_FU_getOpenWaterTransferInfo`(ownum VARCHAR(100)) RETURNS varchar(200) CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN
	declare transferInfo varchar(100);
	if ownum is null THEN
		return (null);
	else 
		select group_concat(join_team_notes) into transferInfo from dg_open_water where transfer_target = CONVERT(ownum USING utf8) COLLATE utf8_general_ci;
		return (transferInfo);
	end if;
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getPayWayInfoByCwId`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getPayWayInfoByCwId`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getPayWayInfoByCwId`(cwId int) RETURNS varchar(255) CHARSET utf8
BEGIN
	declare payWayInfo varchar(255);
	if cwId is null then
		return (null);
	else 
		select group_concat(concat(concat(dsw.name,':'),doc.settlement_amount)) into payWayInfo from dg_ow_clearingway doc left join dg_settlement_way dsw on doc.cw_code = dsw.number where cw_id = cwId;
		RETURN (payWayInfo);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getPosName`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getPosName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getPosName`(`posId` varchar(100)) RETURNS varchar(255) CHARSET utf8
BEGIN
    declare posName VARCHAR(100);
    if posId is NULL THEN
      return(NULL);
    ELSE
      select CONCAT(CONCAT(number,'-'),name) into posName from dg_pos where id = posId;
      return(posName);
    END IF;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getSeatName`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getSeatName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getSeatName`(`seatId` INT(11)) RETURNS varchar(255) CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN
    declare seatName VARCHAR(100);
    if seatId is NULL THEN
      return(NULL );
      ELSE
        select name into seatName from dg_consumer_seat where id = seatId;
        return(seatName);
    END IF;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getSeatNameByOpenWaters`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getSeatNameByOpenWaters`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getSeatNameByOpenWaters`(openWaters varchar(1000)) RETURNS varchar(1000) CHARSET utf8
BEGIN
	-- 根据函数`DG_FU_getOpenWatersByCw`的结果集获取到以逗号隔开的客座信息（客座号码-客座名称）
	declare seatNames varchar(1000);
    if openWaters is null then
		return (null);
	else 
		select group_concat(concat(concat(dcs.number,'-'),dcs.name)) into seatNames from dg_open_water dow left join dg_consumer_seat dcs on dow.seat_id = dcs.id where find_in_set(ow_num,openWaters);
		return (seatNames);
    end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getSumOfItemCosts`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getSumOfItemCosts`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getSumOfItemCosts`(openWaters varchar(2000)) RETURNS decimal(10,0)
BEGIN
	declare sumOfItem decimal(18,2);
	if openWaters is null or openWaters = '' then
		return (null);
	else 
		select sum(cast(item_costs_sum as decimal(18,2))) into sumOfItem from dg_open_water where find_in_set(ow_num,openWaters);
		RETURN (sumOfItem);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_getUserName`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_getUserName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_getUserName`(userCode varchar(20)) RETURNS varchar(255) CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN
    declare userName VARCHAR(100);
		declare code varchar(20);
		set code = userCode;
    if userCode is NULL THEN
      return(NULL );
      ELSE
        select concat(concat(emp_code,'-'),emp_name) into userName from sys_user where emp_code = code COLLATE utf8_unicode_ci ;
        return(userName);
    END IF;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DG_FU_sumOfServiceChargeByOpenWaters`
-- ----------------------------
DROP FUNCTION IF EXISTS `DG_FU_sumOfServiceChargeByOpenWaters`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DG_FU_sumOfServiceChargeByOpenWaters`(openWarers varchar(2000)) RETURNS decimal(18,2)
BEGIN
	declare sumOfServiceCharge decimal(18,2);
    if openWaters is null or openWaters = '' then
		return (null);
	else 
		select sum(cast(service_charge as decimal(18,2))) into sumOfServiceCharge from dg_open_water where find_in_set(ow_num,openWaters);
		RETURN (sumOfServiceCharge);
	end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `fnGetPartitionDateForMonth`
-- ----------------------------
DROP FUNCTION IF EXISTS `fnGetPartitionDateForMonth`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `fnGetPartitionDateForMonth`() RETURNS int(11)
begin
    declare v_today datetime default date_add(now(), INTERVAL 1 month);
    return year(v_today) * 100 + month(v_today);
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `func_get_split_string`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_get_split_string`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_get_split_string`(f_string VARCHAR(1000), f_delimiter VARCHAR(5), f_order INT) RETURNS varchar(255) CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN  
  declare result varchar(255) default '';  
  set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1));  
  return result;  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `queryClienBtnList`
-- ----------------------------
DROP FUNCTION IF EXISTS `queryClienBtnList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `queryClienBtnList`(menuId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp = '0';
    SET sTempChd = cast(menuId as char);

    WHILE sTempChd is not NULL DO
      SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT group_concat(id) INTO sTempChd FROM sys_menu where FIND_IN_SET(parent_id,sTempChd)>0 and menu_state=1 and menu_type=2;
    END WHILE;
    return sTemp;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `queryClienMenuList`
-- ----------------------------
DROP FUNCTION IF EXISTS `queryClienMenuList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `queryClienMenuList`(menuId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp = '0';
    SET sTempChd = cast(menuId as char);

    WHILE sTempChd is not NULL DO
      SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT group_concat(id) INTO sTempChd FROM sys_menu where FIND_IN_SET(parent_id,sTempChd)>0 and menu_state=1 and menu_type=1;
    END WHILE;
    return sTemp;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `queryMenuInfo`
-- ----------------------------
DROP FUNCTION IF EXISTS `queryMenuInfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `queryMenuInfo`(menuId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp = '0';
    SET sTempChd = cast(menuId as char);

    WHILE sTempChd is not NULL DO
      SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT group_concat(id) INTO sTempChd FROM sys_menu where FIND_IN_SET(parent_id,sTempChd)>0 and menu_state=1;
    END WHILE;
    return sTemp;
  END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `updateInve`
-- ----------------------------
DROP FUNCTION IF EXISTS `updateInve`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `updateInve`(`itemFileId` int,`itemFileCount` int) RETURNS text CHARSET utf8 COLLATE utf8_unicode_ci
BEGIN
	 DECLARE done INT DEFAULT 0;
   DECLARE a CHAR(100);
   DECLARE b DOUBLE;
	 DECLARE c DECIMAL DEFAULT NULL;
	 DECLARE outData TEXT DEFAULT '';
	 DECLARE cur1 CURSOR FOR SELECT inve_item_id,counter from dg_item_file_zccf where item_id = itemFileId;
	 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	 OPEN cur1;
	 REPEAT
			FETCH cur1 INTO a, b;
			IF NOT done THEN
				select diy.number into c from dg_inve_inventory diy left join dg_inve_items dim on diy.itemId = dim.id where wareID = 
					(select outWareId from dg_inve_items where id = CONVERT(a USING utf8) COLLATE utf8_general_ci) and itemId=CONVERT(a USING utf8) COLLATE utf8_general_ci;
				IF c is not null THEN
					update dg_inve_inventory set number = CONVERT(cast(number-b*itemFileCount as DECIMAL(18,2)) USING utf8) COLLATE utf8_general_ci where wareID = 
						(select outWareId from dg_inve_items where id = CONVERT(a USING utf8) COLLATE utf8_general_ci) and itemId=CONVERT(a USING utf8) COLLATE utf8_general_ci;
					IF c > 0 THEN
						select CONCAT(outData,dim.itemName,':',diy.number,',') into outData from dg_inve_inventory diy left join dg_inve_items dim on diy.itemId = dim.id where wareID = 
							(select outWareId from dg_inve_items where id = CONVERT(a USING utf8) COLLATE utf8_general_ci) and itemId=CONVERT(a USING utf8) COLLATE utf8_general_ci;
					END IF;
			  END IF;
				set c = null;
			END IF;
	 UNTIL done END REPEAT;
	 CLOSE cur1;
   return outData;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `updateItemFileNum`
-- ----------------------------
DROP FUNCTION IF EXISTS `updateItemFileNum`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `updateItemFileNum`(`shopKey` varchar(255)) RETURNS int(11)
BEGIN
		declare typeId INT;  
		declare itemId INT;  
    declare recordTypeNum VARCHAR(10);  
    declare recordNum VARCHAR(10);  
    declare ListCount INT;
		DECLARE typeCount INT;
    declare iTypeRow INT;  
    declare iTypeRow2 INT;  
		declare iRow INT;
		DECLARE iRow2 INT;
		
		-- 获取小类行数 --
		select count(*) into typeCount from dg_item_file_type where parent_id != 0;
    set iTypeRow = 1;  
      
    -- 遍历小类表 --  
    WHILE (iTypeRow <= typeCount) do  
        set iTypeRow2 = iTypeRow - 1;  
        -- 获取处方表的HIS id --  
        SELECT id into typeId  from dg_item_file_type where parent_id != 0 ORDER BY id LIMIT iTypeRow2,1;  
				if(iTypeRow < 10) then
						set recordTypeNum = CONCAT('0',iTypeRow);
				else 
						set recordTypeNum = CONCAT('',iTypeRow);
				end if;
        update dg_item_file_type rc set rc.num = recordTypeNum where rc.id = typeId;  



				-- 获取品项表的行数 --  
				select count(*)  into ListCount from dg_item_file where ppxl_id = typeId;  
				set iRow = 1;  
					
				-- 遍历品项表 --  
				WHILE (iRow <= ListCount) do  
						set iRow2 = iRow-1;  
						-- 获取品项表的 id --  
						SELECT id into itemId  from dg_item_file where ppxl_id = typeId ORDER BY id LIMIT  iRow2,1;
						if(iRow < 10) then
								set recordNum = CONCAT(recordTypeNum,'0',iRow);
								else 
								set recordNum = CONCAT(recordTypeNum,iRow);
						end if;
								update dg_item_file  set num = recordNum where id = itemId;  
						set iRow = iRow+1;
				end while;

				set iTypeRow = iTypeRow+1;  
    end while; 

		return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `autoBack`
-- ----------------------------
DROP EVENT IF EXISTS `autoBack`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `autoBack` ON SCHEDULE EVERY 1 DAY STARTS '2017-08-08 00:00:00' ON COMPLETION PRESERVE ENABLE DO call monthBakFun_copy()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `autoCreateLog`
-- ----------------------------
DROP EVENT IF EXISTS `autoCreateLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `autoCreateLog` ON SCHEDULE EVERY 1 DAY STARTS '2017-08-01 15:00:00' ON COMPLETION PRESERVE ENABLE DO call autoCreateLogTable()
;;
DELIMITER ;
