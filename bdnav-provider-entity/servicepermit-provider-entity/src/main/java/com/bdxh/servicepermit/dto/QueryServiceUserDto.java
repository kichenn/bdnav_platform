package com.bdxh.servicepermit.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryServiceUserDto extends Query {

    @ApiModelProperty("家长号")
    private String cardNumber;

    @ApiModelProperty("学生卡号")
    private String studentNumber;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("家长姓名")
    private String familyName;

    @ApiModelProperty("状态 1 正常使用 2已过期")
    private Integer status;

    @ApiModelProperty("类型 1是试用  2是正式使用")
    private Integer type;

    @ApiModelProperty("可用天数")
    private Integer days;

    @ApiModelProperty("商品Id")
    private Integer productId;
}
