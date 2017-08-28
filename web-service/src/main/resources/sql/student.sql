DROP TABLE IF EXISTS tbl_student;
CREATE TABLE `tbl_student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` varchar(64) NOT NULL DEFAULT '' COMMENT '课程ID',
  `agency_id`  varchar(64) NOT NULL DEFAULT '' COMMENT '机构ID',
  `agency_name` varchar(64) NOT NULL DEFAULT '' COMMENT '所属机构',
  `id_number` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号',
  `student_name` varchar(16) NOT NULL DEFAULT '' COMMENT '学生姓名',
  `student_id` varchar(64) NOT NULL DEFAULT '' COMMENT '学生编号',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-男,1-女',
  `nationality` varchar(16) NOT NULL DEFAULT '' COMMENT '民族',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `physical_condition` varchar(16) NOT NULL DEFAULT '' COMMENT '身体状况',
  `marital_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '婚姻状态 0-未婚,1-已婚,2-离异',
  `political_outlook` varchar(16) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `insured_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '参保状态 0-参保,1-未参保',
  `mobile` varchar(16) NOT NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `work_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '失业状态',
  `fingerprint` varchar(1024) NOT NULL DEFAULT '' COMMENT '存储指纹信息，暂定哈希值',
  `education` varchar(32) NOT NULL DEFAULT '文化水平',
  `profession` varchar(32) NOT NULL DEFAULT '专业',
  `graduation_school` varchar(64) NOT NULL DEFAULT '' COMMENT '毕业学校',
  `registered_residence` varchar(32) NOT NULL DEFAULT '' COMMENT '户口所在地',
  `registered_property` varchar(32) NOT NULL DEFAULT '' COMMENT '户口性质',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0-正常,1-已删除',
  `bank` varchar(64) NOT NULL DEFAULT '' COMMENT '开户行',
  `bank_account` varchar(64) NOT NULL DEFAULT '' COMMENT '银行账户',
  `bank_account_name` varchar(64) NOT NULL DEFAULT '' COMMENT '银行户名',
  `school_date` datetime DEFAULT NULL COMMENT '入学日期',
  `student_cat` varchar(32) NOT NULL DEFAULT '' COMMENT '人员类别',
  `tuition` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '缴纳学费',
  `subsidy_code` varchar(32) NOT NULL DEFAULT '' COMMENT '补贴标准',
  `subsidy_target` varchar(32) NOT NULL DEFAULT '' COMMENT '支付对象',
  `is_dropout` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-否，1-是',
  `dropout` datetime DEFAULT NULL COMMENT '退学日期',
  `has_certificate` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-无，1-有',
  `certificate_level` varchar(32) NOT NULL DEFAULT '' COMMENT '培训证书等级',
  `certificate` varchar(32) NOT NULL DEFAULT '' COMMENT '证书名称',
  `is_using_coupon` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否使用培训券 0-否,1-是',
  `coupon_num` varchar(64) NOT NULL DEFAULT '' COMMENT '培训券码',
  `is_compact` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否企业录用员工,0-否，1-是',
  `compact` varchar(32) NOT NULL DEFAULT '' COMMENT '合同期限,存储为年',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_student_id` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='考勤表';


DROP TABLE IF EXISTS tb_exam_score;

CREATE TABLE `tb_exam_score` (
  `id`             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `student_id`     VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '学生Id',
  `class_id`       VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '班级Id',
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
  UNIQUE KEY `uniq_student_id_class_id` (`student_id`, `class_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '考试成绩记录表';

