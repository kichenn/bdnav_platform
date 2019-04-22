package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryDeviceDto {

    @ApiModelProperty("设备名称")
    private Long Id;


    @ApiModelProperty("设备名称")
    private String DeviceName;


    @ApiModelProperty("包名")
    private String PackageName;


    @ApiModelProperty("状态 1是可用，2是不可用")
    private Integer Status;


    @ApiModelProperty("备注")
    private String Remark;


    @ApiModelProperty("创建时间")
    private Date CreateDate;


    @ApiModelProperty("更新时间")
    private Date UpdateDate;
}
