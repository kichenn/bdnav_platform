package com.bdxh.user.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-24 17:46
 **/
@Data
public class BaseDto implements Serializable {

    //学校app_key
    @ApiModelProperty("微校数据同步所需参数(后台修改可以不传)")
    private String appKey;

    @ApiModelProperty("微校数据同步所需参数(后台修改可以不传)")
    //学校app_secret
    private String appSecret;

    //由腾讯微校回调校方接口时提供
    @ApiModelProperty("微校数据同步所需参数(后台修改可以不传)")
    private String state;


    @ApiModelProperty("学校类型参数(后台修改可以不传)")
    //学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校
    private Byte schoolType;
}