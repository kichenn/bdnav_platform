package com.bdxh.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductDto;
import com.bdxh.service.ProductChildService;
import com.bdxh.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @description: 商品服务控制器
 * @author: xuyuan
 * @create: 2019-01-21 16:35
 **/

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    public ProductService productService;

    @Autowired
    private ProductChildService productChildService;

    public Object addProduct(@Valid ProductDto productDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {

            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }

    }

}
