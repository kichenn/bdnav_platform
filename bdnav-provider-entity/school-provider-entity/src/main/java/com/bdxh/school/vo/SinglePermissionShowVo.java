package com.bdxh.school.vo;

import com.bdxh.school.entity.SinglePermission;
import lombok.Data;

/**
 * @Description: 列表展示dto
 * @Author: Kang
 * @Date: 2019/4/30 11:33
 */
@Data
public class SinglePermissionShowVo extends SinglePermission {

    /**
     * 学校名称
     */
    private String schoolName;
}
