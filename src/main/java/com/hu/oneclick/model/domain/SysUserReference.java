package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user_reference
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.SysUserReference用户推荐人")
@Data
public class SysUserReference implements Serializable {
    /**
     * id
     */
    @Schema(name = "id")
    private Integer id;

    /**
     * 用户id
     */
    @Schema(name = "用户id")
    private Long user_id;

    /**
     * 推荐人id
     */
    @Schema(name = "推荐人id")
    private Long reference_user_id;

    /**
     * 推荐人邮箱
     */
    @Schema(name = "推荐人邮箱")
    private String reference_user_email;

    /**
     * 推荐时间
     */
    @Schema(name = "推荐时间")
    private Date reference_time;

    /**
     * 逻辑删除
     */
    @Schema(name = "逻辑删除")
    private Boolean is_del;

    private static final long serialVersionUID = 1L;
}