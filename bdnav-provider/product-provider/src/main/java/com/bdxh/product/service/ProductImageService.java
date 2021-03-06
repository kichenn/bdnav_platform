package com.bdxh.product.service;

import com.bdxh.common.support.IService;
import com.bdxh.product.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-05-08 19:21:01
 */
@Service
public interface ProductImageService extends IService<ProductImage> {

    /**
    * @Description:  商品id查询商品图片信息
    * @Author: Kang
    * @Date: 2019/6/14 14:54
    */
    List<String> findImgUrlByProductId(Long productId);
}
