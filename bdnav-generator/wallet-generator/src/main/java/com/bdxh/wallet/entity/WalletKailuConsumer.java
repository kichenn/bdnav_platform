package com.bdxh.wallet.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "wallet_kailu_consumer")
public class WalletKailuConsumer {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 平台订单号
     */
    @Column(name = "order_no")
    private Long orderNo;

    /**
     * 外部订单号
     */
    @Column(name = "out_order_no")
    private String outOrderNo;

    /**
     * 第三方支付订单号
     */
    @Column(name = "third_order_no")
    private String thirdOrderNo;

    /**
     * 一卡通流水号
     */
    private String acceptseq;

    /**
     * 消费金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    /**
     * 支付状态 1 未扣款 2 扣款中 3 扣款失败 4 扣款成功
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 业务处理状态 1 未处理 2 处理中 3 已处理
     */
    @Column(name = "business_status")
    private Byte businessStatus;

    /**
     * 学校编码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 设备类型
     */
    @Column(name = "device_type")
    private Byte deviceType;

    /**
     * 设备id
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 消费时间
     */
    @Column(name = "consumption_time")
    private Date consumptionTime;

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
     * 失败原因
     */
    private String message;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取平台订单号
     *
     * @return order_no - 平台订单号
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * 设置平台订单号
     *
     * @param orderNo 平台订单号
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取外部订单号
     *
     * @return out_order_no - 外部订单号
     */
    public String getOutOrderNo() {
        return outOrderNo;
    }

    /**
     * 设置外部订单号
     *
     * @param outOrderNo 外部订单号
     */
    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo == null ? null : outOrderNo.trim();
    }

    /**
     * 获取第三方支付订单号
     *
     * @return third_order_no - 第三方支付订单号
     */
    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    /**
     * 设置第三方支付订单号
     *
     * @param thirdOrderNo 第三方支付订单号
     */
    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo == null ? null : thirdOrderNo.trim();
    }

    /**
     * 获取一卡通流水号
     *
     * @return acceptseq - 一卡通流水号
     */
    public String getAcceptseq() {
        return acceptseq;
    }

    /**
     * 设置一卡通流水号
     *
     * @param acceptseq 一卡通流水号
     */
    public void setAcceptseq(String acceptseq) {
        this.acceptseq = acceptseq == null ? null : acceptseq.trim();
    }

    /**
     * 获取消费金额
     *
     * @return pay_money - 消费金额
     */
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    /**
     * 设置消费金额
     *
     * @param payMoney 消费金额
     */
    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 获取支付状态 1 未扣款 2 扣款中 3 扣款失败 4 扣款成功
     *
     * @return pay_status - 支付状态 1 未扣款 2 扣款中 3 扣款失败 4 扣款成功
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态 1 未扣款 2 扣款中 3 扣款失败 4 扣款成功
     *
     * @param payStatus 支付状态 1 未扣款 2 扣款中 3 扣款失败 4 扣款成功
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取业务处理状态 1 未处理 2 处理中 3 已处理
     *
     * @return business_status - 业务处理状态 1 未处理 2 处理中 3 已处理
     */
    public Byte getBusinessStatus() {
        return businessStatus;
    }

    /**
     * 设置业务处理状态 1 未处理 2 处理中 3 已处理
     *
     * @param businessStatus 业务处理状态 1 未处理 2 处理中 3 已处理
     */
    public void setBusinessStatus(Byte businessStatus) {
        this.businessStatus = businessStatus;
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
     * 获取学校名称
     *
     * @return school_name - 学校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置学校名称
     *
     * @param schoolName 学校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 获取学生姓名
     *
     * @return name - 学生姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学生姓名
     *
     * @param name 学生姓名
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
     * 获取设备类型
     *
     * @return device_type - 设备类型
     */
    public Byte getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型
     *
     * @param deviceType 设备类型
     */
    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取设备id
     *
     * @return device_id - 设备id
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * 设置设备id
     *
     * @param deviceId 设备id
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取设备名称
     *
     * @return device_name - 设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名称
     *
     * @param deviceName 设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * 获取消费时间
     *
     * @return consumption_time - 消费时间
     */
    public Date getConsumptionTime() {
        return consumptionTime;
    }

    /**
     * 设置消费时间
     *
     * @param consumptionTime 消费时间
     */
    public void setConsumptionTime(Date consumptionTime) {
        this.consumptionTime = consumptionTime;
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
     * 获取失败原因
     *
     * @return message - 失败原因
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置失败原因
     *
     * @param message 失败原因
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}