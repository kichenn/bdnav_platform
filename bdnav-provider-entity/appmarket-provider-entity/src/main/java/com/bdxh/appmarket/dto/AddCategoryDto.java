package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 增加应用分类dto
 * @author: xuyuan
 * @create: 2019-03-05 09:41
 **/
@Data
@ApiModel("增加应用分类dto")
public class AddCategoryDto implements Serializable {

    private static final long serialVersionUID = 4667963106846256232L;

    /**
     * 分类名称
     */
    @NotEmpty(message = "分类名称不能为空")
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 图标
     */
    @NotEmpty(message = "图标不能为空")
    @ApiModelProperty("图标")
    private String icon;

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