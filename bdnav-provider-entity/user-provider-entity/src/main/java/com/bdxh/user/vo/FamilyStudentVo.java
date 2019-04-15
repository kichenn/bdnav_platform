/**
 * Copyright (C), 2019-2019
 * FileName: StudentVo
 * Author:   binzh
 * Date:     2019/3/5 10:56
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import  lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Data
public class FamilyStudentVo implements Serializable {

    private static final long serialVersionUID = 3817534645043986082L;
    @ApiModelProperty("学生ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("学生姓名")
    private String sName;

    @ApiModelProperty("家长姓名")
    private String fName;

    @ApiModelProperty("学生卡号")
    private String sCardNumber;

    @ApiModelProperty("学校Code")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("学生家长称呼")
    private String relation;

    @ApiModelProperty("学生家长关系表ID")
    private String fsId;

    @ApiModelProperty("家长卡号")
    private String fCardNumber;

    @ApiModelProperty("备注")
    private String  remark;
}
