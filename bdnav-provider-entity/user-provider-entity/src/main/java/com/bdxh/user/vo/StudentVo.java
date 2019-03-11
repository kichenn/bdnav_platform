/**
 * Copyright (C), 2019-2019
 * FileName: StudentVo
 * Author:   binzh
 * Date:     2019/3/5 16:15
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
@Data
public class StudentVo {
    @ApiModelProperty("学生Id")
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

    @ApiModelProperty("班级ID")
    private  String classId;

    @ApiModelProperty("学校Code")
    private String schoolCode;
}