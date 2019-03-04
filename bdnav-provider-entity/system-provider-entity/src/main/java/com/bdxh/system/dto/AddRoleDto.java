package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AddRoleDto implements Serializable {

    private static final long serialVersionUID = 9202976682572419808L;

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
