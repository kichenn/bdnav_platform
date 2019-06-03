package com.bdxh.order.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "t_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -4996582832745282975L;

    /**
     * 主键
     */
    @Id
    @Column(name = "item_no")
    private Long itemNo;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 用户di
     */
    @Column(name = "user_id")
    private Long userId;

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
     * 业务状态 1 未处理 2 处理中 3 已处理
     */
    @Column(name = "business_status")
    private Byte businessStatus;

    /**
     * 业务号
     */
    @Column(name = "business_no")
    private String businessNo;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


}