package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.RoleQueryDto;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.service.DeptService;
import com.bdxh.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门管理控制类
 */
@RestController
@RequestMapping("/dept")
@Validated
@Slf4j
public class DeptController {

    @Autowired
    private DeptService DeptService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 增加部门
     * @param deptDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addDept",method = RequestMethod.POST)
    public Object addDept(@Valid @RequestBody DeptDto deptDto, BindingResult bindingResult){   //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
         Dept dept = BeanMapUtils.map(deptDto, Dept.class);
            DeptService.save(dept);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改部门
     * @param deptDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/updateDept",method = RequestMethod.POST)
    public Object updateDept(@Valid @RequestBody DeptDto deptDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
           Dept dept = BeanMapUtils.map(deptDto, Dept.class);
            DeptService.update(dept);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param roleQueryDto
     * @param rowBounds
     * @return
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public Object queryList(@Valid @RequestBody RoleQueryDto roleQueryDto, RowBounds rowBounds){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(roleQueryDto);
            List<Dept> Depts = DeptService.selectByExampleAndRowBounds(param,rowBounds);
            return WrapMapper.ok(Depts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据单个id删除部门
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/delDept",method = RequestMethod.POST)
    @Transactional
    public Object delDept(@RequestParam(name = "deptId") @NotNull(message = "部门id不能为空") Long deptId){
       return null;
    }

}
