package com.bdxh.wallet.dto;

import com.bdxh.wallet.enums.AccountStatusEnum;
import com.bdxh.wallet.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddWalletAccount {




    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

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
    private UserTypeEnum userTypeEnum;


    /**
     * 所属组织架构
     */
    @ApiModelProperty("所属组织架构")
    private Long orgId;

    /**
     * 实体卡
     */
    @ApiModelProperty("实体卡")
    private String physicalNumber;

    /**
     * 账户金额
     */
    @ApiModelProperty("账户金额")
    private BigDecimal amount;

    /**
     * 支付密码
     */
    @ApiModelProperty("支付密码")
    private String payPassword;

    /**
     * 免密金额 0为不免密 大于0校验金额
     */
    @ApiModelProperty("免密金额 0为不免密 大于0校验金额")
    private BigDecimal quickPayMoney;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 账户状态：1 正常  2异常
     */

    @ApiModelProperty("账户状态：1 正常  2异常")
    private AccountStatusEnum accountStatusEnum;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
