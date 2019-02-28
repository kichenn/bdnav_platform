package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description: Excel导出类
 * @Author: Kang
 * @Date: 2019/2/28 10:42
 */
@Data
public class SchoolExcelDto extends SchoolQueryDto {

    @ApiModelProperty("导出方式（0：全部导出（带条件），1全部导出（无条件所有学校导出），2：分页导出，3：id选择导出）")
    private Byte isBy = 0;

    @ApiModelProperty("isBy为3id模式导出，ids必传，其他模式请忽略")
    private List<Long> ids;
}
