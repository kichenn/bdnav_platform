package com.bdxh.product.entity;

import javax.persistence.*;

@Table(name = "product_child")
public class ProductChild {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品子id
     */
    @Column(name = "product_child_id")
    private Long productChildId;

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
     * 获取商品子id
     *
     * @return product_child_id - 商品子id
     */
    public Long getProductChildId() {
        return productChildId;
    }

    /**
     * 设置商品子id
     *
     * @param productChildId 商品子id
     */
    public void setProductChildId(Long productChildId) {
        this.productChildId = productChildId;
    }
}