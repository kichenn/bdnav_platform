package com.bdxh.product.service;

import com.bdxh.common.support.IService;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.product.vo.ProductListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
* @Description:  商品service
* @Author: Kang
* @Date: 2019/6/14 14:50
*/
public interface ProductService extends IService<Product> {

    /**
     * 新增商品
     * @param productDto
     */
    void addProduct(ProductAddDto productDto);

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
     * @param productQueryDto
     * @return
     */
    PageInfo<ProductListVo> findProduct(ProductQueryDto productQueryDto);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    ProductDetailsVo findProductDetails(Long id);

    /**
     * 根据Ids查询商品集合
     *
     * @param productIds
     */
    List<Product> findProductByIds(String productIds);

    /**
    * @Description: 商品名称查询商品信息
    * @Author: Kang
    * @Date: 2019/6/24 10:59
    */
    Product findProductByName(String productName);
}
