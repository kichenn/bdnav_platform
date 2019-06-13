package com.bdxh.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WanMing
 * @date 2019/6/13 17:41
 */
@Data
public class FeedbackAttachVo {

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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
