package com.bdxh.backend.configration.security.userdetail;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.PermissionControllerClient;
import com.bdxh.system.feign.RoleControllerClient;
import com.bdxh.system.feign.UserControllerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Kang
 * @create: 2019-02-28 14:32
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserControllerClient userControllerClient;

    @Autowired
    private RoleControllerClient roleControllerClient;

    @Autowired
    private PermissionControllerClient permissionControllerClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        Wrapper<User> userWrapper = userControllerClient.queryUserByUserName(username);
        User user = userWrapper.getResult();
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //根据用户id查询角色列表
        Wrapper<List<String>> roleWrapper = roleControllerClient.queryRoleListByUserId(user.getId());
        List<String> roles = roleWrapper.getResult();
        if (roles != null && !roles.isEmpty()) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
        //根据用户id查询权限列表
        Wrapper<List<String>> permissionWrapper = permissionControllerClient.permissionMenusByUserId(user.getId());
        List<String> permissions = permissionWrapper.getResult();
        if (permissions != null && !permissions.isEmpty()) {
            //权限的菜单 也需要 以 ROLE_开头(我们库中未以ROLE开头 所以在此累加)
            permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority("ROLE_" + permission)));
        }
        Byte status = user.getStatus();
        boolean isAccountNonLocked = true;
        if (status.byteValue() == 2) {
            isAccountNonLocked = false;
        }
        MyUserDetails myUserDetails = new MyUserDetails(username, user.getPassword(), isAccountNonLocked, authorities, user);
        return myUserDetails;
    }

}
