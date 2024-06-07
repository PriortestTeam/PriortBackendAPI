package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * custom_field_data
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.CustomFieldData")
@Data
public class CustomFieldData implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    @Schema(name = "用户id")
    private String userId;

    /**
     * 项目id
     */
    @Schema(name = "项目id")
    private String projectId;

    /**
     * 自定义字段id
     */
    @Schema(name = "自定义字段id")
    private String customFieldId;

    /**
     * scope对应值的id
     */
    @Schema(name = "scope对应值的id")
    private String scopeId;

    /**
     * 范围
     */
    @Schema(name = "范围")
    private String scope;

    /**
     * 字段名
     */
    @Schema(name = "字段名")
    private String fieldName;

    /**
     * 自定义存储字段的值
     */
    @Schema(name = "自定义存储字段的值")
    private String valueData;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date createTime;

    /**
     * 是否删除
     */
    @Schema(name = "是否删除")
    private Boolean isDel;

    /**
     * 创建用户id
     */
    @Schema(name = "创建用户id")
    private String createUserId;

    private static final long serialVersionUID = 1L;
}