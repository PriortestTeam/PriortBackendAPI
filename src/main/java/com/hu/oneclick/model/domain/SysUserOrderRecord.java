package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sys_user_order_record
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.SysUserOrderRecord订单记录表")
@Data
public class SysUserOrderRecord implements Serializable {
    /**
     * id
     */
    @Schema(name = "id")
    private Integer id;

    /**
     * 订单id
     */
    @Schema(name = "订单id")
    private Long order_id;

    /**
     * 原价
     */
    @Schema(name = "原价")
    private BigDecimal original_price;

    /**
     * 折扣价
     */
    @Schema(name = "折扣价")
    private BigDecimal discount_price;

    /**
     * 支付状态
     */
    @Schema(name = "支付状态")
    private Boolean status;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private Date create_time;

    /**
     * 是否删除
     */
    @Schema(name = "是否删除")
    private Boolean is_del;

    /**
     * 支付时间
     */
    @Schema(name = "支付时间")
    private Date payment_time;

    /**
     * 支付方式
     */
    @Schema(name = "支付方式")
    private String payment_type;

    /**
     * 服务周期
     */
    @Schema(name = "服务周期")
    private String service_plan_duration;

    /**
     * 容量大小
     */
    @Schema(name = "容量大小")
    private String data_strorage;

    /**
     * 容量价格
     */
    @Schema(name = "容量价格")
    private BigDecimal data_price;

    /**
     * apiCall
     */
    @Schema(name = "apiCall")
    private String api_call;

    /**
     * apiCall价格
     */
    @Schema(name = "apiCall价格")
    private BigDecimal api_call_price;

    /**
     * 采购模式
     */
    @Schema(name = "采购模式")
    private String sub_scription;

    /**
     * 折扣
     */
    @Schema(name = "折扣")
    private BigDecimal discount;

    /**
     * 实际支付
     */
    @Schema(name = "实际支付")
    private BigDecimal expenditure;

    /**
     * 发票转态
     */
    @Schema(name = "发票转态")
    private Boolean invoice;

    private static final long serialVersionUID = 1L;
}