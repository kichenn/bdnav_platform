package com.bdxh.appmarket.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 分类查询dto
 * @author: xuyuan
 * @create: 2019-03-05 11:02
 **/
@Data
@ApiModel("分类查询dto")
public class CategoryQueryDto extends Query {

    @ApiModelProperty("应用分类id")
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 图标地址
     */
    @ApiModelProperty("图标地址")
    private String iconUrl;

    /**
     * 图标名称
     */
    @ApiModelProperty("图标名称")
    private String iconName;

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
