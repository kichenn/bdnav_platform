package com.bdxh.common.helper.excel.exceldto;

import lombok.Data;

import java.util.List;

@Data
public class ExcelBeanDto<E> {



    /**
     * 导出的list集合（已确定导出的列）
     */
    private List<E> excelBeans;

}
