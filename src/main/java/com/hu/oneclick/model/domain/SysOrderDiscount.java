package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sys_order_discount
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.SysOrderDiscount折扣表")
@Data
public class SysOrderDiscount implements Serializable {
    /**
     * 折扣表id
     */
    @Schema(name = "折扣表id")
    private Integer id;

    /**
     * 订阅时长
     */
    @Schema(name = "订阅时长")
    private String subScription;

    /**
     * 容量大小
     */
    @Schema(name = "容量大小")
    private String dataStrorage;

    /**
     * apiCall
     */
    @Schema(name = "apiCall")
    private String apiCall;

    /**
     * 初始折扣
     */
    @Schema(name = "初始折扣")
    private BigDecimal normalDiscount;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}