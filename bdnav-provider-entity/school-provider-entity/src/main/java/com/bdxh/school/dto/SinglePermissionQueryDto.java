package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.enums.SingleUserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 门禁单查询分页条件
 * @Author: Kang
 * @Date: 2019/3/27 16:27
 */
@Data
public class SinglePermissionQueryDto extends Query {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private SingleUserTypeEnum singleUserTypeEnum;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("卡号")
    private String cardNumber;

    @ApiModelProperty("设备编码")
    private String deviceCode;

}