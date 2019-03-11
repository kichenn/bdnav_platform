package com.bdxh.system.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_dept")
public class Dept{
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级部门id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 父级部门ids
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private Long deptName;

    /**
     * 部门全称
     */
    @Column(name = "dept_full_name")
    private String deptFullName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 部门层级
     */
    private Byte level;

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
     * 备注
     */
    private String remark;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取父级部门id
     *
     * @return parent_id - 父级部门id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级部门id
     *
     * @param parentId 父级部门id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父级部门ids
     *
     * @return parent_ids - 父级部门ids
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父级部门ids
     *
     * @param parentIds 父级部门ids
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * 获取部门名称
     *
     * @return dept_name - 部门名称
     */
    public Long getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(Long deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取部门全称
     *
     * @return dept_full_name - 部门全称
     */
    public String getDeptFullName() {
        return deptFullName;
    }

    /**
     * 设置部门全称
     *
     * @param deptFullName 部门全称
     */
    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName == null ? null : deptFullName.trim();
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
     * 获取部门层级
     *
     * @return level - 部门层级
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置部门层级
     *
     * @param level 部门层级
     */
    public void setLevel(Byte level) {
        this.level = level;
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