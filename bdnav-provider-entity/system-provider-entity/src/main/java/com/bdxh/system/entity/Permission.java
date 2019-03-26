package com.bdxh.system.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sys_permission")
public class Permission {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级菜单id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 父级菜单ids
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 路由路径
     */
    private String name;

    /**
     * 菜单名称
     */
    private String title;

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
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;


    /**
     * 角色关系
     */
    private List<RolePermission> rplist;

    /**
     * 备注
     */
    private String remark;

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
     * 获取父级菜单id
     *
     * @return parent_id - 父级菜单id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级菜单id
     *
     * @param parentId 父级菜单id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父级菜单ids
     *
     * @return parent_ids - 父级菜单ids
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父级菜单ids
     *
     * @param parentIds 父级菜单ids
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * 获取路由路径
     */
    public String getName() {
        return name;
    }

    /**
     * 设置路由路径
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取菜单名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置菜单名称
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取前端组件
     *
     * @return component - 前端组件
     */
    public String getComponent() {
        return component;
    }

    /**
     * 设置前端组件
     *
     * @param component 前端组件
     */
    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取类型 1 菜单 2 按钮
     *
     * @return type - 类型 1 菜单 2 按钮
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型 1 菜单 2 按钮
     *
     * @param type 类型 1 菜单 2 按钮
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public Long getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(Long operator) {
        this.operator = operator;
    }



    public List<RolePermission> getRplist() {
        return rplist;
    }

    public void setRplist(List<RolePermission> rplist) {
        this.rplist = rplist;
    }


    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}