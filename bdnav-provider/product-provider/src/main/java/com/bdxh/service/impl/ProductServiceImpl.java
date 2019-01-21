package com.bdxh.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.persistence.ProductChildMapper;
import com.bdxh.persistence.ProductMapper;
import com.bdxh.product.dto.ProductDto;
import com.bdxh.product.entity.Product;
import com.bdxh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 商品service实现
 * @author: xuyuan
 * @create: 2019-01-19 18:25
 **/
@Service
public class ProductServiceImpl extends BaseService<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductChildMapper productChildMapper;

    @Override
    public void addProduct(ProductDto productDto) {
        //判断是单品还是套餐

    }

}
