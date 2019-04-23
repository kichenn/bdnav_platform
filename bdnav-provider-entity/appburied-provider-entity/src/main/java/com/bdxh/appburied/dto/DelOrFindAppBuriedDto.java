package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 删除上报应用App DTO
 * @Author: Kang
 * @Date: 2019/4/12 10:26
 */
@Data
public class DelOrFindAppBuriedDto extends AppBuriedParentDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校编号")
    private Long schoolId;
}