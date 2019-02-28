package com.bdxh.school.helper.excel.exceldto;

import lombok.Data;

import java.util.List;

@Data
public class ExcelBeanDto<E> {


    /**
     * 是否全部导出
     */
    private Boolean isBy = false;

    /**
     * 导出页(非全部导出时)
     */
    private Integer pageSize = 1;

    /**
     * 导出每页的行(非全部导出时)
     */
    private Integer limit = 15;

    /**
     * 导出的list集合（已确定导出的列）
     */
    private List<E> excelBeans;

}
