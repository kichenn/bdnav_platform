package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.SchoolServicePermitStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;



/**
 * @author WanMing
 * @date 2019/5/28 15:43
 */
@Data
public class SchoolServicePermitQueryDto extends Query {



    @ApiModelProperty("学校编码")
    private String schoolCode;


    @ApiModelProperty("学校名称")
    private String schoolName;


    @ApiModelProperty("状态 1 服务有效  2 服务无效")
    private SchoolServicePermitStatusEnum status;


}
