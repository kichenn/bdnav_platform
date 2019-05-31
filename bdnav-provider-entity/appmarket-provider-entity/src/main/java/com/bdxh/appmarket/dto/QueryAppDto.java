package com.bdxh.appmarket.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("条件查询应用dto")
public class QueryAppDto extends Query {

    @ApiModelProperty("主键")
   private Long schoolId;

    @ApiModelProperty("名称")
   private String appName;

    @ApiModelProperty("适用平台 1 安卓 2 ios")
    private Byte platform;

    /**
     *  是否预置 1 是 2 否
     */
    @ApiModelProperty("是否预置 1 是 2 否")
    private Byte preset;



}
