package com.hu.oneclick.relation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


/**
 * 关系实体
 *
 * @author xiaohai
 * @date 2023/06/05
 */
@Getter
@Setter
@TableName("relation")
@Schema(name = "关系实体")
public class Relation {

    /** id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 对象id */
    @Schema(name = "对象id")
    @NotBlank(message = "对象id不能为空")
    private String objectId;

    /** 目标id */
    @Schema(name = "目标id")
    @NotBlank(message = "目标id不能为空")
    private String targetId;

    /** 分类 */
    @Schema(name = "分类", allowableValues = "com.hu.oneclick.relation.enums.RelationCategoryEnum")
    @NotBlank(message = "分类不能为空")
    private String category;

    /** 扩展信息 */
    @Schema(name = "扩展信息(JSON)")
    private String extJson;

}
