package com.bdxh.user.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_student_family")
public class StudentFamily {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 学生学号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 家长id
     */
    @Column(name = "family_id")
    private Long familyId;

    /**
     * 家长姓名
     */
    @Column(name = "family_name")
    private String familyName;

    /**
     * 家长号
     */
    @Column(name = "family_number")
    private String familyNumber;

    /**
     * 关系 父亲 母亲 等等
     */
    private String relation;

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
     * 获取学生学号
     *
     * @return card_number - 学生学号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置学生学号
     *
     * @param cardNumber 学生学号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取学生id
     *
     * @return student_id - 学生id
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * 设置学生id
     *
     * @param studentId 学生id
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取家长id
     *
     * @return family_id - 家长id
     */
    public Long getFamilyId() {
        return familyId;
    }

    /**
     * 设置家长id
     *
     * @param familyId 家长id
     */
    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    /**
     * 获取家长姓名
     *
     * @return family_name - 家长姓名
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 设置家长姓名
     *
     * @param familyName 家长姓名
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName == null ? null : familyName.trim();
    }

    /**
     * 获取家长号
     *
     * @return family_number - 家长号
     */
    public String getFamilyNumber() {
        return familyNumber;
    }

    /**
     * 设置家长号
     *
     * @param familyNumber 家长号
     */
    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber == null ? null : familyNumber.trim();
    }

    /**
     * 获取关系 父亲 母亲 等等
     *
     * @return relation - 关系 父亲 母亲 等等
     */
    public String getRelation() {
        return relation;
    }

    /**
     * 设置关系 父亲 母亲 等等
     *
     * @param relation 关系 父亲 母亲 等等
     */
    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
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