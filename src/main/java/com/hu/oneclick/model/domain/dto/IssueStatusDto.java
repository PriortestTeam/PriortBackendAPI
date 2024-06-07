package com.hu.oneclick.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class IssueStatusDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    private Long id;


    @Schema(name = "状态")
//    @NotBlank(message = "状态不能为空")
    private String issueStatus;

    @Schema(name = "关联测试用例")
//    @NotBlank(message = "关联测试用例不能为空")
    private String verifiedResult;

    @Schema(name = "缺陷修改版本号")
//    @NotBlank(message = "缺陷修改版本号不能为空")
    private String fixVersion;
}
