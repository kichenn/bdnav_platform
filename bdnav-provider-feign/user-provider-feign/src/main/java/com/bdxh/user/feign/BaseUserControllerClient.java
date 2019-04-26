package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.fallback.BaseUserControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 11:28
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = BaseUserControllerFallback.class)
public interface BaseUserControllerClient {

    /**
     * 查询单个家长信息
     * @return
     */
    @RequestMapping(value ="/baseUser/queryAllUserPhone",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> queryAllUserPhone();


    /**
     * 根据手机号查询所有用户手机号是否存在
     * @return
     */
    @RequestMapping(value ="/baseUser/queryUserPhoneByPhone",method = RequestMethod.POST)
    Wrapper queryUserPhoneByPhone(@RequestBody BaseUserQueryDto baseUserQueryDto);
}