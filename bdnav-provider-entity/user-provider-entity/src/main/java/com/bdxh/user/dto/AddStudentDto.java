package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import  lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class AddStudentDto implements Serializable {


    private static final long serialVersionUID = 4428905210769359015L;
    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    @NotBlank(message ="学校编码不能为空")
    private String schoolCode;

    /**
     * 学校Id
     */
    @ApiModelProperty(value="学校Id")
    private String schoolId;

    /**
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    @NotBlank(message ="学校名称不能为空")
    private String schoolName;

    /**
     * 校区名称
     */
    @ApiModelProperty(value="校区名称")
    private String campusName;

    /**
     * 学院名称
     */
    @ApiModelProperty(value="学院名称")
    private String collegeName;

    /**
     * 系名称
     */
    @ApiModelProperty(value="系名称")
    private String facultyName;

    /**
     * 专业名称
     */
    @ApiModelProperty(value="专业名称")
    private String professionName;

    /**
     * 年级名称
     */
    @ApiModelProperty(value="年级名称")
    private String gradeName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value="班级名称")
    private String className;

    /**
     * 班级id
     */
    @ApiModelProperty(value="班级id")
    private Long classId;

    /**
     * 组织架构ids
     */
    @ApiModelProperty(value="组织架构ids")
    private String classIds;

    /**
     * 组织架构名称names
     */
    @ApiModelProperty(value="组织架构名称names")
    private String classNames;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value="学生姓名")
    @NotBlank(message ="学生姓名不能为空")
    private String name;

    /**
     * 学生性别
     */
    @ApiModelProperty(value="学生性别")
    @NotNull(message ="学生性别不能为空")
    private Byte gender;

    /**
     * 出身日期
     */
    @ApiModelProperty(value="出身日期")
    private String birth;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    @NotBlank(message ="手机号不能为空")
    private String phone;

    /**
     * 学号
     */
    @ApiModelProperty(value="学号")
    @NotBlank(message ="学号不能为空")
    private String cardNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty(value="学生份证号")
    private String idcard;

    /**
     * qq号
     */
    @ApiModelProperty(value="学生qq号")
    private String qqNumber;

    /**
     * 微信号
     */
    @ApiModelProperty(value="学生微信号")
    private String wxNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="学生邮箱")
    private String email;


    /**
     * 图像
     */
    @ApiModelProperty("用户头像地址")
    private String image;

    /**
     * 用户头像名称
     */
    @ApiModelProperty(value="用户头像名称")
    private String imageName;


    /**
     * 民族名称
     */
    @ApiModelProperty(value="民族名称")
    private String nationName;

    /**
     * 宿舍地址
     */
    @ApiModelProperty(value="宿舍地址")
    private String dormitoryAddress;

    /**
     * 家庭住址
     */
    @ApiModelProperty(value="家庭住址")
    private String adress;

    /**
     * 物理卡号
     */
    @ApiModelProperty(value="物理卡号")
    private String physicalNumber;

    /**
     * 物理芯片号
     */
    @ApiModelProperty(value="物理芯片号")
    private String physicalChipNumber;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty(value="否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private Date createDate;

    /**
     * 修改日期
     */
    @ApiModelProperty(value="学生ID")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty(value="操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

}