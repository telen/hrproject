DROP TABLE IF EXISTS tbl_class_member;
CREATE TABLE `tbl_class_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `class_id` varchar(64) NOT NULL DEFAULT '' COMMENT '班级ID',
  `student_id` varchar(64) NOT NULL DEFAULT '' COMMENT '学生编号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '学生在该班级状态 0-正常，1-异常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='班级学生表'