package com.bdxh.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 2921436647270652540L;

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
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 校区名称
     */
    @Column(name = "campus_name")
    private String campusName;

    /**
     * 老师姓名
     */
    private String name;

    /**
     * 老师性别
     */
    private Byte gender;

    /**
     * 出身日期
     */
    private String birth;

    /**
     * 老师职称
     */
    private String position;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 工号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * qq号
     */
    @Column(name = "qq_number")
    private String qqNumber;

    /**
     * 微信号
     */
    @Column(name = "wx_number")
    private String wxNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 图像
     */
    private String image;

    /**
     * 图像名称
     */
    @ApiModelProperty("图像名称")
    @Column(name = "image_name")
    private String imageName;

    /**
     * 民族名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 宿舍地址
     */
    @Column(name = "dormitory_address")
    private String dormitoryAddress;

    /**
     * 家庭住址
     */
    private String adress;

    /**
     * 物理卡号
     */
    @Column(name = "physical_number")
    private String physicalNumber;

    /**
     * 物理芯片号
     */
    @Column(name = "physical_chip_number")
    private String physicalChipNumber;

    /**
     * 是否激活 1 未激活 2 激活
     */
    private Byte activate;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改日期
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
     * 获取校区名称
     *
     * @return campus_name - 校区名称
     */
    public String getCampusName() {
        return campusName;
    }

    /**
     * 设置校区名称
     *
     * @param campusName 校区名称
     */
    public void setCampusName(String campusName) {
        this.campusName = campusName == null ? null : campusName.trim();
    }

    /**
     * 获取老师姓名
     *
     * @return name - 老师姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置老师姓名
     *
     * @param name 老师姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取老师性别
     *
     * @return gender - 老师性别
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * 设置老师性别
     *
     * @param gender 老师性别
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * 获取出身日期
     *
     * @return birth - 出身日期
     */
    public String getBirth() {
        return birth;
    }

    /**
     * 设置出身日期
     *
     * @param birth 出身日期
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    /**
     * 获取老师职称
     *
     * @return position - 老师职称
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置老师职称
     *
     * @param position 老师职称
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取工号
     *
     * @return card_number - 工号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置工号
     *
     * @param cardNumber 工号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取身份证号
     *
     * @return idcard - 身份证号
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 设置身份证号
     *
     * @param idcard 身份证号
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    /**
     * 获取qq号
     *
     * @return qq_number - qq号
     */
    public String getQqNumber() {
        return qqNumber;
    }

    /**
     * 设置qq号
     *
     * @param qqNumber qq号
     */
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    /**
     * 获取微信号
     *
     * @return wx_number - 微信号
     */
    public String getWxNumber() {
        return wxNumber;
    }

    /**
     * 设置微信号
     *
     * @param wxNumber 微信号
     */
    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber == null ? null : wxNumber.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取图像
     *
     * @return image - 图像
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置图像
     *
     * @param image 图像
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取民族名称
     *
     * @return nation_name - 民族名称
     */
    public String getNationName() {
        return nationName;
    }

    /**
     * 设置民族名称
     *
     * @param nationName 民族名称
     */
    public void setNationName(String nationName) {
        this.nationName = nationName == null ? null : nationName.trim();
    }

    /**
     * 获取宿舍地址
     *
     * @return dormitory_address - 宿舍地址
     */
    public String getDormitoryAddress() {
        return dormitoryAddress;
    }

    /**
     * 设置宿舍地址
     *
     * @param dormitoryAddress 宿舍地址
     */
    public void setDormitoryAddress(String dormitoryAddress) {
        this.dormitoryAddress = dormitoryAddress == null ? null : dormitoryAddress.trim();
    }

    /**
     * 获取家庭住址
     *
     * @return adress - 家庭住址
     */
    public String getAdress() {
        return adress;
    }

    /**
     * 设置家庭住址
     *
     * @param adress 家庭住址
     */
    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    /**
     * 获取物理卡号
     *
     * @return physical_number - 物理卡号
     */
    public String getPhysicalNumber() {
        return physicalNumber;
    }

    /**
     * 设置物理卡号
     *
     * @param physicalNumber 物理卡号
     */
    public void setPhysicalNumber(String physicalNumber) {
        this.physicalNumber = physicalNumber == null ? null : physicalNumber.trim();
    }

    /**
     * 获取物理芯片号
     *
     * @return physical_chip_number - 物理芯片号
     */
    public String getPhysicalChipNumber() {
        return physicalChipNumber;
    }

    /**
     * 设置物理芯片号
     *
     * @param physicalChipNumber 物理芯片号
     */
    public void setPhysicalChipNumber(String physicalChipNumber) {
        this.physicalChipNumber = physicalChipNumber == null ? null : physicalChipNumber.trim();
    }

    /**
     * 获取是否激活 1 未激活 2 激活
     *
     * @return activate - 是否激活 1 未激活 2 激活
     */
    public Byte getActivate() {
        return activate;
    }

    /**
     * 设置是否激活 1 未激活 2 激活
     *
     * @param activate 是否激活 1 未激活 2 激活
     */
    public void setActivate(Byte activate) {
        this.activate = activate;
    }

    /**
     * 获取创建日期
     *
     * @return create_date - 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改日期
     *
     * @return update_date - 修改日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改日期
     *
     * @param updateDate 修改日期
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

    /**
     * 获取头像名称
     *
     * @return imageName - 获取头像名称
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * 设置头像名称
     *
     * @param imageName 头像名称
     */
    public void setImageName(String imageName) {
        this.imageName = remark == null ? null : imageName.trim();
    }

}