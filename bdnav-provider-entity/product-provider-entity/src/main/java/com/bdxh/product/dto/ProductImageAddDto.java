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

    @ApiModelProperty("商品图片类型")
    private ProductImgTypeEnum productImgTypeEnum;

}