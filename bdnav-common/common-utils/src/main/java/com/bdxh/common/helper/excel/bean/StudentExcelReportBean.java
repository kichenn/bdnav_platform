package com.bdxh.common.helper.excel.bean;

import com.bdxh.common.helper.excel.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 学生导出所需要的列信息
 * @author: binzh
 * @create: 2019-05-13 15:47
 **/
@Data
public class StudentExcelReportBean implements Serializable {

    private static final long serialVersionUID = 7879120052558778496L;

    @ExcelField(title = "学生姓名", order = 1)
    private String name;

    @ExcelField(title = "学号", order = 1)
    private String cardNumber;

    @ExcelField(title = "学校名称", order = 1)
    private String schoolName;

    @ExcelField(title = "学院名称", order = 1)
    private String collegeName="";

    @ExcelField(title = "系名称", order = 1)
    private String facultyName="";

    @ExcelField(title = "专业名称", order = 1)
    private String professionName="";

    @ExcelField(title = "年级名称", order = 1)
    private String gradeName="";

    @ExcelField(title = "班级名称", order = 1)
    private String className="";

    @ExcelField(title = "家庭住址", order = 1)
    private String adress="";

    @ExcelField(title = "出生日期", order = 1)
    private String birth;

    @ExcelField(title = "学生性别", order = 1)
    private String gender;

    @ExcelField(title = "手机号", order = 1)
    private String phone;

    @ExcelField(title = "身份证", order = 1)
    private String idcard="";

    @ExcelField(title = "是否激活", order = 1)
    private String activate;

}