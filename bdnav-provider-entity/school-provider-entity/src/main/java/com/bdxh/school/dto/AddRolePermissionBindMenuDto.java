package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* @Description:  新增或者修改捆绑角色与菜单之间的关系dto
* @Author: Kang
* @Date: 2019/3/28 14:27
*/
@Data
public class AddRolePermissionBindMenuDto {

    @NotNull(message = "学校id不能为空")
    @ApiModelProperty("学校id")
    private Long schoolId;

    @NotEmpty(message = "学校编码不能为空")
    @NotNull(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    @NotNull(message = "角色id不能为空")
    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("菜单权限id集合")
    private List<Long> permissionIds;
}
