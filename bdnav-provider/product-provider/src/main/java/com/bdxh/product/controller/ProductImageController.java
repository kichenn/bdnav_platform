package com.bdxh.product.controller;

import com.bdxh.product.service.ProductImageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 微校商品图片控制器
* @Author Kang
* @Date 2019-05-08 19:21:01
*/
@Controller
@RequestMapping("/productImage")
@Slf4j
@Validated
@Api(value = "微校商品图片控制器", tags = "微校商品图片控制器")
public class ProductImageController {

	@Autowired
	private ProductImageService productImageService;

}