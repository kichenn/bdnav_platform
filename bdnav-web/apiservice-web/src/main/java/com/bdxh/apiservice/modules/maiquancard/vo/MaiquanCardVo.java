package com.bdxh.apiservice.modules.maiquancard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 麦圈开卡响应类
 * @author: xuyuan
 * @create: 2019-03-27 14:32
 **/
@Data
@ApiModel("麦圈开卡响应类")
public class MaiquanCardVo implements Serializable {

    private static final long serialVersionUID = 4409927573684040184L;

    @ApiModelProperty("虚拟账户id")
    private Long virtualCardId;

    @ApiModelProperty("签名")
    private String sign;

}
