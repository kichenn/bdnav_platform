package com.bdxh.system.dto;


import lombok.Data;

import java.util.List;

@Data
public class BaPermissionsDto {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 用户关系权限列表
     */
    private List<AuRolePermissionDto> rolePermissionDto;
}
