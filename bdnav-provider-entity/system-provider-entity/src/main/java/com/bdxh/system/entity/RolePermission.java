package com.bdxh.system.entity;


import javax.persistence.*;

@Table(name = "sys_role_permission")
public class RolePermission {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限id
     */
    @Column(name = "permission_id")
    private Long permissionId;


    /**
     * 启用状态 1 开 2关
     */
    @Column(name = "rp_switch")
    private Integer RpSwitch;

    /**
     * "选中状态 1 选中 2未选中"
     */
    @Column(name = "selected")
    private Integer selected;

    /**
     * "是否选中 1 选中 2未选中"
     */
    @Column(name = "checked")
    private Integer checked;


    /**
     * "半选状态 1 选中 2未选中"
     */
    @Column(name = "indeterminate")
    private Integer indeterminate;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取权限id
     *
     * @return permission_id - 权限id
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限id
     *
     * @param permissionId 权限id
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获取开关权限
     * @return
     */
    public Integer getRpSwitch() {
        return RpSwitch;
    }

    /**
     * 设置开关权限
     * @param rpSwitch
     */
    public void setRpSwitch(Integer rpSwitch) {
        RpSwitch = rpSwitch;
    }

    /**
     * 获取选中状态
     * @return
     */
    public Integer getSelected() {

        return selected;
    }
    /**
     * 设置选中状态
     * @param
     */
    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(Integer indeterminate) {
        this.indeterminate = indeterminate;
    }


}