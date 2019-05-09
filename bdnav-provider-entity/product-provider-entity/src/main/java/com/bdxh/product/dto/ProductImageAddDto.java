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




}