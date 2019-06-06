package com.bdxh.appmarket.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管控版本更新返回给移动端得实体
 */

@Data
public class appVersionVo {


    @ApiModelProperty("应用版本号")
    private String appVersion;

    @ApiModelProperty("应用下载地址")
    private String systemApkUrl;
}
