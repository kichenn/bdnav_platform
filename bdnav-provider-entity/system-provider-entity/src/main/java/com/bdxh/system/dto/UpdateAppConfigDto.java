package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 增加应用配置dto
 * @author: xuyuan
 * @create: 2019-03-21 17:34
 **/
@Data
public class UpdateAppConfigDto implements Serializable {

    private static final long serialVersionUID = 6026287473270217575L;

    /**
     * 应用配置主键
     */
    @NotNull(message = "应用配置主键不能为空")
    @ApiModelProperty("应用配置主键")
    private Long id;

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
