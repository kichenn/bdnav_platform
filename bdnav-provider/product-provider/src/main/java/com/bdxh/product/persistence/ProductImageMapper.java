package com.bdxh.product.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.product.entity.ProductImage;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-08 19:21:01
*/
@Repository
public interface ProductImageMapper extends Mapper<ProductImage> {


	/**
	 * 根据商品ID删除图片
	 */
	int deleteProductImageByProductId(@Param("id")Long id);
}
