package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.fallback.DeptControllerClientFallback;
import com.bdxh.system.vo.DeptDetailsVo;
import com.bdxh.system.vo.DeptTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
    Wrapper<List<DeptTreeVo>> queryDeptTreeById(@RequestParam(name = "deptId")Long deptId);

    /**
     *带条件的搜索查询
     * @return
     */
    @RequestMapping(value = "/dept/queryList")
    @ResponseBody
    Wrapper<List<DeptTreeVo>> queryDeptList(@RequestBody DeptQueryDto deptQueryDto);


    /**
     *查询部门详情
     * @return
     */
    @RequestMapping(value = "/dept/findDeptByParentId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<DeptDetailsVo> findDeptByParentId(@RequestParam(name = "deptId")Long deptId, @RequestParam(name = "parentId")Long parentId);

    /**
     * 增加部门
     */
    @RequestMapping(value = "/dept/addDept")
    @ResponseBody
    Wrapper addDept(@RequestBody DeptDto deptDto);

    /**
     * 修改部门
     */
    @RequestMapping(value = "/dept/updateDept")
    @ResponseBody
    Wrapper updateDept(@RequestBody DeptDto deptDto);

    /**
     * 删除部门
     */
    @RequestMapping(value = "/dept/delDept",method = RequestMethod.POST)
    @ResponseBody
    Wrapper delDept(@RequestParam(name = "deptId")Long deptId);




}
