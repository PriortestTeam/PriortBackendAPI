package com.hu.oneclick.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("测试用例步骤Param")
public class TestCaseStepParam implements Serializable {

    private static final long serialVersionUID = -8652622701616802765L;

    @ApiModelProperty("关联testcase id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long testCaseId;

    @ApiModelProperty("步骤")
    private String testStep;

    @ApiModelProperty("预期结果")
    private String expectedResult;

    @ApiModelProperty("测试数据")
    private String testData;

}
