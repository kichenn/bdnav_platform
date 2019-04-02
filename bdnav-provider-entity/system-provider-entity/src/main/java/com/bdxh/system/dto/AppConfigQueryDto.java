package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @description: 增加应用配置dto
 * @author: xuyuan
 * @create: 2019-03-21 17:34
 **/
@Data
public class AppConfigQueryDto extends Query {

    /**
     * 应用配置主键
     */
    @ApiModelProperty("应用配置主键")
    private Long id;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
