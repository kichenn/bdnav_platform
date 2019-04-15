package com.bdxh.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_teacher_dept")
public class TeacherDept implements Serializable {

    private static final long serialVersionUID = 6769383748212626201L;

    /**
     * 主键
     */
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 老师工号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 老师id
     */
    @Column(name = "teacher_id")
    private Long teacherId;

    /**
     * 组织架构id
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 组织架构名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 组织架构ids
     */
    @Column(name = "dept_ids")
    private String deptIds;

    /**
     * 组织架构names
     */
    @Column(name = "dept_names")
    private String deptNames;

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
     * 获取老师工号
     *
     * @return card_number - 老师工号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置老师工号
     *
     * @param cardNumber 老师工号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取老师id
     *
     * @return teacher_id - 老师id
     */
    public Long getTeacherId() {
        return teacherId;
    }

    /**
     * 设置老师id
     *
     * @param teacherId 老师id
     */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取组织架构id
     *
     * @return dept_id - 组织架构id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置组织架构id
     *
     * @param deptId 组织架构id
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取组织架构名称
     *
     * @return dept_name - 组织架构名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置组织架构名称
     *
     * @param deptName 组织架构名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * 获取组织架构ids
     *
     * @return dept_ids - 组织架构ids
     */
    public String getDeptIds() {
        return deptIds;
    }

    /**
     * 设置组织架构ids
     *
     * @param deptIds 组织架构ids
     */
    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds == null ? null : deptIds.trim();
    }

    /**
     * 获取组织架构names
     *
     * @return dept_names - 组织架构names
     */
    public String getDeptNames() {
        return deptNames;
    }

    /**
     * 设置组织架构names
     *
     * @param deptNames 组织架构names
     */
    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames == null ? null : deptNames.trim();
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