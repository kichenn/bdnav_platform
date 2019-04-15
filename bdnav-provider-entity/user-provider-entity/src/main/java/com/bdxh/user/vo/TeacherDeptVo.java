/**
 * Copyright (C), 2019-2019
 * FileName: TeacherDeptVo
 * Author:   binzh
 * Date:     2019/3/9 15:09
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class TeacherDeptVo implements Serializable {

    private static final long serialVersionUID = 8477818210455527983L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 老师工号
     */
    @ApiModelProperty("老师工号")
    private String cardNumber;


    /**
     * 组织架构id
     */
    @ApiModelProperty("组织架构id")
    private Long deptId;

    /**
     * 组织架构名称
     */
    @ApiModelProperty("组织架构名称")
    private String deptName;

    /**
     * 组织架构ids
     */
    @ApiModelProperty("组织架构ids")
    private String deptIds;

    /**
     * 组织架构names
     */
    @ApiModelProperty("组织架构names")
    private String deptNames;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
