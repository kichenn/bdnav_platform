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
import lombok.Data;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Data
public class FamilyQueryDto extends Query implements Serializable {

    private static final long serialVersionUID = 1973743633551660368L;
    /**
     * 家长卡号
     */
    @ApiModelProperty("家长卡号")
    private String cardNumber;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 学校Code
     */
    @ApiModelProperty("学校Code")
    private String schoolCode;

    /**
     * 家长姓名
     */
    @ApiModelProperty("家长姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty("是否激活 1 未激活 2 激活")
    private Integer activate;



}