package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.user.enums.FamilyBlackUrlStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/** 查询家长黑名单的dto
 * @author WanMing
 * @date 2019/6/25 11:44
 */
@Data
public class FamilyBlackUrlQueryDto extends Query {


    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 家长号
     */
    @ApiModelProperty("家长号")
    private String cardNumber;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    private String studentNumber;


    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    private String siteName;


    /**
     * 状态 1 启用 2 禁用
     */
    @ApiModelProperty("状态 1 启用 2 禁用")
    private FamilyBlackUrlStatusEnum status;



}
