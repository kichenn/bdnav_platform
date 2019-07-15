package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Service
public interface WalletRechargeService extends IService<WalletRecharge> {

	/**
	 *查询所有数量
	 */
 	Integer getWalletRechargeAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelWalletRechargeInIds(List<Long> id);

	/**
	 * 删除充值记录
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
    Boolean delWalletRecharge(String schoolCode, String cardNumber, Long id);

	/**
	 * 根据条件分页查询充值记录
	 * @param queryWalletRechargeDto
	 * @return
	 */
	PageInfo<WalletRechargeVo> findWalletRechargeByCondition(QueryWalletRechargeDto queryWalletRechargeDto);

	/**
	 * 根据id查询单条记录
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
    WalletRecharge findWalletRechargeById(String schoolCode, String cardNumber, Long id);
}
