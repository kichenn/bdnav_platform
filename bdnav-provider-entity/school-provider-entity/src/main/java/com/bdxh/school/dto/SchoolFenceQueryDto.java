package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.BlackStatusEnum;
import com.bdxh.school.enums.GroupTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 学校围栏查询条件
 * @Author: Kang
 * @Date: 2019/4/11 14:29
 */
@Data
public class SchoolFenceQueryDto extends Query {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("用户群类型(1 学生 2 老师)")
    private GroupTypeEnum groupTypeEnum;

    @ApiModelProperty("部门id")
    private Long groupId;

    @ApiModelProperty("围栏状态1 启用 2 禁用")
    private BlackStatusEnum blackStatusEnum;

    @ApiModelProperty("围栏名称")
    private String name;
}
