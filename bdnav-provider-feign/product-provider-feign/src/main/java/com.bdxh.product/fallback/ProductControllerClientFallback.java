package com.bdxh.product.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.feign.ProductControllerClient;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 应用分类feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 15:50
 **/
@Component
public class ProductControllerClientFallback implements ProductControllerClient {

    @Override
    public Wrapper queryProduct(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addProduct(ProductAddDto productAddDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateProduct(ProductUpdateDto productUpdateDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteProduct(@NotNull(message = "商品id不能为空") Long productId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryListPage(ProductQueryDto productQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryList(ProductQueryDto productQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper exists(@NotEmpty(message = "商品展示名称不能为空") String productShowName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper buyShow(@NotNull(message = "业务类型不能为空") Byte businessType) {
        return WrapMapper.error();
    }
}
