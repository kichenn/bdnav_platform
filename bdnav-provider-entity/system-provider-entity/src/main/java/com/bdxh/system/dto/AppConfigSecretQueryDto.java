package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 应用秘钥查询dto
 * @author: xuyuan
 * @create: 2019-03-22 09:43
 **/
@ApiModel("应用秘钥查询dto")
@Data
public class AppConfigSecretQueryDto extends Query {

    private static final long serialVersionUID = -2954920394500218403L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long Id;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long AppId;

    /**
     * 商户号
     */
    @ApiModelProperty("商户号")
    private Long MchId;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String MchName;

    /**
     * 秘钥
     */
    @ApiModelProperty("秘钥")
    private String AppSecret;

    /**
     * 回调地址
     */
    @ApiModelProperty("回调地址")
    private String NoticeUrl;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date CreateDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date UpdateDate;

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
