package com.bdxh.system.dto;

import com.bdxh.system.entity.RolePermission;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RolePermissionDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 父级菜单ids
     */
    private String parentIds;
    /**
     * 菜单名称
     */
    private String title;

    /**
     * 路由路径
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 类型 1 菜单 2 按钮
     */
    private Byte type;

    /**
     * 创建时间
     */

    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色关系
     */
    private List<RolePermission> rplist;


}
