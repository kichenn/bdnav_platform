package com.bdxh.system.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Permission;
import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.system.entity.RolePermission;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.service.PermissionService;
import com.bdxh.system.service.RolePermissionService;
import com.bdxh.system.service.UserRoleService;
import com.bdxh.system.vo.PermissionTreeVo;
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
                treeVo.setTitle(e.getTitle());
                treeVo.setCreateDate(e.getCreateDate());
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<PermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }

    @RequestMapping(value = "/permissionMenus", method = RequestMethod.POST)
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
    public Object addPermission(@RequestBody AddPermissionDto addPermissionDto) {
        return WrapMapper.ok(permissionService.addPermission(addPermissionDto));
    }

    /**
     * @Description: 修改用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:58
     */
    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限", response = Boolean.class)
    @ResponseBody
    public Object modifyPermission(@RequestBody ModifyPermissionDto mdifyPermissionDto) {
        return WrapMapper.ok(permissionService.modifyPermission(mdifyPermissionDto));
    }


    /**
     * 根据菜单名称搜索查询
     * @param title
     * @return
     */
    @RequestMapping(value = "/findByTitle", method = RequestMethod.POST)
    @ApiOperation(value = "根据菜单名称进行查询", response = Boolean.class)
    public Object findByTitle(@RequestParam("title") String title) {
        return WrapMapper.ok(permissionService.findByTitle(title));
    }



    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:57
     */
    @RequestMapping(value = "/delPermissionById", method = RequestMethod.GET)
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
    @ApiOperation(value = "根据条件查询相对菜单", response = List.class)
    @ResponseBody
    public Object theTreeMenu(@RequestParam(value = "roleId") Long roleId,@RequestParam(value = "selected",defaultValue = "2") Integer selected) {
        List<RolePermissionDto> permissions= permissionService.theTreeMenu(roleId,selected);
        List<PermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)&&permissions.size()>0) {
            permissions.stream().forEach(e -> {
                PermissionTreeVo treeVo = new PermissionTreeVo();
                treeVo.setTitle(e.getTitle());
                treeVo.setCreateDate(e.getCreateDate());
                if (!roleId.equals("")){
                    treeVo.setExpand(Boolean.TRUE);
                }
                if (e.getId().equals(e.getRplist().get(0).getPermissionId())&&e.getRplist().get(0).getRoleId().equals(roleId)&&e.getRplist().get(0).getSelected().equals(2)) {
                    treeVo.setSelected(Boolean.TRUE);
                }
                if (e.getId().equals(e.getRplist().get(0).getPermissionId())&&e.getRplist().get(0).getRoleId().equals(roleId)&&e.getRplist().get(0).getIndeterminate().equals(1)){
                    treeVo.setIndeterminate(Boolean.TRUE);
                }
                if (e.getId().equals(e.getRplist().get(0).getPermissionId())&&e.getRplist().get(0).getRoleId().equals(roleId)&&e.getRplist().get(0).getChecked().equals(1)){
                    treeVo.setChecked(Boolean.TRUE);
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
   @RequestMapping(value = "/addOrUpdatePermission", method = RequestMethod.POST)
    @ApiOperation(value = "保存并修改权限", response = Boolean.class)
    public Object addOrUpdatePermission(@RequestBody BaPermissionsDto baPermissionsDto) {
       try{
           //根据roid将所有权限进行删除
           rolePermissionService.delRolePermission(Long.valueOf(baPermissionsDto.getRoleId()));
           //在批量将前台数据添加进去
           List<AuRolePermissionDto> rpd=baPermissionsDto.getRolePermissionDto();
           for (AuRolePermissionDto pd:rpd){
               RolePermission rolePermission=new RolePermission();
               rolePermission.setRoleId(Long.valueOf(baPermissionsDto.getRoleId()));
               rolePermission.setPermissionId(pd.getId());
               rolePermission.setChecked(pd.getChecked());
               rolePermission.setIndeterminate(pd.getIndeterminate());
               rolePermissionService.save(rolePermission);
           }

            return WrapMapper.ok();
        }catch (Exception e){
        e.printStackTrace();
        return WrapMapper.error(e.getMessage());
     }
    }




    @RequestMapping(value = "/thePermissionMenu", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部菜单", response = List.class)
    public Object thePermissionMenu() {
        List<Permission> permissions = permissionService.selectAll();
        List<PermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (Permission ps:permissions){
                PermissionTreeVo treeVo = new PermissionTreeVo();
                treeVo.setTitle(ps.getTitle());
                treeVo.setCreateDate(ps.getCreateDate());
                BeanUtils.copyProperties(ps, treeVo);
                treeVos.add(treeVo);
            }
        }
        TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<PermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
        return WrapMapper.ok(result);
    }

    /**
     * 根据id查看菜单详情
     * @return
     */
    @RequestMapping(value = "/findPermissionById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查看菜单详情", response = Boolean.class)
    public Object findPermissionById(@RequestParam(value = "id") Long id) {
      Permission permissions = permissionService.selectByKey(id);
        return WrapMapper.ok(permissions);
    }

    /**
     * 父id查询部门信息
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/findPermissionByParentId", method = RequestMethod.GET)
    @ApiOperation(value = "父id查询部门信息")
    public Object findPermissionByParentId(@RequestParam("parentId") Long parentId) {
        return WrapMapper.ok(permissionService.findPermissionByParentId(parentId));
    }


    @RequestMapping(value="/userPermissionMenu",method = RequestMethod.POST)
    @ApiOperation("当前用户所有菜单列表")
    public Object userPermissionMenu(@RequestParam("userId") Long userId){
        try {
            List<UserPermissionDto> permissions = permissionService.findUserRights(userId, new Byte("1"));
            List<PermissionTreeVo> treeVos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(permissions)) {
                permissions.stream().forEach(e -> {
                    PermissionTreeVo treeVo = new PermissionTreeVo();
                    treeVo.setTitle(e.getTitle());
                    treeVo.setPath(e.getPath());
                    treeVo.setComponent(e.getComponent());
                    treeVo.setIcon(e.getIcon());
                    treeVo.setName(e.getName());
                    treeVo.setCreateDate(e.getCreateDate());
                    BeanUtils.copyProperties(e, treeVo);
                    treeVos.add(treeVo);
                });
            }
            TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
            List<PermissionTreeVo> result = treeLoopUtils.getTree(treeVos);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
