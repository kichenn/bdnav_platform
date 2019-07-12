package com.bdxh.wallet.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class QueryPhysicalCard extends Query {


    @ApiModelProperty("学校编号")
    private String schoolCode;

    /**
     * 学号、工号
     */
    @ApiModelProperty("学号、工号")
    private String cardNumber;


    /**
     * 卡物理号
     */
    @ApiModelProperty("卡物理号")
    private String physicalNumber;


    /**
     * 物理卡状态 1 正常 2 挂失 3注销
     */
    @ApiModelProperty("物理卡状态 1 正常 2 挂失 3注销")
    private Byte status;



}
