package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**修改学校收费部门的dto
 * @author WanMing
 * @date 2019/7/10 18:42
 */
@Data
public class ModifySchoolChargeDeptDto {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    private String chargeDeptName;

    /**
     * 部门类型 1 充值部门 2 消费部门
     */
    @ApiModelProperty("部门类型 1 充值部门 2 消费部门")
    private Byte chargeDeptType;

    /**
     * 消费类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类
     */
    @ApiModelProperty("收费部门类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类")
    private Byte consumeType;

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
