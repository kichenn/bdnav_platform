package com.bdxh.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class AddFamilyStudentDto implements Serializable {


    private static final long serialVersionUID = 1019487844136407774L;
    /**
     * 学校id
     */
    @ApiModelProperty(value="学校id")
    @NotNull(message ="学校id不能为空")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    @NotBlank(message ="学校编码不能为空")
    private String schoolCode;

    /**
     * 家长号
     */
    @ApiModelProperty(value="家长号")
    @NotBlank(message ="家长号不能为空")
    private String cardNumber;

    /**
     * 家长id
     */
    @ApiModelProperty(value="家长id")
    @NotNull(message ="家长id不能为空")
    private Long familyId;

    /**
     * 学生id
     */
    @ApiModelProperty(value="学生id")
    @NotNull(message ="学生id不能为空")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value="学生姓名")
    @NotBlank(message ="学生姓名不能为空")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty(value="学生学号")
    @NotBlank(message ="学生姓名不能为空")
    private String studentNumber;

    /**
     * 关系 父亲 母亲 等等
     */
    @ApiModelProperty(value="关系 父亲 母亲 等等")
    @NotBlank(message ="关系不能为空")
    private String relation;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
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