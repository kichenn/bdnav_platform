package com.bdxh.servicepermit.dto;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ModifyServiceUserDto {

    @ApiModelProperty("服务id")
    private Long id;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("可用天数")
    private Integer days;

    @ApiModelProperty("状态 1 正常使用 2已过期")
    private Integer status;

    @ApiModelProperty("类型 1是试用  2是正式使用")
    private Integer type;

    @ApiModelProperty("修改日期")
    private Date updateDate;

    @ApiModelProperty("操作人卡号")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

}
