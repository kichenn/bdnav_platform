package com.bdxh.apiservice.modules.maiquancard.controller;

import com.bdxh.apiservice.modules.maiquancard.config.AppKeyConfig;
import com.bdxh.apiservice.modules.maiquancard.dto.MaiquanUserDto;
import com.bdxh.apiservice.modules.maiquancard.vo.MaiquanUserVo;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @description: 麦圈一卡通控制器
 * @author: xuyuan
 * @create: 2019-03-26 17:41
 **/
@RestController
@RequestMapping("/maiquanCard")
@Validated
@Slf4j
@Api(value = "麦圈一卡通接口文档", tags = "麦圈一卡通接口文档")
public class MaiquanCardController {


    @RequestMapping(value = "/maiquanUser",method = RequestMethod.GET)
    public Object maiquanUser(@Valid MaiquanUserDto maiquanUserDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            //判断接口时效性 默认30秒
            Date timeStamp = DateUtils.addSeconds(maiquanUserDto.getTimeStamp(), 30);
            Preconditions.checkArgument(timeStamp.getTime()>=System.currentTimeMillis(),"接口时效性超时");
            //验证身份
            String appKey = AppKeyConfig.getAppKey(maiquanUserDto.getAppId(), maiquanUserDto.getMchId());
            Preconditions.checkArgument(StringUtils.isNotEmpty(appKey),"身份信息验证失败");
            //校验签名
            SortedMap<String, String> sortedMap = BeanToMapUtil.objectToTreeMap(maiquanUserDto);
            sortedMap.remove("sign");
            String mapToString = BeanToMapUtil.mapToString(sortedMap);
            String sign = MD5.md5(mapToString + "&key=" + appKey);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(sign,maiquanUserDto.getSign()),"验证签名不通过");
            //查询用户信息
            MaiquanUserVo maiquanUserVo =new MaiquanUserVo();
            maiquanUserVo.setName("张三丰");
            sortedMap = BeanToMapUtil.objectToTreeMap(maiquanUserVo);
            mapToString = BeanToMapUtil.mapToString(sortedMap);
            sign = MD5.md5(mapToString + "&key=" + appKey);
            maiquanUserVo.setSign(sign);
            return WrapMapper.ok(maiquanUserVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/maiquanCreateCard",method = RequestMethod.POST)
    public Object maiquanCreateCard(@Valid MaiquanUserDto maiquanUserDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            //判断接口时效性 默认30秒
            Date timeStamp = DateUtils.addSeconds(maiquanUserDto.getTimeStamp(), 30);
            Preconditions.checkArgument(timeStamp.getTime()>=System.currentTimeMillis(),"接口时效性超时");
            //验证身份
            String appKey = AppKeyConfig.getAppKey(maiquanUserDto.getAppId(), maiquanUserDto.getMchId());
            Preconditions.checkArgument(StringUtils.isNotEmpty(appKey),"身份信息验证失败");
            //校验签名
            SortedMap<String, String> sortedMap = BeanToMapUtil.objectToTreeMap(maiquanUserDto);
            sortedMap.remove("sign");
            String mapToString = BeanToMapUtil.mapToString(sortedMap);
            String sign = MD5.md5(mapToString + "&key=" + appKey);
            Preconditions.checkArgument(StringUtils.equalsIgnoreCase(sign,maiquanUserDto.getSign()),"验证签名不通过");
            //查询用户信息
            MaiquanUserVo maiquanUserVo =new MaiquanUserVo();
            maiquanUserVo.setName("张三丰");
            sortedMap = BeanToMapUtil.objectToTreeMap(maiquanUserVo);
            mapToString = BeanToMapUtil.mapToString(sortedMap);
            sign = MD5.md5(mapToString + "&key=" + appKey);
            maiquanUserVo.setSign(sign);
            return WrapMapper.ok(maiquanUserVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }



}
