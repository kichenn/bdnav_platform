package com.bdxh.product.persistence;

import com.bdxh.product.entity.Product;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductMapper extends Mapper<Product> {

    List<Product> getByCondition(Map<String,Object> param);

}