DROP TABLE if EXISTS tb_account;
CREATE TABLE tb_account (
  `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `user_id`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户Id',
  `account`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '登录账号',
  `password`     VARCHAR(128)         NOT NULL DEFAULT ''
  COMMENT '登录密码(密文)',
  `user_name`    VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户姓名',
  `mobile`       VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户手机号',
  `email`        VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户邮箱',
  `login_ip`     VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '最近登录Ip',
  `login_time`   TIMESTAMP           NOT NULL DEFAULT current_timestamp
  COMMENT '最近登录时间',
  `enable`       TINYINT(11)         NOT NULL DEFAULT 1
  COMMENT '账号是否可用，0=不可用， 1=可用',
  `locked`       TINYINT(11)         NOT NULL DEFAULT 0
  COMMENT '账号是否锁定，0=未锁定，1=锁定',
  `expired_time` TIMESTAMP           NOT NULL DEFAULT '2030-01-01 00:00:00'
  COMMENT '过期时间',
  `create_time`  TIMESTAMP           NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`  TIMESTAMP           NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_account(account),
  KEY idx_user_id(user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '账号记录表';

DROP TABLE if EXISTS tb_account_role;
CREATE TABLE tb_account_role (
  `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `user_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户Id',
  `role_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '角色Id',
  PRIMARY KEY (`id`),
  KEY idx_user_id(user_id),
  KEY idx_role_id(role_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '账号角色关联表';

DROP TABLE if EXISTS tb_role;
CREATE TABLE tb_role (
  `id`             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `role_id`        VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '角色Id',
  `role_name`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '角色名称',
  `role_desc`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '角色职权描述',
  `priority`       VARCHAR(32)         NOT NULL DEFAULT 4294967296
  COMMENT '角色优先级，值越大，优先级越低',
  `enable`         TINYINT(11)         NOT NULL DEFAULT 1
  COMMENT '角色是否可用，0=不可用， 1=可用',
  `parent_role_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '父角色Id',
  `create_time`    TIMESTAMP           NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`    TIMESTAMP           NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_role_id(role_id),
  KEY idx_parent_role_id(parent_role_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '角色记录表';

DROP TABLE if EXISTS tb_authority;
CREATE TABLE tb_authority (
  `id`                  BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `authority_id`        VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限Id',
  `authority_type`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限类型',
  `authority_name`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限名称',
  `authority_desc`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限简要描述',
  `authority_route`     VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限路径',
  `authority_icon`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限图标',
  `parent_authority_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '父级权限',
  `enable`              TINYINT(11)         NOT NULL DEFAULT 1
  COMMENT '资源是否可用，0=不可用， 1=可用',
  `create_time`         TIMESTAMP           NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  `update_time`         TIMESTAMP           NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_authority_id(authority_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限记录表';

DROP TABLE if EXISTS tb_role_authority;
CREATE TABLE tb_role_authority (
  `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `role_id`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '角色Id',
  `authority_id` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '权限Id',
  PRIMARY KEY (`id`),
  KEY idx_role_id(role_id),
  KEY idx_authority_id(authority_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '权限角色关联表';

DROP TABLE if EXISTS tb_action;
CREATE TABLE tb_action (
  `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `action_id`     VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '动作Id',
  `action_name`   VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '动作名称',
  `action_desc`   VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '动作描述',
  `resource_path` VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '资源访问URL',
  `authority_id`  VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '动作关联的权限Id',
  `create_time`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_authority_id` (`action_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '资源操作动作表';


INSERT INTO mohress_training.tb_account (user_id, account, password, user_name, mobile, email, login_ip, login_time, enable, locked, expired_time, create_time, update_time) VALUES ('17081610203221032122', 'root', '$2a$10$ZWVAc1w.cYA9Ye9za.3GE.wf7Ap97HYX47kSWb2tB29w2lZyk5tCC', 'Mr.Monkey', '15201529169', '251961289@qq.com', '127.0.0.1', '2017-08-21 21:15:56', 1, 0, '2018-08-16 10:19:32', '2017-08-16 10:20:41', '2017-08-21 21:15:56');
INSERT INTO mohress_training.tb_account (user_id, account, password, user_name, mobile, email, login_ip, login_time, enable, locked, expired_time, create_time, update_time) VALUES ('17081815504021040603', 'agency', '$2a$10$b3VffC3Kunf98tDmFC5G0.StFrFHirdTePebVH2jP9MGFn1B9ViBy', '培训机构-甲', '15201529169', '251961289@qq.com', '0:0:0:0:0:0:0:1', '2017-08-21 12:30:43', 1, 0, '2030-01-01 00:00:00', '2017-08-18 15:52:59', '2017-08-21 12:30:43');
INSERT INTO mohress_training.tb_account (user_id, account, password, user_name, mobile, email, login_ip, login_time, enable, locked, expired_time, create_time, update_time) VALUES ('17081815504021040605', 'government', '$2a$10$b3VffC3Kunf98tDmFC5G0.StFrFHirdTePebVH2jP9MGFn1B9ViBy', '人社局科员-B', '15201529169', '251961289@qq.com', '127.0.0.1', '2017-08-21 14:35:46', 1, 0, '2030-01-01 00:00:00', '2017-08-18 15:52:59', '2017-08-21 14:35:46');


INSERT INTO mohress_training.tb_account_role (user_id, role_id) VALUES ('17081610203221032122', '17081610225621055995');
INSERT INTO mohress_training.tb_account_role (user_id, role_id) VALUES ('17081815504021040603', '17081610225621055997');
INSERT INTO mohress_training.tb_account_role (user_id, role_id) VALUES ('17081815504021040605', '17081610225621055996');

INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025493', 'page', 'Welcome', '欢迎页面', '/welcome', 'laptop', '', 1, '2017-08-18 14:16:15', '2017-08-18 14:16:15');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025494', 'menu', '学员信息管理', '学员信息管理菜单', '/student', 'team', '17081814132521025493', 1, '2017-08-18 14:17:37', '2017-08-18 14:17:45');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025495', 'menu', '教师信息管理', '教师信息管理菜单', '/teacher', 'user', '17081814132521025493', 1, '2017-08-18 14:18:49', '2017-08-18 14:18:49');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025497', 'menu', '开班信息', '开班信息管理菜单', '', 'solution', '17081814132521025493', 1, '2017-08-18 14:22:45', '2017-08-18 14:25:57');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025498', 'page', '培训课程管理', '培训课程管理页面', '/class/source', '', '17081814132521025497', 1, '2017-08-18 14:24:03', '2017-08-18 14:27:26');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025499', 'page', '培训班级管理', '培训班级管理页面', '/class/room', '', '17081814132521025497', 1, '2017-08-18 14:25:57', '2017-08-18 14:27:26');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025500', 'page', '开班申请', '开班申请详情页', '/class/application', '', '17081814132521025497', 1, '2017-08-18 14:27:13', '2017-08-18 14:27:26');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025501', 'menu', '考勤管理', '考勤管理菜单', '', 'calendar', '17081814132521025493', 1, '2017-08-18 14:32:08', '2017-08-18 14:32:08');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025502', 'page', '考勤记录查看', '考勤记录查看详情页', '/attendance/record', '', '17081814132521025501', 1, '2017-08-18 14:33:25', '2017-08-18 14:33:25');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025503', 'page', '考勤记录统计', '考勤记录统计详情页', '/attendance/statistic', '', '17081814132521025501', 1, '2017-08-18 14:34:38', '2017-08-18 14:34:38');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025504', 'page', '考勤人脸录入', '考勤人脸录入详情页', '/attendance/face', '', '17081814132521025501', 1, '2017-08-18 14:36:37', '2017-08-18 14:36:37');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025505', 'page', '考勤指纹录入', '考勤指纹录入详情页', '/attendance/fingerprint', '', '17081814132521025501', 1, '2017-08-18 14:36:37', '2017-08-18 14:36:37');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025506', 'page', '当日考勤补录', '考情补录详情页', '/attendance/add', '', '17081814132521025501', 1, '2017-08-18 14:37:33', '2017-08-18 14:37:33');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025507', 'menu', '结业管理', '结业管理菜单', '', 'database', '17081814132521025493', 1, '2017-08-18 14:40:03', '2017-08-18 14:40:03');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025508', 'page', '结业方式设置', '结业方式设置详情页', '/graduate/method', '', '17081814132521025507', 1, '2017-08-18 14:42:22', '2017-08-18 14:42:22');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025509', 'page', '申领补贴设置', '申领补贴设置详情页', '/graduate/subsidyApply', '', '17081814132521025507', 1, '2017-08-18 14:43:25', '2017-08-18 14:45:24');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025510', 'page', '结业分查看', '结业分查看详情页', '/graduate/score', '', '17081814132521025507', 1, '2017-08-18 14:46:13', '2017-08-18 14:46:13');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025511', 'page', '结业统计', '结业统计信息展示详情页', '/graduate/statistic', '', '17081814132521025507', 1, '2017-08-18 14:48:02', '2017-08-18 14:48:02');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025512', 'page', '台账生成', '台账查询详情页', '/graduate/ledger', '', '17081814132521025507', 1, '2017-08-18 14:48:02', '2017-08-18 14:48:02');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025513', 'menu', '培训机构管理', '培训机构管理菜单', '/agentMgt', 'file-text', '17081814132521025493', 1, '2017-08-18 14:50:39', '2017-08-18 14:50:39');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025514', 'menu', '开班申请管理', '开班申请管理菜单', '/classMgt', 'file-text', '17081814132521025493', 1, '2017-08-18 14:50:39', '2017-08-18 14:50:39');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025515', 'menu', '考勤检查', '考勤检查菜单', '/attendanceMgt', 'file-text', '17081814132521025493', 1, '2017-08-18 14:58:46', '2017-08-18 14:58:46');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025516', 'menu', '培训班级检查', '培训班级检查菜单', '', 'file-text', '17081814132521025493', 1, '2017-08-18 14:58:46', '2017-08-18 14:58:46');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025517', 'page', '开班检查', '开班检查详情页', '/inspection/inspectionBefore', '', '17081814132521025516', 1, '2017-08-18 14:58:46', '2017-08-18 14:59:56');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025518', 'page', '过程检查', '过程检查详情页', '/inspection/inspectionInprogress', '', '17081814132521025516', 1, '2017-08-18 14:58:46', '2017-08-18 14:59:56');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025519', 'page', '结业检查', '结业检查详情页', '/inspection/inspectionAfter', '', '17081814132521025516', 1, '2017-08-18 14:58:46', '2017-08-18 14:59:56');
INSERT INTO mohress_training.tb_authority (authority_id, authority_type, authority_name, authority_desc, authority_route, authority_icon, parent_authority_id, enable, create_time, update_time) VALUES ('17081814132521025520', 'menu', '台账审核', '台账审核菜单', '/ledgerCheck', 'file-text', '17081814132521025493', 1, '2017-08-18 14:58:46', '2017-08-18 14:59:56');


INSERT INTO mohress_training.tb_role (role_id, role_name, role_desc, priority, enable, parent_role_id, create_time, update_time) VALUES ('17081610225621055995', '超级管理员', '超级管理员', '0', 1, '', '2017-08-16 10:24:03', '2017-08-16 10:24:03');
INSERT INTO mohress_training.tb_role (role_id, role_name, role_desc, priority, enable, parent_role_id, create_time, update_time) VALUES ('17081610225621055996', '人社局-A', '人社局角色', '100', 1, '17081610225621055995', '2017-08-18 15:04:44', '2017-08-18 19:49:54');
INSERT INTO mohress_training.tb_role (role_id, role_name, role_desc, priority, enable, parent_role_id, create_time, update_time) VALUES ('17081610225621055997', '培训机构-A', '培训机构角色', '1000', 1, '17081610225621055995', '2017-08-18 15:04:44', '2017-08-18 19:49:54');

INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025513');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025514');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025515');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025516');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025517');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025518');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025519');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025520');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055996', '17081814132521025493');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025493');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025494');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025495');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025497');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025498');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025499');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025500');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025501');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025502');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025503');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025504');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025505');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025506');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025507');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025508');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025509');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025510');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025511');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055997', '17081814132521025512');