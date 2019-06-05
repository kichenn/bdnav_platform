package com.bdxh.weixiao.configration.security.exception;

/**
* @Description: 传入schoolCode 与token 不一致时，满足前端要求: 抛出异常（schoolCode变化，说明此家长切换了学校，提醒前端更新token）
* @Author: Kang
* @Date: 2019/6/5 18:42
*/
public class SwitchSchoolCodeException extends RuntimeException {
}
