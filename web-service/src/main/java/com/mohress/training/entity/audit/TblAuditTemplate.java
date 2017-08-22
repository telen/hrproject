package com.mohress.training.entity.audit;

import lombok.Data;

import java.util.Date;

/**
 * 审核模板
 *
 * Created by youtao.wan on 2017/8/10.
 */
@Data
public class TblAuditTemplate {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 项目Id
     */
    private String templateId;

    /**
     * 项目名称
     * (培训结构审核、开班审核，台账审核)
     */
    private String templateName;

    /**
     * 项目描述
     */
    private String templateDesc;

    /**
     * 起始节点
     */
    private String startNode;

    /**
     * 终止节点
     */
    private String endNode;

    /**
     * 项目审核流程图
     */
    private String auditFlowDiagram;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
