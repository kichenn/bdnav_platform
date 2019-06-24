package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.dto.AddSysBlackUrlDto;
import com.bdxh.system.fallback.SysBlackUrlControllerClientFallback;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 系统黑名单+病毒库的feign
 * @author WanMing
 * @date 2019/6/20 16:06
 */
@Service
@FeignClient(value = "system-provider-cluster",fallback = SysBlackUrlControllerClientFallback.class)
public interface SysBlackUrlControllerClient {

    /**
     * 添加系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/sysBlackUrl/addSysBlackUrl",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSysBlackUrl( @RequestBody AddSysBlackUrlDto addSysBlackUrlDto);


    /**
     * 删除系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/sysBlackUrl/delSysBlackUrl",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSysBlackUrl(@RequestParam("id") Long id);

    /**
     * 批量删除系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/sysBlackUrl/batchDelSysBlackUrl",method = RequestMethod.GET)
    @ResponseBody
    Wrapper batchDelSysBlackUrl(@RequestParam("ids") List<Long> ids);

    /**
     * 根据条件分页查询系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/sysBlackUrl/findSysBlackUrlByCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo> findSysBlackUrlByCondition(@RequestBody SysBlackUrlQueryDto sysBlackUrlQueryDto);



    /**
     * 查询所有本地病毒库数据
     * @Author: WanMing
     * @Date: 2019/6/24 15:02
     */
    @RequestMapping(value = "/sysBlackUrl/queryAllSysBlackUrl",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SysBlackUrlVo>> queryAllSysBlackUrl();


    /**
     * 批量判断url的安全性
     * @Author: WanMing
     * @Date: 2019/6/24 16:19
     */
    @RequestMapping(value = "/sysBlackUrl/batchCheckSysBlackUrl",method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchCheckSysBlackUrl(@RequestParam("urls") List<String> urls);



}
