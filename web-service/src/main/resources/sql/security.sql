CREATE TABLE tb_account (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户Id',
  `account` varchar(32) NOT NULL DEFAULT '' COMMENT '登录账号',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '登录密码(密文)',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `login_ip` varchar(32) NOT NULL DEFAULT '' COMMENT '最近登录Ip',
  `login_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '最近登录时间',
  `enable` tinyint(11) NOT NULL DEFAULT 1 COMMENT '账号是否可用，0=不可用， 1=可用',
  `locked` tinyint(11) NOT NULL DEFAULT 0  COMMENT '账号是否锁定，0=未锁定，1=锁定',
  `expired_time` timestamp NOT NULL DEFAULT '2030-01-01 00:00:00' COMMENT '过期时间',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_account(account),
  KEY idx_user_id(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号记录表';


CREATE TABLE tb_account_role (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户Id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色Id',
  PRIMARY KEY (`id`),
  KEY idx_user_id(user_id),
  KEY idx_role_id(role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号角色关联表';


CREATE TABLE tb_role (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色Id',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(32) NOT NULL DEFAULT '' COMMENT '角色职权描述',
  `priority` varchar(32) NOT NULL DEFAULT 4294967296 COMMENT '角色优先级，值越大，优先级越低',
  `enable` tinyint(11) NOT NULL DEFAULT 1 COMMENT '角色是否可用，0=不可用， 1=可用',
  `parent_role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父角色Id',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_role_id(role_id),
  KEY idx_parent_role_id(parent_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色记录表';


CREATE TABLE tb_authority (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `authority_id` varchar(32) NOT NULL DEFAULT '' COMMENT '权限Id',
  `authority_name` varchar(32) NOT NULL DEFAULT '' COMMENT '权限名称',
  `authority_desc` varchar(32) NOT NULL DEFAULT '' COMMENT '权限简要描述',
  `resource_path` varchar(32) NOT NULL DEFAULT '' COMMENT '权限关联的资源路径',
  `enable` tinyint(11) NOT NULL DEFAULT 1 COMMENT '资源是否可用，0=不可用， 1=可用',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_authority_id(authority_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限记录表';

CREATE TABLE tb_role_authority (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色Id',
  `authority_id` varchar(32) NOT NULL DEFAULT '' COMMENT '权限Id',
  PRIMARY KEY (`id`),
  KEY idx_role_id(role_id),
  KEY idx_authority_id(authority_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限角色关联表';

INSERT INTO mohress_training.tb_account (user_id, account, password, user_name, mobile, email, login_ip, login_time, enable, locked, expired_time, create_time, update_time) VALUES ('17081610203221032122', 'root', '$2a$10$ZWVAc1w.cYA9Ye9za.3GE.wf7Ap97HYX47kSWb2tB29w2lZyk5tCC', 'Mr.Monkey', '15201529169', '251961289@qq.com', '0:0:0:0:0:0:0:1', '2017-08-16 20:31:59', 1, 0, '2018-08-16 10:19:32', '2017-08-16 10:20:41', '2017-08-16 20:31:59');
INSERT INTO mohress_training.tb_account (user_id, account, password, user_name, mobile, email, login_ip, login_time, enable, locked, expired_time, create_time, update_time) VALUES ('17081610203221032122', 'root', '$2a$10$ZWVAc1w.cYA9Ye9za.3GE.wf7Ap97HYX47kSWb2tB29w2lZyk5tCC', 'Mr.Monkey', '15201529169', '251961289@qq.com', '0:0:0:0:0:0:0:1', '2017-08-16 20:31:59', 1, 0, '2018-08-16 10:19:32', '2017-08-16 10:20:41', '2017-08-16 20:31:59');
INSERT INTO mohress_training.tb_authority (authority_id, authority_name, authority_desc, resource_path, enable, create_time, update_time) VALUES ('17081610203221032567', 'AUTH_root', '任何路径', '/*', 1, '2017-08-16 10:22:41', '2017-08-16 11:24:30');
INSERT INTO mohress_training.tb_role (role_id, role_name, role_desc, priority, enable, parent_role_id, create_time, update_time) VALUES ('17081610225621055995', '超级管理员', '超级管理员', '0', 1, '', '2017-08-16 10:24:03', '2017-08-16 10:24:03');
INSERT INTO mohress_training.tb_role_authority (role_id, authority_id) VALUES ('17081610225621055995', '17081610203221032567');