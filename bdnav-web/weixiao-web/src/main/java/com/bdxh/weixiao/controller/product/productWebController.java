package com.bdxh.weixiao.controller.product;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.feign.ProductControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 微校商品
 * @Author: Kang
 * @Date: 2019/6/14 14:28
 */
@Slf4j
@RestController
@Api(value = "微校商品----微校商品API", tags = "微校商品----微校商品API")
@Validated
public class productWebController {
    @Autowired
    private ProductControllerClient productControllerClient;

    /**
     * 查询当前正在上架切是微校服务的商品
     *
     * @return
     */
    @ApiOperation(value = "家长微校商品----查询微校商品列表")
    @RequestMapping(value = "/authenticationWeixiao/findProductInfo", method = RequestMethod.POST)
    public Object findProductInfo(@Validated @RequestBody ProductQueryDto productQueryDto) {
        try {
            //查询还在上架的并且是微校服务的商品
            return WrapMapper.ok(productControllerClient.findProduct(productQueryDto).getResult());
        } catch (Exception e) {
            return WrapMapper.error();
        }
    }

    /**
     * @Description: 根据id查询商品详情信息
     * @Author: Kang
     * @Date: 2019/6/14 15:23
     */
    @ApiOperation(value = "家长微校商品----id查询商品详情信息")
    @RequestMapping(value = "/authenticationWeixiao/findProductDetail", method = RequestMethod.POST)
    public Object findProductDetails(@RequestParam("id") Long id) {
        return WrapMapper.ok(productControllerClient.findProductDetails(id).getResult());
    }
}