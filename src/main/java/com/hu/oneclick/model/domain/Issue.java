package com.hu.oneclick.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hu.oneclick.model.base.AssignBaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷(Issue)实体类
 *
 * @author makejava
 * @since 2021-02-17 16:20:43
 */
@Data
public class Issue extends AssignBaseEntity implements Serializable {
    private static final long serialVersionUID = 418948698502600149L;

    /**
     * 关联项目id
     */
    @Schema(name = "关联项目id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long projectId;

    /**
     * 名称
     */
    @Schema(name = "名称")
    private String title;

    /**
     * 计划修复时间
     */
    @Schema(name = "计划修复时间")
    private Date planFixDate;

    @Schema(name = "关闭时间")
    private Date closeDate;

    @Schema(name = "关联测试用例")
    private String verifiedResult;

    @Schema(name = "优先级")
    private String priority;

    @Schema(name = "环境")
    private String env;

    @Schema(name = "浏览器")
    private String browser;

    @Schema(name = "平台")
    private String platform;

    @Schema(name = "版本")
    private String issueVersion;

    @Schema(name = "用例分类")
    private String caseCategory;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "状态")
    private String issueStatus;

    @Schema(name = "模块")
    private String module;

    @Schema(name = "当前负责人")
    private String reportTo;

    @Schema(name = "issue_expand")
    private String issueExpand;

    @Schema(name = "缺陷修改版本号")
    private String fixVersion;

    @Schema(name = "severity")
    private String severity;

    @Schema(name = "测试设备")
    private String testDevice;

    @Schema(name = "运行用例Id")
    private long runcaseId;

}