package com.hu.oneclick.model.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * test_cycle_schedule
 * @author 
 */
@Schema(name = "com.hu.oneclick.model.domain.TestCycleSchedule")
@Data
public class TestCycleSchedule implements Serializable {
    private Integer id;

    /**
     * 运行方式id
     */
    @Schema(name = "运行方式id")
    private Integer scheduleModelId;

    /**
     * 执行时间
     */
    @Schema(name = "执行时间")
    private Date runTime;

    /**
     * 执行状态0未执行1执行成功2执行失败
     */
    @Schema(name = "执行状态0未执行1执行成功2执行失败")
    private String runStatus;

    /**
     * test_cycle_id
     */
    @Schema(name = "test_cycle_id")
    private Integer testCycleId;

    private static final long serialVersionUID = 1L;
}