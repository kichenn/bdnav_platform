package com.bdxh.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 增加应用配置dto
 * @author: xuyuan
 * @create: 2019-03-21 17:34
 **/
@Data
public class AddAppConfigDto implements Serializable {

    private static final long serialVersionUID = 6734378389734302751L;

    /**
     * 应用名称
     */
    @ApiModelProperty("应用配置名称")
    private String appName;

    /**
     * 应用描述
     */
    @ApiModelProperty("应用配置描述")
    private String appDesc;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
