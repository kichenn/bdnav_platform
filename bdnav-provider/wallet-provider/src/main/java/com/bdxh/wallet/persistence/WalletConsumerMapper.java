package com.bdxh.wallet.persistence;

import java.math.BigDecimal;
import java.util.List;

import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.vo.WalletConsumerVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: 消费记录的Mapper
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Repository
public interface WalletConsumerMapper extends Mapper<WalletConsumer> {

	/**
	 *查询总条数
	 */
	 Integer getWalletConsumerAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delWalletConsumerInIds(@Param("ids") List<Long> ids);

	/**
	 * 条件+分页查询消费记录
	 * @param walletConsumerDto
	 * @return
	 */
    List<WalletConsumer> findWalletConsumerByCondition(@Param("walletConsumerDto") QueryWalletConsumerDto walletConsumerDto);

	/**
	 * 根据平台订单号查询单条消费记录
	 * @param schoolCode
	 * @param cardNumber
	 * @param orderNo
	 * @return
	 */
	WalletConsumer findWalletConsumerById(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber,@Param("orderNo") String orderNo);

	/**
	 * 根据订单号多条消费记录查询
	 * @param orderNos
	 * @return
	 */
    List<WalletConsumer> findWalletConsumerByOrderNos(@Param("orderNos")List<Long> orderNos);

	/**
	 * 查询单个学校或者所有学校消费总金额
	 * @param schoolCode
	 * @return
	 */
	BigDecimal queryAllConsumerMoney(String schoolCode);
}
