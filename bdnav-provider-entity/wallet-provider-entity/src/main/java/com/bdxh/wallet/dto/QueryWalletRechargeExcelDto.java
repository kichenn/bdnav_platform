package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/** 查询账户充值导出excel条件的dto
 * @author WanMing
 * @date 2019/7/22 14:28
 */
@Data
public class QueryWalletRechargeExcelDto extends QueryWalletRechargeDto {

    /**
     * 导出方式 1 多选导出(传orderNos) 2 条件导出
     */
    @ApiModelProperty("导出方式 1 多选导出(传orderNos) 2 条件导出 ")
    private Byte exportWay = 2;

    /**
     * 多选的ids
     */
    @ApiModelProperty("订单号的集合")
    private List<Long> orderNos;
}
