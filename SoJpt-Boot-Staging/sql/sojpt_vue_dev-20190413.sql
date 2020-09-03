/*
 Navicat Premium Data Transfer

 Source Server         : 47.93.42.209
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 47.93.42.209:3306
 Source Schema         : sojpt_vue_dev

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 13/04/2019 22:14:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dic_log
-- ----------------------------
DROP TABLE IF EXISTS `dic_log`;
CREATE TABLE `dic_log`  (
  `SESSION_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'SESSION_ID, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `USER_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名称, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `USER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户ID, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `POPEDOM_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作功能, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `FORWARD_ACTION` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作uri, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `IP` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `BROWSER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `OS` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ', isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `PARAS` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '参数, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: false, isSort: true, isPassword: false, ',
  `ENTRY_TIME` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_log
-- ----------------------------
INSERT INTO `dic_log` VALUES ('hyxRGBJBS8yFnt33-rvgPRztCHOIz7hrH4pv8NRw', 'root', '9DE5260188E2451F82FEED173E464A1B', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', '03e65cd8173a4827b4ac919b0aa40f89', NULL, '2019-04-13 22:12:00');
INSERT INTO `dic_log` VALUES ('hyxRGBJBS8yFnt33-rvgPRztCHOIz7hrH4pv8NRw', 'admin', '0131EFE5A4A841CC80D6191E91D48553', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', '32203b4ee683418eafc9e14672d7b341', NULL, '2019-04-13 22:11:34');
INSERT INTO `dic_log` VALUES ('hyxRGBJBS8yFnt33-rvgPRztCHOIz7hrH4pv8NRw', 'root', '9DE5260188E2451F82FEED173E464A1B', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', '4860690fc2924719bc7aeb7b960e6832', NULL, '2019-04-13 22:13:05');
INSERT INTO `dic_log` VALUES ('hyxRGBJBS8yFnt33-rvgPRztCHOIz7hrH4pv8NRw', 'admin', '0131EFE5A4A841CC80D6191E91D48553', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', '9d2f4333b6564913ad6706aa9c35b610', NULL, '2019-04-13 22:12:40');
INSERT INTO `dic_log` VALUES ('pw8FouzKZkv7Enc4UZPVsipQ88aJb1gDtbs1jmau', 'admin', '0131EFE5A4A841CC80D6191E91D48553', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', 'bd9564d966b745e8b28048a69f7759fb', NULL, '2019-04-13 22:10:43');
INSERT INTO `dic_log` VALUES ('pw8FouzKZkv7Enc4UZPVsipQ88aJb1gDtbs1jmau', 'root', '9DE5260188E2451F82FEED173E464A1B', '登录', '/admin/login/signIn', '127.0.0.1', 'Chrome 73.0.3683', 'Windows 10 ', 'fd038f1135c04d02b262278aaab902bc', NULL, '2019-04-13 22:10:38');

-- ----------------------------
-- Table structure for dic_popedom
-- ----------------------------
DROP TABLE IF EXISTS `dic_popedom`;
CREATE TABLE `dic_popedom`  (
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键, isAdd: false, isRequired: false, isEdit: true, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `POPEDOM_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `FORWARD_ACTION` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `XH` decimal(4, 0) DEFAULT 0 COMMENT '序号, isAdd: false, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `PARENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级ID, isAdd: true, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `REDIRECT` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '重定向, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `NAV_ICON` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标路径, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `TYPE` decimal(2, 0) DEFAULT 1 COMMENT '类型, 1: 菜单, 2: 权限url, 3: 其它, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `CREATER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `ENTRY_TIME` datetime(0) DEFAULT NULL COMMENT '录入时间, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `IS_LOG` decimal(2, 0) DEFAULT 2 COMMENT '是否审计, 1: 是, 2: 否, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由名称, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `COMPONENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件路径, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `HREF` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '超链接, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `IS_HIDE` decimal(2, 0) DEFAULT 2 COMMENT '是否隐藏, 1: 是, 2: 否, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  `IS_NOT_CACHE` decimal(2, 0) DEFAULT 2 COMMENT '是否不缓存, 1: 是, 2: 否, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: false, isPassword: false, ',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_popedom
-- ----------------------------
INSERT INTO `dic_popedom` VALUES ('09b7e6702f8f47de9f35e10ccf640273', '保存', '/admin/user/save', 901, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('0f1e444016a1443585715b9f693056fb', '更新', '/admin/role/update', 912, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('1495D777724243CE8E77EC3F6E107889', '系统管理', '/admin', 10, NULL, '0', NULL, '_xitong1', 1, NULL, '2019-03-27 11:14:33', 2, 'sys', 'view/main', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('15e88343bbe141e6aa145135569a8f1a', '更新', '/admin/user/update', 903, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('1cbf20c901b843d5b012cbf22747a6ad', '保存', '/admin/popedom/save', 916, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('211d4d1a09ff44b9874f0fec993eb741', '批量删除', '/admin/role/batchDelete', 905, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('24d74edc4e814c20aae8814f165e623e', '批量删除', '/admin/popedom/batchDelete', 913, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('260c549d07574676994c4176a17c6831', '角色权限列表', '/admin/role/popedomListByRoleId', 909, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('3AB76CED80D04114A2880A4F74FA1D6D', '角色管理', 'role', 20, NULL, '1495D777724243CE8E77EC3F6E107889', NULL, '_jiaoseguanli', 1, NULL, '2019-03-27 11:14:33', 2, 'role', 'view/sys/role', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('3f83ecb6cd2d4d17aa5f59e83d308997', '列表', '/admin/user/list', 898, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('4da5621ddc9f4a8c86277d1f863b70d3', '是否禁用', '/admin/user/isDisable', 897, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('5d3960cda950486e8656e5efaee97000', '删除', '/admin/popedom/delete', 914, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('5f36469613f149109c813382fe245216', '删除', '/admin/role/delete', 907, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('675156b69b7b49cc92f5bd7c2f3b9917', '更新', '/admin/popedom/update', 918, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('678ac3f5916a4b8ab83b0d7144df9902', '删除', '/admin/user/delete', 896, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('6D56F15C3BF841A581A2A94C0DEDCBA4', '系统审计', '/admin', 55, NULL, '0', NULL, 'ios-paper', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-27 11:14:33', 2, 'audit', 'view/main', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('7FF8F1E4E44441C4AAFEB9D5E98793F2', '多级菜单', '/multilevel', 60, NULL, '0', NULL, 'md-menu', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-27 11:14:33', 2, 'multilevel', 'view/main', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('848db323f1e94d1e8209d36e7631f6c4', '批量赋权', '/admin/role/batchSavePopedom', 906, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('957fa7ef2c3d4e41ba6f8fada8404467', '用户角色列表', '/admin/user/roleListByUserId', 900, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('AC69A4F00DF44E09B84298B3506B3F89', '权限管理', 'popedom', 30, NULL, '1495D777724243CE8E77EC3F6E107889', NULL, '_jiaoseziliao', 1, NULL, '2019-03-27 11:14:33', 2, 'popedom', 'view/sys/popedom', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('b153a6e3468747d3824567d1542d5e2e', '同步权限', '/admin/popedom/syncPopedom', 917, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('B6FFD470D4A54FFDAA1D0A12F94BF225', '系统监控', '/monitoring', 50, NULL, '0', NULL, '_huaban', 1, NULL, '2019-03-27 11:14:33', 2, 'monitoring', 'view/main', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('c1ccd7481c1247df9011bb26a60cb996', '修改密码', '/admin/user/updatePwd', 904, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('c23cc04af1834c9eaa2432b7a9967b26', '列表', '/admin/popedom/list', 915, NULL, 'AC69A4F00DF44E09B84298B3506B3F89', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('c5541f7567654b3fb216b8ea35c8fc6b', '保存权限', '/admin/role/savePopedom', 911, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('c9c1f71cca5941b39ef391229139c91d', '批量删除', '/admin/user/batchDelete', 894, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('ca367cb6896e41ec849e19a4a5b47fd9', '批量保存角色', '/admin/user/batchSaveRole', 895, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('DCB7C8211FF24AE1B2F4DB9D9EB74DD9', 'druid', 'druid', 60, NULL, 'B6FFD470D4A54FFDAA1D0A12F94BF225', NULL, '_shujukuguanlizu', 1, NULL, '2019-03-27 11:14:33', 2, 'druid', 'view/monitor/druid.vue', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('ddb99f19a06541f69c52ab12660f2680', '列表', '/admin/role/list', 908, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('e27fcd0bd3f946b28d605069795b0e70', '列表', '/admin/log/list', 919, NULL, 'FF9C8A3D93E548FCBB06C4525F93CEFE', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('E50E8C10E04645F2BE80D802D689A3A5', '三级', 'level_2_2_1', 893, NULL, 'F45520653CE24F59BE5D4B28F13FFB76', NULL, 'md-funnel', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-27 11:14:33', 2, 'level_2_2_1', 'view/multilevel/level-2-2/level-3-1.vue', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('e6b12691dee642e5ac03e75685fb8eb5', '重置密码', '/admin/user/resetPwd', 899, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('E8817E45455D4B3D83637907B76461B5', 'javaMelody', 'melody', 70, NULL, 'B6FFD470D4A54FFDAA1D0A12F94BF225', NULL, '_weiwangguanicon-defuben-', 1, NULL, '2019-03-27 11:14:33', 2, 'melody', 'view/monitor/javamelody.vue', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('F0B5E6DDAA4B428BAA6C86ACB38C1CF1', '用户管理', 'user', 10, NULL, '1495D777724243CE8E77EC3F6E107889', NULL, '_yonghu', 1, NULL, '2019-03-27 11:14:33', 2, 'user', 'view/sys/user', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('F45520653CE24F59BE5D4B28F13FFB76', '二级-2', 'level_2_1', 2, NULL, '7FF8F1E4E44441C4AAFEB9D5E98793F2', NULL, 'md-funnel', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-30 16:14:49', 2, 'level_2_2', 'components/parent-view', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('F59354424E0E4325A9CC89A11CC114C1', '二级-1', 'level_2_1', 1, NULL, '7FF8F1E4E44441C4AAFEB9D5E98793F2', NULL, 'md-funnel', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-27 11:14:33', 2, 'level_2_1', 'view/multilevel/level-2-1.vue', NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('f680e917c7d24955a01e5677d42731f2', '保存角色', '/admin/user/saveRole', 902, NULL, 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('fdda18c73bda4f779ce8baee4fed8ff3', '保存', '/admin/role/save', 910, NULL, '3AB76CED80D04114A2880A4F74FA1D6D', NULL, NULL, 2, '9DE5260188E2451F82FEED173E464A1B', '2019-04-13 21:37:16', 2, NULL, NULL, NULL, 2, 2);
INSERT INTO `dic_popedom` VALUES ('FF9C8A3D93E548FCBB06C4525F93CEFE', '系统日志', 'log', 0, NULL, '6D56F15C3BF841A581A2A94C0DEDCBA4', NULL, 'ios-list-box-outline', 1, '0837BE156B8A4DE8B24701F17115B47C', '2019-03-27 11:14:33', 2, 'log', 'view/audit/log.vue', NULL, 2, 2);

-- ----------------------------
-- Table structure for dic_role
-- ----------------------------
DROP TABLE IF EXISTS `dic_role`;
CREATE TABLE `dic_role`  (
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `ROLE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `XH` decimal(65, 30) DEFAULT NULL COMMENT '排序, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `REMARK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '注释, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `PARENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '父ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `CREATER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `TYPE` decimal(1, 0) DEFAULT 1 COMMENT '角色类型, 1: 角色, 2: 部门, 3:单位, 4:其它,isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `IS_PRIVATE` decimal(1, 0) DEFAULT 2 COMMENT '是否私有, 1: 私有, 2: 公开, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `ENTRY_TIME` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_role
-- ----------------------------
INSERT INTO `dic_role` VALUES ('29ce536c36bb4020be5c3cc0a37338e7', '商务部', NULL, NULL, '0', '0131EFE5A4A841CC80D6191E91D48553', 2, 2, '2019-03-30 17:07:32');
INSERT INTO `dic_role` VALUES ('44dc8fbca8954713b6a70b0ac3078470', '测试角色', NULL, NULL, '0', '0131EFE5A4A841CC80D6191E91D48553', 1, 2, '2019-03-30 17:01:45');
INSERT INTO `dic_role` VALUES ('b40d665874274e399985083485a42785', '系统管理员', NULL, NULL, '0', NULL, 1, 1, '2019-01-04 15:03:12');
INSERT INTO `dic_role` VALUES ('bd504f48219548eb95f7783a0b91a59a', '经理', NULL, NULL, '29ce536c36bb4020be5c3cc0a37338e7', '0131EFE5A4A841CC80D6191E91D48553', 1, 2, '2019-03-30 17:07:48');

-- ----------------------------
-- Table structure for dic_role_popedom
-- ----------------------------
DROP TABLE IF EXISTS `dic_role_popedom`;
CREATE TABLE `dic_role_popedom`  (
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `ROLE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外键关联role, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `POPEDOM_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外键关联权限, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `CREATER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT ', isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `ENTRY_TIME` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `POPEDOM_ID`(`POPEDOM_ID`) USING BTREE,
  INDEX `ROLE_ID`(`ROLE_ID`) USING BTREE,
  CONSTRAINT `dic_role_popedom_ibfk_1` FOREIGN KEY (`POPEDOM_ID`) REFERENCES `dic_popedom` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dic_role_popedom_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `dic_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_role_popedom
-- ----------------------------
INSERT INTO `dic_role_popedom` VALUES ('096cd1aca4db401baf4869326284eb01', 'b40d665874274e399985083485a42785', 'FF9C8A3D93E548FCBB06C4525F93CEFE', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('0aca7107ae9049a788e6c18c714b01a3', 'b40d665874274e399985083485a42785', '09b7e6702f8f47de9f35e10ccf640273', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('0d91dd710d0f4f70b94d7bcabe5e6107', 'b40d665874274e399985083485a42785', 'ddb99f19a06541f69c52ab12660f2680', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('11bcb16fbf17418fb515a0d412fb8045', 'b40d665874274e399985083485a42785', 'AC69A4F00DF44E09B84298B3506B3F89', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('12c95abdd2354000a4e17cce26fd9e61', 'b40d665874274e399985083485a42785', 'fdda18c73bda4f779ce8baee4fed8ff3', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('185ea6adc705468a9b92d8c530cdd6ce', '44dc8fbca8954713b6a70b0ac3078470', 'ca367cb6896e41ec849e19a4a5b47fd9', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('1871e84ef51148b78e819e176aa166ab', '44dc8fbca8954713b6a70b0ac3078470', 'e27fcd0bd3f946b28d605069795b0e70', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('187369f2a46d49159f9878c30edeba5d', 'b40d665874274e399985083485a42785', '5f36469613f149109c813382fe245216', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('18c0ab6ffb184cf0bd19b2cf97a0d5d4', '44dc8fbca8954713b6a70b0ac3078470', '7FF8F1E4E44441C4AAFEB9D5E98793F2', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('1fb228fd8c2648c798e9cdbd78e46a6f', 'b40d665874274e399985083485a42785', 'B6FFD470D4A54FFDAA1D0A12F94BF225', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('23d37ba4269b4f62aa643b636a2ba054', '44dc8fbca8954713b6a70b0ac3078470', '09b7e6702f8f47de9f35e10ccf640273', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('2d8b430a30134f00835a794aadf3f9b9', 'b40d665874274e399985083485a42785', 'f680e917c7d24955a01e5677d42731f2', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('30d505c86483426080256202a6984f31', 'b40d665874274e399985083485a42785', 'ca367cb6896e41ec849e19a4a5b47fd9', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('3d4eef66c27e40608438b30656895239', '44dc8fbca8954713b6a70b0ac3078470', 'E8817E45455D4B3D83637907B76461B5', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('3db7f8a8deb5497a9f1fdb03a769dc60', 'b40d665874274e399985083485a42785', '211d4d1a09ff44b9874f0fec993eb741', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('420d77428b7948b8a2a5feddcbbb27dc', '44dc8fbca8954713b6a70b0ac3078470', '15e88343bbe141e6aa145135569a8f1a', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('431d3a72f6544c038bd7c248942aebc4', '44dc8fbca8954713b6a70b0ac3078470', 'ddb99f19a06541f69c52ab12660f2680', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('4d74168dfd69412c81e226224b3390f0', 'b40d665874274e399985083485a42785', 'E8817E45455D4B3D83637907B76461B5', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('53042ae842d8480e8556897adea12755', '44dc8fbca8954713b6a70b0ac3078470', 'FF9C8A3D93E548FCBB06C4525F93CEFE', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('54f303aeb8c24cc4aa323c961ea39a5d', '44dc8fbca8954713b6a70b0ac3078470', '6D56F15C3BF841A581A2A94C0DEDCBA4', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('55743a957c8a4d658aef7855bae28828', '44dc8fbca8954713b6a70b0ac3078470', 'E50E8C10E04645F2BE80D802D689A3A5', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('5958818f976e460283ee2469259ead24', '44dc8fbca8954713b6a70b0ac3078470', '260c549d07574676994c4176a17c6831', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('597e6838338840c3ad0aa3c0a8e36421', '44dc8fbca8954713b6a70b0ac3078470', 'DCB7C8211FF24AE1B2F4DB9D9EB74DD9', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('626ebf7365a440f0ab833d74b011c6c4', 'b40d665874274e399985083485a42785', '3AB76CED80D04114A2880A4F74FA1D6D', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('6478698d6aa64450bbeb83ba55889ec6', '44dc8fbca8954713b6a70b0ac3078470', 'F45520653CE24F59BE5D4B28F13FFB76', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('64dfeb9b11674f7e98ae4756c7942f3c', 'b40d665874274e399985083485a42785', '4da5621ddc9f4a8c86277d1f863b70d3', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('65fa47c8a23d4f929df2a6f32aba751e', 'b40d665874274e399985083485a42785', '260c549d07574676994c4176a17c6831', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('672c5120fda94c4c8d549e84e8e9ef92', '44dc8fbca8954713b6a70b0ac3078470', 'B6FFD470D4A54FFDAA1D0A12F94BF225', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('6f7c0b2491a246cb9c83ed550af0b515', '44dc8fbca8954713b6a70b0ac3078470', '5f36469613f149109c813382fe245216', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('7317c6eda92d45e5bde98ba923ea02d5', 'b40d665874274e399985083485a42785', '0f1e444016a1443585715b9f693056fb', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('7eeffa9671ab4e07a826ce26781cb254', '44dc8fbca8954713b6a70b0ac3078470', '848db323f1e94d1e8209d36e7631f6c4', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('85255d6a6102404098e4151a37684249', '44dc8fbca8954713b6a70b0ac3078470', 'fdda18c73bda4f779ce8baee4fed8ff3', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('8685eb7a17dd4c6d87deffe20203e559', 'b40d665874274e399985083485a42785', '3f83ecb6cd2d4d17aa5f59e83d308997', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('98e1bec29ea142bd9ae5f6aab49fc0f8', '44dc8fbca8954713b6a70b0ac3078470', 'F59354424E0E4325A9CC89A11CC114C1', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('9dddf615212641c18d4223ece3a08367', '44dc8fbca8954713b6a70b0ac3078470', '957fa7ef2c3d4e41ba6f8fada8404467', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('a1eaf1d2ceb840b98898f72e4dfed116', 'b40d665874274e399985083485a42785', '1495D777724243CE8E77EC3F6E107889', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('a23ff48f188c42499ae0f76113ea1dfe', '44dc8fbca8954713b6a70b0ac3078470', '211d4d1a09ff44b9874f0fec993eb741', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('aa0284366df84712af96177b4e5e4bf3', 'b40d665874274e399985083485a42785', '848db323f1e94d1e8209d36e7631f6c4', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('afbbb549c066490fb7ce088310a12c7d', 'b40d665874274e399985083485a42785', 'c5541f7567654b3fb216b8ea35c8fc6b', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('b010ac83aae64c5a89b0252cd88ccae2', '44dc8fbca8954713b6a70b0ac3078470', '1495D777724243CE8E77EC3F6E107889', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('b2e9ef2f219349eaa556b953dece13c6', 'b40d665874274e399985083485a42785', 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('b7f15736c1c945d38700d2c6a502cf97', '44dc8fbca8954713b6a70b0ac3078470', 'AC69A4F00DF44E09B84298B3506B3F89', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('c28126483b8f4195a19c13624a5981a1', '44dc8fbca8954713b6a70b0ac3078470', 'f680e917c7d24955a01e5677d42731f2', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('c72c88eb85bf41fdb5bf26210b84fdca', 'b40d665874274e399985083485a42785', 'e27fcd0bd3f946b28d605069795b0e70', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('d118754e95f949a997b016b5ba0405a4', 'b40d665874274e399985083485a42785', '6D56F15C3BF841A581A2A94C0DEDCBA4', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('d3f27fc3c9754c88a3241e2ef37a061f', 'b40d665874274e399985083485a42785', 'DCB7C8211FF24AE1B2F4DB9D9EB74DD9', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('d45dd93a73bf4cc3bb20d66820fe265f', '44dc8fbca8954713b6a70b0ac3078470', 'c23cc04af1834c9eaa2432b7a9967b26', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('da27dfae4f8049ce80301953bccedb73', '44dc8fbca8954713b6a70b0ac3078470', '4da5621ddc9f4a8c86277d1f863b70d3', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('df08aa278ebd447c800b802037b25e57', '44dc8fbca8954713b6a70b0ac3078470', 'c9c1f71cca5941b39ef391229139c91d', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('e3a36671513042ddb581a03eefd20ff1', '44dc8fbca8954713b6a70b0ac3078470', '0f1e444016a1443585715b9f693056fb', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('e6ecfdf800b14e3d976e6b44fefbf099', '44dc8fbca8954713b6a70b0ac3078470', 'F0B5E6DDAA4B428BAA6C86ACB38C1CF1', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('e798d6f4ada2465e84ee9fcb4641346a', '44dc8fbca8954713b6a70b0ac3078470', 'e6b12691dee642e5ac03e75685fb8eb5', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('e9e4b13af59a41e68f63d37353babe24', '44dc8fbca8954713b6a70b0ac3078470', '3f83ecb6cd2d4d17aa5f59e83d308997', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('eaa6c88cead24a7d916d9ceaf278c3a0', 'b40d665874274e399985083485a42785', '15e88343bbe141e6aa145135569a8f1a', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('eede7a03632a49d0bd3d271725ea9cc9', '44dc8fbca8954713b6a70b0ac3078470', '3AB76CED80D04114A2880A4F74FA1D6D', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('ef933ddacb48476e938692e07ad65dcd', 'b40d665874274e399985083485a42785', 'c9c1f71cca5941b39ef391229139c91d', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('f0f1c6609901430685670264d38d1fb0', '44dc8fbca8954713b6a70b0ac3078470', '678ac3f5916a4b8ab83b0d7144df9902', NULL, '2019-03-30 17:02:09');
INSERT INTO `dic_role_popedom` VALUES ('f462b8af1afc413aa8d6ebd38fdbcf04', 'b40d665874274e399985083485a42785', '957fa7ef2c3d4e41ba6f8fada8404467', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('f8c428ed2a5049c09f49e8dd8d29d56e', 'b40d665874274e399985083485a42785', 'c23cc04af1834c9eaa2432b7a9967b26', NULL, '2019-04-13 22:13:13');
INSERT INTO `dic_role_popedom` VALUES ('f95410c877c5450f91e571ebc0db901f', 'b40d665874274e399985083485a42785', 'e6b12691dee642e5ac03e75685fb8eb5', NULL, '2019-04-13 22:13:12');
INSERT INTO `dic_role_popedom` VALUES ('fd235ad11b304ee1a61f75813322afcf', '44dc8fbca8954713b6a70b0ac3078470', 'c5541f7567654b3fb216b8ea35c8fc6b', NULL, '2019-03-30 17:02:10');
INSERT INTO `dic_role_popedom` VALUES ('ff7b48e59b3249e685fb236707528e84', 'b40d665874274e399985083485a42785', '678ac3f5916a4b8ab83b0d7144df9902', NULL, '2019-04-13 22:13:12');

-- ----------------------------
-- Table structure for dic_user
-- ----------------------------
DROP TABLE IF EXISTS `dic_user`;
CREATE TABLE `dic_user`  (
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `USER_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录名称, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, verify: userCode, ',
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '显示名, isAdd: true, isRequired: true, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `PASSWD` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码, isAdd: true, isRequired: true, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: true, verify: pass, ',
  `UNIT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `SALT` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: false, isSort: false, isPassword: false, ',
  `CREATER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: true, isPassword: false, ',
  `PARENT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父ID, isAdd: false, isRequired: false, isEdit: false, isShow: false, isQuery: false, isSort: false, isPassword: false, ',
  `TYPE` decimal(2, 0) DEFAULT 1 COMMENT '用户类型, 1: 普通用户, 2: 管理员, isAdd: true, isRequired: false, isEdit: true, isShow: true, isQuery: true, isSort: true, isPassword: false, ',
  `IS_DISABLED` decimal(1, 0) NOT NULL DEFAULT 2 COMMENT '是否禁用, 1: 是, 2: 否, isAdd: false, isRequired: false, isEdit: false, isShow: true, isQuery: false, isSort: true, isPassword: false, ',
  `ENTRY_TIME` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_user
-- ----------------------------
INSERT INTO `dic_user` VALUES ('0131EFE5A4A841CC80D6191E91D48553', 'admin', '管理员', 'b6fc218b8b781253a82c1318bb9f33cf85f97fa823dda6f3731f04d406b64211', NULL, 'vs_nvKGJK3z6UvNT7qorVnOWd7Dy5uS4', '使用者', NULL, NULL, 2, 2, '2019-04-13 22:10:23');
INSERT INTO `dic_user` VALUES ('9DE5260188E2451F82FEED173E464A1B', 'root', '超级管理员', '75b03d3bfb6ec4237a9a93a0f2912015e7d9d691448b15f6ed1e716cb8326c02', NULL, 'Q_JENH8rzU7xf9wqe-6z8wYdLPQMvc8u', '开发者使用', NULL, NULL, 2, 2, '2019-04-13 22:10:26');
INSERT INTO `dic_user` VALUES ('a2b2ee2067384f24a64348c852c28429', 'test', 'test', 'e75db847812aa91dc18d588c181b9f12f076cfa98b6163db14965d566b20795b', NULL, 'mvwyYOb7AP4qT_pomdYVg-f-FrPFd3IC', NULL, '0131EFE5A4A841CC80D6191E91D48553', NULL, 2, 2, '2019-04-13 22:10:25');

-- ----------------------------
-- Table structure for dic_user_role
-- ----------------------------
DROP TABLE IF EXISTS `dic_user_role`;
CREATE TABLE `dic_user_role`  (
  `ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `USER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色ID',
  `CREATER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `ENTRY_TIME` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `USER_ID`(`USER_ID`) USING BTREE,
  INDEX `ROLE_ID`(`ROLE_ID`) USING BTREE,
  CONSTRAINT `dic_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `dic_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dic_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `dic_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_user_role
-- ----------------------------
INSERT INTO `dic_user_role` VALUES ('e787c443b2264b209aa6a4361d0e2bac', '0131EFE5A4A841CC80D6191E91D48553', 'b40d665874274e399985083485a42785', NULL, '2019-03-30 16:32:57');

-- ----------------------------
-- Function structure for dic_popedom_child
-- ----------------------------
DROP FUNCTION IF EXISTS `dic_popedom_child`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `dic_popedom_child`(rootId VARCHAR(50)) RETURNS text CHARSET latin1
BEGIN 
       DECLARE sTemp text; 
       DECLARE sTempChd text;
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
     
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(id) INTO sTempChd FROM dic_popedom where FIND_IN_SET(PARENT_ID,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
END
;;
delimiter ;

-- ----------------------------
-- Function structure for dic_role_child
-- ----------------------------
DROP FUNCTION IF EXISTS `dic_role_child`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `dic_role_child`(rootId VARCHAR(50)) RETURNS text CHARSET latin1
BEGIN 
       DECLARE sTemp text; 
       DECLARE sTempChd text;
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
     
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(id) INTO sTempChd FROM dic_role where FIND_IN_SET(PARENT_ID,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
END
;;
delimiter ;

-- ----------------------------
-- Function structure for dic_role_childs
-- ----------------------------
DROP FUNCTION IF EXISTS `dic_role_childs`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `dic_role_childs`(rootIds text) RETURNS text CHARSET latin1
BEGIN 
       DECLARE sTemp text; 
       DECLARE sTempChd text;
			 DECLARE i int;
			 SET i = 1;
       SET sTemp = '$'; 

       WHILE SPLIT_STR(rootIds, ',', i) is not null DO 

				 SET sTemp =SPLIT_STR(rootIds, ',', i); 

         SET i = i+1;

				 WHILE sTempChd is not null DO 
					 SET sTemp = concat(sTemp,',',sTempChd); 
					 SELECT group_concat(id) INTO sTempChd FROM dic_role where FIND_IN_SET(PARENT_ID,sTempChd)>0; 
				 END WHILE; 

			END WHILE; 

       RETURN sTemp; 
END
;;
delimiter ;

-- ----------------------------
-- Function structure for SPLIT_STR
-- ----------------------------
DROP FUNCTION IF EXISTS `SPLIT_STR`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `SPLIT_STR`(x varchar(255)
,  delim VARCHAR(12),
  pos INT) RETURNS varchar(255) CHARSET utf8
RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '')
;;
delimiter ;

-- ----------------------------
-- Function structure for sys_guid
-- ----------------------------
DROP FUNCTION IF EXISTS `sys_guid`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `sys_guid`() RETURNS varchar(50) CHARSET utf8
BEGIN
	RETURN REPLACE(UUID(),"-","");
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table dic_popedom
-- ----------------------------
DROP TRIGGER IF EXISTS `popedom_xh`;
delimiter ;;
CREATE DEFINER = `root`@`%` TRIGGER `popedom_xh` BEFORE INSERT ON `dic_popedom` FOR EACH ROW BEGIN
select xh into @new_xh from dic_popedom order by  xh desc LIMIT 1 ;
set new.xh  = @new_xh+1 ;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
