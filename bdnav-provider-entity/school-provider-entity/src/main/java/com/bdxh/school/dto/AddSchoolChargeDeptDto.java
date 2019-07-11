package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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
