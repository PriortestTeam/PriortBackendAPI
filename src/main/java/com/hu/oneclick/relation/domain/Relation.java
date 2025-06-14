package com.hu.oneclick.relation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 关系实体
 *
 * @author xiaohai
 * @date 2023/06/05
 */
@Getter
@Setter
@TableName("relation")
@ApiModel("关系实体")
public class Relation {

    /** id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 对象id */
    @ApiModelProperty("对象id")
    @NotBlank(message = "对象id不能为空")
    private String objectId;

    /** 目标id */
    @ApiModelProperty("目标id")
    @NotBlank(message = "目标id不能为空")
    private String targetId;

    /** 分类 */
    @ApiModelProperty(value = "分类", notes = "com.hu.oneclick.relation.enums.RelationCategoryEnum")
    @NotBlank(message = "分类不能为空")
    private String category;

    //** 扩展信息 *//*
    @ApiModelProperty("扩展信息(JSON)")
    private String extJson;

    /**
     * 标题（来自关联表）
     */
    @ApiModelProperty("标题")
    private String title;

}
