package com.bdxh.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_item0")
public class OrderItem {
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
    private String remark;

    /**
     * 获取主键
     *
     * @return item_no - 主键
     */
    public Long getItemNo() {
        return itemNo;
    }

    /**
     * 设置主键
     *
     * @param itemNo 主键
     */
    public void setItemNo(Long itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取学校编码
     *
     * @return school_code - 学校编码
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * 设置学校编码
     *
     * @param schoolCode 学校编码
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode == null ? null : schoolCode.trim();
    }

    /**
     * 获取用户di
     *
     * @return user_id - 用户di
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户di
     *
     * @param userId 用户di
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * 获取商品价格
     *
     * @return product_price - 商品价格
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 设置商品价格
     *
     * @param productPrice 商品价格
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * 获取商品售价
     *
     * @return product_sell_price - 商品售价
     */
    public BigDecimal getProductSellPrice() {
        return productSellPrice;
    }

    /**
     * 设置商品售价
     *
     * @param productSellPrice 商品售价
     */
    public void setProductSellPrice(BigDecimal productSellPrice) {
        this.productSellPrice = productSellPrice;
    }

    /**
     * 获取商品类型 1 单品 2 套餐
     *
     * @return product_type - 商品类型 1 单品 2 套餐
     */
    public Byte getProductType() {
        return productType;
    }

    /**
     * 设置商品类型 1 单品 2 套餐
     *
     * @param productType 商品类型 1 单品 2 套餐
     */
    public void setProductType(Byte productType) {
        this.productType = productType;
    }

    /**
     * 获取单品传本身id 套餐传所有id使用逗号分隔
     *
     * @return product_item - 单品传本身id 套餐传所有id使用逗号分隔
     */
    public String getProductItem() {
        return productItem;
    }

    /**
     * 设置单品传本身id 套餐传所有id使用逗号分隔
     *
     * @param productItem 单品传本身id 套餐传所有id使用逗号分隔
     */
    public void setProductItem(String productItem) {
        this.productItem = productItem == null ? null : productItem.trim();
    }

    /**
     * 获取商品数量
     *
     * @return product_num - 商品数量
     */
    public Byte getProductNum() {
        return productNum;
    }

    /**
     * 设置商品数量
     *
     * @param productNum 商品数量
     */
    public void setProductNum(Byte productNum) {
        this.productNum = productNum;
    }

    /**
     * 获取商品其他信息
     *
     * @return product_extra - 商品其他信息
     */
    public String getProductExtra() {
        return productExtra;
    }

    /**
     * 设置商品其他信息
     *
     * @param productExtra 商品其他信息
     */
    public void setProductExtra(String productExtra) {
        this.productExtra = productExtra == null ? null : productExtra.trim();
    }

    /**
     * 获取业务状态 1 未处理 2 处理中 3 已处理
     *
     * @return business_status - 业务状态 1 未处理 2 处理中 3 已处理
     */
    public Byte getBusinessStatus() {
        return businessStatus;
    }

    /**
     * 设置业务状态 1 未处理 2 处理中 3 已处理
     *
     * @param businessStatus 业务状态 1 未处理 2 处理中 3 已处理
     */
    public void setBusinessStatus(Byte businessStatus) {
        this.businessStatus = businessStatus;
    }

    /**
     * 获取业务号
     *
     * @return business_no - 业务号
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * 设置业务号
     *
     * @param businessNo 业务号
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}