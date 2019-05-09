package com.bdxh.product.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.service.ProductService;
import com.bdxh.product.vo.ProductDetailsVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 商品服务控制器
 * @author: xuyuan
 * @create: 2019-01-21 16:35
 **/

@RestController
@RequestMapping("/product")
@Validated
@Slf4j
@Api(value = "微校商品服务控制器", tags = "微校商品服务控制器")
public class ProductController {

    @Autowired
    public ProductService productService;

    /**
     * 查询微校商品
     *
     * @param productQueryDto
     * @return
     */
    @RequestMapping(value = "/findProduct", method = RequestMethod.POST)
    @ApiOperation(value = "查询微校商品")
    public Object findProduct(@RequestBody ProductQueryDto productQueryDto) {
            PageInfo<Product> productPageInfo = productService.findProduct(productQueryDto);
            return WrapMapper.ok(productPageInfo);
    }
    /**
     * 查询微校商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findProductDetails", method = RequestMethod.POST)
    @ApiOperation(value = "查询微校商品详情")
    public Object findProductDetails(@RequestParam("id") Long id) {
            ProductDetailsVo productDetailsVo = productService.findProductDetails(id);
            return WrapMapper.ok(productDetailsVo);
    }

    /**
     * 删除商品信息
     * @param productId
     */
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品信息")
    public Object deleteProduct(@RequestParam("productId") Long productId){
        try {
            productService.deleteProduct(productId);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
