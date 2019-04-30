package com.bdxh.product.service;

import com.bdxh.common.support.IService;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 商品service
 * @author: xuyuan
 * @create: 2019-01-19 18:14
 **/
public interface ProductService extends IService<Product> {

    /**
     * 新增商品
     * @param productDto
     */
    boolean addProduct(ProductAddDto productDto);

    /**
     * 更新商品
     * @param productUpdateDto
     */
    void updateProduct(ProductUpdateDto productUpdateDto);

    /**
     * 根据主键删除商品
     * @param productId
     */
    void deleteProduct(Long productId);

    /**
     * 根据条件分页查询商品
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo queryListPage(Map<String,Object> param, Integer pageNum, Integer pageSize);


    /**
     * 根据条件查询列表
     * @param param
     * @return
     */
    List<Product> queryList(Map<String,Object> param);

}
