package com.bdxh.servicepermit.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/7/4 15:37
 */
@Data
public class ServiceUserVo implements Serializable {


    @ApiModelProperty("服务id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("产品主键")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("学校主键")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("家长主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long familyId;


    @ApiModelProperty("家长号")
    private String cardNumber;


    @ApiModelProperty("家长姓名")
    private String familyName;

    @ApiModelProperty("学生卡号")
    private String studentNumber;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("可用天数")
    private Integer days;

    @ApiModelProperty("开始使用时间")
    private Date startTime;


    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("状态 1 正常使用 2已过期")
    private Integer status;


    @ApiModelProperty("类型 1是试用  2是正式使用")
    private Integer type;


    @ApiModelProperty("创建日期")
    private Date createDate;


    @ApiModelProperty("修改日期")
    private Date updateDate;


    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;
}
