package com.bdxh.wallet.persistence;

import java.util.List;

import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Repository
public interface WalletRechargeMapper extends Mapper<WalletRecharge> {

	/**
	 *查询总条数
	 */
	 Integer getWalletRechargeAllCount();

    /**
     * 批量删除方法
     */
    Integer delWalletRechargeInIds(@Param("ids") List<Long> ids);

    /**
     * 根据我方订单号，修改相关信息
     *
     * @param walletRecharge
     * @return
     */
    Integer modifyWalletRechargeByOrderNo(@Param("walletRecharge") WalletRecharge walletRecharge);

	/**
	 * 根据条件查询
	 * @param queryWalletRecharge
	 * @return
	 */
    List<WalletRecharge>  findWalletRechargeByCondition(@Param("queryWalletRecharge")QueryWalletRechargeDto queryWalletRecharge);

	/**
	 * 查询不同充值类型下充值成功的总金额
	 * @param schoolCode
	 * @return
	 */
	List<WalletRecharge> findWalletRechargeTypeMoneySum(@Param("schoolCode") String schoolCode);

	/**
	 * 根据多个订单号查询充值记录列表
	 * @Author: WanMing
	 * @Date: 2019/7/22 15:00
	 */
    List<WalletRecharge> queryWalletRechargeByOrderNos(@Param("orderNos") List<Long> orderNos);
}
