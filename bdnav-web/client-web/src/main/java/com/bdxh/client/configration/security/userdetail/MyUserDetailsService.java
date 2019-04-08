package com.bdxh.client.configration.security.userdetail;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolPermissionControllerClient;
import com.bdxh.school.feign.SchoolRoleControllerClient;
import com.bdxh.school.feign.SchoolUserControllerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 14:32
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;

    @Autowired
    private SchoolRoleControllerClient schoolRoleControllerClient;

    @Autowired
    private SchoolPermissionControllerClient schoolPermissionControllerClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        Wrapper<SchoolUser> userWrapper = schoolUserControllerClient.findSchoolUserByName(username);
        SchoolUser user = userWrapper.getResult();
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //根据用户id查询角色列表
        Wrapper<List<String>> roleWrapper = schoolRoleControllerClient.findSchoolRolesByUserId(user.getId());
        List<String> roles = roleWrapper.getResult();
        if (roles != null && !roles.isEmpty()){
            roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
        }
        //根据用户id查询权限列表
        Wrapper<List<String>> permissionWrapper = schoolPermissionControllerClient.permissionMenusByUserId(user.getId());
        List<String> permissions = permissionWrapper.getResult();
        if (permissions!=null && !permissions.isEmpty()){
            permissions.forEach(permission->authorities.add(new SimpleGrantedAuthority("ROLE_"+permission)));
        }
        Byte status = user.getStatus();
        boolean isAccountNonLocked = true;
        if (status.byteValue()==2){
            isAccountNonLocked = false;
        }
        MyUserDetails myUserDetails = new MyUserDetails(username,user.getPassword(),isAccountNonLocked,authorities,user);
        return myUserDetails;
    }

}
