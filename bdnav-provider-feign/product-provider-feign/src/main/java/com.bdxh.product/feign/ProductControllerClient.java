package com.bdxh.product.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.fallback.ProductControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @description: 应用分类feign客户端
 * @author: xuyuan
 * @create: 2019-04-11 15:49
 **/
@Service
@FeignClient(value = "product-provider-cluster",fallback = ProductControllerClientFallback.class)
public interface ProductControllerClient {

    @RequestMapping(value = "/product/queryProduct",method = RequestMethod.GET)
    Wrapper<Product> queryProduct(@RequestParam(name = "id") Long id);

    @RequestMapping(value = "/product/addProduct",method = RequestMethod.POST)
    Wrapper addProduct(@RequestBody ProductAddDto productAddDto);

    @RequestMapping(value = "/product/updateProduct",method = RequestMethod.POST)
    Wrapper updateProduct(@RequestBody ProductUpdateDto productUpdateDto);

    @RequestMapping(value = "/product/deleteProduct",method = RequestMethod.POST)
    Wrapper deleteProduct(@RequestParam(name = "productId") @NotNull(message = "商品id不能为空") Long productId);

    @RequestMapping(value = "/product/queryListPage",method = RequestMethod.POST)
    Wrapper<PageInfo<Product> > queryListPage(@RequestBody ProductQueryDto productQueryDto);

    @RequestMapping(value = "/product/queryList",method = RequestMethod.POST)
    Wrapper<List<Product>> queryList(@RequestBody ProductQueryDto productQueryDto);

    @RequestMapping(value = "/product/exists",method = RequestMethod.GET)
    Wrapper exists(@RequestParam(name = "productShowName") @NotEmpty(message = "商品展示名称不能为空") String productShowName);

    @RequestMapping(value = "/product/buyShow",method = RequestMethod.GET)
    Wrapper<Map> buyShow(@RequestParam(name = "businessType") @NotNull(message = "业务类型不能为空") Byte businessType);
}
