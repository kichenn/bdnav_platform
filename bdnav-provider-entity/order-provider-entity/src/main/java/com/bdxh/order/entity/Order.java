package com.bdxh.order.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;


/**
* @Description: 实体类
* @Date 2019-05-06 11:48:20
*/

@Data
@Table(name = "t_order")
public class Order {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
/*	@ApiModelProperty("订单号")*/
	private Long OrderNo;

	@Column(name = "third_order_no")
/*	@ApiModelProperty("第三方订单号")*/
	private String ThirdOrderNo;

	@Column(name = "school_id")
/*	@ApiModelProperty("学校主键")*/
	private Long SchoolId;

	@Column(name = "school_code")
/*	@ApiModelProperty("学校编码")*/
	private String SchoolCode;

	@Column(name = "school_name")
	@ApiModelProperty("学校名称")
	private String SchoolName;

	@Column(name = "user_id")
	@ApiModelProperty("家长主键")
	private Long UserId;

	@Column(name = "card_number")
	@ApiModelProperty("学号")
	private String CardNumber;

	@Column(name = "user_name")
	@ApiModelProperty("用户姓名")
	private String UserName;

	@Column(name = "user_type")
	@ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
	private Integer UserType;

	@Column(name = "open_id")
	@ApiModelProperty("用户openid")
	private String OpenId;

	@Column(name = "total_money")
	@ApiModelProperty("订单总金额")
	private BigDecimal TotalMoney;

	@Column(name = "order_money")
	@ApiModelProperty("订单金额")
	private BigDecimal OrderMoney;

	@Column(name = "pay_money")
	@ApiModelProperty("支付金额")
	private BigDecimal PayMoney;

	@Column(name = "trade_status")
	@ApiModelProperty("交易状态 1 进行中 2 已取消 3 已删除 4 交易成功")
	private Integer TradeStatus;

	@Column(name = "pay_status")
	@ApiModelProperty("支付状态 1 未支付 2 支付中 3 支付成功 4 支付失败")
	private Integer PayStatus;

	@Column(name = "business_status")
	@ApiModelProperty("业务状态 1 未处理 2 已处理")
	private Integer BusinessStatus;

	@Column(name = "business_type")
	@ApiModelProperty("业务类型 1 校园钱包充值  2管控服务")
	private Integer BusinessType;

	@Column(name = "pay_type")
	@ApiModelProperty("支付渠道 1 微信支付")
	private Integer PayType;

	@Column(name = "trade_type")
	@ApiModelProperty("订单类型  1 JSAPI支付")
	private Integer TradeType;

	@Column(name = "pay_time")
	@ApiModelProperty("支付时间")
	private Date PayTime;

	@Column(name = "pay_end_time")
	@ApiModelProperty("支付结束时间")
	private Date PayEndTime;

	@Column(name = "product_id")
	@ApiModelProperty("产品主键")
	private String ProductId;

	@Column(name = "create_date")
	@ApiModelProperty("创建时间")
	private Date CreateDate;

	@Column(name = "update_date")
	@ApiModelProperty("修改时间")
	private Date UpdateDate;

	@Column(name = "operator")
	@ApiModelProperty("操作人")
	private Long Operator;

	@Column(name = "operator_name")
	@ApiModelProperty("操作人姓名")
	private String OperatorName;

	@Column(name = "remark")
	@ApiModelProperty("备注")
	private String Remark;



}