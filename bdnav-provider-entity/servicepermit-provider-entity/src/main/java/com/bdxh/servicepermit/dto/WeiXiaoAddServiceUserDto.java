package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-27 17:35
 **/
@Data
public class WeiXiaoAddServiceUserDto {
    @ApiModelProperty("服务id")
    private Long id;

    @ApiModelProperty("订单主键")
    private Long orderNo;

    @ApiModelProperty("产品主键")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String productName;

    //如果是添加试用 那么就不需要给上面的属性赋值
    @ApiModelProperty("学校主键")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("家长主键")
    private Long familyId;

    @ApiModelProperty("家长号")
    private String cardNumber;

    @ApiModelProperty("家长姓名")
    private String familyName;

    @ApiModelProperty("学生卡号")
    private String studentNumber;

    @ApiModelProperty("可用天数")
    private Integer days;

    @ApiModelProperty("学生姓名")
    private String studentName;

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