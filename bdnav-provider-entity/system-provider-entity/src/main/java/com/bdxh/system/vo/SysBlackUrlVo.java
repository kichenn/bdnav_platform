package com.bdxh.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author WanMing
 * @date 2019/6/20 14:28
 */
@Data
public class SysBlackUrlVo {


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;


    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    private String name;

    /**
     * 网站域名或者ip
     */
    @ApiModelProperty("网站域名或者ip")
    private String ip;

    /**
     * 网站来源 1 北斗 2 金山 3 360 4 百度
     */
    @ApiModelProperty("网站来源 1 北斗 2 金山 3 360 4 百度")
    private Byte origin;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
