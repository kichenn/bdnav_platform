/**
 * Copyright (C), 2019-2019
 * FileName: StudentVo
 * Author:   binzh
 * Date:     2019/3/5 16:15
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Data
public class StudentVo implements Serializable {
    private static final long serialVersionUID = -4694086728702829530L;
    @ApiModelProperty("学生Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private  Long sId;

    @ApiModelProperty("学生姓名")
    private String sName;

    @ApiModelProperty("学生学号")
    private String cardNumber;

    @ApiModelProperty("性别")
    private Byte gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("出生年月日")
    private String birth;

    @ApiModelProperty("头像地址")
    private String image;

    @ApiModelProperty("用户头像名称")
    private String imageName;

    @ApiModelProperty("所属校区")
    private String campusName;

    @ApiModelProperty("学院名称")
    private String collegeName;

    @ApiModelProperty("系名称")
    private String facultyName;

    @ApiModelProperty("专业名称")
    private String professionName;

    @ApiModelProperty("年级名称")
    private String gradeName;

    @ApiModelProperty("家长姓名")
    private String fName;

    @ApiModelProperty("家长电话")
    private String fPhone;

    @ApiModelProperty("与家庭成员的关系")
    private String relation;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("班级名称")
    private String className;
    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty("是否激活 1 未激活 2 激活")
    private Byte activate;

    @ApiModelProperty("班级ID")
    private  String classId;

    @ApiModelProperty("学校Code")
    private String schoolCode;
}
