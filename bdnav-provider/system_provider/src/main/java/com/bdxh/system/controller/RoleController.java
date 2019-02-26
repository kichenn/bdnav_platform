package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.RoleDto;
import com.bdxh.system.dto.RoleQueryDto;
import com.bdxh.system.entity.Role;
import com.bdxh.system.service.RoleService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
@Validated
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 增加角色
     * @param roleDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Object addRole(@Valid @RequestBody RoleDto roleDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Role role = BeanMapUtils.map(roleDto, Role.class);
            roleService.save(role);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/delRole",method = RequestMethod.POST)
    public Object delRole(@RequestParam(name = "id") @NotNull(message = "角色id不能为空") Long id){
        try {
            roleService.delRole(id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据ids批量删除角色
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delBatchRole",method = RequestMethod.POST)
    @Transactional
    public Object delBatchRole(@RequestParam(name = "ids") @NotEmpty(message = "角色id不能为空") String ids){
        try {
            String[] idsArr = StringUtils.split(ids,",");
            List<Long> idsLongArr = new ArrayList<>(15);
            if (idsArr!=null&&idsArr.length>0){
                for (int i=0;i<idsArr.length;i++){
                    String id = idsArr[i];
                    if (StringUtils.isNotEmpty(id)){
                        idsLongArr.add(Long.valueOf(id));
                    }
                }
            }
            roleService.delBatchRole(idsLongArr);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 修改角色信息
     * @param roleDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public Object updateRole(@Valid @RequestBody RoleDto roleDto,BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Role role = BeanMapUtils.map(roleDto, Role.class);
            roleService.update(role);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryRoleById",method = RequestMethod.GET)
    public Object queryRole(@RequestParam(name = "id") @NotNull(message = "角色id不能为空") Long id){
        try {
            Role role = roleService.selectByKey(id);
            return WrapMapper.ok(role);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询列表
     * @param roleQueryDto
     * @return
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public Object queryList(@Valid @RequestBody RoleQueryDto roleQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(roleQueryDto);
            List<Role> Roles = roleService.findList(param);
            return WrapMapper.ok(Roles);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param roleQueryDto
     * @return
     */
    @RequestMapping(value = "/queryListPage",method = RequestMethod.GET)
    public Object queryListPage(@Valid @RequestBody RoleQueryDto roleQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(roleQueryDto);
            PageInfo<Role> Roles = roleService.findRoleList(param, roleQueryDto.getPageNum(),roleQueryDto.getPageSize());
            return WrapMapper.ok(Roles);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
