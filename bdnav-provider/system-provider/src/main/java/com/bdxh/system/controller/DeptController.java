package com.bdxh.system.controller;

import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.vo.DeptDetailsVo;
import com.bdxh.system.vo.DeptTreeVo;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.dto.DeptQueryDto;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.service.DeptService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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
import java.util.ArrayList;
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


    @Autowired
    private DeptService deptService;

    /**
     * 部门id递归查询
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/findDeptTreeById", method = RequestMethod.GET)
    @ApiOperation("部门树形结构关系")
    public Object findDeptTreeById(@RequestParam(name = "deptId", defaultValue = "1") Long deptId) {
        try {
            //根据部门查询所有部门
            List<Dept> depts = deptService.findParentDeptById(deptId);
            List<DeptTreeVo> treeVos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(depts)) {
                depts.stream().forEach(e -> {
                    DeptTreeVo treeVo = new DeptTreeVo();
                    treeVo.setTitle(e.getDeptFullName());
                    treeVo.setSort(e.getSort());
                    treeVo.setCreateDate(e.getCreateDate());
         /*           treeVo.setLevel(String.valueOf(e.getLevel()));*/
                    BeanUtils.copyProperties(e, treeVo);
                    treeVos.add(treeVo);
                });
            }
            TreeLoopUtils<DeptTreeVo> treeLoopUtils = new TreeLoopUtils<>();
           // List<DeptTreeVo> result = treeLoopUtils.getChild(new Long("1"), treeVos);
            List<DeptTreeVo> result = treeLoopUtils.getTree(treeVos);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }


    /**
     * 增加部门
     *
     * @param deptDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    @ApiOperation("增加部门信息")
    public Object addDept(@Valid @RequestBody DeptDto deptDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Dept deptss= deptService.getByDeptName(deptDto.getDeptFullName());
            Preconditions.checkArgument(deptss == null, "该部门已存在,请勿重复添加");
            Boolean flag=deptService.addDept(deptDto);
            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 修改部门
     *
     * @param deptDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/updateDept", method = RequestMethod.POST)
    @ApiOperation("修改部门信息")
    public Object updateDept(@Valid @RequestBody DeptDto deptDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean flag;
            Dept deptt= deptService.getByDeptName(deptDto.getDeptFullName());
            if (deptt!=null){
                if(deptt.getDeptFullName().equals(deptDto.getDeptFullName())&&!deptt.getId().equals(deptDto.getId())){
                    return WrapMapper.error("该部门名称已存在,请更换名称");
                }else{
                    flag =  deptService.modifyDept(deptDto);
                }
            }else{
                flag =  deptService.modifyDept(deptDto);
            }

            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据单个id删除部门
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/delDept", method = RequestMethod.POST)
    @ApiOperation("删除单个部门")
    public Object delDept(@RequestParam(name = "deptId") Long deptId) {
        try {
            //deptService.deleteByKey(deptId);
            Boolean flag=deptService.delDept(deptId);
                return WrapMapper.ok(flag);

        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 查询部门详情
     *
     * @return
     */
    @ApiOperation("查询部门详情")
    @RequestMapping(value = "/findDeptByParentId", method = RequestMethod.GET)
    public Object findDeptByParentId(@RequestParam(name = "deptId") Long deptId, @RequestParam(name = "parentId") Long parentId) {
        DeptDetailsVo ddv = new DeptDetailsVo();
        Dept parentDept = deptService.findDeptByParentId(deptId, parentId);
        ddv.setDept(parentDept);
        Dept dept = deptService.selectByKey(parentDept.getParentId());
        ddv.setParentName(dept.getDeptFullName());
        ddv.setParentLeven(dept.getLevel());
        return WrapMapper.ok(ddv);
    }


    /**
     * 根据条件查询列表
     *
     * @param deptQueryDto
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    public Object queryList(@Valid @RequestBody DeptQueryDto deptQueryDto,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(deptQueryDto);
            List<Dept> depts = deptService.queryList(param);
            List<DeptTreeVo> treeVos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(depts)) {
                depts.stream().forEach(e -> {
                    DeptTreeVo treeVo = new DeptTreeVo();
                    treeVo.setTitle(e.getDeptFullName());
                    treeVo.setSort(e.getSort());
                    BeanUtils.copyProperties(e, treeVo);
                    treeVos.add(treeVo);
                });
            }
            return WrapMapper.ok(treeVos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     *
     * @param deptQueryDto
     * @return
     */
    @ApiOperation("根据条件分页查找字典")
    @RequestMapping(value = "/queryListPage", method = RequestMethod.POST)
    public Object queryListPage(@Valid @RequestBody DeptQueryDto deptQueryDto,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(deptQueryDto);
            PageInfo<Dept> depts = deptService.findListPage(param, deptQueryDto.getPageNum(), deptQueryDto.getPageSize());
            return WrapMapper.ok(depts);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
