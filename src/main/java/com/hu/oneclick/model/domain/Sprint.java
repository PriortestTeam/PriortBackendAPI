package com.hu.oneclick.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hu.oneclick.model.base.AssignBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 迭代(Sprint)实体类
 *
 * @author makejava
 * @since 2021-02-03 09:36:08
 */
@Data
public class Sprint extends AssignBaseEntity implements Serializable {
    private static final long serialVersionUID = -33132559253115264L;

    /**
     * 关联项目id
     */
    @Schema(name = "关联项目id")
    private Long projectId;
    /**
     * 名称
     */
    @Schema(name = "名称")
    private String title;

    /**
     * 开始时间
     */
    @Schema(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 结束时间
     */
    @Schema(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 描述
     */
    @Schema(name = "描述")
    private String description;

    @Schema(name = "记录")
    private String epic;

    @Schema(name = "模块")
    private String module;

    @Schema(name = "sprintGoal")
    private String sprintGoal;

    @Schema(name = "状态")
    private String sprintStatus;

    @Schema(name = "扩展数据")
    private String sprintExpand;

}
