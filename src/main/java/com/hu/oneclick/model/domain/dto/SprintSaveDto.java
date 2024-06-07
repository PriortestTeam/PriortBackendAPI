package com.hu.oneclick.model.domain.dto;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: jhh
 * @Date: 2023/5/16
 */
@Data
public class SprintSaveDto {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;

    /**
     * 关联项目id
     */
    @Schema(name = "关联项目id")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
    /**
     * 名称
     */
    @Schema(name = "名称")
    private String title;

    /**
     * 开始时间
     */
    @Schema(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private Date startDate;
    /**
     * 结束时间
     */
    @Schema(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private Date endDate;
    /**
     * 描述
     */
    @Schema(name = "描述")
    private String description;

    @Schema(name = "记录")
    private String epic;

    @Schema(name = "模块")
    private String module;

    @Schema(name = "sprintGoal")
    private String sprintGoal;

    @Schema(name = "状态")
    private String sprintStatus;


    /**
     * 自定义字段值
     */
    @Schema(name = "自定义字段值")
    private JSONObject customFieldDatas;
}
