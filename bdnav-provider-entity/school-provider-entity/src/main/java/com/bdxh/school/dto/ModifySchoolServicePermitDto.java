package com.bdxh.school.dto;

import com.bdxh.school.enums.SchoolServicePermitStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author WanMing
 * @date 2019/5/28 14:28
 */
@Data
public class ModifySchoolServicePermitDto{



    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;


    @ApiModelProperty("学校id")
    private Long schoolId;


    @ApiModelProperty("学校编码")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("服务开始时间")
    private Date startTime;


    @ApiModelProperty("服务结束时间")
    private Date endTime;


    @ApiModelProperty("状态 1 服务有效  2 服务无效")
    private SchoolServicePermitStatusEnum status;

    @ApiModelProperty("延期天数")
    private Byte addDays;


    @ApiModelProperty("备注")
    private String remark;



}
