package com.bdxh.school.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_school")
public class School implements Serializable {

    private static final long serialVersionUID = 8162066399868878861L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 学校地区代码
     */
    @Column(name = "school_area_code")
    private String schoolAreaCode;

    /**
     * 学校地区
     */
    @Column(name = "school_area")
    private String schoolArea;

    /**
     * 学校地址
     */
    @Column(name = "school_address")
    private String schoolAddress;

    /**
     * 学校logo地址
     */
    @Column(name = "school_logo")
    private String schoolLogo;

    /**
     * 学校logo地址
     */
    @Column(name = "school_logo_name")
    private String schoolLogoName;

    /**
     * 学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
     */
    @Column(name = "school_type")
    private Byte schoolType;

    /**
     * 学校性质 1 公立 2 私立
     */
    @Column(name = "school_nature")
    private String schoolNature;

    /**
     * 教职工数量
     */
    @Column(name = "teacher_nums")
    private Integer teacherNums;

    /**
     * 学生数量
     */
    @Column(name = "student_nums")
    private Integer studentNums;

    /**
     * 联系人
     */
    @Column(name = "school_contact")
    private String schoolContact;

    /**
     * 联系人手机
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 联系人职位
     */
    @Column(name = "contact_position")
    private String contactPosition;

    /**
     * 学校key
     */
    @Column(name = "school_key")
    private String schoolKey;

    /**
     * 学校secret
     */
    @Column(name = "school_secret")
    private String schoolSecret;

    /**
     * 服务商key
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * 服务商secret
     */
    @Column(name = "app_secret")
    private String appSecret;

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
     * 获取学校名称
     *
     * @return school_name - 学校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置学校名称
     *
     * @param schoolName 学校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 获取学校地区代码
     *
     * @return school_area_code - 学校地区代码
     */
    public String getSchoolAreaCode() {
        return schoolAreaCode;
    }

    /**
     * 设置学校地区代码
     *
     * @param schoolAreaCode 学校地区代码
     */
    public void setSchoolAreaCode(String schoolAreaCode) {
        this.schoolAreaCode = schoolAreaCode == null ? null : schoolAreaCode.trim();
    }

    /**
     * 获取学校地区
     *
     * @return school_area - 学校地区
     */
    public String getSchoolArea() {
        return schoolArea;
    }

    /**
     * 设置学校地区
     *
     * @param schoolArea 学校地区
     */
    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea == null ? null : schoolArea.trim();
    }

    /**
     * 获取学校地址
     *
     * @return school_address - 学校地址
     */
    public String getSchoolAddress() {
        return schoolAddress;
    }

    /**
     * 设置学校地址
     *
     * @param schoolAddress 学校地址
     */
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress == null ? null : schoolAddress.trim();
    }

    /**
     * 获取学校logo地址
     *
     * @return school_logo - 学校logo地址
     */
    public String getSchoolLogo() {
        return schoolLogo;
    }

    /**
     * 设置学校logo地址
     *
     * @param schoolLogo 学校logo地址
     */
    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo == null ? null : schoolLogo.trim();
    }

    /**
     * 获取学校logo图片地址
     *
     * @return school_logo - 学校logo地址
     */
    public String getSchoolLogoName() {
        return schoolLogoName;
    }

    /**
     * 设置学校logo图片地址
     *
     * @param schoolLogoName 学校logo图片地址
     */
    public void setSchoolLogoName(String schoolLogoName) {
        this.schoolLogoName = schoolLogoName == null ? null : schoolLogoName.trim();
    }

    /**
     * 获取学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
     *
     * @return school_type - 学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
     */
    public Byte getSchoolType() {
        return schoolType;
    }

    /**
     * 设置学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
     *
     * @param schoolType 学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
     */
    public void setSchoolType(Byte schoolType) {
        this.schoolType = schoolType;
    }

    /**
     * 获取学校性质 1 公立 2 私立
     *
     * @return school_nature - 学校性质 1 公立 2 私立
     */
    public String getSchoolNature() {
        return schoolNature;
    }

    /**
     * 设置学校性质 1 公立 2 私立
     *
     * @param schoolNature 学校性质 1 公立 2 私立
     */
    public void setSchoolNature(String schoolNature) {
        this.schoolNature = schoolNature == null ? null : schoolNature.trim();
    }

    /**
     * 获取教职工数量
     *
     * @return teacher_nums - 教职工数量
     */
    public Integer getTeacherNums() {
        return teacherNums;
    }

    /**
     * 设置教职工数量
     *
     * @param teacherNums 教职工数量
     */
    public void setTeacherNums(Integer teacherNums) {
        this.teacherNums = teacherNums;
    }

    /**
     * 获取学生数量
     *
     * @return student_nums - 学生数量
     */
    public Integer getStudentNums() {
        return studentNums;
    }

    /**
     * 设置学生数量
     *
     * @param studentNums 学生数量
     */
    public void setStudentNums(Integer studentNums) {
        this.studentNums = studentNums;
    }

    /**
     * 获取联系人
     *
     * @return school_contact - 联系人
     */
    public String getSchoolContact() {
        return schoolContact;
    }

    /**
     * 设置联系人
     *
     * @param schoolContact 联系人
     */
    public void setSchoolContact(String schoolContact) {
        this.schoolContact = schoolContact == null ? null : schoolContact.trim();
    }

    /**
     * 获取联系人手机
     *
     * @return contact_phone - 联系人手机
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置联系人手机
     *
     * @param contactPhone 联系人手机
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * 获取联系人职位
     *
     * @return contact_position - 联系人职位
     */
    public String getContactPosition() {
        return contactPosition;
    }

    /**
     * 设置联系人职位
     *
     * @param contactPosition 联系人职位
     */
    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition == null ? null : contactPosition.trim();
    }

    /**
     * 获取学校key
     *
     * @return school_key - 学校key
     */
    public String getSchoolKey() {
        return schoolKey;
    }

    /**
     * 设置学校key
     *
     * @param schoolKey 学校key
     */
    public void setSchoolKey(String schoolKey) {
        this.schoolKey = schoolKey == null ? null : schoolKey.trim();
    }

    /**
     * 获取学校secret
     *
     * @return school_secret - 学校secret
     */
    public String getSchoolSecret() {
        return schoolSecret;
    }

    /**
     * 设置学校secret
     *
     * @param schoolSecret 学校secret
     */
    public void setSchoolSecret(String schoolSecret) {
        this.schoolSecret = schoolSecret == null ? null : schoolSecret.trim();
    }

    /**
     * 获取服务商key
     *
     * @return app_key - 服务商key
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置服务商key
     *
     * @param appKey 服务商key
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * 获取服务商secret
     *
     * @return app_secret - 服务商secret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置服务商secret
     *
     * @param appSecret 服务商secret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
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