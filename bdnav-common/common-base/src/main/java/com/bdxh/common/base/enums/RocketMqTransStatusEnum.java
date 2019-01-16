package com.bdxh.common.base.enums;

/**
 * @description: rocketmq事务状态枚举类
 * @author: xuyuan
 * @create: 2019-01-15 11:19
 **/
public enum  RocketMqTransStatusEnum {

    COMMIT_MESSAGE("0","提交"),
    ROLLBACK_MESSAGE("1","回退"),
    UNKNOW("2","未决");

    private String code;

    private String mesage;

    RocketMqTransStatusEnum(String code,String message) {
        this.code=code;
        this.mesage=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

}
