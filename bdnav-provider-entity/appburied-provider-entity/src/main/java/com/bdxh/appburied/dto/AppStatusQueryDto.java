package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.AppStatusEnum;
import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 上报应用查询
 * @Author: Kang
 * @Date: 2019/4/12 10:42
 */
@Data
public class AppStatusQueryDto extends Query {

    @ApiModelProperty("平台 1 android 2 ios")
    private InstallAppsPlatformEnum installAppsPlatformEnum;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("应用状态1.正常 2.锁定")
    private AppStatusEnum appStatusEnum;
}