/**
 * Copyright (C), 2019-2019
 * FileName: StudentVo
 * Author:   binzh
 * Date:     2019/3/5 10:56
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;

import  lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class FamilyStudentVo {

    @ApiModelProperty("学生ID")
    private Integer id;

    @ApiModelProperty("学生姓名")
    private String sName;

    @ApiModelProperty("学生性别")
    private String sGender;

    @ApiModelProperty("学生卡号")
    private String cardNumber;

    @ApiModelProperty("学生家长称呼")
    private String relation;

    @ApiModelProperty("学生家长关系表ID")
    private Long fsId;
}
