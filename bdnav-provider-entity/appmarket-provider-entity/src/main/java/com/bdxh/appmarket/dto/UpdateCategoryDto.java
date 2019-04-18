package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 增加应用分类dto
 * @author: xuyuan
 * @create: 2019-03-05 09:41
 **/
@Data
@ApiModel("增加应用分类dto")
public class UpdateCategoryDto implements Serializable {

    private static final long serialVersionUID = -5292167927179260675L;

    @NotNull(message = "应用分类id不能为空")
    @ApiModelProperty("应用分类id")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;
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
