package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateRoleDto implements Serializable {

    private static final long serialVersionUID = 5885982808694321117L;

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 角色
     */
    @NotEmpty(message ="角色不能为空")
    @ApiModelProperty("角色")
    private String role;

    /**
     * 角色名称
     */
    @NotEmpty(message ="角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String roleName;


    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
