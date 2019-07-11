package com.bdxh.wallet.persistence;

import java.util.List;

import com.bdxh.wallet.entity.WalletConsumer;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
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
}
