package com.bdxh.common.helper.baidu.yingyan.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 创建百度圆形围栏 请参
 * @Author: Kang
 * @Date: 2019/4/16 11:04
 */
@Data
public class CreateFenceRoundRequest {

    /**
     * 用户的ak
     */
    private String ak;

    /**
     * service的唯一标识
     */
    private int service_id;

    /**
     * 围栏名称
     */
    private String fence_name;

    /**
     * 围栏去噪参数
     */
    private int denoise = 0;

    /**
     * 围栏圆心经度
     */
    private double longitude;

    /**
     * 围栏圆心纬度
     */
    private double latitude;

    /**
     * 围栏半径
     */
    private double radius;

    /**
     * wgs84：GPS经纬度
     * gcj02：国测局经纬度
     * bd09ll：百度经纬度
     */
    private String coord_type;

    /**
     * 监控对象，非必传
     * 监控对象的entity_name，使用说明：
     * 1、监控一个entity
     * <p>
     * 规则：monitored_person=entity_name
     * <p>
     * 示例：monitored_person=张三
     * <p>
     * 2、监控多个entity
     * <p>
     * 首先按照监控一个entity的方法创建围栏，再调用geofence/addmonitoredperson接口添加其他entity
     * <p>
     * 3、监控service下全部entity
     * 规则：monitored_person=#allentity
     * <p>
     * "#allentity"为监控全部entity的特殊字符
     */
    private String monitored_person;
}
