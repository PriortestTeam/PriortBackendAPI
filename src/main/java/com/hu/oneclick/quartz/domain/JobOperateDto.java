package com.hu.oneclick.quartz.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 定时任务变更DTO
 *
 * @author xiaohai
 * @date 2023/07/10
 */
@Setter
@Getter
@Schema(name = "定时任务操作DTO")
public class JobOperateDto implements Serializable {

    private static final long serialVersionUID = 3308577719580670615L;

    @Schema(name = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @Schema(name = "任务组名")
    private String jobGroupName = "DEFAULT";

}
