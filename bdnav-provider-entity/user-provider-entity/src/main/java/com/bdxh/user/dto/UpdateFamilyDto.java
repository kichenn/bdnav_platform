package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:修改家长Dto
 * @author: binzh
 * @create: 2019-03-11 17:40
 **/
@Data
public class UpdateFamilyDto implements Serializable {

    private static final long serialVersionUID = 2342455005199240692L;

    @ApiModelProperty("家长Id")
    private String id;
    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    @NotBlank(message ="学校编码不能为空")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 家长姓名
     */
    @ApiModelProperty("家长姓名")
    private String name;

    /**
     * 家长性别
     */
    @ApiModelProperty("家长性别")
    private Byte gender;

    /**
     * 出身日期
     */
    @ApiModelProperty("出身日期")
    private String birth;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 家长号
     */
    @ApiModelProperty("家长号")
    @NotBlank(message ="家长号不能为空")
    private String cardNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idcard;

    /**
     * qq号
     */
    @ApiModelProperty("qq号")
    private String qqNumber;

    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String wxNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 图像
     */
    @ApiModelProperty("用户头像地址")
    private String image;

    /**
     * 用户头像名称
     */
    @ApiModelProperty("用户头像名称")
    private String imageName;

    /**
     * 民族名称
     */
    @ApiModelProperty("民族名称")
    private String nationName;

    /**
     * 家庭住址
     */
    @ApiModelProperty("家庭住址")
    private String adress;

    /**
     * 物理卡号
     */
    @ApiModelProperty("物理卡号")
    private String physicalNumber;

    /**
     * 物理芯片号
     */
    @ApiModelProperty("物理芯片号")
    private String physicalChipNumber;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty("是否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private Date createDate;

    /**
     * 修改日期
     */
    @ApiModelProperty("修改日期")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}