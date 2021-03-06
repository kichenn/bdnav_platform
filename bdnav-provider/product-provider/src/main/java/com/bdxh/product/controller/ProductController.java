package com.bdxh.product.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.service.ProductImageService;
import com.bdxh.product.service.ProductService;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.product.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 商品服务控制器
 * @Author: Kang
 * @Date: 2019/6/14 11:57
 */
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
    @ApiOperation(value = "查询微校商品", response = Product.class)
    public Object findProduct(@Validated @RequestBody ProductQueryDto productQueryDto) {
        PageInfo<ProductListVo> productPageInfo = productService.findProduct(productQueryDto);
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
     *
     * @param id
     */
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品信息")
    public Object deleteProduct(@RequestParam("id") Long id) {
        try {
            productService.deleteProduct(id);

            return WrapMapper.ok();
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据ID查询商品
     *
     * @param id
     */
    @RequestMapping(value = "/findProductById", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID查询商品")
    public Object findProductById(@RequestParam("id") Long id) {
        try {
            return WrapMapper.ok(productService.selectByKey(id));
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增商品
     *
     * @param productDto
     */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品信息")
    public Object addProduct(@RequestBody ProductAddDto productDto) {
        try {
            productService.addProduct(productDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     *
     * @param productUpdateDto
     */
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ApiOperation(value = "更新商品信息", response = Boolean.class)
    public Object updateProduct(@RequestBody ProductUpdateDto productUpdateDto) {
        productService.updateProduct(productUpdateDto);
        return WrapMapper.ok();
    }

    /**
     * 根据Ids查询商品集合
     *
     * @param productIds
     */
    @RequestMapping(value = "/findProductByIds", method = RequestMethod.GET)
    @ApiOperation(value = "根据ids查询商品集合信息", response = Product.class)
    @ResponseBody
    public Object findProductByIds(@RequestParam("productIds") String productIds) {
        try {
            return WrapMapper.ok(productService.selectAll());
        } catch (Exception e) {
            return WrapMapper.ok(productService.selectAll());
        }

    }


    /**
     * @Description: 商品名称查询商品
     * @Author: Kang
     * @Date: 2019/6/24 10:56
     */
    @RequestMapping(value = "/findProductByName", method = RequestMethod.GET)
    @ApiOperation(value = "商品名称查询商品", response = Product.class)
    @ResponseBody
    public Object findProductByName(@RequestParam("productName") String productName) {
        try {
            return WrapMapper.ok(productService.findProductByName(productName));
        } catch (Exception e) {
            return WrapMapper.ok(productService.selectAll());
        }

    }

    /**
     * 查询所有上架的单品信息
     * @Author: WanMing
     * @Date: 2019/7/4 17:47
     */
    @RequestMapping(value = "/querySingleProductList", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有上架的单品信息", response = ProductListVo.class)
    public Object querySingleProductList(){
        return WrapMapper.ok(productService.querySingleProductList());
    }

}
