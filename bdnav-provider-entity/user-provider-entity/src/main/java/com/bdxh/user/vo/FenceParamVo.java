package com.bdxh.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-19 16:43
 **/
@Data
public class FenceParamVo {

    @ApiModelProperty(value = "账号ID")
    private String accoountId;

}