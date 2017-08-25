DROP TABLE IF EXISTS tbl_class;
CREATE TABLE `tbl_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` varchar(64) NOT NULL DEFAULT '' COMMENT '课程编码',
  `class_id` varchar(64) NOT NULL DEFAULT '' COMMENT '班级编号',
  `classname` varchar(64) NOT NULL DEFAULT '' COMMENT '班级名称',
  `affiliated_profession` varchar(64) NOT NULL DEFAULT '' COMMENT '所属专业',
  `predicted_count` int(11) NOT NULL DEFAULT '0' COMMENT '预计开班人数',
  `proposer` varchar(64) NOT NULL DEFAULT '' COMMENT '申请人',
  `proposer_mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '申请人联系方式',
  `teach_plan_file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '教学计划文件存放地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0-正常，1-已删除',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '结束时间',
  `on_class_time` time NOT NULL DEFAULT '00:00:00' COMMENT '课程上课时间',
  `off_class_time` time NOT NULL DEFAULT '00:00:00' COMMENT '课程下课时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `inspection_count` int(11) NOT NULL DEFAULT '0' COMMENT '抽查次数',
  `inspection_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-未抽查，1-通过，2-未通过',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_class_id` (`class_id`),
  KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表'