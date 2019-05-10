package com.bdxh.product.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageUpdateDto implements Serializable {

    private static final long serialVersionUID = 5295579643785690138L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
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

}