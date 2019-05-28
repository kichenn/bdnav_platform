package com.bdxh.common.helper.baidu.yingyan.request;

import lombok.Data;
/**
 * @description:
 * @author: binzh
 * @create: 2019-05-23 17:03
 **/
@Data
public class FindTrackRequest {

    /**
     * 用户的AK，授权使用
     */
    private String ak;

    /**
     * service的ID，service 的唯一标识
     */
    private int service_id;

    /**
     * entity唯一标识
     */
    private String entity_name;

    /**
     * 起始时间（起始的loc_time） 时间戳
     */
    private String start_time;

    /**
     * 结束时间（结束的loc_time）时间戳
     */
    private String end_time;

    /**
     * 是否返回纠偏后轨迹
     */
    private int is_processed;

    /**
     * 纠偏选项
     */
    private String process_option;

    /**
     * 里程补偿方式
     */
    private String supplement_mode;

    /**
     * 低速阈值
     */
    private String low_speed_threshold;

    /**
     * 返回的坐标类型
     */
    private String coord_type_output;

    /**
     * 返回轨迹点的排序规则
     */
    private String sort_type;

    /**
     * 分页索引
     */
    private int page_index;

    /**
     * 分页大小
     */
    private int page_size;

    /**
     * 用户的权限签名，若用户所用AK的校验方式为SN校验时该参数必须。
     */
    private String sn;

}