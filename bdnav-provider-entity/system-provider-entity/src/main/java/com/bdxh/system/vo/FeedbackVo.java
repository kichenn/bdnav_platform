package com.bdxh.system.vo;

import com.bdxh.system.dto.AddFeedbackAttachDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sun.xml.internal.ws.developer.Serialization;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * @author WanMing
 * @date 2019/6/13 16:58
 */
@Data
public class FeedbackVo {



    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;


    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;



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

    /**
     * 处理状态 1.已处理 2.未处理
     */
    @ApiModelProperty("处理状态 1.已处理 2.未处理")
    private Byte status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;


    /**
     * 可存放多个图片的url
     */
    @ApiModelProperty("可存放多个图片的url")
    private List<FeedbackAttachVo> image;
}
