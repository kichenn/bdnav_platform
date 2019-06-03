package com.bdxh.servicepermit.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Description:  查询角色许可条件dto
* @Author: Kang
* @Date: 2019/6/1 14:29
*/
@Data
public class ServiceRoleQueryDto extends Query {

    @ApiModelProperty("角色名称")
    private String name;
}
