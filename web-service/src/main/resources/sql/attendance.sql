DROP TABLE IF EXISTS tbl_attendance;
CREATE TABLE `tbl_attendance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `attendance_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考勤编码',
  `course_id` varchar(64) NOT NULL DEFAULT '' COMMENT '课程编码',
  `class_id` varchar(64) NOT NULL DEFAULT '' COMMENT '班级编号',
  `device_id` varchar(64) NOT NULL DEFAULT '' COMMENT '设备ID',
  `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '设备识别得出的ID',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '学生名称,少查询一张表,冗余该字段',
  `remark` varchar(32) NOT NULL DEFAULT '' COMMENT '备注',
  `attendance_time` varchar(1024) NOT NULL DEFAULT '' COMMENT '当天内所有的打卡时间,存放为时间戳加逗号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0-正常,1-请假,2-迟到,3-早退,4-补打卡',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `agency_id` varchar(64) NOT NULL DEFAULT '' COMMENT '机构ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_attendance_id` (`attendance_id`),
  KEY `idx_class_id` (`class_id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='考勤表';