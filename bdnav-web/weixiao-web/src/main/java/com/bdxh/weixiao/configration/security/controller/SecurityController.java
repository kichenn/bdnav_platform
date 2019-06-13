package com.bdxh.weixiao.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.servicepermit.feign.ServiceRolePermitControllerClient;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.properties.weixiao.WeixiaoLoginConstant;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 腾讯微校认证信息
 * @Author: Kang
 * @Date: 2019/5/14 14:42
 */
@RestController
@RequestMapping
@Api(value = "微校认证相关", tags = "微校认证交互API")
@Slf4j
public class SecurityController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private ServiceRolePermitControllerClient serviceRolePermitControllerClient;


    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @RequestMapping(value = "/authenticationWeixiao/toAuth", method = RequestMethod.GET)
    @ApiOperation(value = "schoolCode进行返回微校授权信息", response = String.class)
    public Object toAuth(@RequestParam("schoolCode") String schoolCode, @RequestParam("address") String address) {
        School school = schoolControllerClient.findSchoolBySchoolCode(schoolCode).getResult();
        Preconditions.checkArgument(school != null, "schoolCode异常");

        String redirectUri = WeixiaoLoginConstant.REDIRECT_URI_URL.replace("@address@", address + "?schoolCode=" + schoolCode);

        String wxCodeUrl = WeixiaoLoginConstant.WXCODE_URL.replace("@schoolCode@", school.getSchoolCode())
                .replace("@appKey@", WeixiaoLoginConstant.appKey)
                .replace("@redirectUri@", redirectUri);

        log.info("授权url:" + wxCodeUrl);
        return WrapMapper.ok(wxCodeUrl);
    }


    /**
     * @param schoolCode 学校code
     * @param wxcode     微信授权码
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/authenticationWeixiao/login", method = RequestMethod.GET)
    @ApiOperation(value = "获取token(微校授权完登录)", response = String.class)
    public void login(@RequestParam("schoolCode") String schoolCode, @RequestParam("wxcode") String wxcode, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("app_key", WeixiaoLoginConstant.appKey);
            jsonObject.put("app_secret", WeixiaoLoginConstant.appSecret);
            jsonObject.put("wxcode", wxcode);
            //发起请求获取access_token
            String tokenResponseStr = HttpClientUtils.doPostJson(WeixiaoLoginConstant.TOKEN_URL, jsonObject.toJSONString());
            if (StringUtils.isNotEmpty(tokenResponseStr)) {
                tokenResponseStr = StringEscapeUtils.unescapeJava(tokenResponseStr);
            }
            jsonObject = JSON.parseObject(tokenResponseStr);
            String accessToken = jsonObject.getString("access_token");
            Preconditions.checkArgument(StringUtils.isNotEmpty(accessToken), "获取微信token失败");
            //通过token获取用户信息
            jsonObject.clear();
            jsonObject.put("token", accessToken);
            //通过access_token获取用户信息
            String userInfoResponseStr = HttpClientUtils.doPostJson(WeixiaoLoginConstant.USER_INFO_URL, jsonObject.toJSONString());
            if (StringUtils.isNotEmpty(userInfoResponseStr)) {
                userInfoResponseStr = StringEscapeUtils.unescapeJava(userInfoResponseStr);
            }
            jsonObject = JSON.parseObject(userInfoResponseStr);
            String resultCode = jsonObject.getString("errcode");
            Preconditions.checkArgument(StringUtils.equals(resultCode, "0"), "获取用户信息失败");

            log.info("userInfo:" + JSONObject.toJSONString(jsonObject));

            School school = schoolControllerClient.findSchoolBySchoolCode(schoolCode).getResult();
            Preconditions.checkArgument(school != null, "schoolCode异常，不存在");
            //组装用户信息放入
            UserInfo userInfo = new UserInfo();
            userInfo.setSchoolCode(schoolCode);
            userInfo.setName(jsonObject.getString("name"));
            userInfo.setWeixiaoStuId(jsonObject.getString("weixiao_stu_id"));
            userInfo.setPhone(jsonObject.getString("telephone"));
            userInfo.setIdentityType(jsonObject.getString("identity_type"));
            userInfo.setSchoolId(school.getId());
            //设置角色和权限信息
            Map<String, Object> claims = new HashMap<>(16);

            //家长登录设置权限相关信息
            if (userInfo.getIdentityType().equals("1")) {
                //家长登录(设置卡号)
                userInfo.setFamilyCardNumber(jsonObject.getString("card_number"));
                //家长卡号查询 自己孩子相关信息以及家长信息
                List<FamilyStudentVo> familyStudentVo = familyStudentControllerClient.queryStudentByFamilyCardNumber(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber()).getResult();
                Preconditions.checkArgument(CollectionUtils.isNotEmpty(familyStudentVo), "家长卡号，学校code异常");
                userInfo.setFamilyId(familyStudentVo.get(0).getFId());
                userInfo.setCardNumber(familyStudentVo.stream().map(e -> {
                    return e.getSCardNumber();
                }).collect(Collectors.toList()));

                //组装用户权限信息
                List<String> authorities = new ArrayList<>();
                //权限map（key：roleName value：list<String> 开通此权限的孩子列表）
                Map<String, List<String>> mapWeixiaoPermits = new HashMap<>();
                //查询服务权限列表信息
                Wrapper<List<ServiceRolePermitInfoVo>> rolePermitsWrapper = serviceRolePermitControllerClient.findServiceRolePermitInfoVo(userInfo.getFamilyCardNumber());
                List<ServiceRolePermitInfoVo> rolePermits = rolePermitsWrapper.getResult();
                if (CollectionUtils.isNotEmpty(rolePermits)) {
                    rolePermits.forEach(e -> {
                        if (mapWeixiaoPermits.containsKey(e.getRoleName())) {
                            //此角色存在
                            List<String> mapPermits = mapWeixiaoPermits.get(e.getRoleName());
                            mapPermits.add(e.getStudentCardNumber());
                            mapWeixiaoPermits.put(e.getRoleName(), mapPermits);
                        } else {
                            //此角色不存在
                            List<String> tempPermits = new ArrayList<>();
                            tempPermits.add(e.getStudentCardNumber());
                            mapWeixiaoPermits.put(e.getRoleName(), tempPermits);
                        }
                    });
                    //添加权限
                    for (String key : mapWeixiaoPermits.keySet()) {
                        authorities.add(key);
                    }
                }
                userInfo.setWeixiaoGrantedAuthorities(mapWeixiaoPermits);
                //设置角色和权限信息
                UserInfo userTemp = BeanMapUtils.map(userInfo, UserInfo.class);
                claims.put(SecurityConstant.USER_INFO, JSON.toJSONString(userTemp));
                claims.put(SecurityConstant.AUTHORITIES, JSON.toJSONString(authorities));
            } else {
                //孩子登录
                List<String> cardNumbers = new ArrayList<>();
                cardNumbers.add(jsonObject.getString("card_number"));
                userInfo.setCardNumber(cardNumbers);
                //学生卡号查询 学生相关信息以及家长信息
                FamilyStudentVo familyStudentVo = familyStudentControllerClient.studentQueryInfo(userInfo.getSchoolCode(), userInfo.getCardNumber().get(0)).getResult();
                Preconditions.checkArgument(familyStudentVo != null, "学生卡号，学校code异常");
                userInfo.setFamilyId(familyStudentVo.getFId());
                userInfo.setFamilyCardNumber(familyStudentVo.getFCardNumber());

                //学生登录只设置自己的信息
                UserInfo userTemp = BeanMapUtils.map(userInfo, UserInfo.class);
                claims.put(SecurityConstant.USER_INFO, JSON.toJSONString(userTemp));
            }

            String subject = userInfo.getFamilyId().toString();
            //生成token
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(subject)
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            //将token存入redis(单位秒。)
            redisUtil.setWithExpireTime(SecurityConstant.TOKEN_KEY + subject, token, SecurityConstant.TOKEN_EXPIRE_TIME * 60);
            Wrapper<String> wrapper = WrapMapper.ok(token);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(JSON.toJSONString(wrapper).getBytes("utf-8"));
        } catch (Exception e) {
            Wrapper<String> wrapper = WrapMapper.error(e.getMessage());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(500);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(JSON.toJSONString(wrapper).getBytes("utf-8"));
        }

    }

    @GetMapping("/getWeixiaoUserInfoByToken")
    @ApiOperation(value = "token获取用户信息", response = String.class)
    public Object getWeixiaoUserInfoByToken() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        return WrapMapper.ok(userInfo);
    }

    @GetMapping("/getTokenTime")
    @ApiOperation(value = "获取wtoken的有效时间", response = String.class)
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
}
