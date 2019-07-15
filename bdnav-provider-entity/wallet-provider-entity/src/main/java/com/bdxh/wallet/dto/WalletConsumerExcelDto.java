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
public class WalletConsumerExcelDto extends QueryWalletConsumerDto {

    /**
     * 导出方式 1 多选导出(传ids) 2 导出当前页 3 日期选择导出(传开始时间 结束时间)
     */
    @ApiModelProperty("导出方式 1 多选导出(传ids) 2 导出当前页 3 日期选择导出(传开始时间 结束时间) ")
    private Byte exportWay;

    /**
     * 多选的ids
     */
    @ApiModelProperty("多选的ids")
    private List<Long> ids;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

}
