package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * system_config
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.SystemConfig系统配置表")
@Data
public class SystemConfig implements Serializable {
    /**
     * id
     */
    @Schema(name = "id")
    private Integer id;

    /**
     * key
     */
    @Schema(name = "key")
    private String key;

    /**
     * value
     */
    @Schema(name = "value")
    private String value;

    /**
     * 组别
     */
    @Schema(name = "组别")
    private String group;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date create_time;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private Date update_time;

    /**
     * ui显示
     */
    @Schema(name = "ui显示0显示1不显示")
    private Integer uiDisplay;

    /**
     * 分组中文名
     */
    @Schema(name = "分组中文名")
    private String groupLabelCN;



    private static final long serialVersionUID = 1L;
}