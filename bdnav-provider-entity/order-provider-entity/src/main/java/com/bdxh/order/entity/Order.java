package com.bdxh.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "order")
public class Order implements Serializable {

    private static final long serialVersionUID = 2871016506957216726L;

    /**
     * 订单号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * 第三方订单号
     */
    @Column(name = "third_order_no")
    private String thirdOrderNo;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户openid
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 订单总金额
     */
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    /**
     * 订单金额
     */
    @Column(name = "order_money")
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    /**
     * 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     */
    @Column(name = "trade_status")
    private Byte tradeStatus;

    /**
     * 支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 业务状态 1 未处理 2 处理中 3 已处理
     */
    @Column(name = "business_status")
    private Byte businessStatus;

    /**
     * 渠道类型 1 自有渠道
     */
    @Column(name = "channel_type")
    private Byte channelType;

    /**
     * 业务类型 1 微校付费服务
     */
    @Column(name = "business_type")
    private Byte businessType;

    /**
     * 支付渠道 1 微信支付
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 订单类型  1 JSAPI支付
     */
    @Column(name = "trade_type")
    private Byte tradeType;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 支付结束时间
     */
    @Column(name = "pay_end_time")
    private Date payEndTime;

    /**
     * 产品用#分隔 id用逗号分隔 数量用=分隔
     */
    @Column(name = "product_ids")
    private String productIds;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 备注
     */
    private String remark;

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
     * 获取第三方订单号
     *
     * @return third_order_no - 第三方订单号
     */
    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    /**
     * 设置第三方订单号
     *
     * @param thirdOrderNo 第三方订单号
     */
    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo == null ? null : thirdOrderNo.trim();
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户openid
     *
     * @return open_id - 用户openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置用户openid
     *
     * @param openId 用户openid
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取学号
     *
     * @return card_number - 学号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置学号
     *
     * @param cardNumber 学号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取订单总金额
     *
     * @return total_money - 订单总金额
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * 设置订单总金额
     *
     * @param totalMoney 订单总金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 获取订单金额
     *
     * @return order_money - 订单金额
     */
    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    /**
     * 设置订单金额
     *
     * @param orderMoney 订单金额
     */
    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * 获取支付金额
     *
     * @return pay_money - 支付金额
     */
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    /**
     * 设置支付金额
     *
     * @param payMoney 支付金额
     */
    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 获取交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     *
     * @return trade_status - 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     */
    public Byte getTradeStatus() {
        return tradeStatus;
    }

    /**
     * 设置交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     *
     * @param tradeStatus 交易状态 1 进行中 2 已取消 3 已删除 4 交易成功
     */
    public void setTradeStatus(Byte tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * 获取支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     *
     * @return pay_status - 支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     *
     * @param payStatus 支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
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
     * 获取渠道类型 1 自有渠道
     *
     * @return channel_type - 渠道类型 1 自有渠道
     */
    public Byte getChannelType() {
        return channelType;
    }

    /**
     * 设置渠道类型 1 自有渠道
     *
     * @param channelType 渠道类型 1 自有渠道
     */
    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    /**
     * 获取业务类型 1 微校付费服务
     *
     * @return business_type - 业务类型 1 微校付费服务
     */
    public Byte getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型 1 微校付费服务
     *
     * @param businessType 业务类型 1 微校付费服务
     */
    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取支付渠道 1 微信支付
     *
     * @return pay_type - 支付渠道 1 微信支付
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置支付渠道 1 微信支付
     *
     * @param payType 支付渠道 1 微信支付
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取订单类型  1 JSAPI支付
     *
     * @return trade_type - 订单类型  1 JSAPI支付
     */
    public Byte getTradeType() {
        return tradeType;
    }

    /**
     * 设置订单类型  1 JSAPI支付
     *
     * @param tradeType 订单类型  1 JSAPI支付
     */
    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取支付结束时间
     *
     * @return pay_end_time - 支付结束时间
     */
    public Date getPayEndTime() {
        return payEndTime;
    }

    /**
     * 设置支付结束时间
     *
     * @param payEndTime 支付结束时间
     */
    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    /**
     * 获取产品用#分隔 id用逗号分隔 数量用=分隔
     *
     * @return product_ids - 产品用#分隔 id用逗号分隔 数量用=分隔
     */
    public String getProductIds() {
        return productIds;
    }

    /**
     * 设置产品用#分隔 id用逗号分隔 数量用=分隔
     *
     * @param productIds 产品用#分隔 id用逗号分隔 数量用=分隔
     */
    public void setProductIds(String productIds) {
        this.productIds = productIds == null ? null : productIds.trim();
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
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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