package com.bdxh.onecard.feigin;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.XianAddBlanceDto;
import com.bdxh.onecard.dto.XianQueryBlanceDto;
import com.bdxh.onecard.dto.XianQueryConsListDto;
import com.bdxh.onecard.dto.XianSyscDataDto;
import com.bdxh.onecard.fallback.XianCardControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 西安一卡通feigin客户端
 * @author: xuyuan
 * @create: 2019-01-11 18:42
 **/
@Service
@FeignClient(value = "onecard-provider-cluster",fallback= XianCardControllerFallback.class)
@RequestMapping("/xianCard")
public interface XianCardControllerClient {

    /**
     * 一卡通身份验证余额查询接口
     * @return
     */
    @RequestMapping(value = "/syscUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper syscUser(@RequestBody XianSyscDataDto xianSyscDataDto);

    /**
     * 一卡通身份验证余额查询接口
     * @return
     */
    @RequestMapping(value = "/queryBalance", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryBalance(@RequestBody XianQueryBlanceDto xianQueryBlanceDto);

    /**
     * 一卡通充值接口
     */
    @RequestMapping("/addBalance")
    @ResponseBody
    Wrapper addBalance(@RequestBody XianAddBlanceDto xianAddBlanceDto);

    /**
     * 消费记录查询
     */
    @RequestMapping(value = "/queryTradeList", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryTradeList(@RequestBody XianQueryConsListDto xianQueryConsListDto);

    /**
     * 充值结果查询
     */
    @RequestMapping(value = "/queryAddResult", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryAddResult(@RequestParam(name = "schoolCode") String schoolCode, @RequestParam(name = "orderNo") String orderNo);

}
