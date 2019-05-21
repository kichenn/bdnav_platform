package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class ModifySchoolModeDto {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 学校ID
     */
    @ApiModelProperty("学校ID")
    private Long schoolId;

    /**
     * 学校CODE
     */
    @ApiModelProperty("学校CODE")
    private String schoolCode;

    /**
     * 模式名称
     */
    @ApiModelProperty("模式名称")
    private String modelName;

    /**
     * 使用平台 1安卓 2苹果
     */
    @ApiModelProperty("使用平台 1安卓 2苹果")
    private String platform;

    /**
     * 模式优先级
     */
    @ApiModelProperty("模式优先级")
    private Byte priority;

    /**
     * 可用的应用
     */
    @ApiModelProperty("可用的应用")
    private String usableApp;

    /**
     * 可用的设备
     */
    @ApiModelProperty("可用的设备")
    private String usableDevice;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


}
