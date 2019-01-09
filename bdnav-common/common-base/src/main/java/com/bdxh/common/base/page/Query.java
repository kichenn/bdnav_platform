package com.bdxh.common.base.page;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询实体基类
 * @author: xuyuan
 * @create: 2019-01-09 18:21
 **/
@Data
public class Query implements Serializable {

    private static final long serialVersionUID = -4809752093459439802L;

    /**
     * 页码
     */
    private Integer pageNum=1;

    /**
     * 页面大小
     */
    private Integer pageSize=15;

}
