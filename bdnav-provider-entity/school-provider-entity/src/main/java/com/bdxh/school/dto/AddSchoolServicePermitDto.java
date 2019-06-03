package com.bdxh.school.dto;

import com.bdxh.school.enums.SchoolServicePermitStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/5/28 11:16
 */
@Data
public class AddSchoolServicePermitDto implements Serializable {



    @ApiModelProperty("学校主键")
    @NotNull(message = "学校主键不能为空")
    private Long schoolId;


    @ApiModelProperty("学校编码")
    @NotBlank(message = "学校编号不能为空")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    @NotBlank(message = "学校名称不能为空")
    private String schoolName;


    @ApiModelProperty("服务开始时间")
    private Date startTime;


    @ApiModelProperty("服务结束时间")
    private Date endTime;


    @ApiModelProperty("状态 1 服务有效  2 服务无效")
    @NotNull(message = "状态不能为空")
    private SchoolServicePermitStatusEnum status;


    @ApiModelProperty("延期天数")
    private Byte addDays;


    @ApiModelProperty("备注")
    private String remark;


}
