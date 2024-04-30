-- --------------------------------------------------------
-- 主机:                           121.40.242.143
-- 服务器版本:                        5.7.29 - MySQL Community Server (GPL)
-- 服务器操作系统:                      linux-glibc2.12
-- HeidiSQL 版本:                  11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 app_base.menu 结构
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '菜单名字',
  `parent_menu_id` int(11) DEFAULT NULL COMMENT '上级菜单ID',
  `type` char(1) DEFAULT NULL COMMENT '菜单类型（0一级菜单，1二级菜单2三级菜单，3按钮）',
  `static_router` varchar(50) DEFAULT NULL COMMENT '路径名',
  `skip_url` varchar(125) DEFAULT NULL COMMENT '跳转url',
  `icon` varchar(125) DEFAULT NULL COMMENT '图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `is_out_url` char(1) DEFAULT '1' COMMENT '是否是外部地址0是，1否',
  `status` varchar(50) DEFAULT NULL COMMENT '菜单状态 1正常，0下架',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  app_base.menu 的数据：~28 rows (大约)
DELETE FROM `menu`;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`menu_id`, `menu_name`, `parent_menu_id`, `type`, `static_router`, `skip_url`, `icon`, `order_num`, `is_out_url`, `status`, `remark`, `create_time`, `update_time`) VALUES
	(1, '应急处理', 0, '0', '', '', '', 0, '', '1', '一级菜单', NULL, NULL),
	(2, '硬件管理', 0, '0', '', '', 'Finished', 6, '0', '1', '一级菜单', NULL, NULL),
	(3, '项目管理', 0, '1', '/ProjectManage', '/projectManage', 'Files', 12, '0', '1', '一级菜单', NULL, NULL),
	(4, '人员管理', 0, '1', '/UserManage', '/userManage', 'Avatar', 17, '0', '1', '一级菜单', NULL, NULL),
	(5, '个人信息', 4, '1', '/SelfInformation', '/selfInformation', 'UserFilled', 23, '0', '1', '二级菜单', NULL, NULL),
	(6, '修改密码', 4, '1', '/AccountSecurity', '/accountSecurity', 'Lock', 24, '0', '1', '二级菜单', NULL, NULL),
	(7, '权限管理', 4, '1', '/Role', '/Role', 'Avatar', 25, '0', '1', '二级菜单', NULL, NULL),
	(8, '应急处理-查询', 1, '3', '', '', '', 2, '', '1', '按钮', NULL, NULL),
	(9, '应急处理-申请', 1, '3', '', '', '', 3, '', '1', '按钮', NULL, NULL),
	(10, '应急处理-审核', 1, '3', '', '', '', 4, '', '1', '按钮', NULL, NULL),
	(11, '应急处理-处理', 1, '3', '', '', '', 5, '', '1', '按钮', NULL, NULL),
	(12, '硬件管理-查询', 2, '3', '', '', '', 7, '0', '1', '按钮', NULL, NULL),
	(13, '硬件管理-添加', 2, '3', '', '', '', 8, '0', '1', '按钮', NULL, NULL),
	(14, '硬件管理-编辑', 2, '3', '', '', '', 9, '0', '1', '按钮', NULL, NULL),
	(15, '硬件管理-删除', 2, '3', '', '', '', 10, '0', '1', '按钮', NULL, NULL),
	(16, '硬件管理-操作', 2, '3', '', '', '', 11, '0', '1', '按钮', NULL, NULL),
	(17, '项目管理-添加', 3, '3', '/ProjectManage', '/projectManage', 'Files', 14, '0', '1', '按钮', NULL, NULL),
	(18, '项目管理-编辑', 3, '3', '/ProjectManage', '/projectManage', 'Files', 14, '0', '1', '按钮', NULL, NULL),
	(19, '项目管理-删除', 3, '3', '/ProjectManage', '/projectManage', 'Files', 15, '0', '1', '按钮', NULL, NULL),
	(20, '项目管理-查询', 3, '3', '/ProjectManage', '/projectManage', 'Files', 16, '0', '1', '按钮', NULL, NULL),
	(21, '人员管理-查询', 4, '3', '/UserManage', '/UserManage', 'Files', 18, '0', '1', '按钮', NULL, NULL),
	(22, '人员管理-添加', 4, '3', '/UserManage', '/UserManage', 'Files', 19, '0', '1', '按钮', NULL, NULL),
	(23, '人员管理-编辑', 4, '3', '/UserManage', '/UserManage', 'Files', 19, '0', '1', '按钮', NULL, NULL),
	(24, '人员管理-删除', 4, '3', '/UserManage', '/UserManage', 'Files', 21, '0', '1', '按钮', NULL, NULL),
	(26, '角色管理-添加', 7, '3', '/Role', '/Role', 'Files', 27, '0', '1', '按钮', NULL, NULL),
	(27, '角色管理-编辑', 7, '3', '/Role', '/Role', 'Files', 27, '0', '1', '按钮', NULL, NULL),
	(29, '角色管理-删除', 7, '3', '/Role', '/Role', 'Files', 29, '0', '1', '按钮', NULL, NULL);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
