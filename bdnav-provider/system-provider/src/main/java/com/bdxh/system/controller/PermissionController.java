package com.bdxh.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.AuRolePermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.entity.Permission;
import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.RolePermission;
import com.bdxh.system.service.PermissionService;
import com.bdxh.system.service.RolePermissionService;
import com.bdxh.system.service.impl.RolePermissionServiceImpl;
import com.bdxh.system.vo.PermissionTreeVo;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/2/28 19:49
 */
@RestController
@RequestMapping("/permission")
@Validated
@Slf4j
@Api(value = "权限操作菜单管理", tags = "权限操作菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * @Description: 角色id查询用户菜单or按钮权限
     * @Author: Kang
     * @Date: 2019/2/28 19:55
     */
    @RequestMapping(value = "/findPermissionByRoleId", method = RequestMethod.GET)
    @ApiOperation(value = "角色id查询用户菜单or按钮权限", response = List.class)
    @ResponseBody
    public Object findPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type) {
        List<Permission> permissions = permissionService.findPermissionByRoleId(roleId, type);

        List<PermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                PermissionTreeVo treeVo = new PermissionTreeVo();
                treeVo.setTitle(e.getName());
                treeVo.setCreateDate(e.getCreateDate());
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<PermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }

    @RequestMapping(value = "/permissionMenus", method = RequestMethod.GET)
    @ApiOperation(value = "角色id查询用户菜单", response = List.class)
    @ResponseBody
    public Object permissionMenus(@RequestParam("roleId") Long roleId) {
        List<String> permissions = permissionService.permissionMenus(roleId);
        return WrapMapper.ok(permissions);
    }

    /**
     * @Description: 增加用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:56
     */
    @RequestMapping(value = "/addPermission", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户权限", response = Boolean.class)
    @ResponseBody
    public Object addPermission(@RequestBody AddPermissionDto dto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        return WrapMapper.ok(permissionService.addPermission(permission));
    }

    /**
     * @Description: 修改用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:58
     */
    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限", response = Boolean.class)
    @ResponseBody
    public Object modifyPermission(@RequestBody ModifyPermissionDto dto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        return WrapMapper.ok(permissionService.modifyPermission(permission));
    }

    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:57
     */
    @RequestMapping(value = "/delPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户权限", response = Boolean.class)
    @ResponseBody
    public Object delPermissionId(@RequestParam("id") Long id) {
        return WrapMapper.ok(permissionService.delPermissionById(id));
    }

    /**
     * @Description: 角色删除所有权限
     * @Author: Kang
     * @Date: 2019/3/4 17:50
     */
    @RequestMapping(value = "/delPermissionByRoleId", method = RequestMethod.POST)
    @ApiOperation(value = "角色删除所有权限", response = Boolean.class)
    @ResponseBody
    public Object delPermissionByRoleId(@RequestParam("roleId") Long roleId) {
        List<Long> permissionIds = rolePermissionService.findPermissionIdByRoleId(roleId);
        return WrapMapper.ok(permissionService.batchDelPermission(permissionIds));
    }

    /**
     *  查询全部菜单列表
     *   @return
     */
    @RequestMapping(value = "/theTreeMenu", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部菜单", response = List.class)
    @ResponseBody
    public Object theTreeMenu(@RequestParam(value = "roleId") Long roleId,@RequestParam(value = "selected",defaultValue = "2") Integer selected) {

        List<RolePermissionDto> permissions = permissionService.theTreeMenu(roleId,selected);
          System.out.print("==============="+permissions);

        List<PermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                PermissionTreeVo treeVo = new PermissionTreeVo();
                treeVo.setTitle(e.getName());
                treeVo.setCreateDate(e.getCreateDate());
                treeVo.setExpand(Boolean.TRUE);
                if (e.getId().equals(e.getRplist().get(0).getPermissionId())&&e.getRplist().get(0).getRoleId().equals(roleId)) {
                    treeVo.setSelected(Boolean.TRUE);
                }
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<PermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }


    /**
     * 保存并修改权限
     * @return
     */
   @RequestMapping(value = "/addorUpdatePermission", method = RequestMethod.POST)
    @ApiOperation(value = "保存并修改权限", response = Boolean.class)
    public Object addorUpdatePermission(@RequestParam(value = "roleId") Long roleId,@RequestParam(value = "arpdDtos")String arpdDtos) {
       try{
       JSONArray addPermissionDto = JSON.parseArray(arpdDtos);
   /*        String permissionId=jsonObject.getString("id");*/
           //查询当前角色关系表中全部权限
           List<RolePermission> rps=rolePermissionService.findPermissionId(roleId);
           Boolean  b = rps.containsAll(addPermissionDto) && addPermissionDto.containsAll(rps);
           if (b.equals(Boolean.FALSE)){
               for(RolePermission u:rps){
                   rolePermissionService.deleteByKey(u.getId());
               }
               for (int i = 0; i < addPermissionDto.size(); i++) {
                   JSONObject jsonObject = (JSONObject) addPermissionDto.get(i);
                   RolePermission rolePermission=new RolePermission();
                   rolePermission.setPermissionId(Long.valueOf(jsonObject.getString("id")));
                   rolePermission.setRoleId(roleId);
                   rolePermission.setSelected(2);
                   rolePermissionService.save(rolePermission);
            }
       }
            return WrapMapper.ok();
        }catch (Exception e){
        e.printStackTrace();
        return WrapMapper.error(e.getMessage());
    }
    }


    /**
     * 保存并修改权限
     * @return
     */
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ApiOperation(value = "测试返回数据保存并修改权限", response = Boolean.class)
    public Object test1(
            @RequestParam(value = "roleId") Long roleId) {
            //查询当前角色关系表中全部权限
        List<RolePermission> rps=rolePermissionService.findPermissionId(roleId);
        for(RolePermission u:rps){
            System.out.print(u.getId());
            rolePermissionService.deleteByKey(u.getId());
        }
            //判断list中的值长度有则进入下一层 没有权限增添加
   /*         if (rps.size()>0){
            }*/
        return WrapMapper.ok(rps);
    }


}
