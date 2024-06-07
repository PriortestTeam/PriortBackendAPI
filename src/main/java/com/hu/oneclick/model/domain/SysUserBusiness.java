package com.hu.oneclick.model.domain;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * sys_user_business
 * @author 
 */
@Schema(name = "用户角色模块关系表")
@Data
public class SysUserBusiness implements Serializable {
    /**
     * 主键
     */
    @Schema(name = "主键")
    private Long id;

    /**
     * 类别
     */
    @Schema(name = "类别")
    private String type;

    /**
     * 主id
     */
    @Schema(name = "主id")
    private String keyId;

    /**
     * 值
     */
    @Schema(name = "值")
    private String value;

    /**
     * 值
     */
    @Schema(name = "不可见项")
    private String invisible;

    /**
     * 按钮权限
     */
    @Schema(name = "按钮权限")
    private String btnStr;

    /**
     * 租户id
     */
    @Schema(name = "租户id")
    private Long tenantId;

    /**
     * 删除标记，0未删除，1删除
     */
    @Schema(name = "删除标记，0未删除，1删除")
    private String deleteFlag;

    @Schema(name = "角色id")
    private Long  roleId;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name= "项目id")
    private Long projectId;

    @Schema(name= "项目名称")
    private String projectName;

    @Schema(name= "用户id")
    private Long userId;

    @Schema(name= "用户名称")
    private String userName;

    private static final long serialVersionUID = 1L;
}