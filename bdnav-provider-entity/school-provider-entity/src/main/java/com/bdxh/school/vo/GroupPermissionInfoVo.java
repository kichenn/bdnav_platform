package com.bdxh.school.vo;

import com.bdxh.school.entity.GroupPermission;
import lombok.Data;

/**
 * @Description: 门禁组信息详情展示
 * @Author: Kang
 * @Date: 2019/4/18 12:22
 */
@Data
public class GroupPermissionInfoVo extends GroupPermission {

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 组名称(部门名称或者系名称)
     */
    private String groupName;
}
