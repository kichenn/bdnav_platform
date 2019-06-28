package com.bdxh.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageAddDto implements Serializable {

    private static final long serialVersionUID = 5295579643785690138L;

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
     * 图片类型  1 图标图片  2 商品图片详情
     */
    @ApiModelProperty("图片类型 1图标图片  2商品图片详情")
    private String imgType;

}