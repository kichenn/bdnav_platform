package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/6/20 11:53
 */
@Data
public class AddSysBlackUrlDto {

    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    @NotBlank(message = "网站名称不能为空")
    private String name;

    /**
     * 网站域名或者ip
     */
    @ApiModelProperty("网站域名或者ip")
    @NotBlank(message = "URL地址不能为空")
    private String ip;

    /**
     * 网站来源 1 北斗 2 金山 3 360 4 百度
     */
    @ApiModelProperty("网站来源 1 北斗 2 金山 3 360 4 百度")
    @NotNull(message = "网站来源不能为空")
    private Byte origin;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
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

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
