package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**修改学校pos的设备信息
 * @author WanMing
 * @date 2019/7/15 15:06
 */
@Data
public class ModifySchoolPosDeviceDto {

    /**
     * 设备主键
     */
    @ApiModelProperty("设备主键")
    @NotNull(message = "设备主键不能为空")
    private Long deviceId;

    /**
     * 所属收费部门
     */
    @ApiModelProperty("新的收费部门id")
    @NotNull(message = "新的收费部门id")
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

}
