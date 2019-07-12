package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.fallback.PhysicalCardControllerFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "wallet-provider-cluster",fallback = PhysicalCardControllerFallback.class)
public interface PhysicalCardControllerClient {

    /**
     * 分页查询
     * @param queryPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/findPhysicalCardInCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<PhysicalCard>> findPhysicalCardInCondition(@RequestBody QueryPhysicalCard queryPhysicalCard);


    /**
     * 添加
     * @param addPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/AddPhysicalCard",method = RequestMethod.POST)
    @ResponseBody
    Wrapper AddPhysicalCard(@RequestBody AddPhysicalCard addPhysicalCard);


    /**
     * 修改
     * @param modifyPhysicalCard
     * @return
     */
    @RequestMapping(value = "/physicalCard/modifyPhysicalCard",method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyPhysicalCard(@RequestBody ModifyPhysicalCard modifyPhysicalCard);



    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/physicalCard/modifyPhysicalCard",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delPhysicalCard(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id);


    /**
     * 根据id查询详情
     * @return
     */
    @RequestMapping(value = "/physicalCard/findPhysicalCardById",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<PhysicalCard> findPhysicalCardById(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id);



}
