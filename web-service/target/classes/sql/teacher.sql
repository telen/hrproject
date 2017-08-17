
DROP TABLE IF EXISTS tbl_teacher;
CREATE TABLE `tbl_teacher` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teacher_id` varchar(64) NOT NULL DEFAULT '' COMMENT '教师编号',
  agency_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '所属机构ID',
  `id_number` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `name` varchar(8) NOT NULL DEFAULT '' COMMENT '教师姓名',
  `gender` TINYINT NOT NULL DEFAULT 0 COMMENT '性别 0-男，1-女',
  `nationality` varchar(16) NOT NULL DEFAULT '' COMMENT '民族',
  `birthday` DATETIME NOT NULL DEFAULT '1971-01-01 08:00:00' COMMENT '生日',
  address VARCHAR(255) NOT NULL DEFAULT '' COMMENT '地址',
  mobile  VARCHAR(32) NOT NULL DEFAULT '' COMMENT '电话',
  education VARCHAR(16) NOT NULL DEFAULT '' COMMENT '学历',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '0-正常,1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_teacher_id` (`teacher_id`),
  key idx_agency_id(agency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';
