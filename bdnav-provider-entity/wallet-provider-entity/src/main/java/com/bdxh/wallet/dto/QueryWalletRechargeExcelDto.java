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
     * 导出方式 1 多选导出(传ids) 2 导出当前页(默认) 3 日期选择导出(传开始时间 结束时间)
     */
    @ApiModelProperty("导出方式 1 多选导出(传orderNos) 2 导出当前页 3 日期选择导出(传开始时间 结束时间) ")
    private Byte exportWay = 2;

    /**
     * 多选的ids
     */
    @ApiModelProperty("订单号的集合")
    private List<Long> orderNos;
}
