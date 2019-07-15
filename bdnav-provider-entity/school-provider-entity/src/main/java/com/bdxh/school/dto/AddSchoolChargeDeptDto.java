package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 添加学校收费部门的Dto
 * @author WanMing
 * @date 2019/7/10 18:21
 */
@Data
public class AddSchoolChargeDeptDto {

    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    @NotBlank(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    @NotBlank(message = "收费部门名称不能为空")
    private String chargeDeptName;

    /**
     * 收费部门类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类
     */
    @ApiModelProperty("收费部门类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类")
    @NotNull(message = "收费部门类型不能为空")
    private Byte chargeDeptType;

    /**
     * 操作人主键
     */
    @ApiModelProperty("操作人主键")
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
