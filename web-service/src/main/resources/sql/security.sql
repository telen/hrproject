CREATE TABLE tb_account (
  `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键Id',
  `user_id`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '用户Id',
  `account`      VARCHAR(32)         NOT NULL DEFAULT ''
  COMMENT '登录账号',
  `password`     VARCHAR(32)         NOT NULL DEFAULT ''
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

drop TABLE if EXISTS tb_authority;
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

drop TABLE if EXISTS tb_role_authority;
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

drop TABLE if EXISTS tb_action;
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
  COMMENT = '资源操作动作表'