package com.bdxh.weixiao.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.AccountUnqiue;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.helper.weixiao.authentication.MessageUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@Validated
@Slf4j
@Api(value = "电子围栏-----微校学生出入围栏日志API", tags = "电子围栏-----微校学生出入围栏日志API")
public class FenceAlarmWebController {

    @Autowired
    private AccountControllerClient accountControllerClient;

    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    /**
     * 收费服务
     * 查询所有
     *
     * @param cardNumber 学生学号
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation("家长电子围栏-----查询所有围栏警报接口")
    @RequestMapping(value = "/fenceAlarmWeb/getAllFenceAlarmInfos", method = RequestMethod.POST)
    public Object getAllFenceAlarmInfos(@RequestParam("cardNumber") String cardNumber,
                                        @RequestParam("fenceId") String fenceId) {
        try {
            //获取试用列表
            Map<String, Boolean> mapNoTrial = SecurityUtils.getCurrentAuthOnTrial();
            if (!mapNoTrial.get(cardNumber)) {
                //没有在试用，查看是否开通正式权限
                Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
                //获取孩子列表信息
                List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
                Boolean isBy = thisCardNumbers.contains(cardNumber);
                if (!isBy) {
                    throw new PermitException();
                }
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return fenceAlarmControllerClient.getFenceAlarmInfos(userInfo.getSchoolCode(), cardNumber, fenceId);
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
            }
            return WrapMapper.error(messge);
        }

    }

    /**
     * 收费服务
     * 查询单个
     *
     * @param cardNumber
     * @param id
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation("家长电子围栏-----查询单个围栏警报接口")
    @RequestMapping(value = "/fenceAlarmWeb/getFenceAlarmInfo", method = RequestMethod.POST)
    public Object getFenceAlarmInfo(@RequestParam(name = "cardNumber") @NotNull(message = "学生cardNumber不能为空") String cardNumber,
                                    @RequestParam(name = "id") @NotNull(message = "id不能为空") String id) {
        try {
            //获取试用列表
            Map<String, Boolean> mapNoTrial = SecurityUtils.getCurrentAuthOnTrial();
            if (!mapNoTrial.get(cardNumber)) {
                //没有在试用，查看是否开通正式权限
                Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
                //获取孩子列表信息
                List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
                Boolean isBy = thisCardNumbers.contains(cardNumber);
                if (!isBy) {
                    throw new PermitException();
                }
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return WrapMapper.ok(fenceAlarmControllerClient.getFenceAlarmInfo(userInfo.getSchoolCode(), cardNumber, id).getResult());

        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
            }
            return WrapMapper.error(messge);
        }
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