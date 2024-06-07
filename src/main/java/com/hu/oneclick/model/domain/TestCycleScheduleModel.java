package com.hu.oneclick.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * test_cycle_schedule_model
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.TestCycleScheduleModel")
@Data
public class TestCycleScheduleModel implements Serializable {
    private Integer id;

    /**
     * 测试周期id
     */
    @Schema(name = "测试周期id")
    private Integer testCycleId;

    /**
     * 开始时间天
     */
    @Schema(name = "开始时间天")
    private Date autoJobStart;

    /**
     * 开始时间时分秒
     */
    @Schema(name = "开始时间时分秒")
    private Date autoJobRun;

    /**
     * 执行路径
     */
    @Schema(name = "执行路径")
    private String autoJobLink;

    /**
     * 结束时间
     */
    @Schema(name = "结束时间")
    private Date autoJobEnd;

    /**
     * 重复方式
     */
    @Schema(name = "重复方式")
    private String frequency;

    private static final long serialVersionUID = 1L;
}