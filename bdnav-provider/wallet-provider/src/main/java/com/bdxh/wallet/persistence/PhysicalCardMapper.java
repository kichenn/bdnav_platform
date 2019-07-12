package com.bdxh.wallet.persistence;

import java.util.List;

import com.bdxh.wallet.entity.PhysicalCard;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Repository
public interface PhysicalCardMapper extends Mapper<PhysicalCard> {

	/**
	 *查询总条数
	 */
	 Integer getPhysicalCardAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delPhysicalCardInIds(@Param("ids") List<Long> ids);

	/**
	 * 根据实体卡号修改信息
	 * @param physicalCard
	 * @return
	 */
	Integer modifyInfoByPhysicalCard(@Param("physicalCard") PhysicalCard physicalCard);
}
