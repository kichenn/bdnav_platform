package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.entity.BaseUserUnqiue;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 16:07
 **/
public interface BaseUserUnqiueService extends IService<BaseUserUnqiue> {
    //查询所有用户手机号
    List<String> queryAllUserPhone(BaseUserUnqiue baseUserUnqiue);

    /**
     * 判断手机号是否存在
     *
     * @param phone
     * @return
     */
    Integer queryUserPhone(String phone, String schoolCode);

    /**
     * 修改手机号码
     * @Author: WanMing
     * @Date: 2019/6/19 15:08
     */
    void modifyPhone(String schoolCode, String oldPhone, String newPhone);
}