package com.bdxh.wallet.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.wallet.entity.WalletRecharge;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-07-10 18:36:58
*/
@Repository
public interface WalletRechargeMapper extends Mapper<WalletRecharge> {

	/**
	 *查询总条数
	 */
	 Integer getWalletRechargeAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delWalletRechargeInIds(@Param("ids") List<Long> ids);
}
