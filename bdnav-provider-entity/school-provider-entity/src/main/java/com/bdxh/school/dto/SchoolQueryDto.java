package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchoolQueryDto extends Query {

    @ApiModelProperty("学校编码")
    private String schooleCode;

    @ApiModelProperty("学校名称")
    private String schooleName;
}
