package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 增加应用秘钥dto
 * @author: xuyuan
 * @create: 2019-03-22 09:23
 **/
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
     * 学校id
     */
    @ApiModelProperty("学校id")
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
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String mchName;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
