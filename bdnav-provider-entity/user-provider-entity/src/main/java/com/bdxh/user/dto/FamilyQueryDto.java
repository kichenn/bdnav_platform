/**
 * Copyright (C), 2019-2019
 * FileName: FamilyQueryDto
 * Author:   bdxh
 * Date:     2019/2/27 18:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class FamilyQueryDto extends Query implements Serializable {

    private static final long serialVersionUID = 2342455005199240692L;

    /**
     * 主键
     */
    @Id
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
     * 家长姓名
     */
    private String name;

    /**
     * 家长性别
     */
    private Byte gender;

    /**
     * 出身日期
     */
    private String birth;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 家长号
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
     * 民族名称
     */
    @Column(name = "nation_name")
    private String nationName;

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