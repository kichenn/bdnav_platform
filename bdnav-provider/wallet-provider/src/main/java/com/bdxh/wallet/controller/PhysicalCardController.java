package com.bdxh.wallet.controller;

import com.bdxh.common.utils.AESUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.SetPayPwdDto;
import com.bdxh.wallet.service.PhysicalCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Controller
@RequestMapping("/physicalCard")
@Slf4j
@Validated
@Api(value = "卡账户相关", tags = "卡账户交互API")
public class PhysicalCardController {

    @Autowired
    private PhysicalCardService physicalCardService;

    @PostMapping("/findRechargeRecord")
    @ApiOperation(value = "查询账单信息", response = Boolean.class)
    public Object findRechargeRecord(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        return WrapMapper.ok();
    }

    /**
     * @param physicalNumber 实体卡号
     * @param physicalStatus 挂失或者解挂（1 正常 2 挂失）
     * @return
     */
    @PostMapping("/cardStatus")
    @ApiOperation(value = "挂失或者解挂", response = Boolean.class)
    public Object cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") String physicalStatus) {

        return WrapMapper.ok();
    }
}