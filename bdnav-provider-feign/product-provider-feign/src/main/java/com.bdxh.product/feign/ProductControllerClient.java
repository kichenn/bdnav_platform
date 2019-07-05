package com.bdxh.product.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.fallback.ProductControllerClientFallback;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.product.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 应用分类feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 15:49
 **/
@Service
@FeignClient(value = "product-provider-cluster", fallback = ProductControllerClientFallback.class)
public interface ProductControllerClient {
    /**
     * 查询微校商品
     *
     * @param productQueryDto
     * @return
     */
    @RequestMapping(value = "/product/findProduct", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<ProductListVo>> findProduct(@RequestBody ProductQueryDto productQueryDto);

    /**
     * 查询所有商品
     *
     * @param productQueryDto
     * @return
     */
    @RequestMapping(value = "/product/findAllProduct", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<Product>> findAllProduct(@RequestBody ProductQueryDto productQueryDto);

    /**
     * 查询微校商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/product/findProductDetails", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<ProductDetailsVo> findProductDetails(@RequestParam("id") Long id);

    /**
     * 删除商品信息
     *
     * @param id
     */
    @RequestMapping(value = "/product/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    Wrapper deleteProduct(@RequestParam("id") Long id);


    /**
     * 新增商品
     *
     * @param productDto
     */
    @RequestMapping(value = "/product/addProduct", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addProduct(@RequestBody ProductAddDto productDto);

    /**
     * 更新商品
     *
     * @param productUpdateDto
     */
    @RequestMapping(value = "/product/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateProduct(@RequestBody ProductUpdateDto productUpdateDto);

    /**
     * 根据ID查询商品
     *
     * @param id
     */
    @RequestMapping(value = "/product/findProductById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<Product> findProductById(@RequestParam("id") Long id);

    /**
     * 根据Ids查询商品集合
     *
     * @param productIds
     */
    @RequestMapping(value = "/product/findProductByIds", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<Product>> findProductByIds(@RequestParam("productIds") String productIds);

    /**
     * 商品名称查询商品
     */
    @RequestMapping(value = "/product/findProductByName", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<Product> findProductByName(@RequestParam("productName") String productName);

    /**
     * 查询所有上架的单品信息
     * @Author: WanMing
     * @Date: 2019/7/4 17:47
     */
    @RequestMapping(value = "/product/querySingleProductList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<ProductListVo>> querySingleProductList();
}
