package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.BlackStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlackUrlQueryDto extends Query {

    @ApiModelProperty("*学校id")
    private Long schoolId;

    @ApiModelProperty("*学校编码")
    private String schoolCode;

    @ApiModelProperty("*状态")
    private BlackStatusEnum blackStatusEnum;

    @ApiModelProperty("*网站名称")
    private String name;
}
