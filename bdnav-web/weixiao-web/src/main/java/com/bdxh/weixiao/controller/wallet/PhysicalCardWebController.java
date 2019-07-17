package com.bdxh.weixiao.controller.wallet;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.FindBillRecordDto;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.feign.PhysicalCardControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 卡相关
 * @Author: Kang
 * @Date: 2019/7/17 10:48
 */
@Slf4j
@RestController("physicalCardWeb")
@Api(value = "账户卡相关", tags = {"账户卡相关API"})
@Validated
public class PhysicalCardWebController {

    @Autowired
    private PhysicalCardControllerClient physicalCardControllerClient;

    @PostMapping("/findBillRecord")
    @ApiOperation(value = "查询账单信息", response = Boolean.class)
    public Object findRechargeRecord(@Validated @RequestBody FindBillRecordDto findBillRecordDto) {
        return WrapMapper.ok(physicalCardControllerClient.findRechargeRecord(findBillRecordDto).getResult());
    }

    @GetMapping("/findCardStatus")
    @ApiOperation(value = "查询卡的状态", response = Boolean.class)
    public Object findCardStatus(@RequestParam("physicalNumber") String physicalNumber) {
        return WrapMapper.ok(physicalCardControllerClient.findCardStatus(physicalNumber).getResult());
    }

    /**
     * @param physicalNumber 实体卡号
     * @param physicalStatus 挂失或者解挂（1 正常 2 挂失）
     * @return
     */
    @PostMapping("/cardStatus")
    @ApiOperation(value = "挂失或者解挂", response = Boolean.class)
    public Object cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") Byte physicalStatus) {
        return WrapMapper.ok(physicalCardControllerClient.cardStatus(physicalNumber, physicalStatus).getResult());
    }

}
