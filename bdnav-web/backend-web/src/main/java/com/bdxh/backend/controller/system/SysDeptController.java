package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.feign.DeptControllerClient;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 系统部门交互控制层
 */
@RestController
@RequestMapping("/sysDept")
@Validated
@Slf4j
@Api(value = "系统部门交互API", tags = "系统部门交互API")
public class SysDeptController {

    @Autowired
    private DeptControllerClient deptControllerClient;


    /**
     * 根据id查询部门树形菜单
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/queryDeptTreeById",method = RequestMethod.POST)
    @ApiOperation("根据id查询部门树形菜单")
    public Object queryDeptTreeById(@RequestParam(name = "deptId") Long deptId){
        try {
            Preconditions.checkArgument(deptId!=null,"部门id不能为空");
            Wrapper wrapper =deptControllerClient.queryDeptTreeById(deptId);
            return WrapMapper.ok(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




}
