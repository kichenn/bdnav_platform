package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.RoleDto;
import com.bdxh.system.dto.RoleQueryDto;
import com.bdxh.system.entity.Role;
import com.bdxh.system.service.RoleService;
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
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
@Validated
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

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
     * 根据单个id删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/delRole",method = RequestMethod.POST)
    @Transactional
    public Object delRole(@RequestParam(name = "roleId") @NotNull(message = "角色id不能为空") Long roleId){
        try {
            //1.查找当前角色id的用户信息
            List<Role> Rolelist=userRoleService.findUserRole(roleId);
            //2.删除关系表中当前角色id下的数据
            for (int i = 0; i <Rolelist.size() ; i++) {
                System.out.print(i);
                int result=userRoleService.deleteByKey(i);
                if(result>0){
                    //3.删除当前角色
                    roleService.deleteByKey(roleId);
                }
            }
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除角色
     * @param idsinfo
     * @return
     */
    @RequestMapping(value = "/delBatchRole",method = RequestMethod.POST)
    @Transactional
    public Object delBatchRole(@RequestParam(name = "idsinfo")String idsinfo){
        try {
            String[] idsArr =idsinfo.split(",");
            Integer[] ids=new Integer[idsArr.length];
            for(int i=0;i<ids.length;i++){
                int a = Integer.parseInt(idsArr[i]);
                /*ids[i]=Integer.parseInt(idsArr[i]);*/
                //1.查找当前角色id的用户信息
                List<Role> Rolelist=userRoleService.findUserRole(Long.valueOf(a));
                for (int j = 0; j <Rolelist.size() ; j++) {
                    int result=userRoleService.deleteByKey(j);
                    if(result>0){
                        //3.删除当前角色
                        roleService.batchDelete(Rolelist);
                    }
                }

            }
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
     * 根据条件分页查找
     * @param roleQueryDto
     * @param rowBounds
     * @return
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public Object queryList(@Valid @RequestBody RoleQueryDto roleQueryDto, RowBounds rowBounds){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(roleQueryDto);
            List<Role> Roles = roleService.selectByExampleAndRowBounds(param,rowBounds);
            return WrapMapper.ok(Roles);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
