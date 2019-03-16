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
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class StudentQueryDto extends Query implements Serializable {

    private static final long serialVersionUID = -9120796138685156209L;

    /**
     * 主键
     */
    @ApiModelProperty(value="学生ID")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty(value="学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty(value="学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    private String schoolName;

    /**
     * 校区名称
     */
    @ApiModelProperty(value="校区名称")
    private String campusName;

    /**
     * 学院名称
     */
    @ApiModelProperty(value="学院名称")
    private String collegeName;

    /**
     * 系名称
     */
    @ApiModelProperty(value="系名称")
    private String facultyName;

    /**
     * 专业名称
     */
    @ApiModelProperty(value="专业名称")
    private String professionName;

    /**
     * 年级名称
     */
    @ApiModelProperty(value="年级名称")
    private String gradeName;

    /**
     * 班级名称
     */
    @ApiModelProperty(value="班级名称")
    private String className;

    /**
     * 班级id
     */
    @ApiModelProperty(value="班级id")
    private Long classId;

    /**
     * 组织架构ids
     */
    @ApiModelProperty(value="组织架构ids")
    private Long classIds;

    /**
     * 组织架构名称names
     */
    @ApiModelProperty(value="组织架构名称names")
    private String classNames;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value="学生姓名")
    private String name;

    /**
     * 学生性别
     */
    @ApiModelProperty(value="学生性别")
    private Byte gender;

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

}
