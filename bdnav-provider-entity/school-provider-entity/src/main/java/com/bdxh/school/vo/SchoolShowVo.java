package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SchoolShowVo {


    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("详情地址")
    private String schoolArea;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区或者县")
    private String areaOrcounty;

    @ApiModelProperty("学校类型key")
    private Byte schoolType;

    @ApiModelProperty("学校类型value")
    private String schoolTypeValue;

    @ApiModelProperty("教职工人数")
    private Integer teacherNums;

    @ApiModelProperty("学生人数")
    private Integer studentNums;
}
