package com.bdxh.wallet.persistence;

import java.util.List;

import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.entity.WalletConsumer;
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
	 * @param walletConsumer
	 * @return
	 */
    List<WalletConsumer> findWalletConsumerByCondition(@Param("walletConsumer") QueryWalletConsumerDto walletConsumer);

	/**
	 * 根据id查询单条消费记录
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	WalletConsumer findWalletConsumerById(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber,@Param("id") String id);
}
