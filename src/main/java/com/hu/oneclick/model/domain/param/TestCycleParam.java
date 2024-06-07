package com.hu.oneclick.model.domain.param;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试周期参数
 *
 * @author xiaohai
 * @date 2023/05/12
 */
@Setter
@Getter
@Schema(name = "测试周期Param")
public class TestCycleParam implements Serializable {

    private static final long serialVersionUID = 5856652375484820133L;

    @Schema(name = "名称")
    private String title;

    @Schema(name = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(name = "项目周期ID")
    private Long testCycleId;

}
