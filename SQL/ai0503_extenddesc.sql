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

-- 导出  表 app_base.t_extenddesc 结构
CREATE TABLE IF NOT EXISTS `t_extenddesc` (
  `GID` varchar(36) NOT NULL COMMENT '主键',
  `MID` varchar(50) NOT NULL COMMENT '项目ID',
  `DEVICETYPEGID` varchar(36) NOT NULL COMMENT '设备分类id',
  `DEVICETYPECODE` varchar(50) DEFAULT NULL COMMENT '设备分类编码',
  `EXTDATAMODULE` varchar(50) DEFAULT NULL COMMENT '业务数据标识',
  `DETAILLEVEL` int(10) DEFAULT NULL COMMENT '明细层级',
  `DESCVER` varchar(50) NOT NULL COMMENT '描述模版版本',
  `CATALOGCODE` varchar(36) DEFAULT NULL COMMENT '设备分类目录Code',
  `DESCCODE` varchar(255) DEFAULT NULL COMMENT '描述模版编码Code',
  `COLLECTMODE` int(10) DEFAULT NULL COMMENT '采集方式',
  `FIELDLENGTH` int(10) DEFAULT NULL COMMENT '字段长度',
  `EXTFIELDCODE` varchar(50) NOT NULL COMMENT '扩展字段名称',
  `FIELDNAME` varchar(50) NOT NULL COMMENT '数据字段显示名称',
  `FIELDCODE` varchar(50) NOT NULL COMMENT '数据字段名称',
  `FIELDTYPE` varchar(10) NOT NULL COMMENT '字段类型',
  `ISUNIQUE` int(10) DEFAULT '0' COMMENT '是否唯一',
  `ISMUST` int(10) DEFAULT '0' COMMENT '是否必录入',
  `ISSEARCH` int(10) DEFAULT '0' COMMENT '是否为查询条件',
  `ISRESULT` varchar(50) DEFAULT NULL COMMENT '是否为查询结果',
  `SEARCHORDER` int(10) DEFAULT NULL COMMENT '查询条件控件排序',
  `RESULTORDER` int(10) DEFAULT NULL COMMENT '查询结果排序',
  `ISRANGETIME` int(10) DEFAULT NULL COMMENT '是否是区间时间',
  `ISDELETED` int(10) DEFAULT NULL COMMENT '删除标识',
  `GRIDWIDTH` int(10) DEFAULT NULL COMMENT '表格列宽度',
  `NOTE` varchar(300) DEFAULT NULL COMMENT '备注',
  `FIELDDSPLYRULE` varchar(4000) DEFAULT NULL COMMENT '字段显示规则',
  `LAST_TIME` datetime DEFAULT NULL COMMENT '最后变更时间',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `GBCODE` varchar(64) DEFAULT NULL COMMENT '国标编码',
  UNIQUE KEY `UNI_t_extenddesc_GID` (`GID`) USING BTREE,
  KEY `IND_TEXTENDDESC_COMPID` (`MID`) USING BTREE,
  KEY `IND_TEXTENDDESC_TYPECODE` (`DEVICETYPECODE`) USING BTREE,
  KEY `IND_TEXTENDDESC_EXTMODULE` (`EXTDATAMODULE`) USING BTREE,
  KEY `IND_TEXTENDDESC_CATACODE` (`CATALOGCODE`) USING BTREE,
  KEY `IND_TEXTENDDESC_DESCCODE` (`DESCCODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板扩展字段';

-- 正在导出表  app_base.t_extenddesc 的数据：~38 rows (大约)
DELETE FROM `t_extenddesc`;
/*!40000 ALTER TABLE `t_extenddesc` DISABLE KEYS */;
INSERT INTO `t_extenddesc` (`GID`, `MID`, `DEVICETYPEGID`, `DEVICETYPECODE`, `EXTDATAMODULE`, `DETAILLEVEL`, `DESCVER`, `CATALOGCODE`, `DESCCODE`, `COLLECTMODE`, `FIELDLENGTH`, `EXTFIELDCODE`, `FIELDNAME`, `FIELDCODE`, `FIELDTYPE`, `ISUNIQUE`, `ISMUST`, `ISSEARCH`, `ISRESULT`, `SEARCHORDER`, `RESULTORDER`, `ISRANGETIME`, `ISDELETED`, `GRIDWIDTH`, `NOTE`, `FIELDDSPLYRULE`, `LAST_TIME`, `CREATE_TIME`, `GBCODE`) VALUES
	('02fdc6d2bc155c5470aa8d8a9ecac0ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 255, 'ext21', '通讯心跳', 'keepalive', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '默认 60 分钟，单位：分钟', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad0ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 255, 'ext55', 'img 表头图片', 'img', '1', 0, 0, 1, '1', 0, 0, 0, 0, 0, '64 byte image 长text字段', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1s1', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext10', '设备id', 'device_id', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备id信息', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1s5', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext13', '型号', 'model', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '型号', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1s6', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext41', '二维码地址', 'url', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '二维码地址', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1s7', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext11', '站号', 'station_no', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '站号', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 50, 'ext19', '模块电量', 'power', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '量程范围：0～100%；测量精度：3%', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad1xs', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 50, 'ext20', '设备编码', 'serialNo', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备编码，唯一', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad2s7', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext20', '控制版本号', 'op_hw', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '二维码地址', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad2s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext17', '经度', 'longitude', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '经度', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad2ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 50, 'ext18', '倾斜角度', 'angle', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '量程范围：-180～180°；测量精度：±5°', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad3s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext18', '纬度', 'latitude', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '纬度', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad3ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 50, 'ext17', '环境湿度', 'humidity', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '测量范围：0～100%RH；测量精度：±', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad4s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext14', '箱长mm', 'length', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '箱长mm', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad4ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 50, 'ext16', '环境温度', 'temperature', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '测量范围：-30～80℃；测量精度：±0.5℃', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad5s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext15', '箱宽mm', 'width', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '箱宽mm', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad5ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 200, 'ext15', '图片路径', 'pic_url', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '图片规格：不小于 640×480 dpi；图片清', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad6s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext16', '箱高mm', 'hight', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '箱高mm', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad6ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 200, 'ext14', '文件名', 'file_name', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad7s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext19', '自重kg', 'self_weight', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '自重kg', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad7ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 10, 'ext11', '执行结果', 'exe_result', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, NULL, NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad8s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d13', '0103', 'base', 0, '2024', '0103-001', '2024|base|0103|0103-001', 0, 50, 'ext21', '承重kg', 'support_weight', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '承重kg', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad8ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 10, 'ext12', '灭火器颜色', 'color', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '1(2,3)(红,绿,黄)', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9a1', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d15', '0105', 'base', 0, '2024', '0105-001', '2023|base|0105|0105-001', 0, 50, 'ext11', '质量', 'csq', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备编码，唯一', NULL, NULL, '2023-12-14 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9a2', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d15', '0105', 'base', 0, '2024', '0105-001', '2023|base|0105|0105-001', 0, 50, 'ext12', '版本', 'fver', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备编码，唯一', NULL, NULL, '2023-12-14 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9a3', '0', '02fdc6d2bc155c5470aa8d8a9ecad5d15', '0105', 'base', 0, '2024', '0105-001', '2023|base|0105|0105-001', 0, 50, 'ext13', '端口', 'port', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备编码，唯一', NULL, NULL, '2023-12-14 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s10', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext01', '烟感', 'smoke_status', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 正常，1 异常', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s15', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext15', '气压', 'pressure', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '单位为 Pa', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s2', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext11', '水浸电量', 'water_battery', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 正常，1 异常', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s3', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext12', '温度', 'temperature', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '单位为°C', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s4', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext13', '噪声', 'noise', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '单位为 dB', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s5', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext14', '湿度', 'humidity', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '单位为%', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s6', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext02', '水浸', 'water_status', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 正常，1 异常', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s7', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext03', '震动', 'vibrate', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 无震动，1 有震动', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s8', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext04', '人员', 'people', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 无人，1 有人', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9s9', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext05', '光照', 'light', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '0 无光照，1 有光照', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9ss', '0', '02fdc6d2bc155c5470aa8d8a9ecad5db', '0101', 'base', 0, '2023', '0101-001', '2023|base|0101|0101-001', 0, 10, 'ext13', '灭火器指针比例', 'ratio', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '单位%', NULL, NULL, '2023-10-07 14:58:13', NULL),
	('02fdc6d2bc155c5470aa8d8a9ecad9x6', '0', '02fdc6d2bc155c5470aa8d8a9ecad5dt', '0102', 'base', 0, '2023', '0102-001', '2023|base|0102|0102-001', 0, 50, 'ext16', '设备编码', 'serialNo', '0', 0, 0, 1, '1', 0, 0, 0, 0, 0, '设备编码，唯一', NULL, NULL, '2023-12-14 14:58:13', NULL);
/*!40000 ALTER TABLE `t_extenddesc` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
