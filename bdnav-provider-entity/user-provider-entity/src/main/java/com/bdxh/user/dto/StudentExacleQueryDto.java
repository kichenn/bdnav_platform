package com.bdxh.user.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-13 16:34
 **/
@Data
public class StudentExacleQueryDto extends StudentQueryDto implements Serializable {

    private static final long serialVersionUID = -7123645507093549631L;

    @ApiModelProperty("导出方式(1分页学生信息导出，2：选择条件导出，3：全部选择信息导出)")
    @NotNull(message = "导入方式不能为空")
    private Byte isBy = 0;

}