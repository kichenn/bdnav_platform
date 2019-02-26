package com.bdxh.school.configration.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @Author: Kang
* @Date: 2019/2/26 10:52
*/
@Data
public class PageVo<T> implements Serializable {
    @ApiModelProperty("当前页码")
    private int page;

    @ApiModelProperty("每页大小")
    private int size;

    @ApiModelProperty("数据总数")
    private Long total;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty("当前页数据")
    private List<T> list;


    public PageVo<T> init(org.springframework.data.domain.Page<T> page) {
        this.list = page.getContent();
        this.total = page.getTotalElements();
        this.size = page.getSize();
        this.page = page.getNumber();
        this.pages = page.getTotalPages();
        return this;
    }

    public PageVo<T> init(com.github.pagehelper.Page<T> page) {
        this.list = page.getResult();
        this.total = page.getTotal();
        this.size = page.getPageSize();
        this.page = page.getPageNum() - 1;
        this.pages = page.getPages();
        return this;
    }
}
