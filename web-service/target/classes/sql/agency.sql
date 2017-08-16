DROP TABLE IF EXISTS tbl_agency;
CREATE TABLE `tbl_agency` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `agency_id` varchar(64) NOT NULL DEFAULT '' COMMENT '机构ID',
  `agency_name` varchar(128) NOT NULL DEFAULT '' COMMENT '机构名称',
  `agency_head` varchar(64) NOT NULL DEFAULT '' COMMENT '机构负责人',
  `agency_mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '机构负责人电话',
  `agency_mail` varchar(64) NOT NULL DEFAULT '' COMMENT '机构负责人邮件',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '机构地址',
  superior_department VARCHAR(64) NOT NULL DEFAULT '' COMMENT '上级管理部门',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '0-正常,1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_agency_id` (`agency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构基本信息表';
