package com.bdxh.wallet.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QueryWalletAccount extends Query {

    @ApiModelProperty("学校编号")
    private String schoolCode;

    /**
     * 学号、工号
     */
    @ApiModelProperty("学号、工号")
    private String cardNumber;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * 用户类型 1家长 2学生 3老师
     */
    @ApiModelProperty("用户类型 1家长 2学生 3老师")
    private Byte userType;

    /**
     * 所属组织架构
     */
    @ApiModelProperty("所属组织架构")
    private Long orgId;



    /**
     * 账户状态：1 正常  2异常
     */
    @ApiModelProperty("账户状态：1 正常  2异常")
    private Byte status;


}
