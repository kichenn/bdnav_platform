package com.bdxh.product.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.product.vo.ProductDetailsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @description: 应用分类feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 15:50
 **/
@Component
public class ProductControllerClientFallback implements ProductControllerClient {

    @Override
    public Wrapper<PageInfo<Product>> findProduct(ProductQueryDto productQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<ProductDetailsVo> findProductDetails(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteProduct(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addProduct(ProductAddDto productDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateProduct(ProductUpdateDto productUpdateDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Product> findProductById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Product>> findAllProduct(ProductQueryDto productQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Product>> findProductByIds(String productIds) {
        return WrapMapper.error();
    }
}
