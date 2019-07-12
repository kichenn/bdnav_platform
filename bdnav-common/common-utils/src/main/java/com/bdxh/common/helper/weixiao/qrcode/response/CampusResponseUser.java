package com.bdxh.common.helper.weixiao.qrcode.response;

import lombok.Data;

/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/7/11 15:02
 */
@Data
public class CampusResponseUser {

    /**
     * 学号
     */
    private String card_number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年级
     */
    private String grade;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String profession;

    /**
     * 班级
     */
    private String class1;

    /**
     * 职称
     */
    private String identity_title;

    /**
     * 身份认证自定义字段
     */
    private String remark;

    /**
     * 物理卡卡号
     */
    private String physical_card_number;

    /**
     * 物理芯片号
     */
    private String physical_chip_number;
}
