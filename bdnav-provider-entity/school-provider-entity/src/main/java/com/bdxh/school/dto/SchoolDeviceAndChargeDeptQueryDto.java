package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**设备和收费部门的查询dto
 * @author WanMing
 * @date 2019/7/15 16:28
 */
@Data
public class SchoolDeviceAndChargeDeptQueryDto extends Query {
    /**
     * 收费部门的id
     */
    @ApiModelProperty("收费部门的id")
    private Long deptId;
}
