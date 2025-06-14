package com.hu.oneclick.model.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用搜索结果DTO - 只包含id和title
 *
 * @author AI Assistant
 * @date 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("通用搜索结果DTO")
public class SearchResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("标题")
    private String title;
}
