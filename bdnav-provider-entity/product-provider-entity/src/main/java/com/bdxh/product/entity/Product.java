package com.bdxh.product.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = -3829539719464237070L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品展示名称
     */
    @Column(name = "product_show_name")
    private String productShowName;

    /**
     * 商品定价
     */
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 商品售价
     */
    @Column(name = "product_sell_price")
    private BigDecimal productSellPrice;

    /**
     * 商品描述
     */
    @Column(name = "product_description")
    private String productDescription;

    /**
     * 商品类型 1 单品 2 套餐
     */
    @Column(name = "product_type")
    private Byte productType;

    /**
     * 商品上下架状态 1 下架 2 上架
     */
    @Column(name = "sell_status")
    private Byte sellStatus;

    /**
     * 商品图片地址
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 业务类型 1 微校服务
     */
    @Column(name = "business_type")
    private Byte businessType;

    /**
     * 额外信息 微校服务套餐可放id列表使用逗号分隔
     */
    @Column(name = "product_extra")
    private String productExtra;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private Long operator;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

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
    @Column(name = "remark")
    private String remark;

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
     * 获取商品展示名称
     *
     * @return product_show_name - 商品展示名称
     */
    public String getProductShowName() {
        return productShowName;
    }

    /**
     * 设置商品展示名称
     *
     * @param productShowName 商品展示名称
     */
    public void setProductShowName(String productShowName) {
        this.productShowName = productShowName == null ? null : productShowName.trim();
    }

    /**
     * 获取商品定价
     *
     * @return product_price - 商品定价
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 设置商品定价
     *
     * @param productPrice 商品定价
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
     * 获取商品描述
     *
     * @return product_description - 商品描述
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * 设置商品描述
     *
     * @param productDescription 商品描述
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
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
     * 获取商品上下架状态 1 下架 2 上架
     *
     * @return sell_status - 商品上下架状态 1 下架 2 上架
     */
    public Byte getSellStatus() {
        return sellStatus;
    }

    /**
     * 设置商品上下架状态 1 下架 2 上架
     *
     * @param sellStatus 商品上下架状态 1 下架 2 上架
     */
    public void setSellStatus(Byte sellStatus) {
        this.sellStatus = sellStatus;
    }

    /**
     * 获取商品图片地址
     *
     * @return img_url - 商品图片地址
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置商品图片地址
     *
     * @param imgUrl 商品图片地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * 获取业务类型 1 微校服务
     *
     * @return business_type - 业务类型 1 微校服务
     */
    public Byte getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型 1 微校服务
     *
     * @param businessType 业务类型 1 微校服务
     */
    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取额外信息 微校服务套餐可放id列表使用逗号分隔
     *
     * @return product_extra - 额外信息 微校服务套餐可放id列表使用逗号分隔
     */
    public String getProductExtra() {
        return productExtra;
    }

    /**
     * 设置额外信息 微校服务套餐可放id列表使用逗号分隔
     *
     * @param productExtra 额外信息 微校服务套餐可放id列表使用逗号分隔
     */
    public void setProductExtra(String productExtra) {
        this.productExtra = productExtra == null ? null : productExtra.trim();
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


    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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