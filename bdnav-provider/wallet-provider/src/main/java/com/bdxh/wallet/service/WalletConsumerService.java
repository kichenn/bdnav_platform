package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Service
public interface WalletConsumerService extends IService<WalletConsumer> {

	/**
	 *查询所有数量
	 */
 	Integer getWalletConsumerAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelWalletConsumerInIds(List<Long> id);

	/**
	 * 删除消费记录
	 */
    Boolean delWalletConsumer(String schoolCode, String cardNumber, Long id);

	/**
	 * 条件分页查询消费记录
	 * @param queryWalletConsumerDto
	 * @return
	 */
	PageInfo<WalletConsumerVo> findWalletConsumerByCondition(QueryWalletConsumerDto queryWalletConsumerDto);

	/**
	 * 根据id查询单条消费记录
	 * @return
	 */
	WalletConsumer findWalletConsumerById(String schoolCode, String cardNumber, String id);
}