DROP TABLE IF EXISTS tbl_class;
create table tbl_class(
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  course_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '课程编码',
  class_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '班级编号',
  classname VARCHAR(64) NOT NULL DEFAULT '' COMMENT '班级名称',
  affiliated_profession VARCHAR(64) NOT NULL DEFAULT '' COMMENT '所属专业',
  predicted_count INTEGER NOT NULL DEFAULT 0 COMMENT '预计开班人数',
  proposer VARCHAR(64) NOT NULL DEFAULT '' COMMENT '申请人',
  proposer_mobile VARCHAR(32) NOT NULL DEFAULT '' COMMENT '申请人联系方式',
  teach_plan_file_path VARCHAR(255) NOT NULL DEFAULT '' COMMENT '教学计划文件存放地址',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-正常，1-已删除',
  start_time DATETIME NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '开始时间',
  end_time DATETIME NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '结束时间',
  on_class_time TIME NOT NULL DEFAULT '00:00:00' COMMENT '课程上课时间',
  off_class_time TIME NOT NULL DEFAULT '00:00:00' COMMENT '课程下课时间',
  create_time DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  update_time DATETIME NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,

  PRIMARY KEY id(id),
  UNIQUE key uniq_course_id(course_id),
  key idx_class_id(class_id)
) ENGINE = innodb DEFAULT CHARSET =utf8mb4 COMMENT '班级表';