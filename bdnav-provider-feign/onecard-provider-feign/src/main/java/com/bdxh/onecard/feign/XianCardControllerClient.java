package com.bdxh.onecard.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.*;
import com.bdxh.onecard.fallback.XianCardControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description: 西安一卡通feigin客户端
 * @author: xuyuan
 * @create: 2019-01-11 18:42
 **/
@Service
@FeignClient(value = "onecard-provider-cluster",fallback= XianCardControllerFallback.class)
public interface XianCardControllerClient {

    /**
     * 一卡通用户同步接口
     * @return
     */
    @RequestMapping(value = "/xianCard/syscUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper syscUser(@RequestBody XianSyscDataDto xianSyscDataDto);

    /**
     * 一卡通身份验证余额查询接口
     * @return
     */
    @RequestMapping(value = "/xianCard/queryBalance", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryBalance(@RequestBody XianQueryBlanceDto xianQueryBlanceDto);

    /**
     * 一卡通充值接口
     */
    @RequestMapping(value = "/xianCard/addBalance", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addBalance(@RequestBody XianAddBlanceDto xianAddBlanceDto);

    /**
     * 一卡通消费接口
     */
    @RequestMapping(value = "/subBalance", method = RequestMethod.POST)
    @ResponseBody
    Wrapper subBalance(@RequestBody XianSubBlanceDto xianSubBlanceDto);

    /**
     * 消费记录查询
     */
    @RequestMapping(value = "/xianCard/queryTradeList", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryTradeList(@RequestBody XianQueryConsListDto xianQueryConsListDto);

    /**
     * 充值结果查询
     */
    @RequestMapping(value = "/xianCard/queryAddResult", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryAddResult(@RequestParam(name = "schoolCode") String schoolCode, @RequestParam(name = "orderNo") String orderNo);

}
