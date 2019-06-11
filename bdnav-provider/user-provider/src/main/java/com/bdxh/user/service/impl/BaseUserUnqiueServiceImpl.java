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
    public Integer queryUserPhone(String phone, String schoolCode) {
        return baseUserUnqiueMapper.queryUserPhone(phone, schoolCode);
    }
}