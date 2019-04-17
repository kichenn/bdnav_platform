package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AddTeacherDto implements Serializable {


    private static final long serialVersionUID = -3487639627504367771L;
    @ApiModelProperty(value="主键")
    private Long Id;
    /**
     * 学校id
     */
    @ApiModelProperty(value="学校ID")
    @NotNull(message ="学校ID不能为空")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    @NotBlank(message ="学校编码不能为空")
    private String schoolCode;

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
     * 老师姓名
     */
    @ApiModelProperty(value="老师姓名")
    @NotBlank(message ="老师姓名不能为空")
    private String name;

    /**
     * 老师性别
     */
    @ApiModelProperty(value="老师性别")
    @NotNull(message ="老师性别不能为空")
    private Byte gender;

    /**
     * 出身日期
     */
    @ApiModelProperty(value="出身日期")
    private String birth;

    /**
     * 老师职称
     */
    @ApiModelProperty(value="老师职称")
    private String position;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    @NotBlank(message ="手机号不能为空")
    private String phone;

    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    @NotBlank(message ="工号不能为空")
    private String cardNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty(value="身份证号")
    private String idcard;

    /**
     * qq号
     */
    @ApiModelProperty(value="qq号")
    private String qqNumber;

    /**
     * 微信号
     */
    @ApiModelProperty(value="微信号")
    private String wxNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 图像
     */
    @ApiModelProperty(value="图像")
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
    @ApiModelProperty(value="是否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private Date createDate;

    /**
     * 修改日期
     */
    @ApiModelProperty(value="修改日期")
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

    /**
     * 老师部门关系表
     */
    @ApiModelProperty(value="老师部门关系表")
    private List<TeacherDeptDto> teacherDeptDtoList;
}