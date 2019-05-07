package com.bdxh.servicepermit.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
* @Description: 实体类
* @Date 2019-04-26 10:26:58
*/
@Data
@Table(name = "t_service_used")
public class ServiceUsed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ServiceId;

	@Column(name = "order_no")
	private Long OrderNo;

	@Column(name = "product_id")
	private Long ProductId;

	@Column(name = "school_id")
	private Long SchoolId;

	@Column(name = "school_code")
	private String SchoolCode;

	@Column(name = "school_name")
	private String SchoolName;

	@Column(name = "family_id")
	private Long FamilyId;

	@Column(name = "card_number")
	private Long CardNumber;

	@Column(name = "family_name")
	private String FamilyName;

	@Column(name = "start_time")
	private Date StartTime;

	@Column(name = "end_time")
	private Date EndTime;

	@Column(name = "status")
	private Integer Status;

	@Column(name = "type")
	private Integer Type;

	@Column(name = "create_date")
	private Date CreateDate;

	@Column(name = "update_date")

	private Date UpdateDate;

	@Column(name = "operator")

	private Long Operator;

	@Column(name = "operator_name")
	private String OperatorName;

	@Column(name = "remark")
	private String Remark;


}