package com.hu.oneclick.model.domain.dto;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试周期DTO
 *
 * @author xiaohai
 * @date 2023/05/12
 */
@Setter
@Getter
@Schema(name = "测试周期DTO")
public class TestCycleSaveDto implements Serializable {

    private static final long serialVersionUID = 3621834190197699211L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;

    /**
     * 项目id
     */
    @Schema(name = "项目id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
    /**
     * 名称
     */
    @Schema(name = "名称")
    @NotBlank(message = "名称不能为空")
    private String title;
    /**
     * 执行进度 0: 未开始; 1:未完成; 2: 完成
     */
    @Schema(name = "执行进度 0: 未开始; 1:未完成; 2: 完成")
    private Integer exeucteProgress;
    /**
     * 运行状态
     */
    @Schema(name = "运行状态")
    private Integer runStatus;
    /**
     * 最后一次运行时间
     */
    @Schema(name = "最后一次运行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastRunDate;
    /**
     * 版本
     */
    @Schema(name = "版本")
    private String version;
    /**
     * 计划执行时间
     */
    @Schema(name = "计划执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planExecuteDate;
    /**
     * 描述
     */
    @Schema(name = "描述")
    private String description;
    /**
     * 0 默认 1 选中
     */
    @Schema(name = "0 默认 1 选中")
    private Integer currentRelease;
    /**
     * 0 未选择 1选中 (当 currentVersion 选中， 此值一定选择。)
     */
    @Schema(name = "0 未选择 1选中 (当 currentVersion 选中， 此值一定选择。)")
    private Integer released;
    /**
     * 用例执行人
     */
    @Schema(name = "用例执行人")
    private String reportTo;
    /**
     * 关注者
     */
    @Schema(name = "测试方法")
    private String testMethod;
    /**
     * 平台
     */
    @Schema(name = "平台")
    private String testPlatform;
    /**
     * 环境
     */
    @Schema(name = "环境")
    private String env;
    /**
     * allure 报告
     */
    @Schema(name = "allure 报告")
    private String allureReportUrl;
    @Schema(name = "instance_count")
    private Integer instanceCount;
    @Schema(name = "not_run_count")
    private Integer notRunCount;
    @Schema(name = "状态，草稿，待执行")
    private String testCycleStatus;
    @Schema(name = "计划运行job url")
    private String autoJobLink;
    /**
     * auto_job_start
     */
    @Schema(name = "auto_job_start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoJobStart;
    /**
     * auto_job_end
     */
    @Schema(name = "auto_job_end")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoJobEnd;
    @Schema(name = "frequency")
    private String frequency;
    @Schema(name = "remarks")
    private String remarks;
    /**
     * auto_job_run_time
     */
    @Schema(name = "auto_job_run_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date autoJobRunTime;
    @Schema(name = "browser")
    private String browser;
    @Schema(name = "test_frame")
    private String testFrame;
    @Schema(name = "testcycle_expand")
    private String testcycleExpand;
    /**
     * 自定义字段值
     */
    @Schema(name = "自定义字段值")
    private JSONObject customFieldDatas;

}
