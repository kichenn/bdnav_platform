package com.bdxh.user.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.service.BaseUserService;
import com.bdxh.user.vo.FamilyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 11:21
 **/
@Api(value ="学校用户信息管理接口API", tags = "学校用户信息管理接口API")
@RestController
@RequestMapping("/baseUser")
@Validated
@Slf4j
public class BaseUserController {
    @Autowired
    private BaseUserService baseUserService;

    /**
     * 查询所有用户手机号
     * @return
     */
    @ApiOperation(value="查询所有用户手机号")
    @RequestMapping(value ="/queryAllUserPhone",method = RequestMethod.GET)
    public Object queryAllUserPhone() {
        try {
            List<String> list=baseUserService.queryAllUserPhone();
            return WrapMapper.ok(list) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据手机号查询所有用户手机号是否存在
     * @return
     */
    @ApiOperation(value="根据手机号查询所有用户手机号是否存在")
    @RequestMapping(value ="/queryUserPhoneByPhone",method = RequestMethod.POST)
    public Object queryUserPhoneByPhone(@RequestBody  BaseUserQueryDto baseUserQueryDto) {
        try {
           Integer phoneCount=baseUserService.queryUserPhoneByPhone(baseUserQueryDto);
            return WrapMapper.ok(phoneCount) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据手机号查询学校基础用户信息
     * @return
     */
    @ApiOperation(value="根据手机号查询学校基础用户信息")
    @RequestMapping(value ="/queryBaseUserInfoByPhone",method = RequestMethod.GET)
    public Object queryBaseUserInfoByPhone(@RequestParam("phone")String phone) {
        try {
            BaseUser baseUser=baseUserService.queryBaseUserInfoByPhone(phone);
            return WrapMapper.ok(baseUser) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 查询学校所有除了家长的用户
     */
    @RequestMapping(value ="/findAllBaseUserInfo",method = RequestMethod.POST)
    @ResponseBody
   public Object  findAllBaseUserInfo(){
        try {
            return WrapMapper.ok(baseUserService.findAllBaseUserInfo()) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}