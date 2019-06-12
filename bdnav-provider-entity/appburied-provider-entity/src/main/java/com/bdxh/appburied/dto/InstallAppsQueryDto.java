package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Description: 上报应用查询
 * @Author: Kang
 * @Date: 2019/4/12 10:42
 */
@Data
public class InstallAppsQueryDto extends Query {

    @ApiModelProperty("平台 1 android 2 ios")
    private InstallAppsPlatformEnum installAppsPlatformEnum;

    @ApiModelProperty("学校ID")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("创建时间")
    private Date createDate;
}