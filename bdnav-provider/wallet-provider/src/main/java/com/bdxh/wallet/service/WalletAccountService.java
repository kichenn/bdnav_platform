package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.entity.WalletAccount;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Service
public interface WalletAccountService extends IService<WalletAccount> {

	/**
	 *查询所有数量
	 */
 	Integer getWalletAccountAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelWalletAccountInIds(List<Long> id);

	/**
	 * 带条件查询
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<WalletAccount> findWalletAccountInCondition(Map<String,Object> param, Integer pageNum, Integer pageSize);

	/**
	 * 删除账户
	 */
	Boolean delWalletAccount(String schoolCode,String cardNumber, Long id);

}
