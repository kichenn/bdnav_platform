package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;


/**
 *  用户反馈附件添加dto
 * @author WanMing
 * @date 2019/6/13 12:31
 */
@Data
public class AddFeedbackAttachDto implements Serializable {


    /**
     * 上传的图片url
     */
    @ApiModelProperty("上传的图片url")
    private String img;

    /**
     * 上传的图片的名称
     */
    @ApiModelProperty("上传的图片的名称")
    private String imgName;

    /**
     * 备注 排序
     */
    @ApiModelProperty("备注")
    private String remark;
}
