package com.bdxh.system.vo;

import com.bdxh.system.dto.AddFeedbackAttachDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
    private Long id;


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
