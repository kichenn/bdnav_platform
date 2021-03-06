package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.support.BaseService;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.persistence.BaseUserUnqiueMapper;
import com.bdxh.user.service.BaseUserUnqiueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 16:07
 **/
@Service
@Slf4j
public class BaseUserUnqiueServiceImpl extends BaseService<BaseUserUnqiue> implements BaseUserUnqiueService {

    @Autowired
    private BaseUserUnqiueMapper baseUserUnqiueMapper;

    @Override
    public List<String> queryAllUserPhone(BaseUserUnqiue baseUserUnqiue) {

        return baseUserUnqiueMapper.queryAllUserPhone(baseUserUnqiue);
    }

    @Override
    public Integer queryUserPhone(String phone, String schoolCode,String userId) {
        return baseUserUnqiueMapper.queryUserPhone(phone, schoolCode,userId);
    }

    /**
     * 修改手机号码
     *
     * @param schoolCode
     * @param oldPhone
     * @param newPhone
     * @Author: WanMing
     * @Date: 2019/6/19 15:08
     */
    @Override
    public void modifyPhone(String schoolCode, String oldPhone, String newPhone) {
        baseUserUnqiueMapper.modifyPhone(schoolCode, oldPhone, newPhone);
    }
}