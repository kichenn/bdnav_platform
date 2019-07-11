package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.WalletConsumer;
import org.springframework.stereotype.Service;
import java.util.List;

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

}
