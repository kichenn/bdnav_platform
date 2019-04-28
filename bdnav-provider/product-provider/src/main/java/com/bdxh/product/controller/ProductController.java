package com.bdxh.product.controller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 商品服务控制器
 * @author: xuyuan
 * @create: 2019-01-21 16:35
 **/

@Controller
@RequestMapping("/product")
@Validated
@Slf4j
public class ProductController {

    @Autowired
    public ProductService productService;

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品")
    @RequestMapping(value = "/queryProduct",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProduct(@RequestParam(name = "id") @NotNull(message = "商品id不能为空") Long id){
        try {
            Product product = productService.selectByKey(id);
            return WrapMapper.ok(product);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 增加商品
     * @param productAddDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("增加商品")
    @RequestMapping(value = "/addProduct",method = RequestMethod.POST)
    @ResponseBody
    public Object addProduct(@Valid @RequestBody ProductAddDto productAddDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            productService.addProduct(productAddDto);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     * @param productUpdateDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("更新商品")
    @RequestMapping(value = "/updateProduct",method = RequestMethod.POST)
    @ResponseBody
    public Object updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            productService.updateProduct(productUpdateDto);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @ApiOperation("删除商品")
    @RequestMapping(value = "/deleteProduct",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteProduct(@RequestParam(name = "productId") @NotNull(message = "商品id不能为空") Long productId){
        try {
            productService.deleteProduct(productId);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 商品列表查询
     * @param productQueryDto
     * @return
     */
    @ApiOperation("商品列表查询")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.POST)
    @ResponseBody
    public Object queryListPage(@Valid @RequestBody ProductQueryDto productQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(productQueryDto);
            PageInfo pageInfo = productService.queryListPage(param, productQueryDto.getPageNum(), productQueryDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询商品
     * @param productQueryDto
     * @return
     */
    @ApiOperation("根据条件查询商品")
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    @ResponseBody
    public Object queryList(@Valid @RequestBody ProductQueryDto productQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(productQueryDto);
            List<Product> products = productService.queryList(param);
            return WrapMapper.ok(products);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 判断商品展示名称是否重复
     * @param productShowName
     * @return
     */
    @ApiOperation("判断商品展示名称是否重复")
    @RequestMapping(value = "/exists",method = RequestMethod.GET)
    @ResponseBody
    public Object exists(@RequestParam(name = "productShowName") @NotEmpty(message = "商品展示名称不能为空") String productShowName){
        try {
            Product product = new Product();
            product.setProductShowName(productShowName);
            Product productData = productService.selectOne(product);
            Preconditions.checkArgument(productData==null,"商品展示名称重复");
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 商品购买展示
     * @param businessType
     * @return
     */
    @ApiOperation("商品购买展示")
    @RequestMapping(value = "/buyShow",method = RequestMethod.GET)
    @ResponseBody
    public Object buyShow(@RequestParam(name = "businessType") @NotNull(message = "业务类型不能为空") Byte businessType){
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("businessType",businessType);
            param.put("productType",ProductTypeEnum.GROUP.getCode().byteValue());
            List<Product> productsGroup = productService.queryList(param);
            param.put("productType", ProductTypeEnum.SINGLE.getCode().byteValue());
            List<Product> productsSingle = productService.queryList(param);
            param.clear();
            param.put(ProductTypeEnum.GROUP.name(),productsGroup);
            param.put(ProductTypeEnum.SINGLE.name(),productsSingle);
            return WrapMapper.ok(param);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

}
