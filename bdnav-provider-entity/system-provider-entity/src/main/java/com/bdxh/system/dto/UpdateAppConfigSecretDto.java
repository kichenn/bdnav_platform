package com.bdxh.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 增加应用秘钥dto
 * @author: xuyuan
 * @create: 2019-03-22 09:23
 **/
@ApiModel("增加应用秘钥dto")
@Data
public class UpdateAppConfigSecretDto implements Serializable {

    private static final long serialVersionUID = 3920003521186466836L;

    /**
     * 应用秘钥id
     */
    @NotNull(message = "应用秘钥id不能为空")
    @ApiModelProperty("应用秘钥id")
    private Long id;

    /**
     * 商户名称
     */
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