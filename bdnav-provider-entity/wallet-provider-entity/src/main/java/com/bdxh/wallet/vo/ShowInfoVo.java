package com.bdxh.wallet.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShowInfoVo {

    @ApiModelProperty("卡号")
    private String cardNumber;

    @ApiModelProperty("性别")
    private Byte gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像地址")
    private String image;

    @ApiModelProperty("年级名称")
    private String gradeName;

    @ApiModelProperty("班级名称")
    private String className;

    @ApiModelProperty("老师职称")
    private String position;

}
