package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchoolQueryDto extends Query {




    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("学校类型 1 小学 2 初中 3 高中 4 中专 5 大专 6 高校")
    private Byte schoolType;

    @ApiModelProperty("学校性质 1 公立 2 私立")
    private String schoolNature;

    @ApiModelProperty("区域,省,市,区")
    private String text;
}
