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
 * 测试用例dto
 *
 * @author xiaohai
 * @date 2023/03/06
 */
@Setter
@Getter
@Schema(name = "测试用例DTO")
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestCaseSaveDto implements Serializable {

    private static final long serialVersionUID = -806606802497649838L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;

    /**
     * 关联项目id
     */
    @Schema(name = "关联项目id")
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
     * 优先级
     */
    @Schema(name = "优先级")
    @NotBlank(message = "优先级不能为空")
    private String priority;

    /**
     * 故事id
     */
    @Schema(name = "故事id")
    @NotBlank(message = "故事id不能为空")
    private String feature;

    /**
     * 描述
     */
    @Schema(name = "描述")
    private String description;

    /**
     * 执行时间
     */
    @Schema(name = "执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;

    /**
     * 浏览器
     */
    @Schema(name = "浏览器")
    private String browser;

    /**
     * 平台
     */
    @Schema(name = "平台")
    private String platform;

    /**
     * 版本
     */
    @Schema(name = "版本")
    private String version;

    /**
     * 用例类别
     */
    @Schema(name = "用例类别")
    private String caseCategory;

    /**
     * 用例类型
     */
    @Schema(name = "用例类型")
    private String testType;

    /**
     * 前提条件
     */
    @Schema(name = "前提条件")
    private String testCondition;

    /**
     * 环境
     */
    @Schema(name = "环境")
    private String env;

    /**
     * 外部id
     */
    @Schema(name = "外部id")
    private String externalLinkId;

    /**
     * 最后运行状态
     */
    @Schema(name = "最后运行状态")
    private Integer lastRunStatus;

    /**
     * 模块
     */
    @NotBlank(message = "模块不能为空")
    @Schema(name = "模块")
    private String module;

    /**
     * 测试设备
     */
    @Schema(name = "测试设备")
    private String testDevice;

    /**
     * 测试数据
     */
    @Schema(name = "测试数据")
    private String testData;

    /**
     * 测试方法
     */
    @Schema(name = "测试方法")
    private String testMethod;

    /**
     * test_status
     */
    @Schema(name = "test_status")
    private String testStatus;

    /**
     * 测试执行状态
     */
    @Schema(name = "测试执行状态")
    private Integer runStatus;

    /**
     * testcase_expand
     */
    @Schema(name = "testcase_expand")
    private String testcaseExpand;

    /**
     * remarks
     */
    @Schema(name = "remarks")
    private String remarks;

    /**
     * 修改者
     */
    @Schema(name = "修改者")
    private String updateUserId;

    /**
     * 创建者
     */
    @Schema(name = "创建者")
    private Long userId;

    /**
     * 自定义字段值
     */
    @Schema(name = "自定义字段值")
    private JSONObject customFieldDatas;

    /**
     * reportTo
     */
    @Schema(name = "reportTo")
    private String reportTo;

}
