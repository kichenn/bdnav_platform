package com.bdxh.app.configration.security.userdetail;

import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/5/10 15:04
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountControllerClient accountControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断是否为手机号
        boolean isPhone = ValidatorUtil.isMobile(username);
        //效验该登录账号是手机号还是登录名信息
        String phone = isPhone ? username : "";
        String loginName = isPhone ? "" : username;
        Wrapper<Account> wrapper = accountControllerClient.findAccountByLoginNameOrPhone(phone, loginName);
        Account account = wrapper.getResult();
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //true为未过期,false  已过期
        boolean isAccountNonExpired = account.getAccountExpired() == 1 ? Boolean.TRUE : Boolean.FALSE;
        //true为未锁定,false 已锁定
        boolean isAccountNonLocked = account.getAccountLocked() == 1 ? Boolean.TRUE : Boolean.FALSE;

        //设置组织id
        switch (account.getUserType()) {
            case 1:
                //学生
                //查询该学生的信息
                StudentVo student = studentControllerClient.queryStudentInfo(account.getSchoolCode(), account.getCardNumber()).getResult();
                if (student == null) {
                    throw new UsernameNotFoundException("学生不存在");
                }
                //组织id：此处为班级id
                account.setGroupId(student.getClassId());
                break;
            case 2:
                //老师
                break;
            case 3:
                //家长
                break;
        }

        MyUserDetails myUserDetails = new MyUserDetails(account.getId(), username, account.getPassword(), isAccountNonExpired, isAccountNonLocked, account);
        return myUserDetails;
    }

}
