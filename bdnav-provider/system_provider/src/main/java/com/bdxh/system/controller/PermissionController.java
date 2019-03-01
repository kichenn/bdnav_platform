package com.bdxh.system.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.helper.tree.utils.TreeLoopUtils;
import com.bdxh.system.helper.tree.vo.PermissionTreeVo;
import com.bdxh.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    /**
     * @Description: 角色id查询用户菜单or按钮权限
     * @Author: Kang
     * @Date: 2019/2/28 19:55
     */
    @RequestMapping(value = "/findPermissionByRoleId", method = RequestMethod.GET)
    @ApiOperation(value = "角色id查询用户权限", response = Boolean.class)
    @ResponseBody
    public Object findPermissionByRoleId(@RequestParam("roleId") Long roleId, @RequestParam("type") Byte type) {
        List<Permission> permissions = permissionService.findPermissionByRoleId(roleId, type);

        List<PermissionTreeVo> treeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                PermissionTreeVo treeVo = new PermissionTreeVo();
                BeanUtils.copyProperties(e, treeVo);
                treeVos.add(treeVo);
            });
        }
        TreeLoopUtils<PermissionTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<PermissionTreeVo> result = treeLoopUtils.getChild(new Long("1"), treeVos);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 增加用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:56
     */
    @RequestMapping(value = "/addPermission", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户权限", response = Boolean.class)
    @ResponseBody
    public Object addPermission() {
        return WrapMapper.ok();
    }

    /**
     * @Description: 修改用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:58
     */
    @RequestMapping(value = "/modifyPermission", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户权限", response = Boolean.class)
    @ResponseBody
    public Object modifyPermission() {
        return WrapMapper.ok();
    }

    /**
     * @Description: 删除用户权限
     * @Author: Kang
     * @Date: 2019/2/28 19:57
     */
    @RequestMapping(value = "/delPermissionById", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户权限", response = Boolean.class)
    @ResponseBody
    public Object delPermission() {
        return WrapMapper.ok();
    }

//    /**
//     * 根据用户id查询角色列表
//     * @param userId
//     * @return
//     */
//    @ApiOperation("根据用户id查询角色列表")
//    @RequestMapping(value = "/queryPermissionListByUserId",method = RequestMethod.GET)
//    public Object queryPermissionListByUserId(@RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId){
//        try {
//            List<String> permissions = permissionService.getPermissionListByUserId(userId);
//            return WrapMapper.ok(permissions);
//        }catch (Exception e){
//            e.printStackTrace();
//            return WrapMapper.error(e.getMessage());
//        }
//    }

}
