/**
 * Copyright (C), 2019-2019
 * FileName: StudentQueryDto
 * Author:   bdxh
 * Date:     2019/2/28 12:27
 * Description:
 * History:
 * <author>          <time>              <version>             <desc>
 * bin           2019/2/28 12:27           版本号         学生查询条件Dto类
 */
package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StudentQueryDto extends Query implements Serializable {


    private static final long serialVersionUID = -1551326503823454942L;
    /**
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    private String schoolName;


    /**
     * 学生姓名
     */
    @ApiModelProperty(value="学生姓名")
    private String name;


    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty(value="是否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学生学号")
    private String cardNumber;

    /**
     * 学校Code
     */
    @ApiModelProperty(value = "学校Code")
    private String schoolCode;

    /**
     * 院校IDs
     */
    @ApiModelProperty(value = "院校IDs")
    private String classIds;



}
