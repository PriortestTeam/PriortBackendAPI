package com.hu.oneclick.model.domain;

import com.hu.oneclick.model.base.AssignBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 故事(Feature)实体类
 *
 * @author makejava
 * @since 2021-02-03 13:54:35
 */
@Data
public class Feature extends AssignBaseEntity implements Serializable {
    private static final long serialVersionUID = 495256750642592776L;

    /**
     * 名称
     */
    @Schema(name = "名称")
    private String title;

    /**
     * 记录
     */
    @Schema(name = "记录")
    private String epic;
    /**
     * 关联项目id
     */
    @Schema(name = "关联项目id")
    private Long projectId;
    /**
     * 指派给谁
     */
    @Schema(name = "指派给谁")
    private String reportTo;
    /**
     * 状态
     */
    @Schema(name = "状态")
    private String featureStatus;
    /**
     * 版本
     */
    @Schema(name = "版本")
    private String version;
    /**
     * 描述
     */
    @Schema(name = "描述")
    private String description;

    @Schema(name = "模块")
    private String module;

    @Schema(name = "备注")
    private String remarks;

    @Schema(name = "扩展数据")
    private String featureExpand;
}
