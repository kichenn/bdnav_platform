package com.bdxh.backend.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.feign.DeptControllerClient;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * 系统部门交互控制层
 */
@RestController
@RequestMapping("/sysDept")
@Validated
@Slf4j
@Api(value = "系统部门交互API", tags = "系统部门交互API")
public class sysDeptController {

    @Autowired
    private DeptControllerClient deptControllerClient;


    /**
     * 根据id查询部门树形菜单
     * @param deptId
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryDeptTreeById",method = RequestMethod.GET)
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

    /**
     * 带条件的查询
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryDeptList",method = RequestMethod.GET)
    @ApiOperation("带条件的查询部门")
    public Object queryDeptList(@Valid @RequestBody DeptQueryDto deptQueryDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper =deptControllerClient.queryDeptList(deptQueryDto);
            return WrapMapper.ok(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
