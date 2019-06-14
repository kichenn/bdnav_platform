package com.bdxh.servicepermit.dto;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ModifyServiceUserDto {

    @ApiModelProperty("服务id")
    private Long id;

    @ApiModelProperty("可用天数")
    private Integer days;

    @ApiModelProperty("状态 1 正常使用 2已过期")
    private Integer status;

    @ApiModelProperty("修改日期")
    private Date updateDate;

    @ApiModelProperty("操作人卡号")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;
}
