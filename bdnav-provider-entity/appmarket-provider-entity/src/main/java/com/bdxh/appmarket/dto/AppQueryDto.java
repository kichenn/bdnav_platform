package com.bdxh.appmarket.dto;

import com.bdxh.appmarket.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 查询应用dto
 * @author: xuyuan
 * @create: 2019-03-05 11:31
 **/
@Data
@ApiModel("查询应用dto")
public class AppQueryDto extends Query {

    @ApiModelProperty("应用id")
    /**
     * 应用id
     */
    private Long id;

    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    private Long categoryId;

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
     * 应用图标
     */
    @ApiModelProperty("应用图标")
    private String appIcon;

    /**
     * 应用描述
     */
    @ApiModelProperty("应用描述")
    private String appDesc;

    /**
     *  状态 1 上架 2 下架
     */
    @ApiModelProperty("状态 1 上架 2 下架")
    private Byte status = 2;

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
     * 操作姓名
     */
    @ApiModelProperty("操作姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
