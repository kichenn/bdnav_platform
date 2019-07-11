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
     *
     */
    private String college;

    private String profession;

    private String class1;

    private String identity_title;

    private String remark;

    private String physical_card_number;

    private String physical_chip_number;
}
