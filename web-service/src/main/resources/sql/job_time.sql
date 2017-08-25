drop TABLE IF EXISTS tbl_job_time;
create table tbl_job_time(
  id INTEGER AUTO_INCREMENT NOT NULL COMMENT 'ID',
  job_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '标识哪个任务',
  time_value DATETIME NOT NULL DEFAULT '2970-01-01 08:00:00' COMMENT '时间记录值，比如上次统计时间',
  create_time DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  update_time DATETIME NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

  PRIMARY KEY id(id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务时间表';