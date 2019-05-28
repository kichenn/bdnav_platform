package com.bdxh.account.feign;

import com.bdxh.account.dto.*;
import com.bdxh.account.entity.AccountLog;
import com.bdxh.account.fallback.AccountLogControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@FeignClient(value = "account-provider-cluster", fallback = AccountLogControllerClientFallback.class)
public interface AccountLogControllerClient {

    /**
     * 增加账户日志信息
     */
    @RequestMapping(value = "/accountLog/addAccountLog", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addAccountLog(@RequestBody AddAccountLogDto addAccountLogDto);

    /**
     * 学校code，用户学号查询账户最后一次登录信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/findAcountLogByCodeAndCard", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<AccountLog> findAcountLogByCodeAndCard(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber);

    /**
     * 登录名查询账户最后一次登录信息
     *
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/findAcountLogByLoginName", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<AccountLog> findAcountLogByLoginName(@RequestParam("loginName") String loginName);

    /**
     * userId查询账户最后一次登录信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/findAcountLogByUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<AccountLog> findAcountLogByUserId(@RequestParam("userId") String userId);


}