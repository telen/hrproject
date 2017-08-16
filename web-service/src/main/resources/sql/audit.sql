CREATE TABLE tb_audit_flow (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `flow_id` varchar(32) NOT NULL DEFAULT '' COMMENT '流程Id',
  `project_id` varchar(32) NOT NULL DEFAULT '' COMMENT '项目Id',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '流程当前节点Id',
  `creator` varchar(32) NOT NULL DEFAULT '' COMMENT '审核流程发起人',
  `version` INTEGER NOT NULL DEFAULT 0 COMMENT '版本号',
  `node_status` tinyint(11) NOT NULL DEFAULT 0 COMMENT '当前节点状态，0=待审核，1=审核通过， 2=审核不通过',
  `flow_status` tinyint(11) NOT NULL DEFAULT 0  COMMENT '当前流程审核状态，0=待审核，1=审核通过， 2=审核不通过',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_flow_id(flow_id),
  KEY idx_project_id(project_id),
  KEY idx_creator(creator)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核流程表';

CREATE TABLE tb_audit_member (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '审核节点Id',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '审核人用户Id',
  `priority` INTEGER NOT NULL DEFAULT 0 COMMENT '审核成员优先级',
  PRIMARY KEY (`id`),
  KEY idx_node_id(node_id),
  KEY idx_user_id(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='节点审核人关联表';


CREATE TABLE tb_audit_node (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '节点Id',
  `node_name` varchar(32) NOT NULL DEFAULT '' COMMENT '节点名称',
  `node_desc` varchar(32) NOT NULL DEFAULT '' COMMENT '节点描述信息',
  `previous_node` varchar(32) NOT NULL DEFAULT '' COMMENT '前置节点',
  `next_node` varchar(32) NOT NULL DEFAULT '' COMMENT '后置节点',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_node_id(node_id),
  KEY idx_previous_node(previous_node),
  KEY idx_next_node(next_node)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核节点表';

CREATE TABLE tb_audit_project (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `project_id` varchar(32) NOT NULL DEFAULT '' COMMENT '项目Id',
  `project_name` varchar(32) NOT NULL DEFAULT '' COMMENT '项目名称',
  `project_desc` varchar(32) NOT NULL DEFAULT '' COMMENT '项目简单描述',
  `start_node` varchar(32) NOT NULL DEFAULT '' COMMENT '起始节点',
  `end_node` varchar(32) NOT NULL DEFAULT '' COMMENT '终止节点',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_project_id(project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目审核表';


CREATE TABLE tb_audit_record (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `record_id` varchar(32) NOT NULL DEFAULT '' COMMENT '审核记录Id',
  `flow_id` varchar(32) NOT NULL DEFAULT '' COMMENT '审核流程Id',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '审核节点Id',
  `auditor` varchar(32) NOT NULL DEFAULT '' COMMENT '审核人Id',
  `action` tinyint(11) NOT NULL DEFAULT -1 COMMENT '审核动作，0=初始化审核流程，1=撤销动作，2=回退动作, 3=审核通过动作，4=审核否决动作',
  `audit_result` varchar(32) NOT NULL DEFAULT '' COMMENT '审核结果',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp  COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY uniq_record_id(record_id),
  KEY idx_flow_id(flow_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核项目记录表';
