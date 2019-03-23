package com.bdxh.system.dto;

import lombok.Data;


@Data
public class AuRolePermissionDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * "选中状态 1 选中 2未选中"
     */
    private Integer selected;

    /**
     * "是否选中 1 选中 2未选中"
     */
    private Integer checked;

    /**
     * "半选状态 1 选中 2未选中"
     */
    private Integer indeterminate;

}
