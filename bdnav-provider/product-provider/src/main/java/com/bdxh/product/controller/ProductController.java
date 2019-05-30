package com.bdxh.product.controller;

import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.service.ProductService;
import com.bdxh.product.vo.ProductDetailsVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return WrapMapper.ok( productService.selectByKey(id));
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
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     *
     * @param productUpdateDto
     */
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ApiOperation(value = "更新商品信息")
    public Object updateProduct(@RequestBody ProductUpdateDto productUpdateDto) {
        productService.updateProduct(productUpdateDto);
        return WrapMapper.ok();
    }

    /**
     * 查询所有商品信息
     * @param productQueryDto
     * @return
     */
    @RequestMapping(value = "/findAllProduct",method =RequestMethod.GET)
    @ApiOperation(value = "查询所有商品信息")
    public Object findAllProduct(@RequestBody ProductQueryDto productQueryDto){
        return WrapMapper.ok(productService.selectAll());
    }
    /**
     * 根据Ids查询商品集合
     *
     * @param productIds
     */
    @RequestMapping(value = "/findProductByIds", method = RequestMethod.GET)
    @ResponseBody
    public Object  findProductByIds(@RequestParam("productIds") String productIds){
        try {
            return WrapMapper.ok(productService.selectAll());
        }catch (Exception e){
            return WrapMapper.ok(productService.selectAll());
        }

    }

}
