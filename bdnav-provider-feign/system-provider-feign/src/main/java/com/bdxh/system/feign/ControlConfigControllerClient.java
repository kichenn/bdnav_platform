package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.dto.QueryControlConfig;
import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.fallback.ControlConfigControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(value = "system-provider-cluster",fallback= ControlConfigControllerClientFallback.class)
public interface ControlConfigControllerClient {

    /**
     * 增加部门
     */
    @RequestMapping(value = "/controlConfig/addControlConfig")
    @ResponseBody
    Wrapper addControlConfig(@RequestBody AddControlConfig addControlConfig);

    /**
     * 修改部门
     */
    @RequestMapping(value = "/controlConfig/modifyControlConfig")
    @ResponseBody
    Wrapper modifyControlConfig(@RequestBody ModifyControlConfig modifyControlConfig);

    /**
     * 删除部门
     */
    @RequestMapping(value = "/controlConfig/delControlConfig",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delControlConfig(@RequestParam(name = "id") Long id);

    /**
     * 根据类型查找信息
     * @param appType
     * @return
     */
    @RequestMapping(value = "/controlConfig/findAppType",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> findAppType(@RequestParam(name = "appType") Byte appType);


    /**
     * 带条件的分页查询
     */
    @RequestMapping(value = "/controlConfig/queryListPage")
    @ResponseBody
    Wrapper<PageInfo<ControlConfig>> queryListPage(@RequestBody QueryControlConfig queryControlConfig);

}
