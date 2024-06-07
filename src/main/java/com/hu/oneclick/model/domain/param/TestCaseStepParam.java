package com.hu.oneclick.model.domain.param;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试用例步骤Param
 *
 * @author xiaohai
 * @date 2023/03/06
 */
@Setter
@Getter
@Schema(name = "测试用例步骤Param")
public class TestCaseStepParam implements Serializable {

    private static final long serialVersionUID = -8652622701616802765L;

    @Schema(name = "关联testcase id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long testCaseId;

    @Schema(name = "步骤")
    private String testStep;

    @Schema(name = "预期结果")
    private String expectedResult;

    @Schema(name = "测试数据")
    private String testData;

}
