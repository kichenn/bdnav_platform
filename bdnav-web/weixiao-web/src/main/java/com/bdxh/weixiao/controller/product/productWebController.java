package com.bdxh.weixiao.controller.product;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.feign.ProductControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-22 16:14
 **/
@Slf4j
@RequestMapping(value = "/productWeb")
@RestController
@Api(value = "微校商品----微校商品API", tags = "微校商品----微校商品API")
@Validated
public class productWebController {
    @Autowired
    private ProductControllerClient productControllerClient;

    /**
     * 查询当前正在上架切是微校服务的商品
     * @return
     */
    @ApiOperation(value = "家长微校商品----查询微校商品列表")
    @RequestMapping(value = "/findProductInfo",method = RequestMethod.GET)
    public Object findProductInfo(){
        try {
            //查询还在上架的并且是微校服务的商品
            ProductQueryDto productQueryDto=new ProductQueryDto();
            productQueryDto.setSellStatus(Byte.valueOf("2"));
            productQueryDto.setBusinessType(Byte.valueOf("1"));
            return WrapMapper.ok(productControllerClient.findProduct(productQueryDto).getResult());
        }catch (Exception e){
            return WrapMapper.error();
        }
    }
}