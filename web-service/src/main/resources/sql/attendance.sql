DROP TABLE IF EXISTS tbl_attendance;
create table tbl_attendance(
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  attendance_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '考勤编码',
  course_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '课程编码',
  class_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '班级编号',
  device_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '设备ID',
  user_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '设备识别得出的ID',
  username VARCHAR(64) NOT NULL DEFAULT '' COMMENT '学生名称,少查询一张表,冗余该字段',
  remark VARCHAR(32) NOT NULL DEFAULT '' COMMENT '备注',
  attendance_time VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '当天内所有的打卡时间,存放为时间戳加逗号',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-正常,1-请假,2-迟到,3-早退,4-补打卡',
  create_time DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',

  PRIMARY KEY id(id),
  UNIQUE key uniq_attendance_id(attendance_id),
  key idx_class_id(class_id),
  key idx_course_id(course_id)
) ENGINE = innodb DEFAULT CHARSET =utf8mb4 COMMENT '考勤表';