package com.bdxh.product.persistence;

import com.bdxh.product.entity.ProductChild;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProductChildMapper extends Mapper<ProductChild> {

    int exists(@Param("productId") Long productId);

}