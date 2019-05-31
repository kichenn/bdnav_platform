package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/5/28 16:01
 */
@Data
public class SchoolServicePermitShowVo {


    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("学校编码")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("服务开始时间")
    private Date startTime;


    @ApiModelProperty("服务结束时间")
    private Date endTime;


    @ApiModelProperty("状态 1 服务有效  2 服务无效")
    private Byte status;

    @ApiModelProperty("延期天数")
    private Byte addDays;


    @ApiModelProperty("创建时间")
    private Date createDate;

}
