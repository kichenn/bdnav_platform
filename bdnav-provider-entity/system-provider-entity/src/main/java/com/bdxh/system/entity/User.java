package com.bdxh.system.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sys_user")
public class User {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 性别 1 男 2 女
     */
    private Byte sex;

    /**
     * 出生日期
     */
    private String birth;


    /**
     * 手机
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 部门id
     */
    @Column(name = "dept_id")
    private Long deptId;


    /**
     * 状态 1 正常 2 锁定
     */
    private Byte status;

    /**
     * 类型 1 普通用户 2 管理员
     */
    private Byte type;

    /**
     * 头像图片
     */
    @Column(name = "image")
    private String Image;

    /**
     * 文件地址
     */
    @Column(name = "img_file_address")
    private String ImgFileAddress;

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

    private List<UserRole> urList;

    /**
     * 获取用户角色关系
     * @return
     */
    public List<UserRole> getUrList() {
        return urList;
    }

    /**
     * 设置用户角色关系
     * @param urList
     */
    public void setUrList(List<UserRole> urList) {
        this.urList = urList;
    }

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
     * 获取头像
     *
     * @return image - 头像
     */
    public String getImage() {
        return Image;
    }



    /**
     * 设置头像
     *
     * @param image 头像
     */
    public void setImage(String image) {
        this.Image = image == null ? null : image.trim();
    }
    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取姓名
     *
     * @return real_name - 姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置姓名
     *
     * @param realName 姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取性别 1 男 2 女
     *
     * @return sex - 性别 1 男 2 女
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别 1 男 2 女
     *
     * @param sex 性别 1 男 2 女
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public String getBirth() {
        return birth;
    }


    /**
     * 设置出生日期
     *
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    /**
     * 获取手机
     *
     * @return phone - 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机
     *
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取电子邮件
     *
     * @return email - 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }


    /**
     * 获取部门id
     *
     * @return dept_id - 部门id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置部门id
     *
     * @param deptId 部门id
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取状态 1 正常 2 锁定
     *
     * @return status - 状态 1 正常 2 锁定
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态 1 正常 2 锁定
     *
     * @param status 状态 1 正常 2 锁定
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取类型 1 普通用户 2 管理员
     *
     * @return type - 类型 1 普通用户 2 管理员
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型 1 普通用户 2 管理员
     *
     * @param type 类型 1 普通用户 2 管理员
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


    public String getImgFileAddress() {
        return ImgFileAddress;
    }

    public void setImgFileAddress(String imgFileAddress) {
        this.ImgFileAddress = imgFileAddress == null ? null : imgFileAddress.trim();
    }

}