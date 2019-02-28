package com.bdxh.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherDto implements Serializable {

    private static final long serialVersionUID = 2921436647270652540L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校id
     */
    @NotNull(message = "学校id不能为空")
    private Long schoolId;

    /**
     * 学校编码
     */
    @NotNull(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 学校名称
     */
    @NotNull(message = "学校名称不能为空")
    private String schoolName;

    /**
     * 校区名称
     */
    @NotNull(message = "校区名称不能为空")
    private String campusName;

    /**
     * 老师姓名
     */
    @NotNull(message = "老师姓名不能为空")
    private String name;

    /**
     * 老师性别
     */
    @NotNull(message = "老师性别不能为空")
    private Byte gender;

    /**
     * 出身日期
     */
    private String birth;

    /**
     * 老师职称
     */
    @NotNull(message = "老师职称不能为空")
    private String position;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String phone;

    /**
     * 工号
     */
    @NotNull(message = "工号不能为空")
    private String cardNumber;

    /**
     * 身份证号
     */
    @NotNull(message = "身份证号不能为空")
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
    @NotNull(message = "学校id不能为空")
    private String image;

    /**
     * 民族名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 宿舍地址
     */
    @NotNull(message = "宿舍地址不能为空")
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


}