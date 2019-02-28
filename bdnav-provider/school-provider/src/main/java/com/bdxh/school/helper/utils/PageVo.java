package com.bdxh.school.helper.utils;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: pageVo
 * @Author: Kang
 * @Date: 2019/2/28 16:05
 */
@Data
public class PageVo<T> {
    @ApiModelProperty("当前页码")
    private int pageNum;

    @ApiModelProperty("每页大小")
    private int pageSize;

    @ApiModelProperty("数据总数")
    private Long total;

    @ApiModelProperty("总页数")
    private int pages;


    @ApiModelProperty("当前页数据")
    private List<T> list = new ArrayList<T>();


    public PageVo<T> init(Page<T> page) {
        this.list = page.getResult();
        this.total = page.getTotal();
        this.pageSize = page.getPageSize();
        this.pageNum = page.getPageNum();
        this.pages = page.getPages();
        return this;
    }

}
