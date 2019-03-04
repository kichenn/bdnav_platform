package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.fallback.DeptControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@FeignClient(value = "system-provider-cluster",fallback= DeptControllerClientFallback.class)
public interface DeptControllerClient {

    /**
     * 根据id查询部门树形菜单
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/dept/findDeptTreeById")
    @ResponseBody
    Wrapper<List<String>> queryDeptTreeById(Long deptId);
}
