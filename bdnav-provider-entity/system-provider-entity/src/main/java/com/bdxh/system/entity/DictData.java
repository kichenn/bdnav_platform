package com.bdxh.system.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_dict_data")
public class DictData {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 字典类型id
     */
    @Column(name = "dict_id")
    private Long dictId;

    /**
     * 字典名称
     */
    @Column(name = "data_name")
    private String dataName;

    /**
     * 字典值
     */
    @Column(name = "data_value")
    private String dataValue;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取字典类型id
     *
     * @return dict_id - 字典类型id
     */
    public Long getDictId() {
        return dictId;
    }

    /**
     * 设置字典类型id
     *
     * @param dictId 字典类型id
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    /**
     * 获取字典名称
     *
     * @return data_name - 字典名称
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * 设置字典名称
     *
     * @param dataName 字典名称
     */
    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    /**
     * 获取字典值
     *
     * @return data_value - 字典值
     */
    public String getDataValue() {
        return dataValue;
    }

    /**
     * 设置字典值
     *
     * @param dataValue 字典值
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue == null ? null : dataValue.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public Long getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(Long operator) {
        this.operator = operator;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}