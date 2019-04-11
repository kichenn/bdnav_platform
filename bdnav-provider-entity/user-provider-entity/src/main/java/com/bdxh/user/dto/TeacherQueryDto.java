/**
 * Copyright (C), 2019-2019
 * FileName: TeacherQueryDto
 * Author:   bdxh
 * Date:     2019/3/1 10:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherQueryDto extends Query implements Serializable {


    private static final long serialVersionUID = 9116556926740499311L;
    /**
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    private String schoolName;

    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    private String schoolCode;

    /**
     * 老师姓名
     */
    @ApiModelProperty(value="老师姓名")
    private String name;

    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String cardNumber;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty(value="是否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 电话号码
     */
    @ApiModelProperty(value="电话号码")
    private String phone;



}
