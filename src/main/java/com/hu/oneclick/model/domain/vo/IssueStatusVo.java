package com.hu.oneclick.model.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IssueStatusVo {

    @Schema(name = "id")
    private Long id;

    @Schema(name = "状态")
    private String issueStatus;


}
