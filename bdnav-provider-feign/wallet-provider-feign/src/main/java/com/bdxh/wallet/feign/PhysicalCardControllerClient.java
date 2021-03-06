package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.fallback.PhysicalCardControllerFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 实体卡feign
 * @Author: Kang
 * @Date: 2019/7/15 17:34
 */
@Service
@FeignClient(value = "wallet-provider-cluster", fallback = PhysicalCardControllerFallback.class)
public interface PhysicalCardControllerClient {

    /**
     * 查询账单信息
     *
     * @param findBillRecordDto
     * @return
     */
    @PostMapping("/physicalCard/findBillRecord")
    Wrapper findRechargeRecord(@Validated @RequestBody FindBillRecordDto findBillRecordDto);

    /**
     * 查询卡的状态
     *
     * @param physicalNumber
     * @return
     */
    @GetMapping("/physicalCard/findCardStatus")
    Wrapper<Boolean> findCardStatus(@RequestParam("physicalNumber") String physicalNumber);

    /**
     * @param physicalNumber 实体卡号
     * @param physicalStatus 挂失或者解挂（1 正常 2 挂失）
     * @return
     */
    @PostMapping("/physicalCard/cardStatus")
    Wrapper<Boolean> cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") Byte physicalStatus);

    /**
     * 分页查询
     *
     * @param queryPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/findPhysicalCardInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<PhysicalCard>> findPhysicalCardInCondition(@RequestBody QueryPhysicalCard queryPhysicalCard);


    /**
     * 添加
     *
     * @param addPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/addPhysicalCard", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addPhysicalCard(@RequestBody AddPhysicalCard addPhysicalCard);


    /**
     * 修改
     *
     * @param modifyPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/modifyPhysicalCard", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyPhysicalCard(@RequestBody ModifyPhysicalCard modifyPhysicalCard);


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/physicalCard/delPhysicalCard", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delPhysicalCard(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id);


    /**
     * 根据id查询详情
     *
     * @return
     */
    @RequestMapping(value = "/physicalCard/findPhysicalCardById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<PhysicalCard> findPhysicalCardById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id);


}
