package com.bdxh.product.dto;

import com.bdxh.product.enums.ProductImgTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageAddDto implements Serializable {

    private static final long serialVersionUID = 5295579643785690138L;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("图片名称")
    private String imageName;

    @ApiModelProperty("图片排序值")
    private Byte sort;

    @ApiModelProperty("商品图片类型 1.普通图标 2.商品详情图标")
    private Byte imgType;

}