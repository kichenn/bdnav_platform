package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** 导出消费记录的dto
 * @author WanMing
 * @date 2019/7/15 14:05
 */
@Data
public class QueryWalletConsumerExcelDto extends QueryWalletConsumerDto {

    /**
     * 导出方式  1 多选导出(传orderNos) 2 条件导出
     */
    @ApiModelProperty("导出方式 1 多选导出(传orderNos) 2 条件导出")
    private Byte exportWay = 2;

    /**
     * 多选的ids
     */
    @ApiModelProperty("订单号的集合")
    private List<Long> orderNos;



}
