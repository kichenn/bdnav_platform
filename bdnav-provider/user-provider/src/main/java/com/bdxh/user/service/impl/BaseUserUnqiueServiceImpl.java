package com.bdxh.user.service.impl;

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
    public List<String> queryAllUserPhone() {
        return  baseUserUnqiueMapper.queryAllUserPhone();
    }

    @Override
    public Integer queryUserPhone(String phone) {
        return baseUserUnqiueMapper.queryUserPhone(phone);
    }
}