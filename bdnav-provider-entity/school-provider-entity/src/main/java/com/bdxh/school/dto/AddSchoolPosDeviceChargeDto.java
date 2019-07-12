package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author WanMing
 * @date 2019/7/11 11:18
 */
@Data
public class AddSchoolPosDeviceChargeDto {


    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    @NotBlank(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 设备主键
     */
    @ApiModelProperty("设备主键")
    @NotNull(message = "设备主键不能为空")
    private Long deviceId;

    /**
     * 所属收费部门
     */
    @ApiModelProperty("所属收费部门")
    @NotNull(message = "所属收费部门不能为空")
    private Long chargeDeptId;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人名称
     */
    @ApiModelProperty("操作人名称")
    private String operatorName;

    /**
     * 注释
     */
    @ApiModelProperty("注释")
    private String remark;

}
