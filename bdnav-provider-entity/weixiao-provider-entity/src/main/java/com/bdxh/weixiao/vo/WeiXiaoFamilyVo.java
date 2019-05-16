package com.bdxh.weixiao.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-16 11:07
 **/
public class WeiXiaoFamilyVo implements Serializable {
    private static final long serialVersionUID = -6829925878012132576L;
    //家长ID
    @ApiModelProperty("家长ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private String id;

    //家长姓名
    @ApiModelProperty("家长姓名")
    private String name;

    //家长CardNumber
    @ApiModelProperty("家长CardNumber")
    private String cardNumber;

    @ApiModelProperty("学校ID")
    private String schoolId;

    //学校Code
    @ApiModelProperty("学校Code")
    private String schoolCode;

    @ApiModelProperty
    private String schoolName;

    //家长性别
    @ApiModelProperty("家长性别")
    private Byte fGender;

    //家长电话
    @ApiModelProperty("家长电话")
    private String phone;

    //家长头像
    @ApiModelProperty("家长头像")
    private String image;

    //家长头像名称
    @ApiModelProperty("家长头像名称")
    private String imageName;

    //家长生日
    @ApiModelProperty("家长生日")
    private String birth;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty("是否激活 1 未激活 2 激活")
    private Byte activate;

    //备注
    @ApiModelProperty("备注")
    private String remark;

    //创建时间
    @ApiModelProperty("创建时间")
    private String createDates;

}