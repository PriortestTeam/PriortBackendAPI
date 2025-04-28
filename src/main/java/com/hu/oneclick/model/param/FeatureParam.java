package com.hu.oneclick.model.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hu.oneclick.model.entity.Feature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: jhh
 * @Date: 2023/4/24
 */
@Getter
@Setter
@ApiModel("故事Param")
public class FeatureParam implements Serializable {
    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @ApiModelProperty("视图ID")
    private String viewId;

    public LambdaQueryWrapper<Feature> getQueryCondition()  {
        LambdaQueryWrapper<Feature> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(this.title), Feature::getTitle, this.title);
        queryWrapper.eq(null != this.projectId, Feature::getProjectId, this.projectId);
        queryWrapper.orderByDesc(Feature::getCreateTime);
        return queryWrapper;
    }
}
