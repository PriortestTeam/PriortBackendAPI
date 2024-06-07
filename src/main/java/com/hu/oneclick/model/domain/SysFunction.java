package com.hu.oneclick.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_function
 * @author 
 */
@Schema(name = "功能模块表")
@Data
public class  SysFunction implements Serializable {
    /**
     * 主键
     */
    @Schema(name = "主键")
    private Long id;

    /**
     * 编号
     */
    @Schema(name = "编号")
    private String number;

    /**
     * 名称
     */
    @Schema(name = "名称")
    private String name;

    /**
     * 上级编号
     */
    @Schema(name = "上级编号")
    private String parentNumber;

    /**
     * 链接
     */
    @Schema(name = "链接")
    private String url;

    /**
     * 组件
     */
    @Schema(name = "组件")
    private String component;

    /**
     * 收缩
     */
    @Schema(name = "收缩")
    private Boolean state;

    /**
     * 排序
     */
    @Schema(name = "排序")
    private String sort;

    /**
     * 启用
     */
    @Schema(name = "启用")
    private Boolean enabled;

    /**
     * 类型
     */
    @Schema(name = "类型")
    private String type;

    /**
     * 功能按钮
     */
    @Schema(name = "功能按钮")
    private String pushBtn;

    /**
     * 图标
     */
    @Schema(name = "图标")
    private String icon;

    /**
     * 删除标记，0未删除，1删除
     */
    @Schema(name = "删除标记，0未删除，1删除")
    private String deleteFlag;

    private static final long serialVersionUID = 1L;
}