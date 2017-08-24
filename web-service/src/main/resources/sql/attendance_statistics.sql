drop TABLE IF EXISTS tbl_attendance_statistics;
create table tbl_attendance_statistics(
  id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
  class_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '班级ID',
  calssname VARCHAR(64) NOT NULL DEFAULT '' COMMENT '班级名称',
  teacher_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '教师名称',
  teacher_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '教师ID',
  agency_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '机构ID',
  agency_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '机构名称',
  normal_count INTEGER NOT NULL DEFAULT 0 COMMENT '考勤正常人数',
  be_late_count INTEGER NOT NULL DEFAULT 0 COMMENT '考勤迟到人数',
  leave_early_count INTEGER NOT NULL DEFAULT 0 COMMENT '考勤早退',
  absent_count INTEGER NOT NULL DEFAULT 0 COMMENT '缺勤人数',
  added_count INTEGER NOT NULL DEFAULT 0 COMMENT '补录人数',
  create_time DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',

  PRIMARY KEY id(id),
  key idx_class_id(class_id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤统计表';