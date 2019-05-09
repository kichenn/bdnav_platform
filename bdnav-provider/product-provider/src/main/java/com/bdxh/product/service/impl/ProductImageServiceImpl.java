package com.bdxh.product.service.impl;

import com.bdxh.product.entity.ProductImage;
import com.bdxh.product.persistence.ProductImageMapper;
import com.bdxh.product.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
* @Description: 业务层实现
* @Author Kang
* @Date 2019-05-08 19:21:01
*/
@Service
@Slf4j
public class ProductImageServiceImpl extends BaseService<ProductImage> implements ProductImageService {

	@Autowired
	private ProductImageMapper productImageMapper;

	/*
	 *查询总条数
	 */
	@Override
	public Integer getProductImageAllCount(){
		return productImageMapper.getProductImageAllCount();
	}

	/*
	 *批量删除方法
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean batchDelProductImageInIds(List<Long> ids){
		return productImageMapper.delProductImageInIds(ids) > 0;
	}
}
