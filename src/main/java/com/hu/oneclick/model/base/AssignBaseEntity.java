package com.hu.oneclick.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class AssignBaseEntity implements Serializable {

    private static final long serialVersionUID = -1025285783773774055L;

    //解决swagger获取id精度缺失问题,postman不会有这个问题
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(fill = FieldFill.INSERT)
    @Schema(name = "创建人ID")
    private Long createUserId;
    //
    //@TableField(fill = FieldFill.INSERT)
    //@Schema(name = value = "创建人名")
    //private String createUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "更新人ID")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;
    //
    //@ApiModelProperty(value = "更新人名")
    //@TableField(fill = FieldFill.INSERT_UPDATE)
    //private String updateUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //@TableLogic
    //@TableField(value = "is_delete", fill = FieldFill.INSERT)
    //@Schema(name = value = "逻辑删除")
    //private Integer isDelete;

}
