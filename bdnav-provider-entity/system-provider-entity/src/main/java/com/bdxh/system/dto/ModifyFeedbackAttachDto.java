package com.bdxh.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author WanMing
 * @date 2019/6/13 15:30
 */
@Data
public class ModifyFeedbackAttachDto {



    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;


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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
