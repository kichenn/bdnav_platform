package com.bdxh.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description: 二维码解码入参
 * @Author: Kang
 * @Date: 2019/7/11 11:06
 */
@Data
public class QRCodeDto {

    @NotNull(message = "场景id不能为空")
    @ApiModelProperty(value = "场景id（1.门禁 2.消费 3.签到 4.其它）")
    private Integer scene;

    @NotEmpty(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String deviceNo;

    @NotEmpty(message = "地点不能为空")
    @ApiModelProperty(value = "地点")
    private String location;

    @NotEmpty(message = "校园码不能为空")
    @ApiModelProperty(value = "校园码")
    private String authCode;

    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty(value = "学校编码")
    private String schoolCode;

    @ApiModelProperty(value = "消费金额：场景为 2.消费 时传递")
    private BigDecimal money;
}
