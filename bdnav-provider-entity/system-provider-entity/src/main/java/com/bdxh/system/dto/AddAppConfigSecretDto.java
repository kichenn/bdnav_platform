package com.bdxh.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 增加应用秘钥dto
 * @author: xuyuan
 * @create: 2019-03-22 09:23
 **/
@ApiModel("增加应用秘钥dto")
@Data
public class AddAppConfigSecretDto implements Serializable {

    private static final long serialVersionUID = -510700724515676512L;

    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    @ApiModelProperty("应用id")
    private Long AppId;

    /**
     * 商户名称
     */
    @NotEmpty(message = "商户名称不能为空")
    @ApiModelProperty("商户名称")
    private String MchName;

    /**
     * 回调地址
     */
    @ApiModelProperty("回调地址")
    private String NoticeUrl;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long Operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String Remark;

}
