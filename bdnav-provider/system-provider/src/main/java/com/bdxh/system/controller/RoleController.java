package com.bdxh.system.controller;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddRoleDto;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.dto.RoleQueryDto;
import com.bdxh.system.dto.UpdateRoleDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.Role;
import com.bdxh.system.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "系统角色管理", tags = "系统角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 增加角色
     * @param addRoleDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("添加角色信息")
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Object addRole(@Valid @RequestBody AddRoleDto addRoleDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Role roleData = roleService.getRoleByRole(addRoleDto.getRole());
            Preconditions.checkArgument(roleData == null, "该角色已经存在,请勿重复添加");
            Role role = BeanMapUtils.map(addRoleDto, Role.class);
            roleService.save(role);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改角色信息
     * @param updateRoleDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改角色信息")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public Object updateRole(@Valid @RequestBody UpdateRoleDto updateRoleDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean flag;
            Role role = BeanMapUtils.map(updateRoleDto, Role.class);
            Role roleData = roleService.getRoleByRole(updateRoleDto.getRole());
           /* Role roleData = roleService.selectByKey(updateRoleDto.getId());*/
           if (roleData!=null){
              if(roleData.getRole().equals(updateRoleDto.getRole())&&!roleData.getId().equals(updateRoleDto.getId())){
                  return WrapMapper.error("该角色已存在,请更换角色");
              }else{
                  flag =roleService.update(role)>0;
              }
          }else{
              flag =roleService.update(role)>0;
          }
            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据id删除角色
     * @return
     */
    @ApiOperation("根据id删除角色")
    @RequestMapping(value = "/delRole",method = RequestMethod.GET)
    public Object delRole(@RequestParam(name = "roleId") Long roleId){
        try {
            roleService.delRole(roleId);
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
    @ApiOperation("根据ids批量删除角色")
    @RequestMapping(value = "/delBatchRole",method = RequestMethod.POST)
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
     * 根据id查询角色信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询角色信息")
    @RequestMapping(value = "/queryRoleById",method = RequestMethod.GET)
    public Object queryRoleById(@RequestParam(name = "id") @NotNull(message = "角色id不能为空") Long id){
        try {
            Role role = roleService.selectByKey(id);
            return WrapMapper.ok(role);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    @ApiOperation("根据用户id查询角色列表")
    @RequestMapping(value = "/queryRoleListByUserId",method = RequestMethod.GET)
    public Object queryRoleListByUserId(@RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId){
        try {
            List<String> roles = roleService.getRoleListByUserId(userId);
            return WrapMapper.ok(roles);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询角色列表
     * @param roleQueryDto
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    public Object queryList(@Valid @RequestBody RoleQueryDto roleQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
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
    @ApiOperation("根据条件分页查找")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.POST)
    public Object queryListPage(@Valid @RequestBody RoleQueryDto roleQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(roleQueryDto);
            PageInfo<Role> Roles = roleService.findListPage(param, roleQueryDto.getPageNum(),roleQueryDto.getPageSize());
            return WrapMapper.ok(Roles);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 分页查询字典列表
     * @return
     */
    @ApiOperation("分页查询角色列表")
    @RequestMapping(value = "/findPageRoleListAll",method = RequestMethod.GET)
    public Object findPageRoleListAll(@RequestParam(name = "pageNum")Integer pageNum,
                                      @RequestParam(name = "pageSize")Integer pageSize){
        try {
            RoleQueryDto rqd=new RoleQueryDto();
            rqd.setPageNum(pageNum);
            rqd.setPageSize(pageSize);
            PageInfo<Role> roleVos=roleService.findRolesInConditionPaging(rqd.getPageNum(),rqd.getPageSize());
            return WrapMapper.ok(roleVos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




}
