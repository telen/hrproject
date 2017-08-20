DROP TABLE IF EXISTS tbl_course;
create table tbl_course(
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  course_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '课程编码',
  course_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '课程名称',
  profession VARCHAR(64) NOT NULL DEFAULT '' COMMENT '所属专业',
  period INTEGER NOT NULL DEFAULT 0 COMMENT '学时',
  teaching_material VARCHAR(64) NOT NULL DEFAULT '' COMMENT '所用教材',
  agency_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '所属机构ID',
  profession_brief VARCHAR(64) NOT NULL DEFAULT '' COMMENT '专业描述',
  course_brief VARCHAR(255) NOT NULL DEFAULT '' COMMENT '课程简介',
  industry_category VARCHAR(64) NOT NULL DEFAULT '' COMMENT '行业类别',
  training_form VARCHAR(64) NOT NULL DEFAULT '' COMMENT '培训形式',
  training_jobs VARCHAR(64) NOT NULL DEFAULT '' COMMENT '培训工种',
  teacher_id VARCHAR(64) NOT NULL DEFAULT '' COMMENT '教师ID',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-正常，1-已删除',
  create_time DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  update_time DATETIME NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,

  PRIMARY KEY id(id),
  UNIQUE key uniq_course_id(course_id),
  key idx_agency_id(agency_id)
) ENGINE = innodb DEFAULT CHARSET =utf8mb4 COMMENT '课程表';