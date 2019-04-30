package com.bdxh.school.vo;

import com.bdxh.school.entity.SchoolDevice;
import lombok.Data;

@Data
public class SchoolDeviceShowVo extends SchoolDevice {

    /**
     * 学校名称
     */
    private String schoolName;
}
