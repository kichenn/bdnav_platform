package com.bdxh.appburied.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WanMing
 * @date 2019/7/2 18:37
 */
@Data
public class FamilyQueryApplyLogDto extends Query {

    @ApiModelProperty("家长的编号")
    private String operatorCode;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学生的卡号集合")
    private List<String> studentCardNumbers;
}
