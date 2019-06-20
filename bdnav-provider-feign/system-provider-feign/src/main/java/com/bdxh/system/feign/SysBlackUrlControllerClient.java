package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.SysBlackUrlQueryDto;
import com.bdxh.system.dto.AddSysBlackUrlDto;
import com.bdxh.system.fallback.SysBlackUrlControllerClientFallback;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
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
     * 判断url是否在黑名单库 true存在 false不存在
     * @Author: WanMing
     * @Date: 2019/6/20 15:54
     */
    @RequestMapping(value = "/sysBlackUrl/querySysBlackUrlByUrl",method = RequestMethod.GET)
    @ResponseBody
    Wrapper querySysBlackUrlByUrl(@RequestParam("url")String url);



}
