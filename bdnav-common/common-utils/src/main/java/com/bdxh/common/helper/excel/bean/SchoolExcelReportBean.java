package com.bdxh.common.helper.excel.bean;

import com.bdxh.common.helper.excel.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 所需导出的列信息
 * @Author: Kang
 * @Date: 2019/2/27 18:43
 */
@Data
public class SchoolExcelReportBean implements Serializable {

    private static final long serialVersionUID = -4043191344338749266L;

    @ExcelField(title = "序号", order = 1)
    private Long id;

    @ExcelField(title = "学校编码", order = 1)
    private String schoolCode;

    @ExcelField(title = "学校名称", order = 1)
    private String schoolName;

    @ExcelField(title = "创建时间", order = 1)
    private String createDate;

    @ExcelField(title = "省份", order = 1)
    private String province = "";

    @ExcelField(title = "城市", order = 1)
    private String city = "";

    @ExcelField(title = "区/县", order = 1)
    private String areaOrcounty = "";

    @ExcelField(title = "学校类型", order = 1)
    private String schoolTypeValue;

    @ExcelField(title = "教职工人数", order = 1)
    private Integer teacherNums;

    @ExcelField(title = "学生数", order = 1)
    private Integer studentNums;

}
