package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 百度ECharts基础数据对象
 * @author WanMing
 * @date 2019/5/24 17:54
 */
@Data
public class BaseEchartsVo implements Serializable {

    @ApiModelProperty("图表参数名称")
    private String name;

    @ApiModelProperty("数据值")
    private Long value;

    @ApiModelProperty("名称集合")
    private List<String> names;
}
