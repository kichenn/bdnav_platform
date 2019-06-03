package com.bdxh.app.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.account.dto.*;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.account.feign.AccountLogControllerClient;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.app.configration.redis.RedisUtil;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import com.bdxh.app.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.udf.AggregatingUDFFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 权限控制相关
 * @Author: Kang
 * @Date: 2019/5/10 15:01
 */
@RestController
@Api(value = "app用户登录信息", tags = "app用户登录信息")
@Slf4j
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountControllerClient accountControllerClient;

    @Autowired
    private AccountLogControllerClient accountLogControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    @ApiOperation(value = "获取token(用户登录)", response = Boolean.class)
    @RequestMapping(value = "/authenticationApp/login", method = RequestMethod.GET)
    public void login(@RequestParam("username") String username, @RequestParam("password") String password,
                      @RequestParam("imei") String imei,
                      @RequestParam("clientId") String clientId,
                      @RequestParam("operationSystem") Byte operationSystem,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
            Account account = myUserDetails.getAccount();
            Account accountTemp = BeanMapUtils.map(account, Account.class);
            accountTemp.setPassword("");
            String accountStr = JSON.toJSONString(accountTemp);
            //登录成功生成token
            long currentTimeMillis = System.currentTimeMillis();
            redisUtil.setWithExpireTime(SecurityConstant.TOKEN_IS_REFRESH + account.getId(), DateUtil.format(new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME * 60 * 1000), "yyyy-MM-dd HH:mm:ss"), SecurityConstant.TOKEN_EXPIRE_TIME * 60);
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(account.getLoginName())
                    .claim(SecurityConstant.ACCOUNT, accountStr)
                    .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            //将token放入redis
            redisUtil.set(SecurityConstant.TOKEN_KEY + myUserDetails.getId(), token);
            //设置用户登录状态
            log.info("authenticated user {}, setting security context", username);
            //将认证信息存入securitycontext，在jwt的过滤器中(MyAuthenticationFilter)一直为null(给security内置过滤器清除了)
            //保存认证信息
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticate);
            //写入登录日志
            switch (account.getUserType()) {
                case 1:
                    //学生
                    //查询该学生的信息
                    StudentVo student = studentControllerClient.queryStudentInfo(account.getSchoolCode(), account.getCardNumber()).getResult();
                    AddAccountLogDto addAccountLogDto = new AddAccountLogDto();
                    addAccountLogDto.setSchoolId(account.getSchoolId());
                    addAccountLogDto.setSchoolCode(account.getSchoolCode());
                    addAccountLogDto.setSchoolName(account.getSchoolName());
                    addAccountLogDto.setGroupId(student == null ? "testGroupId" : student.getClassId());
                    addAccountLogDto.setUserId(account.getUserId());
                    addAccountLogDto.setCardNumber(account.getCardNumber());
                    addAccountLogDto.setUserPhone(account.getUserPhone());
                    addAccountLogDto.setUserName(account.getUserName());
                    addAccountLogDto.setLoginName(account.getLoginName());
                    addAccountLogDto.setImei(imei);
                    addAccountLogDto.setClientId(clientId);
                    addAccountLogDto.setOperationSystem(operationSystem);
                    addAccountLogDto.setRemark("");
                    accountLogControllerClient.addAccountLog(addAccountLogDto);
                    UserDevice ud=userDeviceControllerClient.findUserDeviceByCodeOrCard(account.getSchoolCode(),account.getCardNumber()).getResult();
                    if (ud!=null){
                        ModifyUserDevice mud=new ModifyUserDevice();
                        mud.setId(ud.getId());
                        mud.setClientId(clientId);
                        userDeviceControllerClient.modifyUserDeviceInfo(mud);
                    }else{
                        AddUserDevice aud=new AddUserDevice();
                        aud.setSchoolId(account.getSchoolId());
                        aud.setSchoolCode(account.getSchoolCode());
                        aud.setSchoolName(account.getSchoolName());
                        aud.setGroupId(Long.valueOf(student.getClassId()));
                        aud.setUserId(account.getUserId());
                        aud.setCardNumber(account.getCardNumber());
                        aud.setUserName(account.getUserName());
                        aud.setImei(imei);
                        aud.setClientId(clientId);
                        userDeviceControllerClient.addUserDeviceInfo(aud);
                    }
                    break;
                case 2:
                    //老师
                    break;
                case 3:
                    //家长
                    break;
            }
            Wrapper wrapper = WrapMapper.ok(token);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                message = "用户名或者密码不正确";
            }
            if (e instanceof AccountExpiredException) {
                message = "账户已过期";
            }
            if (e instanceof LockedException) {
                message = "账户已被锁定";
            }
            Wrapper wrapper = WrapMapper.error(message);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(401);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        }
    }


//    @ApiOperation(value = "注销token(用户登出)", response = Boolean.class)
//    @RequestMapping(value = "/accountLogout", method = RequestMethod.GET)
//    public void accountLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            SecurityUtils.logout(request);
//            Wrapper wrapper = WrapMapper.ok("注销成功");
//            String str = JSON.toJSONString(wrapper);
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Content-type", "application/json; charset=UTF-8");
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/json;charset=utf-8");
//            response.getOutputStream().write(str.getBytes("utf-8"));
//        } catch (Exception e) {
//            Wrapper wrapper = WrapMapper.error(e.getMessage());
//            String str = JSON.toJSONString(wrapper);
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setStatus(401);
//            response.setHeader("Content-type", "application/json; charset=UTF-8");
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/json;charset=utf-8");
//            response.getOutputStream().write(str.getBytes("utf-8"));
//        }
//    }

    @GetMapping("/getAccountInfoByToken")
    @ApiOperation(value = "token获取用户信息", response = String.class)
    public Object getAccountInfoByToken() {
        Account account = SecurityUtils.getCurrentUser();
        account.setPassword("");
        return WrapMapper.ok(account);
    }

    @GetMapping("/getTokenTime")
    @ApiOperation(value = "获取token的有效时间", response = String.class)
    public Object getTokenTime(@RequestParam(name = "loseToken") String loseToken) {
        String authHeader = loseToken;
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String resultDate = DateUtil.format(claims.getExpiration(), "yyyy-MM-dd HH:mm:ss");
                return WrapMapper.ok(resultDate);
            } catch (ExpiredJwtException e) {
                return WrapMapper.ok(e.getClaims().getExpiration());
            }
        }
        return WrapMapper.error();
    }

    @PostMapping("/modifyPwd")
    @ApiOperation(value = "修改密码", response = Boolean.class)
    public Object modifyPwd(@RequestBody @Validated ModifyAccountPwdDto modifyAccountPwdDto) {
        return accountControllerClient.modifyPwd(modifyAccountPwdDto);
    }

    @PostMapping("/authenticationApp/forgetPwd")
    @ApiOperation(value = "找回密码", response = Boolean.class)
    public Object forgetPwd(@RequestBody @Validated ForgetPwd forgetPwd) {
        return accountControllerClient.forgetPwd(forgetPwd);
    }

    @GetMapping("/authenticationApp/getCaptcha")
    @ApiOperation(value = "获取验证码", response = Boolean.class)
    public Object getCaptcha(@RequestParam("phone") String phone) {
        return accountControllerClient.getCaptcha(phone);
    }
}
