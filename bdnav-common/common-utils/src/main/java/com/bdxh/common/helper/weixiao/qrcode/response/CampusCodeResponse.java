package com.bdxh.common.helper.weixiao.qrcode.response;

import lombok.Data;

/**
 * @Description: 解析校园码后返参
 * @Author: Kang
 * @Date: 2019/7/11 15:00
 */
@Data
public class CampusCodeResponse {

    /**
     * 状态码
     */
    private String code;

    /**
     * 返参用户user信息
     */
    private CampusResponseUser user;

    /**
     * offline 当前二维码状态
     */
    private String offline;

    /**
     * message 描述
     * 错误码
     * 名称	解决方案
     * 1000	必填参数不存在
     * 1001	请求已过期，请检查时间戳
     * 1002	签名验证失败，请使用签名验证工具校验
     * 1005	app_key错误
     * 10001	学校代码不正确
     * 10002	当前学校未接入校园码
     * 10003	解码失败，码格式不正确
     * 10004	解码成功，二维码已过期
     * 10005	权限不足
     * 10006	该码已被截图
     * 10007	该码已被使用
     */
    private String message;
}
