package com.bdxh.account.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询
 * @Author: WanMing
 * @Date: 2019/7/5 10:05
 */
@Data
public class QueryUserDeviceDto extends Query {


    /**
     * 学校编号
     */
    @ApiModelProperty("学校编号")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;


    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;





}
