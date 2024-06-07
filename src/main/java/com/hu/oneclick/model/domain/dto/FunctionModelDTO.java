package com.hu.oneclick.model.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author houaixia
 * 权限设置DTO
 */
@Data
@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionModelDTO  implements Serializable {

    @Schema(name= "模块id")
    private Integer funId;

    @Schema(name= "功能字符串{'funId':40,'btnStr':'1,2,7'}）")
    private String btnStr;


}
