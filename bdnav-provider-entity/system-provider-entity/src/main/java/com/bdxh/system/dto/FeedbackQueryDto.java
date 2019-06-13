package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 查询用户反馈的dto
 * @author WanMing
 * @date 2019/6/13 16:45
 */
@Data
public class FeedbackQueryDto extends Query {




    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;


    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

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

    /**
     * 邮件
     */
    @ApiModelProperty("邮件")
    private String email;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 反馈内容
     */
    @ApiModelProperty("反馈内容")
    private String content;





}
