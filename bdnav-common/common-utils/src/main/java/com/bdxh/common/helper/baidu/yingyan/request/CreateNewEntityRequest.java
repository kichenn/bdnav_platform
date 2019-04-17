package com.bdxh.common.helper.baidu.yingyan.request;

import lombok.Data;

/**
 * @Description: 创建监控对象 请参
 * @Author: Kang
 * @Date: 2019/4/16 11:55
 */
@Data
public class CreateNewEntityRequest {

    /**
     * 用户的ak
     */
    private String ak;

    /**
     * service的唯一标识
     */
    private int service_id;


    /**
     * entity名称，座位其唯一标识
     */
    private String entity_name;

    /**
     * entity描述
     */
    private String entity_desc;




}
