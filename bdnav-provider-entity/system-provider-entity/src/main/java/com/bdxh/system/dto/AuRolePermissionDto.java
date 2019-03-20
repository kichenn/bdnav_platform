package com.bdxh.system.dto;

import lombok.Data;

@Data
public class AuRolePermissionDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 启用状态 1 开 2关
     */
    private Integer RpSwitch;

    /**
     * "选中状态 1 选中 2未选中"
     */
    private Integer selected;

}
