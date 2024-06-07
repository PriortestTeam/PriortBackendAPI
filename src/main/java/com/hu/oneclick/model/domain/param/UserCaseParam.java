package com.hu.oneclick.model.domain.param;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(name = "用户故事请求对象")
@Data
public class UserCaseParam implements Serializable {


    @Schema(name = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    @Schema(name = "标题")
    private String title;

    @Schema(name = "类别")
    private String useCategory;
    /**
     * 级别
     */
    @Schema(name = "级别")
    private String level;
    /**
     * 等级
     */
    @Schema(name = "等级")
    private String grade;

    /**
     * 流程场景、
     */
    @Schema(name = "流程场景")
    private String scenario;
    @Schema(name = "故事用例扩展")
    private JSONObject usecaseExpand;

    @Schema(name = "所属故事ID")
    private long featureId;

    @Schema(name = "备注")
    private String remarks;
}
