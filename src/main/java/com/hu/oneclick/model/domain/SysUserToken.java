package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user_token
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.SysUserToken")
@Data
public class SysUserToken implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    @Schema(name = "用户id")
    private String userId;

    /**
     * token名称
     */
    @Schema(name = "token名称")
    private String tokenName;

    /**
     * token值
     */
    @Schema(name = "token值")
    private String tokenValue;

    /**
     * 过期时间
     */
    @Schema(name = "过期时间")
    private Date expirationTime;

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
     * 状态
     */
    @Schema(name = "状态")
    private Boolean status;

    /**
     * 剩余调用api次数
     */
    @Schema(name = "剩余调用api次数")
    private Long apiTimes;

    /**
     * 创建人
     */
    @Schema(name = "创建人")
    private String createId;

    private static final long serialVersionUID = 1L;
}