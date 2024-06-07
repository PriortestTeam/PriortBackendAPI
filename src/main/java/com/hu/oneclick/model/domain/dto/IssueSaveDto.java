package com.hu.oneclick.model.domain.dto;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jhh
 * @Date: 2023/4/25
 */
@Data
public class IssueSaveDto implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;

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
     * 计划修复时间
     */
    @Schema(name = "计划修复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planFixDate;

    @Schema(name = "优先级")
//    @NotBlank(message = "优先级不能为空")
    private String priority;

    @Schema(name = "环境")
//    @NotBlank(message = "环境不能为空")
    private String env;

    @Schema(name = "浏览器")
//    @NotBlank(message = "浏览器不能为空")
    private String browser;

    @Schema(name = "平台")
//    @NotBlank(message = "平台不能为空")
    private String platform;

    @Schema(name = "版本")
//    @NotBlank(message = "版本不能为空")
    private String issueVersion;

    @Schema(name = "用例分类")
//    @NotBlank(message = "用例分类不能为空")
    private String caseCategory;

    @Schema(name = "描述")
//    @NotBlank(message = "描述不能为空")
    private String description;

    @Schema(name = "状态")
//    @NotBlank(message = "状态不能为空")
    private String issueStatus;

    @Schema(name = "模块")
//    @NotBlank(message = "模块不能为空")
    private String module;

    @Schema(name = "当前负责人")
//    @NotBlank(message = "当前负责人不能为空")
    private String reportTo;

    @Schema(name = "关联测试用例")
//    @NotBlank(message = "关联测试用例不能为空")
    private String verifiedResult;

    @Schema(name = "严重程度")
//    @NotBlank(message = "严重程度不能为空")
    private String severity;

    @Schema(name = "测试设备")
//    @NotBlank(message = "测试设备不能为空")
    private String testDevice;

    @Schema(name = "缺陷修改版本号")
//    @NotBlank(message = "缺陷修改版本号不能为空")
    private String fixVersion;

    /**
     * 自定义字段值
     */
    @Schema(name = "自定义字段值")
    private JSONObject customFieldDatas;


    @Schema(name = "运行用例Id")
    private long runcaseId;

}
