package com.hu.oneclick.model.domain.dto;

import cn.hutool.json.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试用例步骤子步骤DTO
 *
 * @author xiaohai
 * @date 2023/04/09
 */
@Setter
@Getter
@Schema(name = "测试用例步骤子步骤DTO")
public class TestCaseStepSaveSubDto implements Serializable {

    private static final long serialVersionUID = 2326317164248935852L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;

    @Schema(name = "步骤")
    private String testStep;

    @Schema(name = "预期结果")
    private String expectedResult;

    @Schema(name = "测试数据")
    private String testData;

    @Schema(name = "remarks")
    private String remarks;

    @Schema(name = "test_step_id")
    private Long testStepId;

    @Schema(name = "执行条件")
    private String teststepCondition;

    @Schema(name = "自定义字段值")
    private JSONArray customFieldDatas;

}
