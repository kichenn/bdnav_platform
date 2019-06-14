package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户反馈添加的Dto
 * @author WanMing
 * @date 2019/6/13 12:17
 */
@Data
public class AddFeedbackDto {


    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
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
    @NotBlank(message = "反馈的内容不能为空")
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
     * 操作人员主键
     */
    @ApiModelProperty("操作人员主键")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 可存放多个图片的url
     */
    @ApiModelProperty("可存放多个图片的url")
    private List<AddFeedbackAttachDto> image;

}
