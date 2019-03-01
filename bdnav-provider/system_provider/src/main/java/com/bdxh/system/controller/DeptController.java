package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.vo.DeptVo;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.service.DeptService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "系统部门相关API", tags = "系统部门管理")
public class DeptController {

    //当前为父级等级节点（1为当前第一级节点，也就是父级节点）
    private static final Byte LEVEL = 1;


    @Autowired
    private DeptService deptService;


    /**
     * 部门id递归查询
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/findDeptTreeById", method = RequestMethod.GET)
    @ApiOperation("部门树形结构关系")
    public Object findDeptTreeById(@RequestParam(name="deptId") Long deptId) {
        try {
            List<Dept> depts = deptService.findParentDeptById(deptId,LEVEL);
            if (CollectionUtils.isEmpty(depts)) {
                return WrapMapper.error("该部门下无其他部门，请检查！");
            }
            List<DeptVo> schoolClassDtos = depts.stream().map(e -> {
                DeptVo tempDto = new DeptVo();
                BeanUtils.copyProperties(e, tempDto);
                tempDto.setDeptVos(deptService.findDeptRelation(tempDto));
                return tempDto;
            }).collect(Collectors.toList());
            return WrapMapper.ok(schoolClassDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    /**
     * 增加部门
     * @param deptDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addDept",method = RequestMethod.POST)
    public Object addDept(@Valid @RequestBody DeptDto deptDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
         Dept dept = BeanMapUtils.map(deptDto, Dept.class);
            deptService.save(dept);
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
            deptService.update(dept);
            return WrapMapper.ok();
        } catch (Exception e) {
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
    public Object delDept(@RequestParam(name = "deptId") @NotNull(message = "部门id不能为空") Long deptId){
        try {
            deptService.deleteByKey(deptId);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @ApiOperation("根据id查询部门")
    @RequestMapping(value = "/queryDeptById",method = RequestMethod.GET)
    public Object queryDept(@RequestParam(name = "id") @NotNull(message = "部门id不能为空") Long id){
        try {
            Dept dept = deptService.selectByKey(id);
            return WrapMapper.ok(dept);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据条件查询列表
     * @param deptQueryDto
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public Object queryList(@Valid @RequestBody DeptQueryDto deptQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(deptQueryDto);
            List<Dept> depts = deptService.queryList(param);
            return WrapMapper.ok(depts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param deptQueryDto
     * @return
     */
    @ApiOperation("根据条件分页查找字典")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.GET)
    public Object queryListPage(@Valid @RequestBody DeptQueryDto deptQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(deptQueryDto);
            PageInfo<Dept> depts = deptService.findListPage(param, deptQueryDto.getPageNum(),deptQueryDto.getPageSize());
            return WrapMapper.ok(depts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




}
