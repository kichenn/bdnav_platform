package com.bdxh.common.base.constant;

import com.bdxh.common.base.enums.ErrorCodeEnum;
import com.bdxh.common.base.exception.BusinessException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 阿里短信
 * @author: xuyuan
 * @create: 2018-12-17 14:25
 **/
public class AliyunSmsConstants {

	public interface taobaoSms {
		String domain = "http://gw.entity.taobao.com/router/rest";
		String accessKeyId = "23331537";
		String accessKeySecret = "2108f6f83545d7ed15e3aabf4f04484a";
	}

	/**
	 * 短信模板枚举
	 */
	public enum SmsTempletEnum {

		TEMPLATE_身份验证("TEMPLATE_SELF_AUTH", "SMS_6390432", "身份验证","code,product"),
		TEMPLATE_博学派("TEMPLATE_LIBERAL SCHOOL", "SMS_152461236","博学派" ,"code");

		/**
		 * 短信类型
		 */
		private String busType;

		/**
		 * 模板code
		 */
		private String templetCode;

		/**
		 * 短信签名
		 */
		private String signName;

		/**
		 * 参数
		 */
		private String smsParamName;

		/**
		 * 根据模板code获取枚举值
		 * @param templateCode
		 * @return
		 */
		public static SmsTempletEnum getEnumByTemplateCode(String templateCode) {
			SmsTempletEnum smsTempletEnum = null;
			for (SmsTempletEnum ele : SmsTempletEnum.values()) {
				if (ele.getTempletCode().equals(templateCode)) {
					smsTempletEnum = ele;
					break;
				}
			}
			return smsTempletEnum;
		}

		/**
		 * 根据短信类型获取枚举值
		 * @param busType
		 * @return
		 */
		public static SmsTempletEnum getEnumByBusType(String busType) {
			SmsTempletEnum smsTempletEnum = null;
			for (SmsTempletEnum ele : SmsTempletEnum.values()) {
				if (ele.getBusType().equals(busType)) {
					smsTempletEnum = ele;
					break;
				}
			}
			return smsTempletEnum;
		}

		/**
		 * 根据模板code判断短信模板是否存在
		 * @param smsTemplateCode
		 * @return
		 */
		public static boolean isSmsTemplate(String smsTemplateCode) {

			if (StringUtils.isEmpty(smsTemplateCode)) {
				throw new BusinessException(ErrorCodeEnum.UAC10011020);
			}
			List<String> templetCodeList = getTemplateCodeList();

			return templetCodeList.contains(smsTemplateCode);
		}

		public static List<SmsTempletEnum> getList() {
			return Arrays.asList(SmsTempletEnum.values());
		}

		public static List<String> getTemplateCodeList() {
			List<String> templetCodeList = Lists.newArrayList();
			List<SmsTempletEnum> list = getList();
			for (SmsTempletEnum templetEnum : list) {
				if (StringUtils.isEmpty(templetEnum.getTempletCode())) {
					continue;
				}
				templetCodeList.add(templetEnum.getTempletCode());
			}
			return templetCodeList;
		}

		SmsTempletEnum(String busType, String templetCode,String signName,String smsParamName) {
			this.busType = busType;
			this.templetCode = templetCode;
			this.signName=signName;
			this.smsParamName = smsParamName;
		}

		public String getBusType() {
			return busType;
		}

		public void setBusType(String busType) {
			this.busType = busType;
		}

		public String getTempletCode() {
			return templetCode;
		}

		public void setTempletCode(String templetCode) {
			this.templetCode = templetCode;
		}

		public String getSignName() {
			return signName;
		}

		public void setSignName(String signName) {
			this.signName = signName;
		}

		public String getSmsParamName() {
			return smsParamName;
		}

		public void setSmsParamName(String smsParamName) {
			this.smsParamName = smsParamName;
		}
	}

}
