package com.bdxh.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_family_fence")
public class FamilyFence implements Serializable {

    private static final long serialVersionUID = -1795209385053184011L;

    /**
     * 主键
     */
    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

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
     * 家长号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 家长id
     */
    @Column(name = "family_id")
    private Long familyId;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 学生学号
     */
    @Column(name = "student_number")
    private String studentNumber;

    /**
     * 围栏id 百度返回
     */
    @Column(name = "fence_id")
    private Integer fenceId;

    /**
     * 围栏名称
     */
    @Column(name = "fence_name")
    private String fenceName;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 半径
     */
    private BigDecimal radius;

    /**
     * 坐标类型
     */
    @Column(name = "coord_type")
    private String coordType;

    /**
     * 围栏去噪参数
     */
    private Integer denoise;

    /**
     * 状态 1 启用 2 禁用
     */
    private Byte status;

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
     * 获取家长号
     *
     * @return card_number - 家长号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置家长号
     *
     * @param cardNumber 家长号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
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
     * 获取学生姓名
     *
     * @return student_name - 学生姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生姓名
     *
     * @param studentName 学生姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    /**
     * 获取学生学号
     *
     * @return student_number - 学生学号
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * 设置学生学号
     *
     * @param studentNumber 学生学号
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber == null ? null : studentNumber.trim();
    }

    /**
     * 获取围栏id 百度返回
     *
     * @return fence_id - 围栏id 百度返回
     */
    public Integer getFenceId() {
        return fenceId;
    }

    /**
     * 设置围栏id 百度返回
     *
     * @param fenceId 围栏id 百度返回
     */
    public void setFenceId(Integer fenceId) {
        this.fenceId = fenceId;
    }

    /**
     * 获取围栏名称
     *
     * @return fence_name - 围栏名称
     */
    public String getFenceName() {
        return fenceName;
    }

    /**
     * 设置围栏名称
     *
     * @param fenceName 围栏名称
     */
    public void setFenceName(String fenceName) {
        this.fenceName = fenceName == null ? null : fenceName.trim();
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取维度
     *
     * @return latitude - 维度
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 设置维度
     *
     * @param latitude 维度
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取半径
     *
     * @return radius - 半径
     */
    public BigDecimal getRadius() {
        return radius;
    }

    /**
     * 设置半径
     *
     * @param radius 半径
     */
    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    /**
     * 获取坐标类型
     *
     * @return coord_type - 坐标类型
     */
    public String getCoordType() {
        return coordType;
    }

    /**
     * 设置坐标类型
     *
     * @param coordType 坐标类型
     */
    public void setCoordType(String coordType) {
        this.coordType = coordType == null ? null : coordType.trim();
    }

    /**
     * 获取围栏去噪参数
     *
     * @return denoise - 围栏去噪参数
     */
    public Integer getDenoise() {
        return denoise;
    }

    /**
     * 设置围栏去噪参数
     *
     * @param denoise 围栏去噪参数
     */
    public void setDenoise(Integer denoise) {
        this.denoise = denoise;
    }

    /**
     * 获取状态 1 启用 2 禁用
     *
     * @return status - 状态 1 启用 2 禁用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态 1 启用 2 禁用
     *
     * @param status 状态 1 启用 2 禁用
     */
    public void setStatus(Byte status) {
        this.status = status;
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