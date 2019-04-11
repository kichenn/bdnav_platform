package com.bdxh.school.dto;

import com.bdxh.school.enums.BlackStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 修改URL黑名单DTO
 * @Author: Kang
 * @Date: 2019/4/11 10:48
 */
@Data
public class ModifyBlackUrlDto {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("网站名称")
    private String name;

    @ApiModelProperty("填写域名或者ip")
    private String ip;

    @ApiModelProperty("状态")
    private BlackStatusEnum blackStatusEnum;

//	@ApiModelProperty("创建时间")
//	private Date createDate;

    @ApiModelProperty("修改时间")
    private Date updateDate;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;


}