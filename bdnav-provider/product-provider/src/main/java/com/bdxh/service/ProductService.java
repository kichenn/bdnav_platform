package com.bdxh.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.product.dto.ProductDto;
import com.bdxh.product.entity.Product;

/**
 * @description: 商品service
 * @author: xuyuan
 * @create: 2019-01-19 18:14
 **/
public interface ProductService extends IService<Product> {

    /**
     * 新增产品
     * @param productDto
     */
    void addProduct(ProductDto productDto);

}
