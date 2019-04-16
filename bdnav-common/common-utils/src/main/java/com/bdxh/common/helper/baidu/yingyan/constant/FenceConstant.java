package com.bdxh.common.helper.baidu.yingyan.constant;

/**
 * @Description: 百度围栏入参常量
 * @Author: Kang
 * @Date: 2019/4/16 10:45
 */
public class FenceConstant {

    //学校端围栏Ak
    public static final String AK = "hS2ggfP9IIGxKFDeff87I7rqEdar07bf";

    //学校端围栏service的唯一标识
    public static final int SERVICE_ID = 211624;

    /**
     * 单位：米。每个轨迹点都有一个定位误差半径radius，
     * 这个值越大，代表定位越不准确，
     * 可能是噪点。围栏计算时，
     * 如果噪点也参与计算，
     * 会造成误报的情况。
     * 设置denoise可控制，
     * 当轨迹点的定位误差半径大于设置值时，
     * 就会把该轨迹点当做噪点，不参与围栏计算。
     * denoise默认值为0，不去噪
     */
    public static int denoise = 0;

    /**
     * 创建圆形围栏url，post请求
     */
    public static final String CREATE_ROUND_URL = "http://yingyan.baidu.com/api/v3/fence/createcirclefence";

    /**
     * 更新圆形围栏url，post请求
     */
    public static final String MODIFY_ROUND_URL = "http://yingyan.baidu.com/api/v3/fence/updatecirclefence";

    /**
     * 删除圆形围栏，并且会将该围栏里的监控对象删除，post请求
     */
    public static final String DELETE_ROUND_URL = "http://yingyan.baidu.com/api/v3/fence/delete";

    /**
     * 创建监控对象url，post请求
     */
    public static final String CREATE_NEW_ENTITY = "http://yingyan.baidu.com/api/v3/entity/add";

}
