package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.feign.DeptControllerClient;
import com.bdxh.system.vo.DeptDetailsVo;
import com.bdxh.system.vo.DeptTreeVo;
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
public class SysDeptController {

    @Autowired
    private DeptControllerClient deptControllerClient;


    /**
     * 根据id查询部门树形菜单
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/queryDeptTreeById", method = RequestMethod.GET)
    @ApiOperation(value="根据id查询部门树形菜单",response = DeptTreeVo.class)
    public Object queryDeptTreeById(@RequestParam(name = "deptId") Long deptId) {
        try {
            Preconditions.checkArgument(deptId != null, "部门id不能为空");
            Wrapper wrapper = deptControllerClient.queryDeptTreeById(deptId);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 带条件的查询
     *
     * @return
     */
    @RequestMapping(value = "/queryDeptList", method = RequestMethod.POST)
    @ApiOperation(value="带条件的查询部门",response = DeptTreeVo.class)
    public Object queryDeptList(@RequestBody DeptQueryDto deptQueryDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = deptControllerClient.queryDeptList(deptQueryDto);
            return WrapMapper.ok(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据父级id查询部门详情
     *
     * @return
     */
    @RequestMapping(value = "/queryDeptById", method = RequestMethod.GET)
    @ApiOperation(value="根据父级id查询部门详情",response = DeptDetailsVo.class)
    public Object queryDeptById(@RequestParam(name = "deptId") Long deptId, @RequestParam(name = "parentId") Long parentId) {
        try {
            DeptDetailsVo result = deptControllerClient.findDeptByParentId(deptId, parentId).getResult();
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 增加部门信息
     *
     * @return
     */
    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    @ApiOperation(value="增加部门",response = Boolean.class)
    public Object addDept(@Validated @RequestBody DeptDto deptDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = deptControllerClient.addDept(deptDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 增加部门信息
     *
     * @return
     */
    @RequestMapping(value = "/updateDept", method = RequestMethod.POST)
    @ApiOperation(value="修改部门信息",response = Boolean.class)
    public Object updateDept(@Validated @RequestBody DeptDto deptDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = deptControllerClient.updateDept(deptDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除部门信息
     *
     * @return
     */
    @RequestMapping(value = "/delDept", method = RequestMethod.POST)
    @ApiOperation(value="删除部门信息",response = Boolean.class)
    public Object delDept(@RequestParam(name = "deptId") Long deptId) {

        try {
            Wrapper wrapper= deptControllerClient.delDept(deptId);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}



