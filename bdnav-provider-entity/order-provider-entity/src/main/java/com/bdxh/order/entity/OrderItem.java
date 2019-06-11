package com.bdxh.order.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name ="t_order_item")
public class OrderItem   {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品价格
     */
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    @Column(name = "product_sell_price")
    private BigDecimal productSellPrice;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @Column(name = "product_type")
    private Byte productType;

    /**
     * 单品传本身id 套餐传所有id使用逗号分隔
     */
    @Column(name = "product_item")
    private String productItem;

    /**
     * 商品数量
     */
    @Column(name = "product_num")
    private Byte productNum;

    /**
     * 商品其他信息
     */
    @Column(name = "product_extra")
    private String productExtra;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


}