package com.bdxh.system.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
    private String name;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private Integer order;

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
     * @return name - 字典名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字典名称
     *
     * @param name 字典名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取字典值
     *
     * @return value - 字典值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置字典值
     *
     * @param value 字典值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * 获取排序
     *
     * @return order - 排序
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * 设置排序
     *
     * @param order 排序
     */
    public void setOrder(Integer order) {
        this.order = order;
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