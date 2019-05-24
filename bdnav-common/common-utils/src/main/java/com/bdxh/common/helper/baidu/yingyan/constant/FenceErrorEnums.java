package com.bdxh.common.helper.baidu.yingyan.constant;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-24 11:10
 **/
public enum FenceErrorEnums {

    SUCCESS("0", "请求成功"),

    ERROR_SERVICE("1", "服务内部错误"),

    ERROR_PARAME("2", "参数错误"),

    ERROR_REQUEST("3", "HttpMethod错误"),

    EMPTY_ENTITY("3003", "指定track不存在"),

    EXISTENCE_ENTITY("3005", "entity_name已存在"),

    ERROR_FORMAT_ENTITY("3006", "查询时间段内的轨迹点过多，无法进行纠偏，请缩短查询时间"),

    ERROR_FORMAT("3007", "数据解析失败，数据中包含非utf8编码字符"),

    EMPTY_SERVICE_ID("4005", "指定service_id不存在"),

    ERROR_MAX_FENCE("5101", "监控对象的围栏个数超出范围，最多100个"),

    EMPTY_FENCE_ENTITY("5102", "监控对象不存在"),

    EMPTY_ENTITY_FENCE("5103", "监控对象上没有fence_id为XXX的围栏"),

    EMPTY_FENCEI_ID("5104", "指定fence_id不存在"),

    ERROR_MAX_POINT("9001", "查询时间段内的轨迹点过多，无法进行轨迹分析，请缩短查询时间");

    private String errorCode;
    private String errorMessage;

    FenceErrorEnums(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}