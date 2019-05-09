package com.bdxh.product.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-05-08 19:21:01
*/
@Data
@Table(name = "t_product_image")
public class ProductImage implements Serializable {

    private static final long serialVersionUID = -6138757438943729177L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 应用id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 图片地址
	 */
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 图片名称
	 */
	@Column(name = "image_name")
	private String imageName;

	/**
	 * 图片顺序
	 */
	@Column(name = "sort")
	private Byte sort;

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
	@Column(name = "operator")
	private Long operator;

	/**
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}