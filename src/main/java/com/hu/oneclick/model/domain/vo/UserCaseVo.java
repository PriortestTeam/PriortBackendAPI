package com.hu.oneclick.model.domain.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户故事返回对象
 */
@Data
@Schema(name = "用户故事返回对象")
public class UserCaseVo implements Serializable {

    @Schema(name = "id")
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
    private String usecaseExpand;


    @Schema(name = "所属故事ID")
    private long featureId;

    @Schema(name = "备注")
    private String remarks;
}
