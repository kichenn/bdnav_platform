package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description: 应用秘钥查询dto
 * @author: xuyuan
 * @create: 2019-03-22 09:43
 **/
@Data
public class AppConfigSecretQueryDto extends Query {

    private static final long serialVersionUID = -2954920394500218403L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long Id;

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
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 商户号
     */
    @ApiModelProperty("商户号")
    private Long mchId;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String mchName;

    /**
     * 秘钥
     */
    @ApiModelProperty("秘钥")
    private String appSecret;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

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
