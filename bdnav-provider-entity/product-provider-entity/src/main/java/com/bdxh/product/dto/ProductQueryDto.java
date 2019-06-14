package com.bdxh.product.dto;

import com.bdxh.common.base.page.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 商品列表查询dto
 * @author: xuyuan
 * @create: 2019-01-22 17:01
 **/
@Data
public class ProductQueryDto extends Query implements Serializable {

    private static final long serialVersionUID = -2242168395369859125L;
    /**
     * 商品展示名称
     */
    @ApiModelProperty("商品展示名称")
    private String productShowName;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @ApiModelProperty("商品类型 1 单品 2 套餐")
    private Byte productType;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    @ApiModelProperty("商品上下架状态 1 下架 2 上架(前端不需要传递，后端自己获取)")
    private Byte sellStatus = 2;

    /**
     * 业务类型 1 微校服务
     */
    @ApiModelProperty("业务类型 1 微校服务(前端不需要传递，后端自己获取)")
    private Byte businessType = 1;
}
