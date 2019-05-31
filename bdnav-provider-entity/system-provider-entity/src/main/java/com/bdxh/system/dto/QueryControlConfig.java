package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryControlConfig extends Query {
    /**
     * 平台 1 andriod 2 ios
     */
    @ApiModelProperty("平台")
    private Byte platform;

    /**
     * 应用名称
     */
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 应用包名
     */
    @ApiModelProperty("应用包名")
    private String appPackage;

    /**
     * 类型 1是隐藏  2是黑盒
     */
    @ApiModelProperty("类型")
    private Byte appType;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateDate;

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
}
