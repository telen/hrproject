DROP TABLE IF EXISTS tb_ledger;

CREATE TABLE `tb_ledger` (
  `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `ledger_id`        VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '台账Id',
  `agency_id`        VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '机构Id',
  `class_id`         VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '班级Id',
  `graduate_numbers` INTEGER             NOT NULL DEFAULT 0
  COMMENT '毕业人数',
  `attendance_rate`  DECIMAL             NOT NULL DEFAULT 0.0
  COMMENT '出勤率',
  `audit_status`     TINYINT             NOT NULL DEFAULT 0
  COMMENT '审核状态，0=初始化，1=等待审核，2=审核通过，3=审核驳回',
  `key_word`         VARCHAR(128)        NOT NULL DEFAULT ''
  COMMENT '搜索关键词，格式定义：培训机构名,培训课程,培训班级',
  `create_time`      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`      TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_ledger_id` (`ledger_id`),
  KEY idx_agency_id (`agency_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '台账记录表';


DROP TABLE IF EXISTS tb_ledger_graduate_snapshot;

CREATE TABLE `tb_ledger_graduate_snapshot` (
  `id`             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `ledger_id`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '台账Id',
  `student_id`     VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '学生Id',
  `student_name`   VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '学生名称',
  `id_number`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '学生身份证号',
  `student_cat`    VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '人员类型',
  `absent_count`   TINYINT             NOT NULL DEFAULT 0
  COMMENT '缺勤天数',
  `insured_status` TINYINT             NOT NULL DEFAULT 0
  COMMENT '是否参保，0=参保，1=未参保',
  `theory_score`   DECIMAL             NOT NULL DEFAULT 0.0
  COMMENT '理论成绩',
  `practice_score` DECIMAL             NOT NULL DEFAULT 0.0
  COMMENT '实践成绩',
  `certificate`    VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '结业证书',
  `certificate_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '结业证书Id',
  `create_time`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_ledger_id` (`ledger_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '毕业生台账快照';