package com.hu.oneclick.relation.domain.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "关系查询参数")
public class RelationParam {

    /** 对象id */
    @Schema(name = "对象id")
    private String objectId;

    /** 目标id */
    @Schema(name = "目标id")
    private String targetId;

    /** 分类 */
    @Schema(name = "分类", allowableValues = "com.hu.oneclick.relation.enums.RelationCategoryEnum")
    private String category;

}
