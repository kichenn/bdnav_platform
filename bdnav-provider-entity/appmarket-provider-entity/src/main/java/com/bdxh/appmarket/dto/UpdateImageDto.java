package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 增加应用图片dto
 * @author: xuyuan
 * @create: 2019-04-11 14:43
 **/
@Data
@ApiModel("更新应用图片dto")
public class UpdateImageDto implements Serializable {

    private static final long serialVersionUID = 4006160056908199375L;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    @ApiModelProperty("主键")
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
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imageUrl;

    /**
     * 图片名称
     */
    @ApiModelProperty("图片名称")
    private String imageName;

    /**
     * 图片顺序
     */
    @ApiModelProperty("图片顺序")
    private Byte sort;

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
