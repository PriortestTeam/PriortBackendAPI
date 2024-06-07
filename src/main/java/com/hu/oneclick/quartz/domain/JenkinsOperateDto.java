package com.hu.oneclick.quartz.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Jenkins操作DTO
 *
 * @author xiaohai
 * @date 2023/07/11
 */
@Setter
@Getter
@Schema(name = "Jenkins操作DTO")
public class JenkinsOperateDto implements Serializable {

    private static final long serialVersionUID = 3308577719580670615L;

    @Schema(name = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

}
