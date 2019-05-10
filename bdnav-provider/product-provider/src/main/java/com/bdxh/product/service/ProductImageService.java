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
	 *查询所有数量
	 */
 	Integer getProductImageAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelProductImageInIds(List<Long> id);

}
