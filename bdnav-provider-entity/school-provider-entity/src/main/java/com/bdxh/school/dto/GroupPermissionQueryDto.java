package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.GroupTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-03-27 10:00:24
*/
@Data
public class GroupPermissionQueryDto extends Query {

	@ApiModelProperty("学校id")
	private Long schoolId;

	@ApiModelProperty("学校编码")
	private String schoolCode;

	@ApiModelProperty("用户群类型 1 学生 2 老师")
	private GroupTypeEnum groupTypeEnum;

	@ApiModelProperty("部门id")
	private Long groupId;

	@ApiModelProperty("设备编码")
	private String deviceCode;

}