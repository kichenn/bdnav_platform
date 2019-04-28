package com.bdxh.order.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 订单明细dto
 * @author: xuyuan
 * @create: 2019-01-09 15:13
 **/
@Data
public class OrderItemUpdateDto implements Serializable {

    private static final long serialVersionUID = 5569510912549853541L;

    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    private BigDecimal productSellPrice;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @NotNull(message = "商品类型不能为空")
    private Byte productType;

    /**
     * 单品传本身id 套餐传所有id使用逗号分隔
     */
    @NotEmpty(message = "商品条目不能为空")
    private String productItem;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    private Byte productNum;

    /**
     * 商品其他信息
     */
    private String productExtra;

}
