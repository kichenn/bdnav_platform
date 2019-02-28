package com.bdxh.school.helper.excel.exceldto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExcelBeanDto<E> {

    private List<E> excelBeans;
}
