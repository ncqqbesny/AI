-- --------------------------------------------------------
-- 主机:                           114.55.135.184
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

-- 导出  表 app_base.t_device_type 结构
CREATE TABLE IF NOT EXISTS `t_device_type` (
  `GID` varchar(36) NOT NULL COMMENT '主键',
  `DEVICETYPENAME` varchar(200) NOT NULL COMMENT '设备类型名称',
  `TYPECODE` varchar(50) NOT NULL COMMENT '分类编码',
  `PARENTGID` varchar(36) DEFAULT NULL COMMENT '父级分类Gid',
  `PARENTCODE` varchar(50) DEFAULT NULL COMMENT '父级分类Code',
  `MID` varchar(50) DEFAULT NULL COMMENT '项目代码',
  `DEVICECOUNT` int(10) DEFAULT '0' COMMENT '设备数',
  `TABLEPREFIX` varchar(50) DEFAULT NULL COMMENT '存储空间',
  `TYPEORDER` int(10) DEFAULT NULL COMMENT '排序',
  `ENABLEDSTATUS` int(2) DEFAULT NULL COMMENT '启用状态1、启用，0停用',
  `CODINGPREFIX` varchar(50) DEFAULT NULL COMMENT '编码前缀',
  `NODEATTR` int(10) DEFAULT NULL COMMENT '节点属性',
  `NODELEVEL` int(10) DEFAULT NULL COMMENT '节点层次',
  `ENABLEDTM` datetime DEFAULT NULL COMMENT '启用时间',
  `CLASSNUM` varchar(64) DEFAULT NULL COMMENT '分类号',
  `ISMAINDEVICE` int(2) DEFAULT NULL COMMENT '是否为主设备 1、是，0 否',
  `LAST_TIME` datetime DEFAULT NULL COMMENT '最后更新时间',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  UNIQUE KEY `UNI_T_SAC_DEVICETYPE_GID` (`GID`) USING BTREE,
  KEY `IND_T_SAC_DEVICETYPE_PID` (`PARENTGID`) USING BTREE,
  KEY `IND_T_SAC_DEVICETYPE_CODE` (`TYPECODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分类管理';

-- 正在导出表  app_base.t_device_type 的数据：~6 rows (大约)
DELETE FROM `t_device_type`;
/*!40000 ALTER TABLE `t_device_type` DISABLE KEYS */;
INSERT INTO `t_device_type` (`GID`, `DEVICETYPENAME`, `TYPECODE`, `PARENTGID`, `PARENTCODE`, `MID`, `DEVICECOUNT`, `TABLEPREFIX`, `TYPEORDER`, `ENABLEDSTATUS`, `CODINGPREFIX`, `NODEATTR`, `NODELEVEL`, `ENABLEDTM`, `CLASSNUM`, `ISMAINDEVICE`, `LAST_TIME`, `CREATE_TIME`) VALUES
	('02fdc6d2bc155c5470aa8d8a9ecad5d11', '网络设备', '0100', NULL, NULL, '0', 0, 'NETD', NULL, 1, NULL, NULL, NULL, '2023-12-28 13:21:29', NULL, 1, NULL, '2023-12-28 13:11:40'),
	('02fdc6d2bc155c5470aa8d8a9ecad5d12', '器具柜设备', '0104', NULL, NULL, '0', 0, 'QJG', NULL, 1, NULL, NULL, NULL, '2023-12-28 13:21:29', NULL, 1, NULL, '2023-12-28 13:11:40'),
	('02fdc6d2bc155c5470aa8d8a9ecad5d13', '应急仓设备', '0103', NULL, NULL, '0', 0, 'YJC', NULL, 1, NULL, NULL, NULL, '2023-12-28 13:21:29', NULL, 1, NULL, '2023-12-28 13:11:40'),
	('02fdc6d2bc155c5470aa8d8a9ecad5d15', '娃娃机', '0105', NULL, NULL, '0', 0, 'WWJ', NULL, 1, NULL, NULL, NULL, '2023-12-28 13:21:29', NULL, 1, NULL, '2023-12-28 13:11:40'),
	('02fdc6d2bc155c5470aa8d8a9ecad5db', '灭火器', '0101', NULL, NULL, '0', 0, 'MHQ', NULL, 1, NULL, NULL, NULL, '2023-09-22 13:21:29', NULL, 1, NULL, '2023-09-22 13:11:40'),
	('02fdc6d2bc155c5470aa8d8a9ecad5dt', '环测网关', '0102', NULL, NULL, '0', 0, 'SHX', NULL, 1, NULL, NULL, NULL, '2023-11-17 13:21:29', NULL, 1, NULL, '2023-11-17 13:11:40');
/*!40000 ALTER TABLE `t_device_type` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
