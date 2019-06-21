package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 购买 服务许可 dto
 * @Author: Kang
 * @Date: 2019/6/20 17:40
 */
@Data
public class AddPayServiceUserDto {

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

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("剩余服务天数")
    private Integer days;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("我方订单号")
    private Long orderNo;

    @ApiModelProperty("备注")
    private String remark;

}
