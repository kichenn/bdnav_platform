package com.bdxh.user.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import  lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-04 10:51
 **/
@Data
public class FamilyStudentDetailsVo implements Serializable {
    private static final long serialVersionUID = 3953145400679544770L;
    /**
     * 主键
     */
    @ApiModelProperty(value="学生家长关系id")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty(value="学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    private String schoolCode;

    /**
     * 家长号
     */
    @ApiModelProperty(value="家长号")
    private String cardNumber;

    /**
     * 家长id
     */
    @ApiModelProperty(value="家长id")
    private Long familyId;

    /**
     * 学生id
     */
    @ApiModelProperty(value="学生id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value="学生姓名")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty(value="学生学号")
    private String studentNumber;

    /**
     * 关系 父亲 母亲 等等
     */
    @ApiModelProperty(value="关系 父亲 母亲 等等")
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

    /**
     * 家长类
     */
    @ApiModelProperty(value = "家长类")
    private FamilyVo familyVo;

    /**
     * 学生类
     */
    @ApiModelProperty(value = "学生类")
    private StudentVo studentVo;
}