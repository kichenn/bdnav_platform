package com.bdxh.wallet.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.service.WalletConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@RestController
@RequestMapping("/walletConsumer")
@Slf4j
@Validated
@Api(value = "卡消费相关", tags = "消费交互API")
public class WalletConsumerController {

    @Autowired
    private WalletConsumerService walletConsumerService;

    @PostMapping("/cardStatus")
    @ApiOperation(value = "挂失或者解挂", response = Boolean.class)
    public Object cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") Byte physicalStatus) {
        return WrapMapper.ok();
    }


}