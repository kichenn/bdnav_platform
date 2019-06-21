package com.bdxh.weixiao.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.helper.weixiao.authentication.MessageUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.servicepermit.feign.ServiceRolePermitControllerClient;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.properties.weixiao.WeixiaoLoginConstant;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import com.xiaoleilu.hutool.http.HttpRequest;
import feign.Param;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 腾讯微校认证信息
 * @Author: Kang
 * @Date: 2019/5/14 14:42
 */
@RestController
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
    private FamilyControllerClient familyControllerClient;

    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @Autowired
    private AccountControllerClient accountControllerClient;

    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;

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
            //此处使用服务商appkey appsecret ，后续如有业务需求可使用学校自己的 appkey  appsecret
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
            userInfo.setSchoolName(school.getSchoolName());
            //设置角色和权限信息
            Map<String, Object> claims = new HashMap<>(16);

            //家长登录设置权限相关信息
            if (userInfo.getIdentityType().equals("1")) {
                //家长登录(设置卡号)
                userInfo.setFamilyCardNumber(jsonObject.getString("card_number"));
                //家长卡号查询 家长信息
                FamilyVo familyVo = familyControllerClient.queryFamilyInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber()).getResult();
                Preconditions.checkArgument(familyVo != null, "家长卡号:" + userInfo.getFamilyCardNumber() + "，学校code:" + userInfo.getSchoolCode() + ",异常");
                userInfo.setFamilyId(Long.valueOf(familyVo.getId()));
                userInfo.setFamilyName(familyVo.getName());
                //查询家长和孩子的关系
                List<FamilyStudentVo> familyStudentVo = familyStudentControllerClient.queryStudentByFamilyCardNumber(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber()).getResult();
                if (familyStudentVo != null) {
                    //如果有孩子则绑定
                    userInfo.setCardNumber(familyStudentVo.stream().map(e -> {
                        return e.getSCardNumber();
                    }).collect(Collectors.toList()));
                }

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
                if (familyStudentVo != null) {
                    //绑定信息不为空，说明该孩子存在家长，赋值家长信息
                    userInfo.setFamilyId(familyStudentVo.getFId());
                    userInfo.setFamilyCardNumber(familyStudentVo.getFCardNumber());
                }

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

    @ApiOperation(value = "微校-手机获取短信验证码")
    @RequestMapping(value = "/authenticationWeixiao/getPhoneCode", method = RequestMethod.GET)
    public Object getPhoneCode(@RequestParam(name = "phone") String phone) {
        return familyStudentControllerClient.getPhoneCode(phone);
    }


    @ApiOperation(value = "百度围栏回调地址接口")
    @RequestMapping(value = "/authenticationWeixiao/baiduFenceRequest", method = RequestMethod.POST)
    public Object baiduFenceRequest(String data, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        if (request.getHeader("SignId").equals("baidu_yingyan")) {
            log.info("允许请求，http请求头中带的参数是：   {}", request.getHeader("SingId"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("围栏线程");
                    //逻辑处理
                    log.info("-----------百度围栏：{}    已启动", Thread.currentThread().getName());
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    log.info("--------------------百度围栏请求的参数：{}    ", jsonObject.toJSONString());
                    Byte type = jsonObject.getByte("type");
                    if (type.equals("1")) {
                        log.info("---------------------百度围栏警报第一次效验不进行任何操作,type：{}", type);
                    } else {
                        log.info("---------------------开始处理百度围栏逻辑");
                        log.info("---------------------开始发送微校消息通知");
                        JSONObject requestDate = jsonObject.getJSONObject("content");
                        String entity = requestDate.getString("monitored_person");
                        entity = entity.replaceAll("account:", "");
                        log.info("-----------------我获取到的实体对象ID：      {}", entity);
                        //查询账号索引表查询出卡号和学校code
                        AccountUnqiue accountUnqiue = accountControllerClient.findAccountInfo(Long.valueOf(entity)).getResult();
                        //设置微校查询通知参数
                        School school = schoolControllerClient.findSchoolBySchoolCode(accountUnqiue.getSchoolCode()).getResult();
                        //学生获取家长关系信息
                        FamilyStudentVo familyStudentVo = familyStudentControllerClient.studentQueryInfo(accountUnqiue.getSchoolCode(), accountUnqiue.getCardNumber()).getResult();
                        if (null != familyStudentVo) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("app_key", school.getSchoolKey());
                            map.put("nonce", RandomStringUtils.randomAlphanumeric(32).toLowerCase().toUpperCase());
                            map.put("school_code", school.getSchoolCode());
                            map.put("timestamp", System.currentTimeMillis() / 1000L);
                            try {
                                log.info("--------------------判断当前学校公众号有没有微信通知能力");
                                String result = MessageUtils.ability(map, school.getSchoolSecret());
                                JSONObject message = JSONObject.parseObject(result);
                                //如果该学校公众号有通知能力就进行微校通知
                                if (message.get("code").equals(0)) {
                                    log.info("--------------------设置消息模板通知");
                                    //设置消息模板通知
                                    HashMap<String, Object> messageMap = new HashMap<>();
                                    messageMap.put("school_code", school.getSchoolCode());
                                    JSONArray jsonArray = new JSONArray();
                                    jsonArray.add(familyStudentVo.getFCardNumber());
                                    messageMap.put("cards", jsonArray.toString());
                                    messageMap.put("title", "围栏警报");
                                    messageMap.put("content", "你的孩子现在的位置有异常");
                                    messageMap.put("sender", familyStudentVo.getSName());
                                    messageMap.put("app_key", school.getSchoolKey());
                                    messageMap.put("timestamp", System.currentTimeMillis() / 1000L + "");
                                    messageMap.put("nonce", RandomStringUtils.randomAlphanumeric(32).toLowerCase().toUpperCase());
                                    //添加自定义参数，分别为提示文案和通知跳转链接，如不传则公众号模版消息会默认显示'你有一条通知待查看'，并跳转到微校通知详情页
                                    JSONArray messageJson = new JSONArray();
                                    messageJson.add("您的孩子给你发送了一条消息");
                                    messageJson.add("http://wx-front-prod.bdxht.com/bdnav-school-micro/dist/appControl/#/message?schoolCode=" + accountUnqiue.getSchoolCode() + "&cardNumber=" + accountUnqiue.getCardNumber());
                                    messageMap.put("customs", messageJson);
                                    String messageResult = MessageUtils.notice(messageMap, school.getSchoolSecret());
                                    JSONObject jsonObject1 = JSONObject.parseObject(messageResult);
                                    if (!jsonObject1.get("code").equals(0)) {
                                        log.info("--------------------设置消息模板通知失败 错误原因---------------------");
                                    }
                                    log.info("--------------------设置消息模板通知已经完成了---------------------");
                                    //对数据库进行记录
                                    AddFenceAlarmDto addFenceAlarmDto = new AddFenceAlarmDto();
                                    addFenceAlarmDto.setSchoolId(school.getId());
                                    addFenceAlarmDto.setAction(requestDate.getString("action"));
                                    addFenceAlarmDto.setCardNumber(accountUnqiue.getCardNumber());
                                    addFenceAlarmDto.setFenceId(requestDate.getInteger("fence_id"));
                                    addFenceAlarmDto.setPrePoint(requestDate.getString("pre_point"));
                                    addFenceAlarmDto.setAlarmPoint(requestDate.getString("alarm_point"));
                                    addFenceAlarmDto.setSchoolCode(school.getSchoolCode());
                                    addFenceAlarmDto.setFenceName(requestDate.getString("fence_name"));
                                    addFenceAlarmDto.setMonitoredPerson(requestDate.getString("monitored_person"));
                                    addFenceAlarmDto.setType(Byte.valueOf("2"));
                                    addFenceAlarmDto.setSchoolName(school.getSchoolName());
                                    fenceAlarmControllerClient.insertFenceAlarmInfo(addFenceAlarmDto);
                                    log.info("-----------添加数据库已经结束");
                                }
                            } catch (Exception e) {
                                log.info("-----------------报错错误信息： {}", e.getMessage());
                            }
                        }
                    }
                    log.info("-----------百度围栏：{}    已结束", Thread.currentThread().getName());
                }
            }).start();
            response.setHeader("SignId", "baidu_yingyan");
            result.put("status", "0");
            result.put("message", "成功");
            log.info("--------------执行完成");
        } else {
            log.info("不允许请求，http请求头中带的参数是：   {}", request.getHeader("SingId"));
            result.put("status", "500");
            result.put("message", "失败");
        }
        return result;
    }
}
