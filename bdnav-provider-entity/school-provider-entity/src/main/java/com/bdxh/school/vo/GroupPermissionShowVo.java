package com.bdxh.school.vo;


import com.bdxh.school.entity.GroupPermission;
import lombok.Data;

/**
 * @Description: 门禁组列表vo
 * @Author: Kang
 * @Date: 2019/4/18 15:13
 */
@Data
public class GroupPermissionShowVo extends GroupPermission {

    /**
     * 学校名称
     */
    private String schoolName;
}
