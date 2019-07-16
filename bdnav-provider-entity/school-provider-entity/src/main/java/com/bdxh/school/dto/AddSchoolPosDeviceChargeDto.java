package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**批量绑定pos机到收费部门
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
    private String schoolCode;

    /**
     * 设备的主键集合
     */
    @ApiModelProperty("设备的主键集合")
    @NotEmpty(message = "设备的主键集合不能为空")
    private List<Long> deviceIds;

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



}
