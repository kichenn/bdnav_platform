package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-26 18:54
 **/
@Data
public class BaseUserQueryDto implements Serializable {


    private static final long serialVersionUID = -8164781615600205843L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 用户类型 1 学生 2 老师 3 家长
     */
    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private Integer userType;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别")
    private Integer gender;

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
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;

    /**
     * 身份证号
     */
    @Column(name = "身份证号")
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
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String image;

    /**
     * 头像名称
     */
    @ApiModelProperty("头像名称")
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

    /**
     * 微校用户激活时所需参数
     */
    private String state;
}