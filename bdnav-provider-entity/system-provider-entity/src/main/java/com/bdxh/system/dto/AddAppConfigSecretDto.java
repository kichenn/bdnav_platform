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
@Data
public class AddAppConfigSecretDto implements Serializable {

    private static final long serialVersionUID = -510700724515676512L;

    /**
     * 学校id
     */
    @NotNull(message = "学校id不能为空")
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @NotEmpty(message = "学校名称不能为空")
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 商户名称
     */
    @NotEmpty(message = "商户名称不能为空")
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
