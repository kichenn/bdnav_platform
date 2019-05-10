package com.bdxh.backend.controller.product;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductQueryDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @ClassName: com.bdxh.backend.controller.product
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/4/24 12:07.
 */


@RestController
@RequestMapping("/productWeb")
@Validated
@Slf4j
@Api(value = "商品服务", tags = "商品服务")
public class ProductWebController {

    @Autowired
    private ProductControllerClient productControllerClient;

    @ApiOperation("根据id查询商品")
    @RequestMapping(value = "/queryProduct", method = RequestMethod.GET)
    public Object queryProduct(@RequestParam(name = "id") @NotNull(message = "商品id不能为空") Long id) {
        try {
            Wrapper wrapper = productControllerClient.findProductDetails(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("增加商品")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public Object addProduct(@Valid @RequestBody ProductAddDto productAddDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }

        try {
            User user = SecurityUtils.getCurrentUser();
            productAddDto.setOperator(user.getId());
            productAddDto.setOperatorName(user.getUserName());
            Wrapper wrapper = productControllerClient.addProduct(productAddDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }


    }


    @ApiOperation("更新商品")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public Object updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            productUpdateDto.setOperator(user.getId());
            productUpdateDto.setOperatorName(user.getUserName());
            Wrapper wrapper = productControllerClient.updateProduct(productUpdateDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("删除商品")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public Object deleteProduct(@RequestParam(name = "productId") @NotNull(message = "商品id不能为空") Long productId) {

        try {
            Wrapper wrapper = productControllerClient.deleteProduct(productId);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("商品列表查询")
    @RequestMapping(value = "/queryListPage", method = RequestMethod.POST)
    public Object queryListPage(@Valid @RequestBody ProductQueryDto productQueryDto) {
        try {
            Wrapper wrapper = productControllerClient.findProduct(productQueryDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }

}