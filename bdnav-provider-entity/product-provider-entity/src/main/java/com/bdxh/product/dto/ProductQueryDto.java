package com.bdxh.product.dto;

import com.bdxh.product.page.Query;
import lombok.Data;

import java.util.Date;

/**
 * @description: 商品列表查询dto
 * @author: xuyuan
 * @create: 2019-01-22 17:01
 **/
@Data
public class ProductQueryDto extends Query {

    /**
     * 商品展示名称
     */
    private String productShowName;

    /**
     * 商品类型 1 单品 2 套餐
     */
    private Byte productType;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    private Byte sellStatus;

    /**
     * 业务类型 1 微校服务
     */
    private Byte businessType;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endTime;

}
