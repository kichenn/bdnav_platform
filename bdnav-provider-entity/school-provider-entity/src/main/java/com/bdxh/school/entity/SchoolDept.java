package com.bdxh.school.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_school_dept")
public class SchoolDept implements Serializable {

    private static final long serialVersionUID = -2319232452972727021L;
    
    /**
     * 主键
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
     * 父级部门names
     */
    @Column(name = "parent_names")
    private String parentNames;

    /**
     * 当前路径
     */
    @Column(name = "this_url")
    private String thisUrl;

    /**
     * 学校id
     */
    @Column(name = "school_id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 层级
     */
    private Byte level;

    /**
     * 排序
     */
    private Integer sort;

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
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

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
     * 获取父级部门names
     *
     * @return parent_names - 父级部门names
     */
    public String getParentNames() {
        return parentNames;
    }

    /**
     * 设置父级部门names
     *
     * @param parentNames 父级部门names
     */
    public void setParentNames(String parentNames) {
        this.parentNames = parentNames == null ? null : parentNames.trim();
    }

    /**
     * 获取绝对路径
     *
     * @return parent_names - 父级部门names
     */
    public String getThisUrl() {
        return thisUrl;
    }

    /**
     * 设置绝对路径
     *
     * @param thisUrl 绝对路径
     */
    public void setThisUrl(String thisUrl) {
        this.thisUrl = thisUrl == null ? null : thisUrl.trim();
    }

    /**
     * 获取学校id
     *
     * @return school_id - 学校id
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * 设置学校id
     *
     * @param schoolId 学校id
     */
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取学校编码
     *
     * @return school_code - 学校编码
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * 设置学校编码
     *
     * @param schoolCode 学校编码
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode == null ? null : schoolCode.trim();
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(Byte level) {
        this.level = level;
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
     * 获取操作人姓名
     *
     * @return operator_name - 操作人姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人姓名
     *
     * @param operatorName 操作人姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
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