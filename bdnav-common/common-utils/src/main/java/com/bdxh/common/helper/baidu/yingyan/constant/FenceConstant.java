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
    public static final int SERVICE_ID = 212229;

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
     * 增加围栏需监控对象 URL post请求
     */
    public static final String ROUND_ADD_ENTITY_URL = "http://yingyan.baidu.com/api/v3/fence/addmonitoredperson";

    /**
     * 删除围栏可去除监控的entity URL POST请求
     */
    public static final String ROUND_DELETE_ENTITY_URL = "http://yingyan.baidu.com/api/v3/fence/deletemonitoredperson";

    /**
     * 删除圆形围栏，并且会将该围栏里的监控对象删除，post请求
     */
    public static final String DELETE_ROUND_URL = "http://yingyan.baidu.com/api/v3/fence/delete";

    /**
     * 创建终端实体url，post请求
     */
    public static final String CREATE_NEW_ENTITY = "http://yingyan.baidu.com/api/v3/entity/add";

    /**
     * 删除终端实体url,post请求
     */
    public static final String DELETE_NEW_ENTITY = "http://yingyan.baidu.com/api/v3/entity/delete";

    /**
     * 查询围栏底下所有entity URL get请求
     */
    public static final String SEL_ROUND_IN_ENTITY_URL="http://yingyan.baidu.com/api/v3/entity/list";

    /**
     * 查询所有围栏信息 GET请求
     */
    public static final String SEL_ROUND_IN_FENCE_URL="http://yingyan.baidu.com/api/v3/fence/list";

    /**
     * 查询单个实体的鹰眼轨迹 GET请求
     */
    public static final String SEL_YINYAN_TRACK_URL ="http://yingyan.baidu.com/api/v3/track/gettrack";

    /**
     * 查询某service下的某一个围栏下的所有entity，方便开发者管理查询entity GET请求
     */
    public static final String SEL_ENTITY_IN_FENCE_URL="http://yingyan.baidu.com/api/v3/fence/listmonitoredperson";

    /**
     * 查找entity最近一个轨迹点，支持实时纠偏。 GET请求
     */
    public static final String SEL_YINYAN_LATESTPOINT_URL="http://yingyan.baidu.com/api/v3/track/getlatestpoint";
}
