package com.bdxh.product.persistence;

import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.vo.ProductDetailsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProductMapper extends Mapper<Product> {

    //根据条件查询商品列表
    List<Product> findProduct(ProductQueryDto productQueryDto);

    //查询商品详情
    ProductDetailsVo findProductDetails(@Param("id") Long id);

    //修改商品信息
    int updateProduct(Product product);

    //新增商品信息
    Product insertProduct(Product product);
}