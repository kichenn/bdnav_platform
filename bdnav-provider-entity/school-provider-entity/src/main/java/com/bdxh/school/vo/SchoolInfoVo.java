package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchoolInfoVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     *
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("学校地区代码")
    private String schoolAreaCode;

    @ApiModelProperty("学校地区")
    private String schoolArea;

    @ApiModelProperty("学校地址")
    private String schoolAddress;

    @ApiModelProperty("学校logo地址")
    private String schoolLogo;

    @ApiModelProperty("学校logo图片地址")
    private String schoolLogoName;

    @ApiModelProperty("学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校")
    private Byte schoolType;

    @ApiModelProperty("学校类型Value")
    private String schoolTypeValue;

    @ApiModelProperty("学校性质 1 公立 2 私立")
    private String schoolNature;

    @ApiModelProperty("学校性质Value")
    private String schoolNatureValue;

    @ApiModelProperty("教职工数量")
    private Integer teacherNums;

    @ApiModelProperty("学生数量")
    private Integer studentNums;

    @ApiModelProperty("联系人")
    private String schoolContact;

    @ApiModelProperty("联系人手机")
    private String contactPhone;

    @ApiModelProperty("联系人职位")
    private String contactPosition;

    @ApiModelProperty("学校key")
    private String schoolKey;

    @ApiModelProperty("学校secret")
    private String schoolSecret;

    /*
    @ApiModelProperty("服务商key")
    private String appKey;

    @ApiModelProperty("服务商secret")
    private String appSecret;*/

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("修改时间")
    private String updateDate;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;
}
